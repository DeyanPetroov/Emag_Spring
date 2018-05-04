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
	<table align="center">
	<tr>
		<th align="center">Brand</th>
		<th align="center">Model</th>
		<th align="center">Description</th>
		<th align="center">Price</th>
		<th align="center">Discount percent</th>
		<th align="center">Discount expiration date</th>
		<th align="center">Profile picture</th>
	</tr>
		<c:forEach var="product" items="${products}">
			<tr>			
				<td align="center">${product.brand}</td>
				<td align="center">${product.model}</td>
				<td align="center">${product.description}</td>
				<td align="center">${product.price} BGN</td>
			 	<td align="center">${product.discountPercent}</td>	
			 	<td align="center">${product.discountExpiration}</td>			
				<td align="center">${product.productPicture}</td>
				<td align="center"><c:set var="productId" value="${product.product_id}" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>