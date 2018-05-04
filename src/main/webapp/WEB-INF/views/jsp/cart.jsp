<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->

<!DOCTYPE html>
<html>
<head>
<title>Your cart</title>
<link rel="stylesheet" type="text/css" href="css/my.css">

</head>
	
<body>
<!-- header -->
<%@include file = "header.jsp" %>
<!-- breadcrumbs -->
	<div class="breadcrumbs">
		<div class="container">
			<ol class="breadcrumb breadcrumb1 animated wow slideInLeft" data-wow-delay=".5s">
				<li><a href="index.html"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>Home</a></li>
				<li class="active">Checkout Page</li>
			</ol>
		</div>
	</div>
<!-- //breadcrumbs -->
<!-- checkout -->
<div class="checkout">
		<div class="container">
			<h3 class="animated wow slideInLeft" data-wow-delay=".5s">Your shopping cart contains: <span>${fn:length(cart.products)}</span> products</h3>
			<div class="checkout-right animated wow slideInUp" data-wow-delay=".5s">
				<table class="timetable_sub">
					<thead>
						<tr>
							<th>SL No.</th>	
							<th>Quantity</th>
							<th>Product Name</th>
							<th>Price</th>
							<th>Add</th>
							<th>Remove</th>
						</tr>
					</thead>
					<c:forEach var="entry" items="${cart.products}">
						<tr class="rem1">
						<td class="invert">1</td>
<!-- 					<td class="invert-image"><a href="single.html"><img src="images/22.jpg" alt=" " class="img-responsive" /></a></td> -->
						<td class="invert">
							 <div class="quantity"> 
								${entry.value}
							</div>
						</td>
						<td class="invert">${entry.key.brand} ${entry.key.model} </td>
						<td class="invert">${entry.key.price * entry.value}</td>
						<td class="invert">
							<form action="addToCart" method="POST">
								<input type="hidden" name="productID" value="${entry.key.productID}">
								<input type="number" name="quantity" min=1 required>
								<input type="submit" value="Add to cart">
								</form>
							</td>
						<td class="invert">
							<form action="removeFromCart" method="POST">
							<input type="hidden" name="productID" value="${entry.key.productID}"> 
							<input type="number" name="quantity" min=1 required> 
							<input type="submit" value="Remove">
							</form>
						</td>
						</c:forEach>
					</tr>
					
				</table>
			</div>
			<div class="checkout-left">	
				<div class="checkout-left-basket animated wow slideInLeft" data-wow-delay=".5s">
					<a href = "orderPage"><h4>Continue to order</h4></a>
					<ul>
					<c:forEach var="entry" items="${cart.products}">
						<li>${entry.key.brand} ${entry.key.model}
						<span>${entry.key.price * entry.value}</span>
					</c:forEach>
					
					<li>Total <span>${sessionScope.user.order.totalCost} </span></li>
					</ul>
				</div>
				<div class="checkout-right-basket animated wow slideInRight" data-wow-delay=".5s">
					<a href="index"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>Continue Shopping</a>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
<!-- //checkout -->
<%@include file = "footer.jsp" %>
</body>
</html>