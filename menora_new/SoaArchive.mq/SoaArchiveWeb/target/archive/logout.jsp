<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>

<%
	request.logout();
	response.sendRedirect(request.getContextPath() + "/");
%>
