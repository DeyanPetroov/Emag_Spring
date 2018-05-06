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
			Brand: <input type="text" name="brand" placeholder="Brand" required title="Enter the product brand" /> <br> 
			Model: <input type="text" name="model" placeholder="Model" required title="Enter the product model" /> <br> 
			Description: <input type="text" name="description" placeholder="Description" title="Enter the product description" /> <br> 
			Price: <input type="number" name="price" step = "0.01" placeholder="Price" required min=1 title="Enter a price for the product" /> <br> 
			Availability: <input type = "number" name = "availability" placeholder = "Availability" required min = 0 title = "Enter the product availability"> <br>
			
			Select product category: 
			<select name="categoryName">
				<c:forEach items="${ categories }" var="category">
					<option>${category.categoryName}</option>
				</c:forEach>
			</select> <br> <br>
			<h3>Characteristics menu </h3><br>
			Choose characteristic: 
			<select name="characteristic" id="chars">
			<c:forEach items ="${characteristics}" var = "characteristic">
				<option value = "">${characteristic.name}</option>
				</c:forEach>
			</select>
			<br>
			Enter characteristic unit:
			<input type="text" name="unit" placeholder="Unit" title="Enter the characteristic unit" /> <br> 		
			Enter characteristic value:	 
			<input type="text" name="value" placeholder="Value" required title="Enter the characteristic value" /> <br> 
			
			<input type="submit" name="addProduct" value="Create Product" /> <br>
		</form> 		
		Go back to <a href="index">main page?</a>							 	
	</div>		
</body>
</html>