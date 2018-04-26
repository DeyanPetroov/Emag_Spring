<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
				<input type="email" name="email" placeholder="E-mail" required title="Enter valid а email" /> <br>
				<input type="password" name="password" placeholder="Password" required
			 	pattern=".{5,20}" title="Between 5 and 20 characters" /> <br>
			 	<input type="number" name="age" placeholder="Age" min=14 title="You must be 14 or older" required /> <br> <br>
			 	<input type="submit" name="signup_submit" value="Sign me up" /> <br> <br>
			 	<a href="login"><input type="button" value="Already have an account? Sign in!" class="login-button" /></a>
			 	
			</form>
		</div>
	
</body>

</html>