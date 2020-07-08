<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
 <%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
<h1>Forms</h1>

  <div class="container">
    <h3>Form example</h3>
	<form:form id="custForm" method="post" modelAttribute="customer" action="/customer/add">


	<div class="formContent">
    <div class="form-group">
      	<form:label class="col-lg-2 control-label" path="firstName"> 
						First Name
					</form:label>
					 <form:errors path="firstName" />
      <form:input path="firstName" class="form-control" placeholder="firstName"/>
      
    </div>
    <div class="form-group">
      	<form:label class="col-lg-2 control-label" path="lastName"> 
						last Name 
					</form:label>
					<form:errors path="lastName" />
      <form:input path="lastName" class="form-control" placeholder="lastName"/>
    
    </div>
  
    <div class="form-group">
    	<form:label class="col-lg-2 control-label" path="status"> 
						Status  
					</form:label>
      
      <form:select class="form-control" path="status">
    		<form:options items="${statusList}" />
	</form:select>
    
    </div>
     <div class="form-group">
        	<form:label class="col-lg-2 control-label" path="email"> 
						Email 
					</form:label>
					 <form:errors path="email" />
      <form:input path="email" class="form-control" placeholder="email"/>
    
     
    </div>
    <div class="form-group">
     <form:label class="col-lg-2 control-label" path="phone"> 
						Phone  <form:errors path="phone" />
					</form:label>
      <form:input path="phone" class="form-control" placeholder="phone"/>
     
     
    </div>
  
    
    
    
    <button type="submit" class="btn btn-primary">Submit</button>
    <button type="reset" class="btn btn-primary">Clear</button>
  </div>
  <p>${msg} </p>
  </form:form>
</div>

</t:layout>


 