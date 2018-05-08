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

#my-button {
    display: inline-block;
    -webkit-box-sizing: content-box;
    -moz-box-sizing: content-box;
    box-sizing: content-box;
    cursor: pointer;
    padding: 10px 20px;
    border: 1px solid white;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    font: normal medium/normal Arial, Helvetica, sans-serif;
    color: rgba(255,255,255,0.9);
    -o-text-overflow: clip;
    text-overflow: clip;
    background: #286090;
    -webkit-box-shadow: 2px 2px 2px 0 rgba(0,0,0,0.2);
    box-shadow: 2px 2px 2px 0 rgba(0,0,0,0.2);
    text-shadow: -1px -1px 0 rgba(15,73,168,0.66);
    -webkit-transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
    -moz-transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
    -o-transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
    transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
}

#input-button {
 display: inline-block;
  -webkit-box-sizing: content-box;
  -moz-box-sizing: content-box;
  box-sizing: content-box;
  padding: 10px 20px;
  border: 1px solid #b7b7b7;
  -webkit-border-radius: 3px;
  border-radius: 3px;
  font: normal medium/normal Arial, Helvetica, sans-serif;
  color: rgba(0,142,198,1);
  -o-text-overflow: clip;
  text-overflow: clip;
  -webkit-box-shadow: 2px 2px 2px 0 rgba(0,0,0,0.2) inset;
  box-shadow: 2px 2px 2px 0 rgba(0,0,0,0.2) inset;
  text-shadow: 1px 1px 0 rgba(255,255,255,0.66) ;
  -webkit-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
  -moz-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
  -o-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
  transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
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
								<input type="password" id = "input-button" name="oldPassword" placeholder = "Old password" required pattern=".{5,20}" title="Between 5 and 20 characters" /><br>
								</td>		
							</tr>
							<tr>
								<td>
								New password:
								<input type="password" id = "input-button" name="newPassword" placeholder = "New password" required pattern=".{5,20}" title="Between 5 and 20 characters"/> <br>
								</td> 
							</tr>
						
							<tr>
								<td>
								Confirm:
									<input type="password" id = "input-button" name="confirmedNewPassword" placeholder = "Confirm the new password" required pattern=".{5,20}" title="Between 5 and 20 characters"/> <br>				
								</td>
							</tr>
							</table>	
							<input type="submit" id = "my-button" value="Submit"/> <br> <br> 
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