<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>eMAG</title>
</head>
<body>
<div align="center">
	<h3>Enter the product details!</h3>
		<form action="addProduct" method="POST">

			<input type="text" name="brand" placeholder="Brand" required
				title="Enter the product brand" /> <br> 
				<input type="text" name="model" placeholder="Model" required
				title="Enter the product model" /> <br> 
				<input type="text" name="description" placeholder="Description"
				title="Enter the product brand" /> <br> 
				<input type="number" name="price" placeholder="Price" required min=1
				title="Enter a price for the product" /> <br> 
				Is the product in stock?<input type="radio" id="Yes" name="availability"
				placeholder="Availability" required	title="Is the product available?" /> <label for="Yes">Yes</label> 
				<input	type="radio" id="No" name="availability" placeholder="Availability"
				required title="Is the product available?" /> <label for="No">No</label> <br> 
				Select product category: <select name="categoryName">
				<c:forEach items="${ categories }" var="category">
					<option>${category.categoryName}</option>
				</c:forEach>
			</select> <br>
			 
			<input type="submit" name="addProduct" value="Create Product" /> <br>
		</form>
		
		<!-- <form action="./uploadProductPicture" method="POST" enctype="multipart/form-data" accept="image/*">
			<input type="file" name="image"><br>
			<input type="submit" value="Choose product picture">
		</form> -->
		Go back to <a href="index">main page?</a>							 	
	</div>		
</body>
</html>