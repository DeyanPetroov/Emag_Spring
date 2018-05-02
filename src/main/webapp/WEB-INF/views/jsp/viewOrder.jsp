<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>You finished completed the order!</h3>
	Date: ${order.date} <br>
	Products: <c:forEach var="entry" items="${order.products}">
					${entry.key.brand} ${entry.key.model}
    				Price of product: ${entry.key.price}
   					Quantity: ${entry.value} <br>
   				</c:forEach>
   	Total cost of products : ${order.totalCost}
	<a href = "${pageContext.request.contextPath}/index">Go to the main page</a>	
</body>
</html>