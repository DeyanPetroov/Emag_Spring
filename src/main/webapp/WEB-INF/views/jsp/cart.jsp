<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping cart</title>
</head>
<body>

<h1>Your cart</h1>
<c:if test="${requestScope.cart != null}">
		<c:forEach var="entry" items="${cart.products}">
    		Price of product: ${entry.key.price}
   			Quantity: ${entry.value}
		</c:forEach>
	</c:if>

</body>
</html>