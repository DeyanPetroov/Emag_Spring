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
	<c:if test="${sessionScope.user != null}">
		<table>
			<tr>
				<td>First name: ${user.firstName}</td>
			</tr>
			<tr>
				<td>Last name: ${user.lastName}</td>
			</tr>
			<tr>
				<td>Address: ${user.address}</td>
			</tr>
			<tr>
				<td>Email: ${user.email}</td>
			</tr>
			<tr>
				<td>Phone: ${user.phone}</td>
			</tr>
			<tr>
				<td>${user.profilePictureURL}</td>
			</tr>
		</table>
		<a href="editProfile"><input type="button" value="Edit profile"/></a>
		<a href="changePassword"><input type="button" value="Change password"/></a>	
	</c:if>
	
	<c:if test="${sessionScope.user == null}">
		<h3>Please <a href="login">log in</a>!</h3>
	</c:if>
	
</body>
</html>