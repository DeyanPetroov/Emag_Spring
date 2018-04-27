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
	
	<c:if test="${ requestScope.wrongOldPassword != null }">
		<h4 style="color: red">${wrongOldPassword}</h4>
	</c:if>
	
	<c:if test="${ requestScope.passwordsMissmatch != null }">
		<h4 style="color: red">${passwordsMissmatch}</h4>
	</c:if>
	
	
	
	<!-- TODO should be validated -->
	<c:if test="${sessionScope.user != null}">
		<form action="changePassword" method="POST">
			<input type="password" name="oldPassword" placeholder = "Old password" required /><br>
			<input type="password" name="newPassword" placeholder = "New password" required/> <br>
			<input type="password" name="confirmedNewPassword" placeholder = "Confirm the new password" required/> <br>	
			<input type="submit" value="Submit"/> <br> <br> 		
		</form>
	</c:if>
	
	<c:if test="${sessionScope.user == null}">
		<c:redirect url = "login">Please log in.</c:redirect>
	</c:if>

</body>
</html>