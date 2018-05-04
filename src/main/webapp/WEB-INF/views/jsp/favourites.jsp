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
	<c:if test="${requestScope.favourites != null}">
		<h4>Favourite products</h4>
		<c:forEach items="${favourites}" var="product">
			${product.brand}, 
			${product.model},
			${product.price},
			<form action="favourite" method="POST">
				<input type="hidden" name="favouriteProduct" value="${product.productID}"> 
				<input type="submit" value="Remove">
			</form>
		</c:forEach>
	</c:if>
	<a href = "${pageContext.request.contextPath}/index">Go to the main page</a>
</body>
</html>