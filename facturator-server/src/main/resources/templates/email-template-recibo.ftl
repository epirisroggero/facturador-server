<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Cobranzas Fulltime Uruguay</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 1.1em;
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
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="450" style="border-collapse: collapse;">
        <tr bgcolor="#efefef">
            <td align="right" bgcolor="#FDFDFD" style="padding: 10px 0 10px 0; border:solid 1px #cfcfcf; border-bottom:none;">
                <img src="cid:header" width="800" height="162"/>
            </td>
        </tr>
        <tr>
            <td bgcolor="#efefef" style="padding: 40px 30px 20px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
                <p>${cliente}</p>
                <p>Estimado cliente,</p>
                <p>Muy buenos días, a continuación le envío su recibo Nro. <b>${serieNro}</b> por el pago de <b>${total}</b>, nota de crédito de la misma y los detalles de facturas de pago según surgen del sistema contable de FULLTIME.</p>
			    <p>Si en algún punto tiene alguna duda o no esté de acuerdo en algo, no dude en comunicarse conmigo, estoy para servirle.<br>Quedando cordialmente a su disposición.</p>

				<span style="font-size: .9em;"><p>Este es un correo no replay (modo No Responder) por lo que agradecemos que si desea comunicarse con nosotros, aplique un doble click en la dirección de e-mail  que luce al pie de la firma.</p></span>

				<p>Muchas gracias</p>
				
            </td>
        </tr>
        
        <tr>
			<td bgcolor="#efefef" style="padding: 10px 30px 10px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
				<img src="cid:documento" />
			</td>
		</tr>	        
		
		<#if tieneNCF??>
		    <tr>
				<td bgcolor="#efefef" style="padding: 10px 30px 10px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
					<img src="cid:notaCredito" />
				</td>
			</tr>	      
		</#if>  
		
		<#if facturasPendientes??>
		<tr>
			<td bgcolor="#efefef" style="padding: 10px 30px 10px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
				<p style="margin-top: 30px; font-weight: bold;">FACTURAS PENDIENTES</p>
				<table class="featureInfo" align="left" width="100%" style="margin: 10px 0 30px 0; border-spacing: 0px; background-color:#FFFFFF;">
			      <tr>
			      	<th style="text-align: left;">Fecha</th>
			        <th style="text-align: left;">Comprobante</th>
			        <th style="text-align: left;">Nro.</th>
			        <th style="text-align: left;">Moneda</th>
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
			     
				</table>
			</td>
		<tr>
		</#if> 

        <tr bgcolor="#fdfdfd">
            <td bgcolor="#fdfdfd" align="right" style="padding: 10px 0px 10px 0px; border:solid 1px #cfcfcf; border-top:none;">
            	<table align="center" border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
            		<tr>
            			<td align="left" style="padding: 0;" bgcolor="#FFFFFF">
                            <img src="cid:photo"  width="132" height="132"/>
            			</td>	
            			<td align="left" style="padding: 0px 10px 0px 20px;" bgcolor="#FFFFFF">
            				<table width="220px" style="border-left:solid 2px #cfcfcf; padding-left: 10px;" >
            					<tr>
            						<td style="font-size:1.3em;font-weight:bold; color:blue;" >
            							<span>${usuario.nombre!""}</span>
            						</td>
            					</tr>
            					<tr>
            						<td style="font-size:1.1em;font-weight:bold; color:#666666;" >
            							<span>${usuario.usuCargo!""}</span>
            						</td>
            					</tr>
            					<tr>
            						<td style="font-size:1.0em;font-weight:normal; color:#000000;">            							
            							<span>Teléfono: 2204.4772</span>
            						</td>
            					</tr>
            					<tr>
            						<td style="font-size:1.0em; font-weight:normal; color:#000000;">
            							<span>WhatsApp:</span>
            							<a href="https://api.whatsapp.com/send?phone=59899681586" target="_blank">099.681.586</a>
            						</td>
            					</tr>
            					<tr>
            						<td style="font-size:1.0em;font-weight:normal; color:#000000;">
            							<span>Correo:</span>
            							<a href="mailto:cobranzasfulltimeuru@gmail.com">${usuario.usuEmail!"cobranzasfulltimeuru@gmail.com"}</a>
            						</td>
            					</tr>
            					<tr>
            						<td style="font-size:1.0em;font-weight:normal; padding-top:5px; color:#000000;">
            							<a href="http://www.fulltime.com.uy" target="_blank">www.fulltime.com.uy</a>
            						</td>
            					</tr>
            				</table>
            			</td>	
            			<td align="right" style="padding: 0;" bgcolor="#FFFFFF" >
                            <img src="cid:footer" />
            			</td>
            		</tr>
            	</table>
            </td>
        </tr>
    </table>

</body>
</html>