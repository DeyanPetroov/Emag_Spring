<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  	
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>eMAG</title>
</head>
<body>
	<c:set var="productId" value="${product.productID}" />
	<img src="download/${picture}" width = "150px" height = "150px"> <br>
 	${product.brand}<br>
	${product.model}<br>
	${product.price}<br>
	<form action="${pageContext.request.contextPath}/cart" method="POST">
		<input type="hidden" name="productID" value="${product.productID}">
		<input type="number" name="quantity" min=1 placeholder="quantity"
			required><br> <input type="submit" value="Add to cart">
	</form>
	<form action="${pageContext.request.contextPath}/favourite"
		method="POST">
		<input type="hidden" name="favouriteProduct"
			value="${product.productID}"> <input type="submit"
			value="&#9829;">
	</form>
	<a href = "${pageContext.request.contextPath}/index">Go to the main page</a>	
</body>
</html>