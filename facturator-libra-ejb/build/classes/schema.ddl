
    create table articulos (
        ArtId varchar(255) not null,
        EmpId varchar(255) not null,
        GTCIdArt varchar(255),
        ArtAbrevia varchar(255),
        ArtActivo varchar(255),
        ArtAlta date,
        ArtBlob longblob,
        ArtBlobExt varchar(255),
        CategArtId varchar(255),
        ArtCodigoOrigen varchar(255),
        ArtCosto decimal(19,2),
        ArtCostoUtilidad decimal(19,2),
        FamiliaId varchar(255),
        ArtCostoFecha date,
        ArtIdAbrevia varchar(255),
        ArtInventario varchar(255),
        IvaIdArt smallint,
        ArtListaPrecios varchar(255),
        ArtLotes varchar(255),
        MarcaId varchar(255),
        MndIdArtCosto smallint,
        ArtNombre varchar(255),
        ArtNotas longtext,
        ArtPartidaId integer,
        PrvIdArt varchar(255),
        ArtPuntos varchar(255),
        ArtRanking decimal(19,2),
        RetencionIdArt smallint,
        RubIdArtCompras varchar(255),
        RubIdArtProd varchar(255),
        RubIdArtVentas varchar(255),
        TextoIdArt varchar(255),
        UnidadId varchar(255),
        ArtVence varchar(255),
        ArtWeb varchar(255),
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

    create table asientos (
        AsId integer not null unique,
        EjeId smallint not null unique,
        EmpId varchar(10) not null unique,
        AsAnio smallint,
        AsBlob longblob,
        AsBlobExt varchar(3),
        AsConcepto varchar(50) not null,
        AsDia smallint,
        AsDocId integer,
        AsFecha date not null,
        AsImportado varchar(1),
        AsMes smallint,
        MndIdAs smallint,
        AsNotas longtext,
        AsNro integer not null,
        AsOrigen varchar(10),
        AsRegistroFecha date,
        AsRegistroHora varchar(8),
        AsTC decimal(10,7),
        TipAsIdAs varchar(3) not null,
        AsDHlin integer,
        UsuIdAs smallint,
        AsValidado varchar(1),
        primary key (AsId, EjeId, EmpId)
    );

    create table asientos1 (
        DHlin integer not null unique,
        AsId integer not null unique,
        EjeId smallint not null unique,
        EmpId varchar(10) not null unique,
        CenIdAs varchar(3),
        DHconcepto varchar(50),
        DHdebe decimal(10,2) not null,
        DHfecha date,
        DHhaber decimal(10,2) not null,
        LocIdDH smallint not null,
        DHNro integer,
        DHreferencia varchar(10),
        RubIdDH varchar(10),
        primary key (DHlin, AsId, EjeId, EmpId)
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
        CMPTIPO decimal(19,2),
        DEPIDCMPDESTINO smallint,
        DEPIDCMPORIGEN smallint,
        NUMCMPID varchar(255),
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
        CuotaMoneda smallint,
        CuotaSaldo decimal(19,2),
        CuotaTotal decimal(19,2),
        primary key (CuotaId, DocId, EmpId)
    );

    create table docfp (
        DocId integer not null,
        EmpId varchar(255) not null,
        FPId smallint not null,
        FormaPagoId smallint,
        FPImporte decimal(19,2),
        MndIdFP smallint,
        FPTotal decimal(19,2),
        primary key (DocId, EmpId, FPId)
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

    create table familias (
        EmpId varchar(10) not null unique,
        FamiliaId varchar(10) not null unique,
        FamiliaIdNom varchar(55) not null,
        FamiliaImputable varchar(1),
        FamiliaNom varchar(40) not null,
        primary key (EmpId, FamiliaId)
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
    
    CREATE TABLE `lfx_DescuentoPrometidoComprobante` (
	  `DpcId` bigint(20) NOT NULL AUTO_INCREMENT,
	  `EmpId` char(10) NOT NULL,
	  `cmpid` decimal(3,0) NOT NULL,
	  `descuento` decimal(10,4) NOT NULL,
	  `retraso` int(11) NOT NULL,
	  `categoriaCliente` varchar(3) DEFAULT NULL,
	  PRIMARY KEY (`DpcId`,`EmpId`),
	  UNIQUE KEY (`cmpid`,`EmpId`,`retraso`,`categoriaCliente`)
	); 

    create table lfx_comisionesJefaturas (
        EmpId varchar(10) not null,
        jefaturaId bigint not null,
        vendedorId varchar(3) not null,
        comision decimal(5,2) not null,
        primary key (EmpId, jefaturaId, vendedorId)
    );

    create table lfx_documentos (
        agencia varchar(50),
        cantidadBultos integer,
        costo decimal(10,2),
        costoEstimadoEntrega decimal(10,2),
        costoOperativo decimal(10,2),
        ordenCompra varchar(50),
        ordenVenta varchar(50),
        reparto varchar(50),
        chofer varchar(50),
        nroEnvio varchar(50),
        localidad varchar(50),
        departamento varchar(50),
        dirEntrega varchar(50),
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

    create table lfx_agendatareas (
    	EmpId varchar(10) not null,	
    	AgeId integer not null,
    	fechaInicio date,
    	nroOrden varchar(20),
    	orden integer,
    	textoAdjunto longtext,
        primary key (EmpId, AgeId)
    );
    
    CREATE  TABLE lfx_tareas (
		EmpId VARCHAR(11) NOT NULL ,
		TareaId CHAR(3) NOT NULL ,
		CapituloId CHAR(3) NULL DEFAULT NULL ,
		PRIMARY KEY (EmpId, TareaId) 
	);
	
	CREATE  TABLE lfx_tareacapitulo (
	    EmpId CHAR(11) NOT NULL ,
	    CapituloId CHAR(3) NOT NULL ,
	    Nombre VARCHAR(45) NULL ,
	    Descripcion` TEXT NULL ,
		PRIMARY KEY (EmpId, CapituloId) 
	);
    
    create table lfx_jefaturas (
        tipo char(1) not null,
        EmpId varchar(10) not null,
        jefaturaId bigint not null,
        comisionPorDefecto decimal(5,2) not null,
        jefe_id varchar(3) not null,
        articulo_id varchar(20) not null,
        familia_id varchar(10) not null,
        proveedor_id varchar(10) not null,
        primary key (EmpId, jefaturaId),
        unique (EmpId, familia_id),
        unique (articulo_id, EmpId),
        unique (EmpId, proveedor_id)
    );

    create table lfx_participacionvendedor (
        DocId integer not null,
        EmpId varchar(10) not null,
        VenId varchar(3) not null,
        porcentaje decimal(10,4),
        primary key (DocId, EmpId, VenId)
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

    create table numeradores (
        EmpId varchar(255) not null,
        NumCmpId varchar(255) not null,
        LocIdNum smallint,
        NumCmpNom varchar(255),
        NumCmpNumero bigint,
        NumCmpSerie varchar(255),
        primary key (EmpId, NumCmpId)
    );

    create table planpagos (
        PPid varchar(255) not null,
        EmpId varchar(255) not null,
        PPNom varchar(255),
        PPdecimales varchar(255),
        PPCuotas smallint,
        PPCuotasIguales varchar(255),
        PPDesde varchar(255),
        PPPrimerDia smallint,
        PPPrimerMes smallint,
        PPrecargo decimal(19,2),
        PPSeparacionDia smallint,
        PPSeparacionMes smallint,
        primary key (PPid, EmpId)
    );

    create table planpagoscuotas (
        PPid varchar(255) not null,
        EmpId varchar(255) not null,
        PPCuotaId smallint not null,
        PPCuotaDias smallint,
        PPCuotaMes smallint,
        PPCuotaPorc decimal(19,2),
        primary key (PPid, EmpId, PPCuotaId)
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

    create table rubros (
        EmpId varchar(255) not null,
        RubId varchar(255) not null,
        RubColumna smallint,
        RubDC varchar(255),
        RubDocumentos varchar(255),
        RubFila smallint,
        GruRubId varchar(255),
        RubIdNom varchar(255),
        RubImputable varchar(255),
        RubInflacion varchar(255),
        MndIdRub smallint,
        RubNom varchar(255),
        RubPresentacion varchar(255),
        RubRetencion varchar(255),
        RetencionIdRub smallint,
        RubServicio varchar(255),
        primary key (EmpId, RubId)
    );

    create table tcambio (
        TCdia date not null unique,
        EmpId varchar(10) not null unique,
        MndId smallint not null unique,
        TCimp1 decimal(10,7),
        TCimp2 decimal(10,7),
        primary key (TCdia, EmpId, MndId)
    );

    create table tiposasientos (
        EmpId varchar(10) not null unique,
        TipAsId varchar(3) not null unique,
        TipAsCentro varchar(1) not null,
        TipAsImp varchar(1) not null,
        TipAsNom varchar(40) not null,
        TipAsResumir varchar(1) not null,
        primary key (EmpId, TipAsId)
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
    
    CREATE  TABLE `libra`.`lfx_cotizaciones` (
	  	EmpId CHAR(10) NOT NULL ,
  		Cdia DATE NOT NULL ,
  		dolarComp DECIMAL(14,7) NOT NULL DEFAULT 0,
  		dolarVta DECIMAL(14,7) NOT NULL DEFAULT 0,
  		euroComp DECIMAL(14,7) NOT NULL DEFAULT 0,
  		euroVta DECIMAL(14,7) NOT NULL DEFAULT 0,
  		PRIMARY KEY (EmpId, Cdia) );

    alter table articulos 
        add index FK215DA6F840122ACA (EmpId, MndIdArtCosto), 
        add constraint FK215DA6F840122ACA 
        foreign key (EmpId, MndIdArtCosto) 
        references monedas (EmpId, MndId);

    alter table articulos 
        add index FK215DA6F8D7786CA1 (EmpId, PrvIdArt), 
        add constraint FK215DA6F8D7786CA1 
        foreign key (EmpId, PrvIdArt) 
        references proveedores (EmpId, PrvId);

    alter table articulos 
        add index FK215DA6F8FAB2E884 (EmpId, FamiliaId), 
        add constraint FK215DA6F8FAB2E884 
        foreign key (EmpId, FamiliaId) 
        references familias (EmpId, FamiliaId);

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

    alter table asientos 
        add index FKD8AE7FF8AEBFA7BD (EmpId, MndIdAs), 
        add constraint FKD8AE7FF8AEBFA7BD 
        foreign key (EmpId, MndIdAs) 
        references monedas (EmpId, MndId);

    alter table asientos 
        add index FKD8AE7FF868620D0D (EmpId, TipAsIdAs), 
        add constraint FKD8AE7FF868620D0D 
        foreign key (EmpId, TipAsIdAs) 
        references tiposasientos (EmpId, TipAsId);

    alter table asientos1 
        add index FK3D217F3967B239B2 (AsId, EjeId, EmpId), 
        add constraint FK3D217F3967B239B2 
        foreign key (AsId, EjeId, EmpId) 
        references asientos (AsId, EjeId, EmpId);

    alter table asientos1 
        add index FK3D217F3996812D69 (EmpId, RubIdDH), 
        add constraint FK3D217F3996812D69 
        foreign key (EmpId, RubIdDH) 
        references rubros (EmpId, RubId);

    alter table clientes 
        add index FK362539B968F54AD3 (EmpId, VenIdCli), 
        add constraint FK362539B968F54AD3 
        foreign key (EmpId, VenIdCli) 
        references vendedores (EmpId, VenId);

    alter table clientes 
        add index FK362539B971CB6355 (EmpId, PrecioVentaIdCli), 
        add constraint FK362539B971CB6355 
        foreign key (EmpId, PrecioVentaIdCli) 
        references preciosventa (EmpId, PrecioVentaId);

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
        add index FK48F1723F1FBE1823 (EMPID, NUMCMPID), 
        add constraint FK48F1723F1FBE1823 
        foreign key (EMPID, NUMCMPID) 
        references numeradores (EmpId, NumCmpId);

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
        add index FKCF6D043FD7787776 (EmpId, PrvIdDoc), 
        add constraint FKCF6D043FD7787776 
        foreign key (EmpId, PrvIdDoc) 
        references proveedores (EmpId, PrvId);

    alter table documentos 
        add index FKCF6D043FAA6C1336 (PPidDoc, EmpId), 
        add constraint FKCF6D043FAA6C1336 
        foreign key (PPidDoc, EmpId) 
        references planpagos (PPid, EmpId);

    alter table documentos 
        add index FKCF6D043FF5F6C2A6 (CliIdDoc, EmpId), 
        add constraint FKCF6D043FF5F6C2A6 
        foreign key (CliIdDoc, EmpId) 
        references clientes (CliId, EmpId);

    alter table documentos 
        add index FKCF6D043F3C9E9CFC (CenIdDoc, EmpId), 
        add constraint FKCF6D043F3C9E9CFC 
        foreign key (CenIdDoc, EmpId) 
        references centroscosto (CenId, EmpId);

    alter table documentos 
        add index FKCF6D043F68F54EEB (EmpId, VenIdDoc), 
        add constraint FKCF6D043F68F54EEB 
        foreign key (EmpId, VenIdDoc) 
        references vendedores (EmpId, VenId);

    alter table documentos 
        add index FKCF6D043F71CB676D (EmpId, PrecioVentaIdDoc), 
        add constraint FKCF6D043F71CB676D 
        foreign key (EmpId, PrecioVentaIdDoc) 
        references preciosventa (EmpId, PrecioVentaId);

    alter table documentos 
        add index FKCF6D043FDB9B907 (EmpId, MndIdDoc), 
        add constraint FKCF6D043FDB9B907 
        foreign key (EmpId, MndIdDoc) 
        references monedas (EmpId, MndId);

    alter table documentos 
        add index FKCF6D043F4A22D2FA (CmpIdDoc, EmpId), 
        add constraint FKCF6D043F4A22D2FA 
        foreign key (CmpIdDoc, EmpId) 
        references comprobantes (CMPID, EMPID);

    alter table documentos 
        add index FKCF6D043F4A8E6BBC (CajaId, EmpId), 
        add constraint FKCF6D043F4A8E6BBC 
        foreign key (CajaId, EmpId) 
        references cajas (CajaId, EmpId);

    alter table documentos 
        add index FKCF6D043FE83DE700 (DepIdDocOrigen, EmpId), 
        add constraint FKCF6D043FE83DE700 
        foreign key (DepIdDocOrigen, EmpId) 
        references depositos (DepId, EmpId);

    alter table documentos 
        add index FKCF6D043FA96B0602 (DepIdDocDestino, EmpId), 
        add constraint FKCF6D043FA96B0602 
        foreign key (DepIdDocDestino, EmpId) 
        references depositos (DepId, EmpId);

    alter table lfx_DescuentoPrometidoComprobante 
        add index lfx_DescuentoPrometidoComprobante_comprobante (cmpid, EmpId), 
        add constraint lfx_DescuentoPrometidoComprobante_comprobante 
        foreign key (cmpid, EmpId) 
        references comprobantes (CMPID, EMPID);

    alter table lfx_comisionesJefaturas 
        add index lfx_comisionesJefaturas_vendedores (EmpId, vendedorId), 
        add constraint lfx_comisionesJefaturas_vendedores 
        foreign key (EmpId, vendedorId) 
        references vendedores (EmpId, VenId);

    alter table lfx_comisionesJefaturas 
        add index FK31A2B221C7D2669E (EmpId, jefaturaId), 
        add constraint FK31A2B221C7D2669E 
        foreign key (EmpId, jefaturaId) 
        references lfx_jefaturas (EmpId, jefaturaId);

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

    alter table lfx_jefaturas 
        add index lfx_participacionVendedor_vendedores (EmpId, jefe_id), 
        add constraint lfx_participacionVendedor_vendedores 
        foreign key (EmpId, jefe_id) 
        references vendedores (EmpId, VenId);

    alter table lfx_jefaturas 
        add index lfx_jefaturas_familias (EmpId, familia_id), 
        add constraint lfx_jefaturas_familias 
        foreign key (EmpId, familia_id) 
        references familias (EmpId, FamiliaId);

    alter table lfx_jefaturas 
        add index lfx_jefaturas_proveedores (EmpId, proveedor_id), 
        add constraint lfx_jefaturas_proveedores 
        foreign key (EmpId, proveedor_id) 
        references proveedores (EmpId, PrvId);

    alter table lfx_jefaturas 
        add index lfx_jefaturas_articulos (articulo_id, EmpId), 
        add constraint lfx_jefaturas_articulos 
        foreign key (articulo_id, EmpId) 
        references articulos (ArtId, EmpId);

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

    alter table planpagoscuotas 
        add index FK209896FAC0D15E14 (PPid, EmpId), 
        add constraint FK209896FAC0D15E14 
        foreign key (PPid, EmpId) 
        references planpagos (PPid, EmpId);

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

    alter table rubros 
        add index FKC9277AF7DB9EE4E (EmpId, MndIdRub), 
        add constraint FKC9277AF7DB9EE4E 
        foreign key (EmpId, MndIdRub) 
        references monedas (EmpId, MndId);

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
        
        
    
        
        
