
    create table articulos (
        ArtId varchar(255) not null,
        EmpId varchar(255) not null,
        GTCIdArt varchar(255),
        ArtAbrevia varchar(255),
        ArtActivo varchar(255),
        ArtAlta date,
        ArtBlob longblob,
        ArtBlobExt varchar(255),
        ArtCodigoOrigen varchar(255),
        ArtCostoUtilidad decimal(19,2),
        ArtIdAbrevia varchar(255),
        ArtInventario varchar(255),
        ArtListaPrecios varchar(255),
        ArtLotes varchar(255),
        ArtNombre varchar(255),
        ArtNotas longtext,
        ArtPartidaId integer,
        ArtPuntos varchar(255),
        ArtRanking decimal(19,2),
        ArtVence varchar(255),
        ArtWeb varchar(255),
        CategArtId varchar(255),
        ArtCosto decimal(19,2),
        FamiliaId varchar(255),
        ArtCostoFecha date,
        IvaIdArt smallint,
        MarcaId varchar(255),
        MndIdArtCosto smallint,
        PrvIdArt varchar(255),
        RetencionIdArt smallint,
        RubIdArtCompras varchar(255),
        RubIdArtProd varchar(255),
        RubIdArtVentas varchar(255),
        TextoIdArt varchar(255),
        UnidadId varchar(255),
        primary key (ArtId, EmpId)
    );

    create table articulos4 (
        ArtId varchar(20) not null unique,
        EmpId varchar(10) not null unique,
        PrecioBaseId varchar(3) not null unique,
        MndIdPrecio smallint,
        ArtPrecio decimal(10,5),
        PrecioBaseConIVA varchar(1),
        ArtPrecioIVA decimal(10,5),
        primary key (ArtId, EmpId, PrecioBaseId)
    );

    create table cajas (
        CajaId smallint not null,
        EmpId varchar(255) not null,
        CajaNom varchar(255),
        LocIdCaj smallint,
        primary key (CajaId, EmpId)
    );

    create table centroscosto (
        CenId varchar(255) not null,
        EmpId varchar(255) not null,
        CenNom varchar(255),
        primary key (CenId, EmpId)
    );

    create table clientes (
        CliId varchar(255) not null,
        EmpId varchar(255) not null,
        CategCliId varchar(255),
        CliDto1 decimal(19,2),
        CliDto2 decimal(19,2),
        CliDto3 decimal(19,2),
        CliExentoIVA varchar(255),
        CliFichaLocal varchar(255),
        CliIdNom varchar(255),
        CliNombre varchar(255),
        CliPuntos varchar(255),
        CliRanking decimal(19,2),
        CliTopeCredito decimal(19,2),
        CliTopeDias smallint,
        LocIdCli smallint,
        MndIdCli smallint,
        PrecioVentaIdCli smallint,
        RubIdCli varchar(255),
        TextoIdCli varchar(255),
        PPidCli varchar(255),
        VenIdCli varchar(255),
        primary key (CliId, EmpId)
    );

    create table comprobantes (
        CMPID bigint not null,
        EMPID varchar(255) not null,
        CMPNOM varchar(255),
        CmpRetencion varchar(255),
        CmpRubroSi varchar(255),
        CmpSRI varchar(255),
        CMPABREVIA varchar(255),
        CMPASIULT decimal(19,2),
        CMPFICHA varchar(255),
        CMPFIFO varchar(255),
        CMPGASTOS varchar(255),
        CMPIDABREVIA varchar(255),
        CMPIDNOM varchar(255),
        CMPIVA varchar(255),
        CMPLIBROS varchar(255),
        CMPPENDIENTE varchar(255),
        CMPSINSTOCK varchar(255),
        CMPTIPONOM varchar(255),
        FORMAPAGOIDCMP decimal(19,2),
        FORMATOIDCMP varchar(255),
        LOCIDCMP decimal(19,2),
        NUMCMPID varchar(255),
        CMPTIPO decimal(19,2),
        DEPIDCMPDESTINO smallint,
        DEPIDCMPORIGEN smallint,
        primary key (CMPID, EMPID)
    );

    create table contactos (
        CtoId varchar(255) not null,
        EmpId varchar(255) not null,
        CtoActivo varchar(255),
        CtoAlta date,
        CtoBlob longblob,
        CtoBlobExt varchar(255),
        CtoCelular varchar(255),
        CtoCliente varchar(255),
        CtoDireccion varchar(255),
        CtoDocumento varchar(255),
        CtoDocumentoTipo varchar(255),
        CtoEmail1 varchar(255),
        CtoEmail2 varchar(255),
        CtoFax varchar(255),
        CtoLocalidad varchar(255),
        CtoNom varchar(255),
        CtoNombre varchar(255),
        CtoNombreCompleto varchar(255),
        CtoNotas longtext,
        CtoPostal varchar(255),
        CtoProveedor varchar(255),
        CtoRSocial varchar(255),
        CtoRUT varchar(255),
        CtoTelefono varchar(255),
        CtoWeb varchar(255),
        DeptoIdCto varchar(255),
        GirIdCto varchar(255),
        GruCtoId varchar(255),
        LocIdCto smallint,
        OriCtoIdCto varchar(255),
        PaisIdCto varchar(255),
        UsuIdCto smallint,
        ZonaIdCto varchar(255),
        primary key (CtoId, EmpId)
    );

    create table depositos (
        DepId smallint not null,
        EmpId varchar(255) not null,
        DepAbrevia varchar(255),
        DepInventario varchar(255),
        DepNom varchar(255),
        LocIdDep smallint,
        primary key (DepId, EmpId)
    );

    create table doccuotas (
        CuotaId smallint not null,
        DocId integer not null,
        EmpId varchar(255) not null,
        CuotaCtoId varchar(255),
        CuotaCtoTipo varchar(255),
        CuotaEntrega varchar(255),
        CuotaFecha date,
        CuotaSaldo decimal(19,2),
        CuotaTotal decimal(19,2),
        CuotaMoneda smallint,
        primary key (CuotaId, DocId, EmpId)
    );

    create table docfp (
        FPId smallint not null,
        DocId integer not null,
        EmpId varchar(255) not null,
        FPImporte decimal(19,2),
        FPTotal decimal(19,2),
        FormaPagoId smallint,
        MndIdFP smallint,
        primary key (FPId, DocId, EmpId)
    );

    create table docruc (
        DocId integer not null,
        DocRutId smallint not null,
        EmpId varchar(255) not null,
        DocRutCiudad varchar(255),
        DocRutDireccion varchar(255),
        DocRutNombre varchar(255),
        DocRutRUT varchar(255),
        DocRutTelefono varchar(255),
        primary key (DocId, DocRutId, EmpId)
    );

    create table documentos (
        DocId integer not null,
        EmpId varchar(255) not null,
        DocArtId varchar(255),
        DocAsId varchar(255),
        DocAutorizacion varchar(255),
        BancoIdDoc varchar(255),
        DocBlob longblob,
        DocBlobExt varchar(255),
        CajaId smallint,
        CenIdDoc varchar(255),
        CliIdDoc varchar(255),
        CmpIdDoc bigint,
        DocConcepto varchar(255),
        DocConciliado varchar(255),
        DocTC decimal(19,2),
        CuentaId smallint,
        DepIdDocDestino smallint,
        DepIdDocOrigen smallint,
        DocDescuentos decimal(19,2),
        DocDescuentosPorc decimal(19,2),
        DocEmitido varchar(255),
        DocEstado varchar(255),
        DocEstadoFecha date,
        DocFanfold1 varchar(255),
        DocFanfold2 varchar(255),
        DocFanfold3 varchar(255),
        DocFanfold4 varchar(255),
        DocFanfold5 varchar(255),
        DocFecha1 date,
        DocFecha2 date,
        DocIVA decimal(19,2),
        IvaIdDoc smallint,
        LocIdDoc smallint,
        MndIdDoc smallint,
        DocNotas longtext,
        DocNumero decimal(19,2),
        DocOrigen varchar(255),
        DocPendiente varchar(255),
        DocPlan varchar(255),
        PPidDoc varchar(255),
        PrecioVentaIdDoc smallint,
        PrvIdDoc varchar(255),
        DocRedondeo decimal(19,2),
        DocReferencia varchar(255),
        DocRegistroFecha date,
        DocRegistroHora varchar(255),
        RubIdDoc varchar(255),
        DocSaldo decimal(19,2),
        DocSerie varchar(255),
        DocSRI varchar(255),
        DocSubTotal decimal(19,2),
        DocTarjetaNro varchar(255),
        TextoIdDoc varchar(255),
        DocTipo smallint,
        DocTitular varchar(255),
        DocTotal decimal(19,2),
        DocTotalQy decimal(19,2),
        DocUltFP smallint,
        DocUltLin integer,
        UsuIdDoc smallint,
        VenIdDoc varchar(255),
        DocVinculado varchar(255),
        primary key (DocId, EmpId)
    );

    create table formaspago (
        EmpId varchar(255) not null,
        FormaPagoId smallint not null,
        FormaPagoAbrevia varchar(255),
        FormaPagoNom varchar(255),
        FormaPagoTipo varchar(255),
        primary key (EmpId, FormaPagoId)
    );

    create table id (
        EmpId varchar(255) not null,
        IDtabla varchar(255) not null,
        IDultimo integer not null,
        primary key (EmpId, IDtabla)
    );

    create table iva (
        EmpId varchar(255) not null,
        IvaId smallint not null,
        IvaAbrevia varchar(255),
        IvaNom varchar(255),
        RubIdIvaD varchar(255),
        RubIdIvaH varchar(255),
        IvaTasa decimal(19,2),
        IvaTipo varchar(255),
        primary key (EmpId, IvaId)
    );

    create table lfx_DescuentoPrometidoComprobante (
        id bigint not null auto_increment,

        CMPID bigint not null,
        EMPID varchar(255) not null,
        
        cmpid bigint not null,
        descuento decimal(10,4) not null,
        EmpId varchar(10),
        retraso integer not null,
        primary key (id),
        unique (EmpId, cmpid, retraso)
    );

    create table lfx_documentos (
        cantidadBultos integer,
        entrega_id bigint,
        DocId integer not null,
        EmpId varchar(255) not null,
        primary key (DocId, EmpId)
    );

    create table lfx_entrega (
        id bigint not null auto_increment,
        codigo varchar(4) not null unique,
        costo decimal(10,2),
        nombre varchar(50) not null,
        relevancia decimal(10,2),
        primary key (id)
    );

    create table lfx_participacionvendedor (
        id bigint not null auto_increment,
        DocId integer,
        EmpId varchar(10),
        porcentaje decimal(10,4),
        VenId varchar(3),
        primary key (id),
        unique (EmpId, DocId, VenId)
    );

    create table lineas (
        DocId integer not null,
        EmpId varchar(255) not null,
        LinId integer not null,
        LinArmado varchar(255),
        ArtIdLin varchar(20),
        LinCantidad decimal(19,2),
        LinCantidadQy decimal(19,2),
        LinCantidadSaldo decimal(19,2),
        LinConcepto varchar(255),
        LinCosto decimal(19,2),
        DepIdLinDestino smallint,
        DepIdLinOrigen smallint,
        LinDto1 decimal(19,2),
        LinDto2 decimal(19,2),
        LinDto3 decimal(19,2),
        LinDtoImp1 decimal(19,2),
        LinDtoImp2 decimal(19,2),
        LinDtoImp3 decimal(19,2),
        LinFecha date,
        LinItems integer,
        LinIVA decimal(19,2),
        IvaIdLin smallint,
        LocIdLin smallint,
        LoteIdLin varchar(255),
        MndIdLinCosto smallint,
        LinNotas longtext,
        LinPrecio1 decimal(19,2),
        LinPrecio2 decimal(19,2),
        LinPrecio3 decimal(19,2),
        RetencionIdLin smallint,
        RubIdLin varchar(255),
        LinSubTotal decimal(19,2),
        LinTotal decimal(19,2),
        LinTotalQy decimal(19,2),
        LinVence date,
        LinVinculada varchar(255),
        primary key (DocId, EmpId, LinId)
    );

    create table listas (
        EmpId varchar(255) not null,
        ListaId varchar(255) not null,
        ListaCatalogo varchar(255),
        ListaIVA varchar(255),
        ListaOrden varchar(255),
        ListaPie varchar(255),
        ListaSubtitulo varchar(255),
        ListaTitulo varchar(255),
        primary key (EmpId, ListaId)
    );

    create table localescomerciales (
        EmpId varchar(255) not null,
        LocId smallint not null,
        LocCiudad varchar(255),
        LocContacto varchar(255),
        LocDir varchar(255),
        LocEmail varchar(255),
        LocFax varchar(255),
        LocNom varchar(255),
        LocNotas longtext,
        LocTel varchar(255),
        primary key (EmpId, LocId)
    );

    create table monedas (
        EmpId varchar(255) not null,
        MndId smallint not null,
        MndAbrevia varchar(255),
        MndInteres decimal(19,2),
        MndNom varchar(255),
        MndRubDCganancias varchar(255),
        MndRubDCperdidas varchar(255),
        MndSim varchar(255),
        MndTCmax decimal(19,2),
        MndTCmin decimal(19,2),
        MndRedondeo smallint,
        primary key (EmpId, MndId)
    );

    create table planpagos (
        PPid varchar(255) not null,
        EmpId varchar(255) not null,
        PPCuotas smallint not null,
        PPCuotasIguales varchar(255),
        PPDesde varchar(255),
        PPNom varchar(255),
        PPPrimerDia smallint not null,
        PPPrimerMes smallint not null,
        PPSeparacionDia smallint not null,
        PPSeparacionMes smallint not null,
        PPdecimales varchar(255),
        PPrecargo decimal(19,2),
        primary key (PPid, EmpId)
    );

    create table planpagoscuotas (
        PPCuotaId smallint not null,
        PPid varchar(255) not null,
        EmpId varchar(255) not null,
        PPCuotaDias smallint not null,
        PPCuotaMes smallint not null,
        PPCuotaPorc decimal(19,2),
        primary key (PPCuotaId, PPid, EmpId)
    );

    create table preciosbase (
        EmpId varchar(255) not null,
        PrecioBaseId varchar(255) not null,
        PrecioBaseNom varchar(255),
        primary key (EmpId, PrecioBaseId)
    );

    create table preciosventa (
        EmpId varchar(255) not null,
        PrecioVentaId smallint not null,
        PrecioBaseId varchar(255),
        PrecioVentaAbrevia varchar(255),
        PrecioVentaNom varchar(255),
        PrecioVentaPorcentaje decimal(19,2),
        PrecioVentaUtilidad varchar(255),
        PrecioVentaVigencia date,
        primary key (EmpId, PrecioVentaId)
    );

    create table proveedores (
        EmpId varchar(255) not null,
        PrvId varchar(255) not null,
        PPidPrv varchar(255),
        CategPrvId varchar(255),
        LocIdPrv smallint,
        PrvAplicaTopes varchar(255),
        PrvContribuyente smallint,
        PrvDto1 decimal(19,2),
        PrvDto2 decimal(19,2),
        PrvDto3 decimal(19,2),
        PrvIdNom varchar(255),
        PrvIvaInc varchar(255),
        PrvNombre varchar(255),
        PrvRanking decimal(19,2),
        PrvSRIautorizacion varchar(255),
        PrvSRIvencimiento date,
        RetencionIdPrv smallint,
        RubIdPrv varchar(255),
        TextoIdPrv varchar(255),
        primary key (EmpId, PrvId)
    );

    create table tcambio (
        TCdia date not null unique,
        EmpId varchar(10) not null unique,
        MndId smallint not null unique,
        TCimp1 decimal(10,7),
        TCimp2 decimal(10,7),
        primary key (TCdia, EmpId, MndId)
    );

    create table usuarios (
        EmpId varchar(255) not null,
        UsuId smallint not null,
        CenIdUsu varchar(255),
        LocIdUsu smallint,
        PermisoId varchar(255),
        UsuCaducidad date,
        UsuESultimo decimal(19,2),
        UsuHorarioDesde varchar(255),
        UsuHorarioHasta varchar(255),
        UsuNom varchar(255),
        UsuNotas longtext,
        UsuPassword varchar(255),
        UsuTipo varchar(255),
        UsuWebSesion varchar(255),
        primary key (EmpId, UsuId)
    );

    create table vendedores (
        EmpId varchar(255) not null,
        VenId varchar(255) not null,
        LocIdVen smallint,
        VenCobrador varchar(255),
        VenNom varchar(255),
        VenPostventa varchar(255),
        VenVendedor varchar(255),
        primary key (EmpId, VenId)
    );

    create table vinculosdoc (
        DocIdVin1 integer not null,
        DocIdVin2 integer not null,
        EmpId varchar(255) not null,
        VinMonto decimal(19,2) not null,
        primary key (DocIdVin1, DocIdVin2, EmpId)
    );

    alter table articulos 
        add index FK215DA6F840122ACA (EmpId, MndIdArtCosto), 
        add constraint FK215DA6F840122ACA 
        foreign key (EmpId, MndIdArtCosto) 
        references monedas (EmpId, MndId);

    alter table articulos 
        add index FK215DA6F8E6A1977 (EmpId, IvaIdArt), 
        add constraint FK215DA6F8E6A1977 
        foreign key (EmpId, IvaIdArt) 
        references iva (EmpId, IvaId);

    alter table articulos4 
        add index FKA57383CD7D0150 (ArtId, EmpId), 
        add constraint FKA57383CD7D0150 
        foreign key (ArtId, EmpId) 
        references articulos (ArtId, EmpId);

    alter table articulos4 
        add index FKA57383C16A77E3F (EmpId, PrecioBaseId), 
        add constraint FKA57383C16A77E3F 
        foreign key (EmpId, PrecioBaseId) 
        references preciosbase (EmpId, PrecioBaseId);

    alter table articulos4 
        add index FKA57383CB9DA33D1 (EmpId, MndIdPrecio), 
        add constraint FKA57383CB9DA33D1 
        foreign key (EmpId, MndIdPrecio) 
        references monedas (EmpId, MndId);

    alter table clientes 
        add index FK362539B968F54AD3 (EmpId, VenIdCli), 
        add constraint FK362539B968F54AD3 
        foreign key (EmpId, VenIdCli) 
        references vendedores (EmpId, VenId);

    alter table clientes 
        add index FK362539B9AA6C0F1E (PPidCli, EmpId), 
        add constraint FK362539B9AA6C0F1E 
        foreign key (PPidCli, EmpId) 
        references planpagos (PPid, EmpId);

    alter table clientes 
        add index FK362539B93502B841 (CliId, EmpId), 
        add constraint FK362539B93502B841 
        foreign key (CliId, EmpId) 
        references contactos (CtoId, EmpId);

    alter table comprobantes 
        add index FK48F1723FD307FE34 (DEPIDCMPDESTINO, EMPID), 
        add constraint FK48F1723FD307FE34 
        foreign key (DEPIDCMPDESTINO, EMPID) 
        references depositos (DepId, EmpId);

    alter table comprobantes 
        add index FK48F1723F33E8208E (DEPIDCMPORIGEN, EMPID), 
        add constraint FK48F1723F33E8208E 
        foreign key (DEPIDCMPORIGEN, EMPID) 
        references depositos (DepId, EmpId);

    alter table doccuotas 
        add index FK19FB738165E9A796 (DocId, EmpId), 
        add constraint FK19FB738165E9A796 
        foreign key (DocId, EmpId) 
        references documentos (DocId, EmpId);

    alter table doccuotas 
        add index FK19FB7381C8939ECD (EmpId, CuotaMoneda), 
        add constraint FK19FB7381C8939ECD 
        foreign key (EmpId, CuotaMoneda) 
        references monedas (EmpId, MndId);

    alter table docfp 
        add index FK5B5240265E9A796 (DocId, EmpId), 
        add constraint FK5B5240265E9A796 
        foreign key (DocId, EmpId) 
        references documentos (DocId, EmpId);

    alter table docfp 
        add index FK5B524028DA24C17 (EmpId, FormaPagoId), 
        add constraint FK5B524028DA24C17 
        foreign key (EmpId, FormaPagoId) 
        references formaspago (EmpId, FormaPagoId);

    alter table docfp 
        add index FK5B52402AEBFA855 (EmpId, MndIdFP), 
        add constraint FK5B52402AEBFA855 
        foreign key (EmpId, MndIdFP) 
        references monedas (EmpId, MndId);

    alter table docruc 
        add index FKB0EF8A4865E9A796 (DocId, EmpId), 
        add constraint FKB0EF8A4865E9A796 
        foreign key (DocId, EmpId) 
        references documentos (DocId, EmpId);

    alter table documentos 
        add index FKCF6D043F3C9E9CFC (CenIdDoc, EmpId), 
        add constraint FKCF6D043F3C9E9CFC 
        foreign key (CenIdDoc, EmpId) 
        references centroscosto (CenId, EmpId);

    alter table documentos 
        add index FKCF6D043F4A22D2FA (CmpIdDoc, EmpId), 
        add constraint FKCF6D043F4A22D2FA 
        foreign key (CmpIdDoc, EmpId) 
        references comprobantes (CMPID, EMPID);

    alter table documentos 
        add index FKCF6D043FDB9B907 (EmpId, MndIdDoc), 
        add constraint FKCF6D043FDB9B907 
        foreign key (EmpId, MndIdDoc) 
        references monedas (EmpId, MndId);

    alter table documentos 
        add index FKCF6D043F71CB676D (EmpId, PrecioVentaIdDoc), 
        add constraint FKCF6D043F71CB676D 
        foreign key (EmpId, PrecioVentaIdDoc) 
        references preciosventa (EmpId, PrecioVentaId);

    alter table documentos 
        add index FKCF6D043F68F54EEB (EmpId, VenIdDoc), 
        add constraint FKCF6D043F68F54EEB 
        foreign key (EmpId, VenIdDoc) 
        references vendedores (EmpId, VenId);

    alter table documentos 
        add index FKCF6D043F4A8E6BBC (CajaId, EmpId), 
        add constraint FKCF6D043F4A8E6BBC 
        foreign key (CajaId, EmpId) 
        references cajas (CajaId, EmpId);

    alter table documentos 
        add index FKCF6D043FA96B0602 (DepIdDocDestino, EmpId), 
        add constraint FKCF6D043FA96B0602 
        foreign key (DepIdDocDestino, EmpId) 
        references depositos (DepId, EmpId);

    alter table documentos 
        add index FKCF6D043FE83DE700 (DepIdDocOrigen, EmpId), 
        add constraint FKCF6D043FE83DE700 
        foreign key (DepIdDocOrigen, EmpId) 
        references depositos (DepId, EmpId);

    alter table documentos 
        add index FKCF6D043FD7787776 (EmpId, PrvIdDoc), 
        add constraint FKCF6D043FD7787776 
        foreign key (EmpId, PrvIdDoc) 
        references proveedores (EmpId, PrvId);

    alter table documentos 
        add index FKCF6D043FF5F6C2A6 (CliIdDoc, EmpId), 
        add constraint FKCF6D043FF5F6C2A6 
        foreign key (CliIdDoc, EmpId) 
        references clientes (CliId, EmpId);

    alter table documentos 
        add index FKCF6D043FAA6C1336 (PPidDoc, EmpId), 
        add constraint FKCF6D043FAA6C1336 
        foreign key (PPidDoc, EmpId) 
        references planpagos (PPid, EmpId);

    alter table documentos 
        add index FKCF6D043F75EC7088 (EmpId, LocIdDoc), 
        add constraint FKCF6D043F75EC7088 
        foreign key (EmpId, LocIdDoc) 
        references localescomerciales (EmpId, LocId);

    alter table documentos 
        add index FKCF6D043F8F5B1DC3 (EmpId, UsuIdDoc), 
        add constraint FKCF6D043F8F5B1DC3 
        foreign key (EmpId, UsuIdDoc) 
        references usuarios (EmpId, UsuId);

    alter table lfx_DescuentoPrometidoComprobante 
        add index lfx_DescuentoPrometidoComprobante_comprobante (cmpid, EmpId), 
        add constraint lfx_DescuentoPrometidoComprobante_comprobante 
        foreign key (cmpid, EmpId) 
        references comprobantes (CMPID, EMPID);

    alter table lfx_documentos 
        add index FKFD5684E065E9A796 (DocId, EmpId), 
        add constraint FKFD5684E065E9A796 
        foreign key (DocId, EmpId) 
        references documentos (DocId, EmpId);

    alter table lfx_documentos 
        add index FKFD5684E090D4DE66 (entrega_id), 
        add constraint FKFD5684E090D4DE66 
        foreign key (entrega_id) 
        references lfx_entrega (id);

    alter table lfx_participacionvendedor 
        add index lfx_participacionVendedor_vendedores (EmpId, VenId), 
        add constraint lfx_participacionVendedor_vendedores 
        foreign key (EmpId, VenId) 
        references vendedores (EmpId, VenId);

    alter table lfx_participacionvendedor 
        add index lfx_participacionVendedor_documentos (DocId, EmpId), 
        add constraint lfx_participacionVendedor_documentos 
        foreign key (DocId, EmpId) 
        references documentos (DocId, EmpId);

    alter table lineas 
        add index FKBE468F2665E9A796 (DocId, EmpId), 
        add constraint FKBE468F2665E9A796 
        foreign key (DocId, EmpId) 
        references documentos (DocId, EmpId);

    alter table lineas 
        add index FKBE468F2648C8E289 (DepIdLinDestino, EmpId), 
        add constraint FKBE468F2648C8E289 
        foreign key (DepIdLinDestino, EmpId) 
        references depositos (DepId, EmpId);

    alter table lineas 
        add index FKBE468F26D069EF5C (EmpId, MndIdLinCosto), 
        add constraint FKBE468F26D069EF5C 
        foreign key (EmpId, MndIdLinCosto) 
        references monedas (EmpId, MndId);

    alter table lineas 
        add index FKBE468F2660FEDD99 (DepIdLinOrigen, EmpId), 
        add constraint FKBE468F2660FEDD99 
        foreign key (DepIdLinOrigen, EmpId) 
        references depositos (DepId, EmpId);

    alter table lineas 
        add index FKBE468F26BEB1CDA5 (ArtIdLin, EmpId), 
        add constraint FKBE468F26BEB1CDA5 
        foreign key (ArtIdLin, EmpId) 
        references articulos (ArtId, EmpId);

    alter table lineas 
        add index FKBE468F26E6A41A5 (EmpId, IvaIdLin), 
        add constraint FKBE468F26E6A41A5 
        foreign key (EmpId, IvaIdLin) 
        references iva (EmpId, IvaId);

    alter table preciosventa 
        add index FKB91B547F16A77E3F (EmpId, PrecioBaseId), 
        add constraint FKB91B547F16A77E3F 
        foreign key (EmpId, PrecioBaseId) 
        references preciosbase (EmpId, PrecioBaseId);

    alter table proveedores 
        add index FKA91D980C35BCD4D5 (PrvId, EmpId), 
        add constraint FKA91D980C35BCD4D5 
        foreign key (PrvId, EmpId) 
        references contactos (CtoId, EmpId);

    alter table tcambio 
        add index FKA6CC904D6D84730B (EmpId, MndId), 
        add constraint FKA6CC904D6D84730B 
        foreign key (EmpId, MndId) 
        references monedas (EmpId, MndId);

    alter table vinculosdoc 
        add index FKA0DC4BF58380818D (DocIdVin2, EmpId), 
        add constraint FKA0DC4BF58380818D 
        foreign key (DocIdVin2, EmpId) 
        references documentos (DocId, EmpId);

    alter table vinculosdoc 
        add index FKA0DC4BF58380818C (DocIdVin1, EmpId), 
        add constraint FKA0DC4BF58380818C 
        foreign key (DocIdVin1, EmpId) 
        references documentos (DocId, EmpId);
