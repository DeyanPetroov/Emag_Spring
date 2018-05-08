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
							<li></i><a href="login">Login</a></li>
							<li></i><a href="register">Register</a></li>
						</c:if>
						<c:if test="${sessionScope.user != null }">
							<c:if test="${sessionScope.user.admin == true}">
								<li></i><a href="adminPage">Admin page</a>
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
							<li><a href="#">Mail Us</a></li>
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
					<h3>Filter By Price</h3>
					<ul class="dropdown-menu1">
							<li><a href="">								               
							<div id="slider-range"></div>							
							<input type="text" id="amount" style="border: 0" />
							</a></li>	
					</ul>
						<script type='text/javascript'>//<![CDATA[ 
						$(window).load(function(){
						 $( "#slider-range" ).slider({
								range: true,
								min: 0,
								max: 100000,
								values: [ 20000, 80000 ],
								slide: function( event, ui ) {  $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
								}
					 });
					$( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) + " - $" + $( "#slider-range" ).slider( "values", 1 ) );


						});//]]>
						</script>
						<script type="text/javascript" src="js/jquery-ui.min.js"></script>
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
				<div class="new-products animated wow slideInUp" data-wow-delay=".5s">
					<h3>New Products</h3>
					<div class="new-products-grids">
						<div class="new-products-grid">
							<div class="new-products-grid-left">
								<a href="single.html"><img src="images/6.jpg" alt=" " class="img-responsive" /></a>
							</div>
							<div class="new-products-grid-right">
								<h4><a href="single.html">occaecat cupidatat</a></h4>
								<div class="rating">
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/1.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/1.png" alt=" " class="img-responsive">
									</div>
									<div class="clearfix"> </div>
								</div>
								<div class="simpleCart_shelfItem new-products-grid-right-add-cart">
									<p> <span class="item_price">$180</span><a class="item_add" href="#">add to cart </a></p>
								</div>
							</div>
							<div class="clearfix"> </div>
						</div>
						<div class="new-products-grid">
							<div class="new-products-grid-left">
								<a href="single.html"><img src="images/26.jpg" alt=" " class="img-responsive" /></a>
							</div>
							<div class="new-products-grid-right">
								<h4><a href="single.html">eum fugiat quo</a></h4>
								<div class="rating">
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/1.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/1.png" alt=" " class="img-responsive">
									</div>
									<div class="clearfix"> </div>
								</div>
								<div class="simpleCart_shelfItem new-products-grid-right-add-cart">
									<p> <span class="item_price">$250</span><a class="item_add" href="#">add to cart </a></p>
								</div>
							</div>
							<div class="clearfix"> </div>
						</div>
						<div class="new-products-grid">
							<div class="new-products-grid-left">
								<a href="single.html"><img src="images/11.jpg" alt=" " class="img-responsive" /></a>
							</div>
							<div class="new-products-grid-right">
								<h4><a href="single.html">officia deserunt</a></h4>
								<div class="rating">
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/2.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/1.png" alt=" " class="img-responsive">
									</div>
									<div class="rating-left">
										<img src="images/1.png" alt=" " class="img-responsive">
									</div>
									<div class="clearfix"> </div>
								</div>
								<div class="simpleCart_shelfItem new-products-grid-right-add-cart">
									<p> <span class="item_price">$259</span><a class="item_add" href="#">add to cart </a></p>
								</div>
							</div>
							<div class="clearfix"> </div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-8 products-right">
				<div class="products-right-grid">
					<div class="products-right-grids animated wow slideInRight"
						data-wow-delay=".5s">
						<div class="sorting">
							<select id="country" class="frm-field required sect" onchange="location = this.value";>
						
								<option value="">&nbsp;</option>
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
				<a href = "${pageContext.request.contextPath}/index">Go to the main page</a>
			</c:if>
			</c:if>
			<c:if test="${requestScope.unavailable == null }">
			<c:forEach var="product" items="${products}">
				<div class="col-md-4 products-right-grids-bottom-grid">
					<div class="new-collections-grid1 products-right-grid1 animated wow slideInUp" data-wow-delay=".5s">
						<div class="new-collections-grid1-image">
							<a href="single.html" class="product-image"><img src="images/19.jpg" alt=" " class="img-responsive"></a>
								<div class="new-collections-grid1-image-pos products-right-grids-pos">
									<a href="${pageContext.request.contextPath}/viewProduct/${product.productID}">Quick View</a>
								</div>
								<div class="new-collections-grid1-right products-right-grids-pos-right">
									<div class="rating">
										<div class="rating-left">
											<img src="images/2.png" alt=" " class="img-responsive">
										</div>
										<div class="rating-left">
											<img src="images/2.png" alt=" " class="img-responsive">
										</div>
										<div class="rating-left">
											<img src="images/2.png" alt=" " class="img-responsive">
										</div>
										<div class="rating-left">
											<img src="images/1.png" alt=" " class="img-responsive">
										</div>
										<div class="rating-left">
											<img src="images/1.png" alt=" " class="img-responsive">
										</div>
										<div class="clearfix"> </div>
									</div>
								</div>
							</div>
							<h4><a href="single.html">${product.brand} ${product.model}</a></h4>
							<p>${product.description}</p>
							<div class="simpleCart_shelfItem products-right-grid1-add-cart">
								<p><i>reduced</i> <span class="item_price">$${product.price}</span>
							</p>
							</div>
						</div>
							<div>
								<form action="${pageContext.request.contextPath}/addToCart"
									method="POST">
									<input type="hidden" name="productID"
										value="${product.productID}"> <input type="number"
										name="quantity" min=1 placeholder="quantity" required><br>
									<input type="submit" value="Add to cart">
								</form>
							</div>
							<div>
								<form action="${pageContext.request.contextPath}/favourite"
									method="POST">
									<input type="hidden" name="favouriteProduct"
										value="${product.productID}"> <input type="submit"
										value="&#9829;">
								</form>
							</div>
							<div>
								<c:if test="${sessionScope.user.admin == true}">
								<a href="${pageContext.request.contextPath}/editProduct/${product.productID}"><button>Edit</button></a>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</c:if>
				</div>				
					<div class="clearfix"> </div>
				</div>
				<nav class="numbering animated wow slideInRight" data-wow-delay=".5s">
				  <ul class="pagination paging">
					<li>
					  <a href="#" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					  </a>
					</li>
					<li class="active"><a href="#">1<span class="sr-only">(current)</span></a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li>
					  <a href="#" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					  </a>
					</li>
				  </ul>
				</nav>
			</div>
			<div class="clearfix"> </div>
<!-- //breadcrumbs -->
<%@include file = "footer.jsp"%>
<!-- //footer -->
</body>
</html>