<%-- 
    Document   : index
    Created on : Sep 13, 2014, 2:42:50 PM
    Author     : Tim
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="RestaurantOrderingProject.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restaurant Ordering Project</title>   
    </head>
    
    <body>
        
        <!-- jumbo-tron -->
    <div class="container">
        <div class="jumbotron">
            <h1>Restaurant Ordering Project</h1>  
        </div>  
        <div>
            <form class="form-inline" id="orderForm" name="orderNow" method="POST" 
                  action="mainServlet?action=getOriginalList" role="form">
                <button type="submit" class="btn btn-primary" id="btnOrderNow">Order Now!</button>
            </form>
        </div>
    </div>
        
        
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="../build/web/modernizr-2.6.2.js" type="text/javascript"></script>
        <script src="../build/web/bootstrap.min.js" type="text/javascript"></script>
        <script src="RestaurantOrderingProject.js" type="text/javascript"></script>
    </body>
</html>
