<%-- 
    Document   : menuPage
    Created on : Sep 13, 2014, 9:02:33 PM
    Author     : Tim
--%>

<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    /**
     * tests if the list exists, not needed because we get here from another page. (the servlet has 
     * already run
     * 
     * 
     */
    Object obj = request.getAttribute("menuItems");
    if (obj == null) {
       response.sendRedirect("mainServlet?action=getOriginalList");
    } 
%>
<!DOCTYPE html>
<html>
    <head>
        <link href="../build/web/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="RestaurantOrderingProject.css" rel="stylesheet" type="text/css"/>
        <title>Menu Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div>
            <form id="orderingForm" name="menuForm" method="POST" action="mainServlet?action=processOrder">
                <div class="checkbox">
                    
                    <c:forEach var="item" items="${menuItems}" varStatus="rowCount">
                        <label><input type="checkbox" name="menuItemSelected" value="${item.name}">
                            ${item.name}, <fmt:formatNumber type="Currency">${item.cost}</fmt:formatNumber><br>
                        </label>
                    </c:forEach>
                        
                </div>
                <input type="submit" name="submit" value="Submit">
            </form>
        </div>
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="RestaurantOrderingProject.js" type="text/javascript"></script>
    </body>
</html>
