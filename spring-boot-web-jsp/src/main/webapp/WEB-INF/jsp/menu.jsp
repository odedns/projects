

 <div class="container-fluid">


  <nav class="navbar navbar-inverse">

    <div class="navbar-header">
      <a class="navbar-brand" href="#">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <ul class="nav navbar-nav">
     <li class="active"><a href="/">Home</a></li>
     <li><a href="/customer/customers">Table</a></li>
     <li><a href="/form">Form</a></li>
     <li><a href="/test">Test</a></li>
     
     <li><a onclick="$('#logoutForm').submit(); return false;" href="#">Logout</a></li>
   
    </ul>

</nav>
  </div>
<!--  form for logout -->
 	<form id="logoutForm" action="/logout" method="post">
 		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />     
     </form>
