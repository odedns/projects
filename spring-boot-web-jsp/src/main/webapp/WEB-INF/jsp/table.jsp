<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
 

<t:layout>    
<h1>Tables</h1>


<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First Name</th>
      <th scope="col">Last Name</th>
      <th scope="col">Email</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach var="cust" items="${customers}" varStatus="loop">
  		<tr>
  		  	<th scope="row">${loop.index }</th>
      		<td>${cust.firstName }</td>
      		<td>${cust.lastName }</td>
      		<td>${cust.email }</td>
  		</tr>
  
  </c:forEach>
   </tbody>
</table>
</t:layout>
