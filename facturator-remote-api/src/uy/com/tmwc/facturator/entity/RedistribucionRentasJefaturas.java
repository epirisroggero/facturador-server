package uy.com.tmwc.facturator.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class RedistribucionRentasJefaturas {
	private Collection<Jefatura> jefaturas;
	private Map<Vendedor, SortedMap<Jefatura, Collection<RedistribucionRentaLinea>>> cobranzasJefes = new HashMap();

	private Map<Vendedor, SortedMap<Vendedor, SortedMap<Jefatura, Collection<RedistribucionRentaLinea>>>> pagosVendedor = new HashMap();

	private Collection<RedistribucionRentaLinea> redistribuciones = new ArrayList();

	public RedistribucionRentasJefaturas(Collection<IAportaRenta> aportacionesRenta, Collection<Jefatura> jefaturas) {
		this.jefaturas = jefaturas;

		for (IAportaRenta aportadorRenta : aportacionesRenta) {
			Collection<Vendedor> participantes = getParticipantes(aportadorRenta);
			Collection<LineaDocumento> lineas = aportadorRenta.getLineas();
			for (LineaDocumento linea : lineas) {
				if (linea.getArticulo() == null) {
					continue;
				}
				for (Jefatura jefatura : jefaturas)
					if (jefatura.aplica(linea.getArticulo(), participantes)) {
						RedistribucionRentaLinea rrl = new RedistribucionRentaLinea(linea, aportadorRenta, jefatura);
						addJefe(rrl);
						addAllVendedores(rrl);
						this.redistribuciones.add(rrl);
					}
			}
		}
	}

	private Collection<Vendedor> getParticipantes(IAportaRenta aportadorRenta) {
		Collection<ParticipacionVendedor> pv = aportadorRenta.getParticipaciones();
		ArrayList nruter = new ArrayList();
		for (ParticipacionVendedor participacionVendedor : pv) {
			nruter.add(participacionVendedor.getVendedor());
		}
		return nruter;
	}

	private void addJefe(RedistribucionRentaLinea rrl) {
		Jefatura jefatura = rrl.getJefatura();
		Vendedor jefe = jefatura.getJefe();
		SortedMap entry1 = (SortedMap) this.cobranzasJefes.get(jefe);
		if (entry1 == null) {
			entry1 = new TreeMap();
			this.cobranzasJefes.put(jefe, entry1);
		}
		Collection entry2 = (Collection) entry1.get(jefatura);
		if (entry2 == null) {
			entry2 = new ArrayList();
			entry1.put(jefatura, entry2);
		}
		entry2.add(rrl);
	}

	private void addAllVendedores(RedistribucionRentaLinea rrl) {
		Jefatura jefatura = rrl.getJefatura();
		Vendedor jefe = jefatura.getJefe();
		IAportaRenta aportadorRenta = rrl.getAportadorRenta();
		Collection<ParticipacionVendedor> participaciones = aportadorRenta.getParticipaciones();
		for (ParticipacionVendedor participacionVendedor : participaciones) {
			Vendedor vendedor = participacionVendedor.getVendedor();
			SortedMap entry1 = (SortedMap) this.pagosVendedor.get(vendedor);
			if (entry1 == null) {
				entry1 = new TreeMap();
				this.pagosVendedor.put(vendedor, entry1);
			}
			SortedMap entry2 = (SortedMap) entry1.get(jefe);
			if (entry2 == null) {
				entry2 = new TreeMap();
				entry1.put(jefe, entry2);
			}
			Collection entry3 = (Collection) entry2.get(jefatura);
			if (entry3 == null) {
				entry3 = new ArrayList();
				entry2.put(jefatura, entry3);
			}
			entry3.add(rrl);
		}
	}

	public Collection<Vendedor> getJefes() {
		HashSet col = new HashSet();
		for (Jefatura jefatura : this.jefaturas) {
			col.add(jefatura.getJefe());
		}
		return col;
	}

	public SortedMap<Jefatura, Collection<RedistribucionRentaLinea>> getCobra(Vendedor jefe) {
		return (SortedMap) this.cobranzasJefes.get(jefe);
	}

	public SortedMap<Vendedor, SortedMap<Jefatura, Collection<RedistribucionRentaLinea>>> getPaga(Vendedor vendedor) {
		return (SortedMap) this.pagosVendedor.get(vendedor);
	}

	public Collection<RedistribucionRentaLinea> getRedistribuciones() {
		return this.redistribuciones;
	}
}