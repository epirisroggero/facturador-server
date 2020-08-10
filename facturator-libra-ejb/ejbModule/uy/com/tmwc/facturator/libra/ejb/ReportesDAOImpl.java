package uy.com.tmwc.facturator.libra.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.classic.Session;

import uy.com.tmwc.facturator.entity.ListaPrecios;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.PreciosVenta;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.libra.entity.Preciosventa;
import uy.com.tmwc.facturator.libra.entity.TipoCambio;
import uy.com.tmwc.facturator.libra.util.BooleanDozerConverter;
import uy.com.tmwc.facturator.mapper.Mapper;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.CatalogDAOService;
import uy.com.tmwc.facturator.spi.ReportesDAO;

/**
 * Session Bean implementation class ReportesService
 */
@Stateless
public class ReportesDAOImpl extends ServiceBase implements ReportesDAO {

	@PersistenceContext
	EntityManager em;

	@EJB
	UsuariosService usuariosService;

	@EJB
	CatalogDAOService catalogDAOService;

	/**
	 * Default constructor.
	 */
	public ReportesDAOImpl() {
	}

	public List<Object[]> stockPrecio(final String[] listas, String tipoFiltro, final String proveedores,
			final String familias, final String desde, final String hasta, boolean filtrarCeros,
			boolean filtrarNegativos) {
		// TODO
		// el deposito q sea parametro (prio ?) - asociado a Ernesto

		// XXX
		// ignorar articulos q no estan en stock
		// esta bien la fecha q estoy usando?
		// con iva o sin iva?

		// XXX parametros!!!

		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		final StringBuilder sb = new StringBuilder("SELECT  a.artId,  a.artNombre,  a.prvIdArt, "
				+ "  a.familiaId,  sa.SACantidad,  a.ArtCostoUtilidad, a.ArtCostoFecha, "
				+ "  a.ArtCosto, a.MndIdArtCosto");

		for (int i = 0; i < listas.length; i++) {
			sb.append(", artpr" + i + ".ArtPrecio" + ", artpr" + i + ".mndIdPrecio" + ", pv" + i
					+ ".PrecioVentaUtilidad" + ", pv" + i + ".PrecioVentaPorcentaje");
		}

		if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
				|| Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
			sb.append(", artpr.ArtPrecio" + ", artpr.mndIdPrecio");
		}

		sb.append(" ")
				.append("FROM " + "Articulos a "
						+ "LEFT JOIN StockActual sa on a.artId = sa.artIdSA and a.empId = sa.empId and sa.depIdSa = ")
				.append(1); // XXX cable

		if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
				|| Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
			sb.append(" LEFT JOIN articulos4 artpr on artpr.precioBaseId = \"01\"").append(
					" and artpr.empId = a.empId and artpr.artId = a.artId"); // XXX cable precioBaseId==01
		}

		for (int i = 0; i < listas.length; i++) {
			sb.append(" LEFT JOIN Preciosventa pv").append(i).append(" ON pv").append(i).append(".precioVentaId = ")
					.append(listas[i]).append(" and pv").append(i).append(".empId = a.empId");
			sb.append(" LEFT JOIN articulos4 artpr").append(i).append(" ON artpr").append(i)
					.append(".precioBaseId = pv").append(i).append(".precioBaseId").append(" and artpr").append(i)
					.append(".empId = a.empId").append(" and artpr").append(i).append(".artId = a.artId");

		}

		sb.append(" where a.empId = ").append('\'').append(getEmpId()).append('\'');
		sb.append(" and a.artActivo = \'S\' ");
		sb.append(" and a.artListaPrecios = \'S\' ");
		
		String decision;
		if ("Familia".equals(tipoFiltro)) {
			decision = "a.familiaId";
			if (familias != null) {
				sb.append(" AND a.familiaId in (" + familias + ")");
			}
		} else if ("Proveedor".equals(tipoFiltro)) {
			decision = "a.prvIdArt";
			if (proveedores != null) {
				sb.append(" AND a.prvIdArt in (" + proveedores + ")");
			}
		} else {// "Codigo"
			decision = "a.artId";
		}
		if (desde != null) {
			sb.append(" AND UPPER(" + decision + ") >= UPPER(?)");
		}
		if (hasta != null) {
			sb.append(" AND UPPER(" + decision + ") <= UPPER(?)");
		}

		if (filtrarCeros || filtrarNegativos) {
			sb.append(" and sa.SACantidad ");
			if (filtrarNegativos && filtrarCeros) {
				sb.append(" > 0");
			} else if (filtrarNegativos) {
				sb.append(" >= 0");
			} else { // filtrarZero
				sb.append(" <> 0");
			}
		}
		sb.append(" order by a.familiaId, a.artNombre");

		Session session = (Session) em.getDelegate();
		@SuppressWarnings("deprecation")
		Connection conn = session.connection();

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sb.toString());
			int param = 1;
			if (desde != null)
				stmt.setString(param++, desde);
			if (hasta != null)
				stmt.setString(param++, hasta);

			ResultSet resultSet = stmt.executeQuery();

			ArrayList<Object[]> returnList = new ArrayList<Object[]>();

			while (resultSet.next()) {
				Object[] returnTupla;
				if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
						|| Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
					returnTupla = new Object[8 + (listas.length + 1) * 3];
				} else {
					returnTupla = new Object[4 + (listas.length + 1) * 3];
				}

				int i = 0;
				returnTupla[i++] = resultSet.getString(1);
				returnTupla[i++] = resultSet.getString(2);
				returnTupla[i++] = resultSet.getString(3);
				returnTupla[i++] = resultSet.getString(4);
				returnTupla[i++] = resultSet.getBigDecimal(5);

				Date fechaCosto = resultSet.getDate(7);

				if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
						|| Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
					returnTupla[i++] = resultSet.getBigDecimal(8);
					returnTupla[i++] = resultSet.getString(9);
					returnTupla[i++] = fechaCosto;
				}

				for (int j = 0; j < listas.length; j++) {
					int base = 10 + j * 4;
					BigDecimal precio = null;
					BigDecimal precioBase = resultSet.getBigDecimal(base);
					if (precioBase != null) {
						precio = Preciosventa.calcularPrecio(precioBase,
								BooleanDozerConverter.toBooleanValue(resultSet.getString(base + 2)),
								resultSet.getBigDecimal(6), resultSet.getBigDecimal(base + 3));
					}
					returnTupla[i++] = precio;
					returnTupla[i++] = resultSet.getString(base + 1);
					returnTupla[i++] = fechaCosto;
				}

				if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
						|| Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
					BigDecimal precioBase = resultSet.getBigDecimal(10 + listas.length * 4);
					returnTupla[i++] = precioBase;
					returnTupla[i++] = resultSet.getString(10 + (listas.length * 4) + 1);
					returnTupla[i++] = fechaCosto;
				}

				returnList.add(returnTupla);
			}

			return returnList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<uy.com.tmwc.facturator.entity.TipoCambio> getTipoCambios() {
		@SuppressWarnings("unchecked")
		List<TipoCambio> list = em.createQuery("select tc from TipoCambio tc").getResultList();
		ArrayList<uy.com.tmwc.facturator.entity.TipoCambio> result = new ArrayList<uy.com.tmwc.facturator.entity.TipoCambio>();
		for (TipoCambio tipoCambio : list) {
			result.add(new uy.com.tmwc.facturator.entity.TipoCambio(tipoCambio.getId().getDia(), new Mapper().map(
					tipoCambio.getMoneda(), Moneda.class), tipoCambio.getComercial()));
		}
		return result;
	}

	public List<uy.com.tmwc.facturator.entity.TipoCambio> getUltimosTipoCambios() {
		@SuppressWarnings("unchecked")
		List<TipoCambio> result = em.createNamedQuery("TipoCambio.ultimosTC").setParameter("empId", getEmpId())
				.getResultList();
		return Mapper.mapList(result, uy.com.tmwc.facturator.entity.TipoCambio.class);
	}

	public List<ListaPrecios> getListasPrecios() {
		List<PreciosVenta> pvs = catalogDAOService.getCatalog(PreciosVenta.class);
		ArrayList<ListaPrecios> lps = new ArrayList<ListaPrecios>();
		for (PreciosVenta preciosventa : pvs) {
			ListaPrecios lp = new ListaPrecios(String.valueOf(preciosventa.getCodigo()), preciosventa.getNombre());
			lps.add(lp);
		}
		return lps;
	}

}
