<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>

</head>
 <%@ include file="header.jsp" %>

<body>
	<div class="container" style="margin:50px">
		<h3>Spring Security Login Example</h3>
		<c:if test="${param.error ne null}">
			<div style="color: red">Invalid credentials.</div>
		</c:if>
		<form action="/login" method="post">
			<div class="form-group">
				<label for="username">UserName:</label> 
				<input type="text"class="form-control" id="username" name="username">
			</div>
			<div class="form-group">
				<label for="pwd">Password:</label> 
				<input type="password"class="form-control" id="pwd" name="password">
			</div>

			<button type="submit" class="btn btn-success">Submit</button>

			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		</form>
		
		
		
	</div>
</body>

</body>
</html>