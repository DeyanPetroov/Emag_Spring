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
	<c:if test="${invalidSession != null }">
		<h4>${invalidSession}</h4>
	</c:if>
	<c:if test="${invalidSession == null }">
	<table>
		<tr>
			<th>Brand</th>
			<th>Model</th>
			<th>Description</th>
			<th>Discount percent</th>
			<th>Discount expiration</th>
			<th>Image</th>
			<th>Availability</th>
		</tr>
		<c:forEach var="product" items="${products}">
			<tr>
				<td align="center">${product.brand}</td>
				<td align="center">${product.model}</td>
				<td align="center">${product.description}</td>
				<td align="center">${product.price}BGN</td>
				<td align="center">${product.discountPercent}</td>
				<td align="center">${product.discountExpiration}</td>
				<td align="center">${product.productImageURL}</td>
				<td align="center">${product.availability}</td>
				<td>
					<form action="cart" method="POST">
						<input type="hidden" name="orderedProduct" value="${product.productID}"> 
						<input type="submit" value="Order">
					</form>
					<form action="favourite" method="POST">
						<input type="hidden" name="favouriteProduct" value="${product.productID}"> 
						<input type="submit" value="&#9829;">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
</body>
</html>