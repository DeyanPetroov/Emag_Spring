<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login to eMAG</title>
<!-- <link rel='stylesheet prefetch'
	href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/themes/smoothness/jquery-ui.css'>
<link rel="stylesheet" href="./css/loginStyle.css"> -->
</head>

<body>

	<c:if test="${ requestScope.error != null}">
	<h4 style="color: red">${error}</h4>
	</c:if>
	
	<c:if test="${sessionScope.user == null}">
	<div class="login" style="position: relative; down: 50px; top: 70px;">
		<div class="login-header">
			<h2>Login</h2>
		</div>
		<div class="login-form">
			<form action = "login" method="POST">
				<input type="text" name="username" placeholder="Username" value = "nadyapetrova" required /> <br> 
				<input type="password" name="password" placeholder="Password" value = "12345" required /> <br>
				 <input type="submit" value="Login" class="login-button" /> <br> <br> 
				 <a href="register"><input type="button" value="Don't have an account? Sign up!" class="register-button" /></a>
				<br>
				<!--<h6 class="no-access">Can't access your account?</h6>  -->
			</form>
		</div>
	</div>
	</c:if>
<!-- <script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script
		src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>
	<script src="js/index.js"></script> -->

</body>
</html>