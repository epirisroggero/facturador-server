<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 16px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="640" style="border-collapse: collapse;">
        <tr style="background-color:#FDFDFD;">
            <td align="center" bgcolor="#FDFDFD" style="padding: 10px 0 10px 0; border:solid 1px #cfcfcf; border-bottom:none;">
                <img src="cid:image"/>
            </td>
        </tr>
        <tr>
            <td bgcolor="#efefef" style="padding: 40px 30px 40px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
                <p>${cliente}</p>
                <p>Estimado cliente,</p>
                <p>Muy buenos días, a continuación le envío su recibo Nro. <b>${serieNro}</b> por el pago de <b>${total}</b>, nota de crédito de la misma y los detalles de facturas de pago según surgen del sistema contable de FULLTIME.</p>
			    <p>Si en algún punto tiene alguna duda o no está de acuerdo en algo, no dude en comunicarse conmigo, estoy para servirle.<br>Quedando cordialmente a su disposición.</p>
				
				<p>${bodyText}</p>
				
				<p>Muchas gracias</p>
            </td>
        </tr>
        <tr style="background-color:#FDFDFD;">
            <td bgcolor="#FDFDFD" align="center" style="padding: 20px 0px 0px 0px; border:solid 1px #cfcfcf; border-top:none;"">
            	<img src="cid:footer" />
            </td>
        </tr>
    </table>

</body>
</html>