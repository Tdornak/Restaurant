<%-- 
    Document   : confirmationPage
    Created on : Sep 15, 2014, 7:39:36 PM
    Author     : tdornak
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="model.MenuItem"%>
<%@page import="java.text.NumberFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <link href="../build/web/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="RestaurantOrderingProject.css" rel="stylesheet" type="text/css"/>
        <title>Confirmation Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div>
            <h1>Thank you for your order</h1>
            <p>you ordered... </p>
        
                <c:forEach var="item" items="${order}" varStatus="rowCount">
                    <br>${item}
                </c:forEach>
     
           
        </div>
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="RestaurantOrderingProject.js" type="text/javascript"></script>
    </body>
</html>
