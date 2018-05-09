<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add product</title>
<base href="http://localhost:8080/EMAG_Spring/editProduct">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link href = "css/my.css" rel = "sylesheet" type = "text/css" media = "all"/>
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

.upload-btn-wrapper {
  position: relative;
  overflow: hidden;
  display: inline-block;
}

.btn {
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

.upload-btn-wrapper input[type=file] {
  font-size: 100px;
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
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
    <div id="leftCol">
			<div class="vertical-menu">
				<a href="${pageContext.request.contextPath}/index" class="active">Back to main page</a>
				 <a href="${pageContext.request.contextPath}/adminPage">Admin page</a>
				 <a href="${pageContext.request.contextPath}/addProduct">Add a new product</a> 
				 <a href = "${pageContext.request.contextPath}/searchOrder">Change order status</a>
			</div>
			</div>
    <div id="rightCol">
	<c:if test = "${sessionScope.user != null }">
	<div id = "profile">
	<div class="login">
			<h3>Edit product</h3>
	</div>
	<div class="container">
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 style="color: white; padding-top: 10px">Enter the product details</h2>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class=" col-md-9 col-lg-9 ">
						<c:if test="${sessionScope.user != null}">
						<img src="download/product_picture/${product.productPicture}" height = 200px width = 200px>
						
							<form action = "${pageContext.request.contextPath}/uploadProductPicture" method = "POST" enctype="multipart/form-data" accept="image/*">
								<div class="upload-btn-wrapper">
									<button class="btn">Upload a file</button>
									<input type = "file" name = "image"><br>
								</div>	
								<input type = "hidden" name = "productID" value ="${product.productID}">
								<input type = "submit" id = "my-button" style = "position: absolute" value = "Change picture">
							</form>
							<form action="${pageContext.request.contextPath}/editProduct/${product.productID}" method="POST">
							<table class="table table-user-information">
								<tbody>
									<tr>
										<td>Brand:
										<input type = "hidden" id = "input-button" name = "productPicture" value = "${product.productPicture}">
										 <input type="text" name="brand" value="${product.brand}" placeholder="Brand"
										required title="Enter the product brand"/> <br>
									   </td>
									</tr>
									<tr>
										<td>
		 								Model: <input type="text" id = "input-button" name="model" value="${product.model}" placeholder="Model" 
		 								required title="Enter the product model" />
										</td>
									</tr>
									<tr>
										<td>
											<textarea rows="5" id = "input-button" name= "description" placeholder = "Description"
											 title = "Enter the product description">${product.description}
											 </textarea><br>
										</td>
									</tr>
									<tr>
										<td>
										Price: <input type="number" id = "input-button" name="price" step = "0.01" value="${product.price}"
										placeholder="Price" required min=1
										title="Enter a price for the product" /> <br> 
										</td>
									</tr>
									<tr>
										<td>
											Discount percent: <input type="number" id = "input-button" name="discountPercent" 
											value="${product.discountPercent}" placeholder="Discount %"
											title="Enter discount % for this product" /> <br>
										</td>
									</tr>
									<tr>
										<td>
											Availability: <input type = "number" id = "input-button" name = "availability" value = "${product.availability}" placeholder = "Availability" 
											required min = 0 title = "Enter the product availability"> <br>
										</td>
									</tr>
									<tr>
										<td>
										Select product category: 
										<select name="categoryName">
										<c:forEach items="${ categories }" var="category">
										<option>${category.categoryName}</option>
										</c:forEach>
										</select> <br> <br>
										</td>
									</tr>
								</tbody>
							</table>
							<table>
								<tr>
									<td>
									<h3>Characteristics menu </h3><br>
										Choose characteristic: 
										<select name = "chars">
										<c:forEach items ="${characteristics}" var = "characteristic">
											<option>${characteristic.name}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>
									Enter characteristic unit:
									<input type="text" id = "input-button" name="unit" placeholder="Unit" title="Enter the characteristic unit" 
									style = "margin-right: 10px;"/> <br>
									</td>
									<td>		
									Enter characteristic value:	 
									<input type="text" id = "input-button" name="value" placeholder="Value" title="Enter the characteristic value" /> <br> 
									</td>
								</tr>
								<tr>
									<td>
									<input type="submit" id = "my-button" value="Save product" id = "my-button"/> <br>
									</td>
								</tr>
							</table>
						</form>
						</c:if>
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