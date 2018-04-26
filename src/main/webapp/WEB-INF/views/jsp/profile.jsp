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
	<c:if test="${sessionScope.user != null}">
		<table>
			<tr>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.address}</td>
				<td>${user.email}</td>
				<td>${user.phone}</td>
				<td>${user.profilePictureURL}</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${sessionScope.user == null}">
		<h3>Please log in!</h3>
	</c:if>
	
</body>
</html>