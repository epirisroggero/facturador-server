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
        <tr>
            <td align="center" bgcolor="#FDFDFD" style="padding: 10px 0 10px 0; border:solid 1px #cfcfcf; border-bottom:none;">
                <img src="cid:image"/>
            </td>
        </tr>
        <tr>
            <td bgcolor="#efefef" style="padding: 40px 30px 40px 30px; border:solid 1px #cfcfcf; border-top:none; border-bottom:none;">
				<p>${bodyText}</p>
            </td>
        </tr>
        <tr>
            <td bgcolor="#fff" style="padding: 20px 0px 0px 0px; border:solid 1px #cfcfcf; border-top:none;"">
            	<img src="cid:footer" />
            </td>
        </tr>
    </table>

</body>
</html>