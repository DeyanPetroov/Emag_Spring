<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>eMAG - Admin</title>
</head>
<body>

	<c:if test="${sessionScope.user != null}">
			<form action = "./uploadProfilePicture" method = "POST" enctype="multipart/form-data" accept="image/*">
				<input type = "file" name = "image"><br>
				<input type = "submit" value = "Change picture">
			</form>
			<form action="${pageContext.request.contextPath}/editProduct/${product.productID}" method="POST">
				<input type="text" name="brand" value="${product.brand}" placeholder="Brand"
					required title="Enter the product brand"/> <br>
					
				<input type="text" name="model" value="${product.model}" placeholder="Model"
					required title="Enter the product model"/> <br>
					
				<input type="text" name="description" value="${product.description}" placeholder="Description"
					title="Enter the product brand"/> <br>

				<input type="number" step="0.01" name="price" value="${product.price}" placeholder="Price"
					required min ="1" title="Enter a price for the product"/> <br>
					
				<input type="number" name="discountPercent" value="${product.discountPercent}" placeholder="Discount %"
					title="Enter discount % for this product" /> <br>
					
				Availability: <input type = "number" name = "availability" value = "${product.availability}" placeholder = "Availability" 
				required min = 0 title = "Enter the product availability"> <br>

					
				Select product category: <select name="categoryName">
					<c:forEach items="${ categories }" var="category">
						 <option>${category.categoryName}</option>
					</c:forEach>				    
			    </select> <br>
			    
			    Choose characteristic: <select name="characteristic" id="chars">
				<c:forEach items="${characteristics}" var="characteristic">
					<option value="">${characteristic.name}</option>
				</c:forEach>
				</select> <br> 
				Enter characteristic unit:
				<input type="text" name="unit" placeholder="Unit" title="Enter the characteristic unit" /> <br> 
				Enter characteristic value: 
				<input type="text"name="value" placeholder="Value" required title="Enter the characteristic value" /> <br>
				<input type="submit" value="Save Product" /> <br> <br>	
			</form>
		</c:if>
		
		<c:if test="${sessionScope.user == null}">
			<c:redirect url = "login">Please log in.</c:redirect>
	</c:if>	

</body>
</html>