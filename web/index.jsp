<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <title id="title">Paideia Software</title>
        <link href="styles/principalI1.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script lang="JavaScript" type="text/JavaScript" src="scripts/funciones.js"></script>
    </head>
    <body onload="initCaptcha()">
        <div id="holder">																																																																																																																																																																																																																																																																											
            <div id="top">
                <div id="name_part_1" >MEDICAL <span class="orange">H</span>ISTORY</div>
                <div id="name_part_2" ><span class="green">S</span>YSTEM</div>	

            </div>
            <div id="contenido">

                <form name='form' action='j_spring_security_check' method='POST'>
                <output id="error" style="color: red" >

                    <% if(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {%>
                        <div class="errorblock">
                                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                                <br/>
                                <% 
                                    String error = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString();
                                    if(!error.matches(".*clave.*")) {%>
                                        <a href="registro/compra.xhtml">Activación</a>
                                <%}%>
                        </div>
                    <%}%>
                </output>
                
                <center>
                <table>
                    <tr>
                        <td>Usuario: </td>
                        <td><input type='text' name='j_username' value=''></td>
                    </tr>
                    <tr>
                       <td>Clave:</td>
                       <td><input type='password' name='j_password'/></td>
                    </tr>
                    <tr>
                        <td><output id="verificador" style="color: blue" ></output> </td>
                        <td><input type="text" id="v_verificador" size="2"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input name="button" type="button" value="Enviar" onclick="validarCatchap()"/> </td>
                    </tr>
               </table>
                </center>
               </form>

            </div>
            <!--Contenido-->
            <!--Pie -->
            <div id="bottom">											
                    Paideia Software. <br/> 
            Teléfono: 990855380 <br/>
            Pasaje Colon 102. CAYMA <br/>
            AREQUIPA PERÚ
                    <br/>
                    <iframe src="https://www.facebook.com/plugins/like.php?href=http://perumedical-vbrothers.rhcloud.com"
                    scrolling="no" frameborder="0"
                    style="border:none; width:450px; height:80px"></iframe>
            </div>
            <!--End Pie-->
        </div>
    </body>
</html>
