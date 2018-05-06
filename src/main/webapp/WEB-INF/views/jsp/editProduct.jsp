<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				 <a href="adminPage">Admin page</a>
				 <a href="addProduct">Add a new product</a> 
				 <a href="#">Make product on sale</a> 
				 <a href="#">Delete user</a>
			</div>
			</div>
    <div id="rightCol">
	<c:if test = "${sessionScope.user != null }">
	<div id = "profile">
	<div class="login">
			<h3>Edit products</h3>
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
						<img src="download/${productPicture}" height = 200px width = 200px>
							<form action = "${pageContext.request.contextPath}/uploadProductPicture" method = "POST" enctype="multipart/form-data" accept="image/*">
								<input type = "file" name = "image"><br>
								<input type = "hidden" name = "productID" value ="${product.productID}">
								<input type = "submit" value = "Change picture">
							</form>
							<form action="${pageContext.request.contextPath}/editProduct/${product.productID}" method="POST">
							<table class="table table-user-information">
								<tbody>
									<tr>
										<td>Brand:
										 <input type="text" name="brand" value="${product.brand}" placeholder="Brand"
										required title="Enter the product brand"/> <br>
									   </td>
									</tr>
									<tr>
										<td>
		 								Model: <input type="text" name="model" value="${product.model}" placeholder="Model" 
		 								required title="Enter the product model" />
										</td>
									</tr>
									<tr>
										<td>
										Description: <input type="text" name="description" value="${product.description} 
										placeholder="Description" title="Enter the product description" /> <br> 
										</td>
									</tr>
									<tr>
										<td>
										Price: <input type="number" name="price" step = "0.01" value="${product.price}"
										placeholder="Price" required min=1
										title="Enter a price for the product" /> <br> 
										</td>
									</tr>
									<tr>
										<td>
											Discount percent: <input type="number" name="discountPercent" 
											value="${product.discountPercent}" placeholder="Discount %"
											title="Enter discount % for this product" /> <br>
										</td>
									</tr>
									<tr>
										<td>
											Availability: <input type = "number" name = "availability" value = "${product.availability}" placeholder = "Availability" 
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
										<select name="characteristic" id="chars">
										<c:forEach items ="${characteristics}" var = "characteristic">
											<option value = "">${characteristic.name}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>
									Enter characteristic unit:
									<input type="text" name="unit" placeholder="Unit" title="Enter the characteristic unit" 
									style = "margin-right: 10px;"/> <br>
									</td>
									<td>		
									Enter characteristic value:	 
									<input type="text" name="value" placeholder="Value" required title="Enter the characteristic value" /> <br> 
									</td>
								</tr>
								<tr>
									<td>
									<input type="submit" value="Save product" /> <br>
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