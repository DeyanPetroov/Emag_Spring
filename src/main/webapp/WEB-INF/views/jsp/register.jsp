<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<html>
<head>
<title>Best Store a Ecommerce Online Shopping Category Flat Bootstrap Responsive Website Template | Register :: w3layouts</title>
<!-- for-mobile-apps -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Best Store Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- //for-mobile-apps -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- js -->
<script src="js/jquery.min.js"></script>
<!-- //js -->
<!-- cart -->
	<script src="js/simpleCart.min.js"> </script>
<!-- cart -->
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<!-- for bootstrap working -->
	<script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
<!-- //for bootstrap working -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

</head>
	
<body>
<!-- header -->
	<div class="header">
		<div class="container">
			<div class="header-grid">
				<div class="header-grid-left animated wow slideInLeft" data-wow-delay=".5s">
					<ul>
						<c:if test="${requestScope.loggedUser != null }">
							<h4>${loggedUser}</h4>
						</c:if>
						<c:if test="${sessionScope.user == null }">
							<li><i class="glyphicon glyphicon-log-in" aria-hidden="true"></i><a href="login">Login</a></li>
							<li><i class="glyphicon glyphicon-book" aria-hidden="true"></i><a href="register">Register</a></li>
						</c:if>
						<c:if test="${sessionScope.user != null }">
							<c:if test="${sessionScope.user.admin == true}">
								<li><i class="glyphicon glyphicon-log-in" aria-hidden="true"></i><a href="adminPage">Admin page</a>
							</c:if>
							<li><i class="glyphicon glyphicon-book" aria-hidden="true"></i><a href="favourite">Favourites</a>
							<li><i class="glyphicon glyphicon-book" aria-hidden="true"></i><a href="profile">My account</a>
							<li><i class="glyphicon glyphicon-book" aria-hidden="true"></i><a href="logout">Logout</a>
						</c:if>
					</ul>
				</div>
				<div class="header-grid-right animated wow slideInRight" data-wow-delay=".5s">
				
				</div>
				<div class="clearfix"> </div>
			</div>
			<div class="logo-nav">
				<div class="logo-nav-left animated wow zoomIn" data-wow-delay=".5s">
					<a href="index"><img src= "images/logo.png"></a>
				</div>
				<div class="logo-nav-left1">
					<nav class="navbar navbar-default">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header nav_2">
						<button type="button" class="navbar-toggle collapsed navbar-toggle1" data-toggle="collapse" data-target="#bs-megadropdown-tabs">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
					</div> 
					<div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
						<ul class="nav navbar-nav">
							<li class="active"><a href="index" class="act">Home</a></li>	
							<!-- Mega Menu -->
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">Products<b class="caret"></b></a>
								<ul class="dropdown-menu multi-column columns-3">
									<div class="row">
										<c:if test="${applicationScope.categories != null}">
											<c:forEach items="${applicationScope['categories']}"
												var="entry">
												<div class="col-sm-4">
													<ul class="multi-column-dropdown">
														<h6>
															<a href="category/${entry.getKey().categoryID}"
																style="text-decoration: none">
																${entry.getKey().categoryName}</a>
														</h6>
														<c:forEach items="${entry.getValue()}" var="subcategory">
															<li><a href="category/${subcategory.categoryID}">${subcategory.categoryName}
															</a>
														</c:forEach>
													</ul>
												</div>
											</c:forEach>
										</c:if>
										<div class="clearfix"></div>
									</div>
								</ul>
							</li>
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">Hot offers <b class="caret"></b></a>
								<ul class="dropdown-menu multi-column columns-3">
									<div class="row">
										<div class="col-sm-4">
											<ul class="multi-column-dropdown">
												<h6>Home Collection</h6>
												<li><a href="furniture.html">Cookware</a></li>
												<li><a href="furniture.html">Sofas</a></li>
												<li><a href="furniture.html">Dining Tables</a></li>
												<li><a href="furniture.html">Shoe Racks</a></li>
												<li><a href="furniture.html">Home Decor</a></li>
											</ul>
										</div>
										<div class="clearfix"></div>
									</div>
								</ul>
							</li>
							<li><a href="mail.html">Mail Us</a></li>
						</ul>
					</div>
					</nav>
				</div>
				<div class="logo-nav-right">
					<div class="search-box">
						<div id="sb-search" class="sb-search">
							<form>
								<input class="sb-search-input" placeholder="Enter your search term..." type="search" id="search">
								<input class="sb-search-submit" type="submit" value="">
								<span class="sb-icon-search"> </span>
							</form>
						</div>
					</div>
						<!-- search-scripts -->
						<script src="js/classie.js"></script>
						<script src="js/uisearch.js"></script>
							<script>
								new UISearch( document.getElementById( 'sb-search' ) );
							</script>
						<!-- //search-scripts -->
				</div>
				<div class="header-right">
					<div class="cart box_1">
						<a href="cart">
							Your cart <img src="images/bag.png" alt="" />
						</a>
						<div class="clearfix"> </div>
					</div>	
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
<!-- //header -->
<!-- register -->
	<div class="register">
		<div class="container">
			<h3 class="animated wow zoomIn" data-wow-delay=".5s">Register Here</h3>
			<div class="login-form-grids">
				<c:if test="${ requestScope.error != null }">
					<h4 align="center" style="color: red">${error}</h4>
				</c:if>
				<!-- REGISTER FORM -->
				<form action="register" method="POST">
					<input type="text" name="first_name" placeholder="First name" required pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br> 
					<input type="text" name="last_name" placeholder="Last name" required pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" /> <br>
					<input type="text" name="username" placeholder="Username" required pattern="^[a-z0-9_-]{3,15}$"
					title="From 3 to 15 characters. Numbers, _ and - allowed" /> <br>
					<input type="email" name="email" placeholder="E-mail" required title="Enter a valid email" /> <br> 
					<input type="password" name="password" placeholder="Password" required pattern=".{5,20}"
					title="Between 5 and 20 characters" /> <br>
					<input type="submit" name="signup_submit" value="Sign me up" /> <br> <br>
					<a href="login">
					<input type="button" value="Already have an account? Sign in!" class="login-button" /></a>
				</form>
				<!-- //REGISTER FORM -->
			</div>
			<div class="register-home animated wow slideInUp" data-wow-delay=".5s">
				<a href="index.html">Home</a>
			</div>
		</div>
	</div>
<!-- //register -->
<%@include file = "footer.jsp" %>
</body>
</html>