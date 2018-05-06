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
				 <a href="addProduct">Add new product</a>
				 <a href="category/1">Edit product</a> 
				 <a href="#">Make product on sale</a> 
				 <a href="#">Delete user</a>
			</div>
			</div>
    <div id="rightCol">
	<c:if test = "${sessionScope.user != null }">
	<div id = "profile">
	<div class="login">
			<h3>Admin page</h3>
			</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 style="color: white; padding-top: 10px">Statistics</h2>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class=" col-md-9 col-lg-9 ">
							<table class="table table-user-information">
								<tbody>
									<tr>
										<td>Registered users: ${totalUsers}</td>
									</tr>
								</tbody>
							</table>
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