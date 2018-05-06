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

<style>

.upload-wrap {
    position: relative;
}

.banner-bottom, .new-collections, .checkout, .collections-bottom, .timer, .register, .products, .typo, .mail, .single, .login, .single-related-products {
    padding: 3em 0;
}

.panel-info > .panel-heading {
  color: #31708f;
  background-color: #286090;
  border-color: #bce8f1;
  height: 72px;
}

#leftCol {
	width: 200px;
	float: left;
}

#rightCol {
	margin-left: 200px;
	/* Change this to whatever the width of your left column is*/
}

.clear {
	clear: both;
}

#profile {
	padding-right: 260px;
}

.vertical-menu {
    width: 300px;
    padding-top: 169px;
    padding-left: 5px;
}

.vertical-menu a {
    background-color: #eee; /* Grey background color */
    color: black; /* Black text color */
    display: block; 
    padding: 25px; /* Add some padding */
    text-decoration: none; /* Remove underline from links */
}

.vertical-menu a:hover {
    background-color: #ccc; /* Dark grey background on mouse-over */
}

.vertical-menu a.active {
    background-color: #286090; /* Add a green color to the "active/current" link */
    color: white;
}

</style>
</head>

<body>
<div id="container">
<div>
	<c:if test="${ requestScope.error != null }">
		<h4 style="color: red">${error}</h4>
	</c:if>
	<c:if test="${sessionScope.user == null}">
		<c:redirect url = "login">Please log in.</c:redirect>
	</c:if>
	<!-- left menu -->
    <div id="leftCol">
			<div class="vertical-menu">
				<a href="${pageContext.request.contextPath}/index" class="active">Back to main page</a>
				 <a href="profile">View profile</a>
				 <a href="changePassword">Change password</a> 
				 <a href="#">Your orders</a> 
			</div>
	</div>
	<!-- //left menu -->
	<!-- right menu  -->
    <div id="rightCol">
    <c:if test="${sessionScope.user == null}">
		<h3>
			Please <a href="login">log in</a>!
		</h3>
	</c:if>
	<c:if test = "${sessionScope.user != null }">
	<div id = "profile">
	<div class="login">
			<h3>Edit profile</h3>
			</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 style="color: white; padding-top: 10px;">Hello ${user.username} :) </h2>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-3 col-lg-3 " align="center">
							<img src="download/${profilePicture}" width="200px"
								height="200px" class="img-circle img-responsive">
						</div>
						<div class=" col-md-9 col-lg-9 ">
						
						<form action = "./uploadProfilePicture" method = "POST" enctype="multipart/form-data" accept="image/*">
							<div class="upload-wrap">
								<input type = "file" name = "image" class="upload-btn">
							</div>					
							<input type = "submit" value = "Change picture" class = "submit-btn">
						</form>
						<div class=" col-md-9 col-lg-9 ">
							<form action="editProfile" method="POST">
							<table class="table table-user-information">
							<tr>
								<td>
								First name:
								<input type="text" name="firstname" value="${user.firstName}" pattern="^[a-zA-Z'-]{3,31}$"
									title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /><br>
								</td>		
							</tr>
							<tr>
								<td>
									Last name:
									<input type="text" name="lastname" value="${user.lastName}" pattern="^[a-zA-Z'-]{3,31}$"
									title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br> 
								</td> 
							</tr>
						
							<tr>
								<td>
									Address: <input type="text" name="address" value="${user.address}" /> <br>
								</td>
							</tr>
							<tr>
								<td>
									Email address: <input type="email" name="email" value="${user.email}" /> <br>
								</td>					
							</tr>
							<tr>
								<td>
									Phone: <input type="text" name="phone" value="${user.phone}" /> <br>
								</td>
							</tr>
							<tr>	
							</td>
							</tr>
							</table>	
							<td>
									<input type="hidden" value="${user.email}" name="oldEmail" /> <br>	
									<input type="submit" value="Submit"/> <br> <br> 
							</form>	
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</c:if>
    </div>
    <div class="clear"></div>
  </div>
	</div>
</body>
</html>