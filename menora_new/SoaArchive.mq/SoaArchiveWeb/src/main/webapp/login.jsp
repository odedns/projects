<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<!DOCTYPE html>
<html lang="he" class="fullPage">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>Sign In</title>
	
	<script src="<%=request.getContextPath()%>/jquery-1.11.2/jquery-1.11.2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		jQuery.noConflict();
	</script>
	<script	src="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/webcomponentsjs/webcomponents.js" type="text/javascript"></script>
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/paper-input/paper-input.html" />
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/paper-card/paper-card.html" />
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/paper-button/paper-button.html" />
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/paper-drawer-panel/paper-drawer-panel.html" />
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/iron-flex-layout/iron-flex-layout.html" />
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/iron-a11y-keys/iron-a11y-keys.html" />
	<link rel="import" href="<%=request.getContextPath()%>/SoaArchiveClient/bower_components/iron-a11y-keys-behavior/iron-a11y-keys-behavior.html" />
	
	<link rel="import" href="<%=request.getContextPath()%>/shared-styles.html" />
	<style is="custom-style" include="shared-styles"></style>
</head>

<body>
	<form id="form" class="flex-center-justified" action="j_security_check" method="post">
		<paper-card  image="<%=request.getContextPath()%>/images/menoraLogo.png">
			<div class="card-content">
				<paper-input label="User Name" name="j_username"></paper-input>
				<paper-input label="Password" name="j_password"></paper-input>
			</div>
			<div class="card-actions">
				<%
					String errorCode = request.getParameter("errorCode");
					if (errorCode != null) {
						if (errorCode.equals("invalidCredentials")) {
				%>
				<div>שם המשתמש או הסיסמא אינם נכונים.</div>
				<%
					} else if (errorCode.equals("invalidLoginSession")) {
				%>
				<div>זמן ההתחברות פג, אנא נסה שנית.</div>
				<%
					}
					}
				%>
					<br />
				<paper-button class="loginButton" raised>Login</paper-button>
			</div>
		</paper-card>
	</form>
	
	<script>
		var button = document.querySelector('paper-button');
		button.addEventListener('tap', function() {
			document.querySelector('form').submit();
		});
		
	</script>
</body>
</html>
