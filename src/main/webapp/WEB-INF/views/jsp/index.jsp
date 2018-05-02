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
		<c:if test="${requestScope.loggedUser != null }">
			<h4>${loggedUser}</h4>
		</c:if>
 		<c:if test="${sessionScope.user == null }">
			<a href="login">Login</a> <br>
			<a href="register">Register</a> <br>
		</c:if>
		<c:if test="${sessionScope.user != null }">
			<c:if test="${sessionScope.user.admin == true}">
				<a href = "adminPage">Admin page</a> <br>
			</c:if>
			<a href="logout">Logout</a> <br>
			<a href= "cart">Cart</a> <br>
			<a href = "favourite">Favourites</a>
			<a href="profile">My account</a>
		</c:if>
	</div>

	<c:if test="${applicationScope.categories != null}">
		<c:forEach items="${applicationScope['categories']}" var="entry">
			<a href="category/${entry.getKey().categoryID}" style="text-decoration: none">*${entry.getKey().categoryName} </a><br>
			<c:forEach items="${entry.getValue()}" var="subcategory">
				<a href="category/${subcategory.categoryID}" style="text-decoration: none">--->${subcategory.categoryName} </a><br>
			</c:forEach>
		</c:forEach>
	</c:if>
</body>
</html>