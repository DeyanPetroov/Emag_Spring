<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add product</title>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/my.css">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<style>

.banner-bottom, .new-collections, .checkout, .collections-bottom, .timer, .register, .products, .typo, .mail, .single, .login, .single-related-products {
    padding: 3em 0;
}

.panel-info > .panel-heading {
  color: #31708f;
  background-color: #286090;
  border-color: #bce8f1;
  height: 72px;
}

#leftCol {
	width: 200px;
	float: left;
}

#rightCol {
	margin-left: 200px;
}

.clear {
	clear: both;
}

#profile {
	padding-right: 260px;
}

.vertical-menu {
    width: 300px;
    padding-top: 169px;
    padding-left: 5px;
}

.vertical-menu a {
    background-color: #eee;
    color: black; 
    display: block; 
    padding: 25px; /* Add some padding */
    text-decoration: none;
}

.vertical-menu a:hover {
    background-color: #ccc; 
}

.vertical-menu a.active {
    background-color: #286090;
    color: white;
}
</style>
</head>

<body>
<div id="container">
<div>
    <div id="rightCol">
	<div id = "profile">
	<div class="login">
			<h3>View order</h3>
	</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info" style="right: 200px;width: 170%;position: relative;left: 5;">
				<div class="panel-heading">
					<h2 style="color: white; padding-top: 10px">Congratulations!</h2>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class=" col-md-9 col-lg-9 ">
							<table class="table table-user-information">
								<tr>
								<td>
									<c:if test="${sessionScope.user.cart.totalCost == 0}">
										Your cart is empty
										<a href= "${pageContext.request.contextPath}/index">Continue shopping</a>
									</c:if>
									
									<c:if test="${sessionScope.user.cart.totalCost != 0}">
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
								</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    </div>
    <div class="clear"></div>
  </div>
	</div>
</body>
</html>