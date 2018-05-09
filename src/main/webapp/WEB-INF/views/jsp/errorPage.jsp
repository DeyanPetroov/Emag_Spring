<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://localhost:8080/EMAG_Spring/errorPage">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/error-css.css" rel="stylesheet" type="text/css" media="all" />
<title>Error</title>
</head>
<body>
    <div class="container">
      <input type="checkbox" id="switch"/>
      <div class="ellipse"></div>
      <div class="ray"></div>
      <div class="head"></div>
      <div class="neck"></div>
      <div class="body">
        <label for="switch"></label>
      </div>
    </div>
    <div class="container">
      <div class="msg msg_1">Oh no!</div>
      <div class="msg msg_2">Something went wrong!</div>
    </div>
    <h2 align="center" style="color: white">Why not go to our <a style="color: white" href="${pageContext.request.contextPath}/index">main page?</a></h2>
</body>
</html>