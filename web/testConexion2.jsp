<%-- 
    Document   : index
    Created on : 26 sep. 2022, 17:09:46
    Author     : fruiz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.restaurante.conexion.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>No tienes permiso para estar aqui</h1>
        <a href="${pageContext.servletContext.contextPath}/Principal">Regresar</a>
    </body>
</html>

