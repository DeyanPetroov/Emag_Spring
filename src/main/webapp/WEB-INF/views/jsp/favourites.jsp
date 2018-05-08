<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add product</title>
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
    background-color: #eee;
    color: black; 
    display: block; 
    padding: 25px; /* Add some padding */
    text-decoration: none;
}

.vertical-menu a:hover {
    background-color: #ccc; 
}

.vertical-menu a.active {
    background-color: #286090;
    color: white;
}
</style>
</head>

<body>
<div id="container">
<div>
    <div id="rightCol">
	<div id = "profile">
	<div class="login">
			<h3>Favourites</h3>
	</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info" style="right: 200px;width: 170%;position: relative;left: 5;">
				<div class="panel-heading">
					<h2 style="color: white; padding-top: 10px">Your favourites.</h2>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class=" col-md-9 col-lg-9 ">
							<table class="table table-user-information">
								<tr>
								<td>
									<c:if test="${requestScope.favourites != null}">
		<c:forEach var="product" items="${favourites}">
				<img src="download/product_picture/${product.productPicture}" alt=" "
					class="img-responsive" width=150px height=150px]> 
			<div>
				<a href="${pageContext.request.contextPath}/viewProduct/${product.productID}">${product.brand} ${product.model}</a>
				<p>${product.description}</p>
				<div class="simpleCart_shelfItem products-right-grid1-add-cart">
					<p>
						Price: $${product.price}</span>
					</p>
				</div>
				<div>
					<form action="${pageContext.request.contextPath}/addToCart"
						method="POST">
						<input type="hidden" name="productID" value="${product.productID}">
						<input type="number" name="quantity" min=1 placeholder="quantity"
							required id = "input-button"><br> <input type="submit"
							value="Add to cart" id = "my-button">
					</form>
				</div>
				<div>
					<form action="${pageContext.request.contextPath}/favourite"
						method="POST">
						<input type="hidden" name="favouriteProduct"
							value="${product.productID}"> 
							<input type="submit" value = "remove" id = "my-button">
					</form>
				</div>
				<div>
					<c:if test="${sessionScope.user.admin == true}">
						<a
							href="${pageContext.request.contextPath}/editProduct/${product.productID}"><button id = "my-button">Edit</button></a>
					</c:if>
				</div>
				</div>
		</c:forEach>
	</c:if>
	<br>
	<a href="${pageContext.request.contextPath}/index" id = "my-button">Go to the main page</a>
								</td>
								</tr>
							</table>
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

    