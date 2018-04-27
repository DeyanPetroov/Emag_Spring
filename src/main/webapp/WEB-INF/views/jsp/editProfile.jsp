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
	<c:if test="${ requestScope.error != null }">
		<h4 style="color: red">${error}</h4>
	</c:if>
	<c:if test="${sessionScope.user != null}">
		<form action="editProfile" method="POST">
			Profile picture: <input type="img" name="image" value="${user.profilePictureURL}" /> <br>
			First name: <input type="text" name="firstname" value="${user.firstName}" /><br>
			Last name: <input type="text" name="lastname" value="${user.lastName}" /> <br> 
			Email: <input type="email" name="email" value="${user.email}" /> <br> 
			Address: <input type="text" name="address" value="${user.address}" /> <br>
			Phone: <input type="text" name="phone" value="${user.phone}" /> <br>		
			<input type="hidden" value="${user.email}" name="oldEmail" /> <br>	
			<input type="submit" value="Submit"/> <br> <br> 		
		</form>
	</c:if>
	
	<c:if test="${sessionScope.user == null}">
		<c:redirect url = "login">Please log in.</c:redirect>
	</c:if>
</body>
</html>