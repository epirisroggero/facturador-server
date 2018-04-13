<%@page import="java.util.Date"%><%@
page import="java.text.SimpleDateFormat"%><%@
page import="java.io.File"
%><% 

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String desdeStr = request.getParameter("desde");
	String hastaStr = request.getParameter("hasta");
	String compsIncluidosStr = request.getParameter("compsIncluidos");
	String compsExcluidosStr = request.getParameter("compsExcluidos");
	boolean hasOutput = false;
	if (desdeStr != null && hastaStr != null) {
		Date desde = sdf.parse(desdeStr);
		Date hasta = sdf.parse(hastaStr);
		javax.naming.InitialContext ctx = new javax.naming.InitialContext();
		uy.com.tmwc.facturator.rapi.ReportesService service = (uy.com.tmwc.facturator.rapi.ReportesService)ctx.lookup("facturator-server-ear/ReportesServiceImpl/local");
		String[] compsIncluidos = parseList(compsIncluidosStr);
		String[] compsExcluidos = parseList(compsExcluidosStr);
		service.generarLiquidacionVendedores(desde, hasta, compsIncluidos, compsExcluidos);
		hasOutput = true;
	}
%><% 
%><html>
	<body>
		<form method="POST" >
			<label>Desde</label>
			<input type="date" name="desde" required <%= desdeStr != null ? "value='" + desdeStr + "'" : "" %>>
			<label>Hasta</label>
			<input type="date"  name="hasta" required <%= hastaStr != null? "value='" + hastaStr + "'" : "" %>>
			<label>Comprobantes Incluidos (vacio es todos)</label>
			<input type="text" name="compsIncluidos" value='<%= (compsIncluidosStr != null ? compsIncluidosStr : "") %>'>
			<label>Comprobantes excluidos (vacio es ninguno)</label>
			<input type="text"  name="compsExcluidos" value='<%= (compsExcluidosStr != null ? compsExcluidosStr : "") %>' >
			
			<input type="submit" value="Enviar">
		</form>
		<% 
		if (hasOutput) {
			File outputDir = new File(System.getProperty("jboss.home.url").substring(6)
					+ "/server/default/deploy/facturator-server-ear.ear/facturator-server.war/liquidacion/output");
			File[] csvs = outputDir.listFiles();
			for (File csv : csvs) {
		%>
		<a href='output/<%= csv.getName() %>'>Descargar <%= csv.getName() %></a>
		<br>
		<%   } %>
		<% } %>
	</body>
</html><%!

private String[] parseList(String input) {
	if (input == null || input.trim().length() == 0) {
		return null;
	} 
	String[] comps = input.replace(" ", "").split(",");
	return comps;
}

%>