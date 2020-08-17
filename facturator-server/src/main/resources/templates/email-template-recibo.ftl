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
            						<td style="font-size:1.3em;font-weight:bold; color:blue;" >${usuario.nombre!""}</td>
            					</tr>
            					<tr>
            						<td style="font-size:1.1em;font-weight:bold; color:#666666;" >Cobranzas Fulltime</td>
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