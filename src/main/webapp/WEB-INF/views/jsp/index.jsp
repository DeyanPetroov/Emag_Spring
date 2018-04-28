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
	<h1 align="center">Welcome to eMAG</h1>
	<div align="center">
		<a href="login">Login</a><br> 
		<a href="register">Register</a> <br>
		<c:if test="${sessionScope.user != null }">
			<a href="logout">Logout</a>
			<br>
		</c:if>
		<a href="profile">My account</a>		
	</div>
	
	<h2>Categories</h2>
	<c:forEach var="map" items="${categories}">
				*${map.key} <br>
		<c:forEach var="category" items="${map.value}">
				--->${category.categoryName} <br>
		</c:forEach>
	</c:forEach>
</body>
</html>