<#ftl encoding='UTF-8'>

<#-- @ftlvariable name="usuario" type="uy.com.tmwc.facturator.entity.Usuario" -->


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es-ES">
	<head>	
	    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	
	    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	
	    <style>
	        body {
	            font-family: 'Roboto', sans-serif;
	            font-size: 1.1em;
	        }
	    </style>
	</head>
	<body style="margin: 0; padding: 0;">
	    <table align="center" border="0" cellpadding="0" cellspacing="0" width="640" style="border-collapse: collapse;">
	        <tr style="background-color:#FFFFFF;">
	            <td align="right" bgcolor="#fdfdfd" style="padding: 10px 0 10px 0; border:solid 1px #cfcfcf; border-bottom:none;">
	                <img src="cid:header" width="800" height="162"/>
	            </td>
	        </tr>
	        <tr>
	            <td bgcolor="#efefef" style="padding: 40px 30px 40px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
	                <p>${cliente}</p>
	                
	                <p>Estimado cliente:</p>
 
					<span style="font-size: .9em;"><p>Este es un correo no replay (modo No Responder) por lo que agradecemos que si desea comunicarse con nosotros, aplique un doble click en la dirección de e-mail  que luce al pie de la firma.</p></span>
	                					
					<p>${bodyText}</p>
	            </td>
	        </tr>
	        <tr>
				<td bgcolor="#efefef" style="padding: 10px 30px 10px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
					<img src="cid:documento" width="750" height="676"/>
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
			    						<td style="font-size:1.4em;font-weight:bold; color:#293a9b;" >${usuario.nombre!""}</td>
			    					</tr>
			    					<#if usuario.usuCargo?? >
			    					<tr style>
			    						<td style="font-size:1.1em;font-weight:bold; color:#666666; padding-bottom:10px;">${usuario.usuCargo!""}</td>
			    					</tr>
			    					</#if>
			    					<tr>
			    						<td style="font-size:0.9em;font-weight:normal; color:#000000;">            							
			    							<span>Teléfono: 22044772</span>
			    						</td>
			    					</tr>
			    					<#if usuario.usuCelular?? >
			    					<tr>
			    						<td style="font-size:0.9em; font-weight:normal; color:#000000;">
			    							<span>Celular:</span>
			    							<a href="https://api.whatsapp.com/send?phone=59892153629" target="_blank">${usuario.usuCelular!""}</a>
			    						</td>
			    					</tr>
			    					</#if>
			    					<#if usuario.usuEmail?? >
			    					<tr>
			    						<td style="font-size:0.9em;font-weight:normal; color:#000000;">
			    							<span>Correo:</span>
			    							<a href="mailto:cobranzas@fulltimeuy.com">${usuario.usuEmail!""}</a>
			    						</td>
			    					</tr>
			    					</#if>
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