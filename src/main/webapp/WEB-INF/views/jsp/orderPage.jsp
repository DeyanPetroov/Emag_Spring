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
	<c:if test="${sessionScope.user == null}">
		<a href = "${pageContext.request.contextPath}/login">Please log in to make an order.</a>			
	</c:if>
	
	<c:if test=" ${sessionScope.user.cart.totalCost} == 0">
		<a href = "${pageContext.request.contextPath}/index">Your cart is empty. </a>			
	</c:if>
	
	<c:if test=" ${sessionScope.user.cart.totalCost} != 0">
	<c:if test="${sessionScope.user != null}">
		<h2>You are on your way to finalize your order ..</h2>
		<br>
		<h3>Your current order</h3>
		<c:forEach var="entry" items="${cart.products}">
			${entry.key.brand} ${entry.key.model}
    		Price of product: ${entry.key.price}
   			Quantity: ${entry.value} <br>
   	</c:forEach>
   			Total cost of products in cart: ${sessionScope.user.cart.totalCost}

		<h3>
			If you want to finish your order, enter your personal details. If you
			are not sure go back to our <a
				href="${pageContext.request.contextPath}/index">main page</a>
		</h3>
		<form action="finalizeOrder" method="POST">
			<input type="text" name="address" placeholder="Delivery address" /> <input
				type="submit" value="Finish order">
		</form>
	</c:if>
	</c:if>
</body>
</html>