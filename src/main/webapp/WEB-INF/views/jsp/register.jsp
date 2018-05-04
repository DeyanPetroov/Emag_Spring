<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<html>
<head>
<title>Best Store a Ecommerce Online Shopping Category Flat Bootstrap Responsive Website Template | Register :: w3layouts</title>
<!-- for-mobile-apps -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Best Store Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- //for-mobile-apps -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- js -->
<script src="js/jquery.min.js"></script>
<!-- //js -->
<!-- cart -->
	<script src="js/simpleCart.min.js"> </script>
<!-- cart -->
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<!-- for bootstrap working -->
	<script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
<!-- //for bootstrap working -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

</head>
	
<body>
<%@include file = "header.jsp" %>
<!-- register -->
	<div class="register">
		<div class="container">
			<h3 class="animated wow zoomIn" data-wow-delay=".5s">Register Here</h3>
			<div class="login-form-grids">
				<c:if test="${ requestScope.error != null }">
					<h4 align="center" style="color: red">${error}</h4>
				</c:if>
				<!-- REGISTER FORM -->
				<form action="register" method="POST">
					<input type="text" name="first_name" placeholder="First name" required pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br> 
					<input type="text" name="last_name" placeholder="Last name" required pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br>
					<input type="text" name="username" placeholder="Username" required pattern="^[a-z0-9_-]{3,15}$"
					title="From 3 to 15 characters. Numbers, _ and - allowed" /> <br>
					<input type="email" name="email" placeholder="E-mail" required title="Enter a valid email" /> <br> 
					<input type="password" name="password" placeholder="Password" required pattern=".{5,20}"
					title="Between 5 and 20 characters" /> <br>
					<input type="submit" name="signup_submit" value="Sign me up" /> <br> <br>
					<a href="login">
					<input type="button" value="Already have an account? Sign in!" class="login-button" /></a>
				</form>
				<!-- //REGISTER FORM -->
			</div>
			<div class="register-home animated wow slideInUp" data-wow-delay=".5s">
				<a href="index.html">Home</a>
			</div>
		</div>
	</div>
<!-- //register -->
<%@include file = "footer.jsp" %>
</body>
</html>

<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up for eMAG</title>
<link rel="stylesheet" href="./css/registerStyle.css">
</head>

<body>
	<c:if test="${ requestScope.error != null }">
		<h4 align="center" style="color: red">${error}</h4>
		</c:if>
	
		<div align="center">
			<h1>Sign up</h1>
			<form action="register" method="POST">
				<input type="text" name="first_name" placeholder="First name"
					required pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br>
				<input type="text" name="last_name" placeholder="Last name" required
					pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br>
				<input type="text" name="username" placeholder="Username" required
					pattern="^[a-z0-9_-]{3,15}$"
					title="From 3 to 15 characters. Numbers, _ and - allowed" />  <br>
				<input type="email" name="email" placeholder="E-mail" required title="Enter a valid email" /> <br>
				<input type="password" name="password" placeholder="Password" required
			 	pattern=".{5,20}" title="Between 5 and 20 characters" /> <br>
			 	<input type="number" name="age" placeholder="Age" min=14 title="You must be 14 or older" required /> <br> <br>
			 	<input type="submit" name="signup_submit" value="Sign me up" /> <br> <br>
			 	<a href="login"><input type="button" value="Already have an account? Sign in!" class="login-button" /></a>
			 	
			</form>
		</div>
	
</body>

</html> --%>