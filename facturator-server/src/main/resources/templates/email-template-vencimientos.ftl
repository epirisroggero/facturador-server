<#ftl encoding='UTF-8'>

<#-- @ftlvariable name="usuario" type="uy.com.tmwc.facturator.entity.Usuario" -->

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es-ES">
	<head>	
	    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	
	    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	
	    <style type="text/css"> 
	        body {
	            font-family: 'Roboto', sans-serif;
	            font-size: 1.2em;
	        }
	    
	    	table.featureInfo, table.featureInfo td, table.featureInfo th { 
	    		border:1px solid #ddd; 
	    		border-collapse:collapse; 
	    		margin:0; 
	    		padding:0; 
	    		font-size: .9em;
	    		padding:.5em .1em; 
	    	} 
	
	    	table.featureInfo th { 
				padding:.5em .2em; 
				text-transform:uppercase; 
				font-weight:bold; 
				background:#e6e6e8; 
			} 
	
			table.featureInfo td{ 
				background:#fff; 
				text-transform:uppercase;
			}
	
			table.featureInfo tr.odd td{ 
				background:#eee; 
			} 
	
			table.featureInfo caption{ 
				text-align:left; 
				font-size:100%; 
				font-weight:bold; 
				text-transform:uppercase; 
				padding:.2em .2em; 
			} 
	   	</style>
	</head>
	<body style="margin: 0; padding: 0;">
	    <table  align="center" border="0" cellpadding="0" cellspacing="0" width="840" style="border-collapse: collapse;">
	        <tr style="background-color:#FFFFFF;">
	            <td align="right" bgcolor="#fdfdfd" style="padding: 10px 0 10px 0; border:solid 1px #cfcfcf; border-bottom:none;">
	                <img src="cid:header" width="800" height="162"/>
	            </td>
	        </tr>

	        <tr>
	            <td bgcolor="#efefef" style="padding: 40px 30px 40px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
	                <p>${cliente}</p>
	                
	                <p>Estimado cliente:</p>
	                
	                <p>Con motivo del vencimiento de una nueva factura, es que le estamos enviando un detalle de sus facturas que al día de hoy nos figuran como pendientes de pago.<br>
En caso de ya haber iniciado una coordinación o haber efectuado el pago, agradecemos desconsiderar este mensaje.<br>
Por el contrario si desea coordinar el pago le agradecemos comunicarse con la persona que luce sus datos debajo en la firma del correo.</p>
					
					<p style="margin-top: 30px; font-weight: bold;">FACTURAS PENDIENTES </p>
					<table class="featureInfo" align="left" width="100%" style="margin-bottom: 30px; border-spacing: 0px; background-color:#FFFFFF;">
				      <tr>
				      	<th style="text-align: left;">Fecha</th>
				        <th style="text-align: left;">Comprobante</th>
				        <th>Nro.</th>
				        <th>Moneda</th>
				        <th style="text-align: left;">F.Pago</th>
				        <th style="text-align: right;">Facturado</th>
				        <th style="text-align: right;">Cancelado</th>
				        <th style="text-align: right;">Adeudado</th>
				        <th style="text-align: right;">Dcto(%)</th>
				        <th style="text-align: right;">A.Neto</th>
				       <tr>
					   <#list pendientes as item>
					   	  <#if item.tieneCuotaVencida>
					   	  	<#assign cuotaVencida = "color:#ae1313;font-weight:bold;">
					   	  <#else>
					   	  	<#assign cuotaVencida = "color:#333333;font-weight:normal;">
					   	  </#if>
					      <tr style="${cuotaVencida}">
					      	<td>${item.fecha}</td>
					        <td>${item.comprobante.nombre}</td>
					        <td>${item.numero?string}</td>
					        <td>${item.moneda.nombre}</td>
					        <td>
					        <#if item.planPago??>
					        	${item.planPago.nombre}
					        </#if>
					        </td>
					        <td style="text-align: right;">${item.facturado?string["0.##"]}</td>
					        <td style="text-align: right;">${item.cancelado?string["0.##"]}</td>
					        <td style="text-align: right;">${item.adeudado?string["0.##"]}</td>
					        <td style="text-align: right;">${item.descuento?string["0"]}%</td>
					        <td style="text-align: right;">${item.adeudadoNeto?string["0.##"]}</td>
					       <tr>
					   </#list>
				      <tr>
				        <td style="background:#333; color: #fff; text-align: right;" colspan="5">TOTAL DOLARES</td>
				        <td style="background:#333; color: #fff; text-align: right;">${totalFacturado?string["0.##"]}</td>
				        <td style="background:#333; color: #fff; text-align: right;">${totalCancelado?string["0.##"]}</td>
				        <td style="background:#333; color: #fff; text-align: right;">${totalAdeudado?string["0.##"]}</td>
				        <td style="background:#333; color: #fff; text-align: right;"></td>
				        <td style="background:#333; color: #fff; text-align: right;">${totalAdeudadoNeto?string["0.##"]}</td>
				       <tr>
					</table>

					<p>Muchas gracias</p>

					<p></p>
					<span style="font-size: .9em;"><p>Este es un correo no replay (modo No Responder) por lo que agradecemos que si desea comunicarse con nosotros, aplique un doble click en la dirección de e-mail que luce al pie de la firma.</p></span>
	            </td>
	        </tr>
	        <tr style="background-color:#FFFFFF;">
	            <td align="center" style="padding: 0px 0px 0px 0px; border:solid 1px #cfcfcf; border-top:none;"">
	            	<img src="cid:formasPago" />
	            </td>
	        </tr>

	        <tr style="background-color:#FFFFFF;" style="padding:0;">
	            <td align="center" style="padding: 0px 0px 0px 0px; border:solid 1px #cfcfcf; border-top:none;"">
                    <table align="center" border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
	            		<tr>
	            			<td align="left" style="padding: 0;" bgcolor="#FFFFFF" style="border:solid 2px #cfcfcf">
	                            <img src="cid:photo" width="132" height="132" />
	            			</td>	
	            			<td align="left" style="padding: 0px 10px 0px 20px;" bgcolor="#FFFFFF">
			    				<table width="220px" style="border-left:solid 2px #cfcfcf; padding-left: 10px;" >
			    					<tr>
			    						<td style="font-size:1.4em;font-weight:bold; color:#3e85c6;">
			    							<span>${usuario.nombre!"Norma Martinez"}</span>
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size:0.9em;font-weight:normal; color:#000000;">            							
			    							<span>${usuario.usuCargo!"Cobranzas Fulltime"}</span>
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size:0.9em;font-weight:normal; color:#000000;">            							
			    							<span>Teléfono: 22044772</span>
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size:0.9em; font-weight:normal; color:#000000;">
			    							<span>WhatsApp:</span>
			    							<a href="https://api.whatsapp.com/send?phone=59899681586" target="_blank">+598 99681586</a>
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size:0.9em;font-weight:normal; color:#000000;">
			    							<span>Correo:</span>
			    							<a href="mailto:vguevara@fulltimeuy.com">${usuario.usuEmail!"cobranzasfulltimeuru@gmail.com"}</a>
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size:1.0em;font-weight:normal; padding-top:5px; color:#000000;">
			    							<a href="http://www.fulltime.com.uy" target="_blank">www.fulltime.com.uy</a>
			    						</td>
			    					</tr>
			    				</table>
	    					</td>
				            <td align="center" style="padding: 20px 0px 0px 0px; border: none;"">
				            	<img src="cid:footer" />
				            </td>
				        </tr>
					</table>
            
            	</td>
			</tr>	

	    </table>
	
	</body>
</html>