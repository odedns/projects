<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	if (request.getQueryString() != null && request.getQueryString().contains("errorCode=")) {
		response.sendRedirect(request.getContextPath());
	}
%>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
	<!--meta name="viewport" content="width=device-width, initial-scale=1" /-->
	
	<title>SOA Archive UI</title>
	
	
	<!--  -   
	 <script   src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
	 -->
	 <script   src="<%=request.getContextPath()%>/jquery-1.12.4.min.js" ></script>
	<script src="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/webcomponentsjs/webcomponents.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/datatables.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/dataTables.material.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/dataTables.checkboxes.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/moment.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/datetime-moment.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/file-size.js"></script>

	
	<script src="<%=request.getContextPath()%>/SoaArchiveClient/SoaArchiveClient.nocache.js" type="text/javascript"></script>
	
	
	<link rel="import" href="<%=request.getContextPath()%>/shared-styles.html" />
	<style is="custom-style" include="shared-styles"></style>
</head>

<body>
	<iframe id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
</body>

</html>
