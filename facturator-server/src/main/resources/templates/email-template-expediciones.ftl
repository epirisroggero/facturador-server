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
	            font-size: 1.2em;
	        }
	    </style>
	</head>
	<body style="margin: 0; padding: 0;">
    	<table align="center" style="border-collapse: collapse;width:500px; border:solid 1px gray;" bgcolor="#efefef" >
            <tbody>
                <tr bgcolor="#efefef" style="padding-bottom:10px; border-bottom:solid 1px gray;height:60px;">
                    <td style="padding: 5px; font-size:1.4em;"><span>TAREA</span></td>
                    <td style="padding: 5px; font-size:1.4em;"><span>${nombre!""}</span></td>
                </tr>
	            <#if fecha?? >
                <tr style="height:45px;">
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Fecha</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${fecha!""}</span></td>
                </tr>
	            </#if>

	            <#if contacto?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Contacto</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${contacto!""}</span></td>
                </tr>
                </#if>

	            <#if usuarioAsignado?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Usuario Asignado</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${usuarioAsignado!""}</span></td>
                </tr>
                </#if>

	            <#if fechaInicio?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Fecha Inicio</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${fechaInicio!""}</span></td>
                </tr>
                </#if>

	            <#if fechaFin?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Fecha Fin</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${fechaFin!""}</span></td>
                </tr>
                </#if>

	            <#if prioridad?? >
                <tr style="height:45px;">
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Prioridad</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${prioridad!""}</span></td>
                </tr>
                </#if>

	            <#if solicitante?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Solicitante</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${solicitante!"no tiene"}</span></td>
                </tr>
                </#if>

	            <#if supervisor1?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Supervisores</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${supervisor1!"no tiene"}</span></td>
                </tr>
                </#if>
                <#if supervisor2?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span></span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${supervisor2!"no tiene"}</span></td>
                </tr>
                </#if>
                <#if supervisor3?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span></span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${supervisor3!"no tiene"}</span></td>
                </tr>
                </#if>

	            <#if vinculo??>
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Vínculo</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${vinculo!""}</span></td>
                </tr>
                </#if>

	            <#if descripcion?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Descripción</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${descripcion!""}</span></td>
                </tr>
                </#if>

	            <#if notas?? >
                <tr>
                    <td style="padding: 5px 15px 5px 15px; font-size:1.1em;"><span>Notas</span></td>
                    <td style="padding: 5px; font-size:1.1em;"><span>${notas!""}</span></td>
                </tr>
                </#if>
            </tbody>
        </table>
	</body>
</html>