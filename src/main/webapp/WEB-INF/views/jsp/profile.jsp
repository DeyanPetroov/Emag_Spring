<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/my.css">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
</head>

<body>
	<c:if test="${sessionScope.user == null}">
		<h3>
			Please <a href="login">log in</a>!
		</h3>
	</c:if>
	<c:if test = "${sessionScope.user != null }">
	<div class="login">
			<h3>My profile</h3>
			</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">Hello ${user.username}</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-3 col-lg-3 " align="center">
							<img src="download/${profilePicture}" width="200px"
								height="200px" class="img-circle img-responsive">
						</div>
						<div class=" col-md-9 col-lg-9 ">
							<table class="table table-user-information">
								<tbody>
									<tr>
										<td>First name:</td>
										<td>${user.firstName}</td>
									</tr>
									<tr>
										<td>Last name:</td>
										<td>${user.lastName}</td>
									</tr>
									<tr>
										<td>Email:</td>
										<td>${user.email}</td>
									</tr>
									<tr>
										<td>Address:</td>
										<td>${user.address}</td>
									</tr>
									<tr>
										<td>Phone:</td>
										<td>${user.phone}</td>
									</tr>
								</tbody>
							</table>
							<a href="editProfile" class="btn btn-primary" style="width: 35%">Edit profile</a>
							<a href="changePassword" class="btn btn-primary">Change password</a> <br><br>							
							<a href="${pageContext.request.contextPath}/index" class="btn btn-primary" style = "width: 74%">Go to the main page</a>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
</body>
</html>