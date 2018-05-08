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
    <div id="leftCol">
			<div class="vertical-menu">
				<a href="${pageContext.request.contextPath}/index" class="active">Back to main page</a>
				 <a href="profile">Your profile</a>
				 <a href="editProfile">Edit profile</a> 
				 <a href="orderHistory">Your orders</a> 
			</div>
			</div>
    <div id="rightCol">
  	<div id = "profile">
	<div class="login">
			<h3>Edit profile</h3>
			</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 style="color: white; padding-top: 10px">Change password </h2>
			</div>
				<div class="panel-body">
					<div class="row">
						<div class=" col-md-9 col-lg-9 ">
							<c:if test="${sessionScope.user != null}">
							<c:if test="${ requestScope.wrongOldPassword != null }">
								<div class="alert alert-danger" role="alert">
									<h4 style="color: red">${wrongOldPassword}</h4>
								</div>
							</c:if>
							<c:if test="${ requestScope.noChange != null }">
								<div class="alert alert-danger" role="alert">
									<h4 style="color: red">${noChange}</h4>
								</div>
							</c:if>
							<c:if test="${ requestScope.passwordsMissmatch != null }">
							<div class="alert alert-danger" role="alert">
								<h4 style="color: red">${passwordsMissmatch}</h4>
							</div>
							</c:if>
							<form action="changePassword" method="POST">
							<table class="table table-user-information">
							<tr>
								<td>
								Old password: 
								<input type="password" name="oldPassword" placeholder = "Old password" required pattern=".{5,20}" title="Between 5 and 20 characters" /><br>
								</td>		
							</tr>
							<tr>
								<td>
								New password:
								<input type="password" name="newPassword" placeholder = "New password" required pattern=".{5,20}" title="Between 5 and 20 characters"/> <br>
								</td> 
							</tr>
						
							<tr>
								<td>
								Confirm password:
									<input type="password" name="confirmedNewPassword" placeholder = "Confirm the new password" required pattern=".{5,20}" title="Between 5 and 20 characters"/> <br>				
								</td>
							</tr>
							</table>	
							<input type="submit" value="Submit"/> <br> <br> 
							</form>	
							</c:if>						
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    </div>
    <div class="clear"></div>
  </div>
	</div>
</body>
</html>