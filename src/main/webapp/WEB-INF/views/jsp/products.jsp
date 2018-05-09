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
<base href="http://localhost:8080/EMAG_Spring/products">
<title>Products</title>
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
</style>
</head>
	
<body>
	<!-- header -->
	<div class="header">
		<div class="container">
			<div class="header-grid">
				<div class="header-grid-left animated wow slideInLeft">
					<ul>
						<c:if test="${requestScope.loggedUser != null }">
							<h4>${loggedUser}</h4>
						</c:if>
						<c:if test="${sessionScope.user == null }">
							<li><a href="login">Login</a></li>
							<li><a href="register">Register</a></li>
						</c:if>
						<c:if test="${sessionScope.user != null }">
							<c:if test="${sessionScope.user.admin == true}">
								<li><a href="adminPage">Admin page</a>
							</c:if>
							<li><a href="favourite">Favourites</a>
							<li><a href="profile">My account</a>
							<li><a href="logout">Logout</a>
						</c:if>
					</ul>
				</div>
				<div class="header-grid-right animated wow slideInRight">
				
				</div>
				<div class="clearfix"> </div>
			</div>
			<div class="logo-nav">
				<div class="logo-nav-left animated wow zoomIn">
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
							<li><a href="index" class="act">Home</a></li>	
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
								<a href="promo">Hot offers</a>		
							</li>
						</ul>
					</div>
					</nav>
				</div>
				<div class="logo-nav-right">
					<div class="search-box">
						<div id="sb-search" class="sb-search">
							<form action="search" method="GET">
								<input class="sb-search-input" placeholder="Enter your search term..." type="text" name="search" id="search">
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
<!-- breadcrumbs -->
	<div class="breadcrumbs">
		<div class="container">
			<ol class="breadcrumb breadcrumb1 animated wow slideInLeft" data-wow-delay=".5s">
				<li><a href="index"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>Home</a></li>
				<li class="active">Products</li>
			</ol>
		</div>
	</div>
	<div class="products">
		<div class="container">
			<div class="col-md-4 products-left">
				<div class="filter-price animated wow slideInUp" data-wow-delay=".5s">
				
					 <!---->
				</div>
				<div class="categories animated wow slideInUp" data-wow-delay=".5s">
					<h3>Categories</h3>
					<ul class="cate">
						<c:forEach items="${applicationScope['categories']}" var="entry">
							<li><a href="category/${entry.getKey().categoryID}" style="text-decoration: none">
									${entry.getKey().categoryName}</a></li>
									<ul>
											<c:forEach items="${entry.getValue()}" var="subcategory">
											<li><a href="category/${subcategory.categoryID}">${subcategory.categoryName}
											</a></li>
											</c:forEach>
									</ul>
							</c:forEach>
					</ul>
				</div>
				
			</div>
			<div class="col-md-8 products-right">
				<div class="products-right-grid">
					<div class="products-right-grids animated wow slideInRight"
						data-wow-delay=".5s">
						<div class="sorting">
							<select id="country" class="frm-field required sect" onchange="location = this.value";>
						
								<option value="">&nbsp;Sort</option>
								<option value="category/${products[0].category.categoryID}/sort/asc">Sort by ascending price</option>
								<option value="category/${products[0].category.categoryID}/sort/desc">Sort by descending price</option>
							</select>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			
			<!-- start -->
			<div class="products-right-grids-bottom">
			<c:if test="${invalidSession != null }">
				<h4>${invalidSession}</h4>
				<a href="${pageContext.request.contextPath}/index">Go to the main page</a>
			</c:if>

			<c:if test="${invalidSession == null }">
				<c:if test="${requestScope.unavailable != null }">
				<h4>${unavailable}</h4>
				<a href = "${pageContext.request.contextPath}/category/2">Go back to our catalog</a>
			</c:if>
			</c:if>
			<c:if test="${requestScope.unavailable == null }">
			<c:forEach var="product" items="${products}">
				<div class="col-md-4 products-right-grids-bottom-grid">
					<div class="new-collections-grid1 products-right-grid1 animated wow slideInUp" data-wow-delay=".5s" style="width: 210px;">
						<div class="new-collections-grid1-image">
							<img src="download/product_picture/${product.productPicture}" alt=" " class="img-responsive" ></a>
								<div class="new-collections-grid1-image-pos products-right-grids-pos">
									<a href="${pageContext.request.contextPath}/viewProduct/${product.productID}">Quick View</a>
								</div>
								<div class="new-collections-grid1-right products-right-grids-pos-right">
									
								</div>
							</div>
							<h4><a href="${pageContext.request.contextPath}/viewProduct/${product.productID}">${product.brand} ${product.model}</a></h4>
							<p>${product.description}</p>
							<div class="simpleCart_shelfItem products-right-grid1-add-cart">
								<p><span class="item_price">$${product.price}</span>
							</p>
							</div>
						</div>
						<div>
							<div>
								<form action="${pageContext.request.contextPath}/addToCart"
									method="POST">
									<input type="hidden" name="productID"
										value="${product.productID}"> 
										<input type="number" name="quantity" min=1 max=10 placeholder="quantity" id = "input-button" required><br>
									<input type="submit" id = "my-button" value="Add to cart">
								</form>
							</div>
							<div>
									
								<form action="${pageContext.request.contextPath}/favourite"
									method="POST">
									<input type="hidden" name="favouriteProduct"
										value="${product.productID}"> <input type="submit"
										id = "my-button" value="&#9829;">
								</form>
							</div>
							<div>
								<c:if test="${sessionScope.user.admin == true}">
								<a href="${pageContext.request.contextPath}/editProduct/${product.productID}"><button id = "my-button">Edit</button></a>
								</c:if>
							</div>
						</div>
					</div>
					</c:forEach>
				</c:if>
				</div>				
					<div class="clearfix"> </div>
				</div>
			</div>
			<div class="clearfix"> </div>
<!-- //breadcrumbs -->
<%@include file = "footer.jsp"%>
<!-- //footer -->
</body>
</html>