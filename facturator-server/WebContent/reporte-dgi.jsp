
<%
	String anioStr = request.getParameter("anio");
	String mesStr = request.getParameter("mes");
	boolean hasOutput = false;
	if (anioStr != null && mesStr != null) {
		int anio = Integer.parseInt(anioStr);
		int mes = Integer.parseInt(mesStr);
		javax.naming.InitialContext ctx = new javax.naming.InitialContext();
		uy.com.tmwc.facturator.rapi.ReportesService service = (uy.com.tmwc.facturator.rapi.ReportesService) ctx.lookup("facturator-server-ear/ReportesServiceImpl/local");
		/*service.generarReporteDGI(mes, anio)*/;
		hasOutput = true;
	}
%>
<%
	
%><html>
<body>
	<form action="reporte-dgi.jsp" method="POST">
		<label>Anio</label> <input type="number" min="2000" max="2040"
			name="anio" required
			<%=hasOutput ? "value='" + anioStr + "'" : ""%>> <label>Mes</label>
		<input type="number" name="mes" min="1" max="12" required
			<%=hasOutput ? "value='" + mesStr + "'" : ""%>> <input
			type="submit" value="Enviar">
	</form>
	<%=hasOutput ? "<a href='output.dbf'>Descargar reporte</a>" : ""%>
	<br>
	<%=hasOutput ? "<a href='output.txt'>Detalles generacion reporte</a>" : ""%>
</body>
</html>