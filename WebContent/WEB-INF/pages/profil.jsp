<%@include file="/WEB-INF/pages/Include/header.jsp"%>

<body>
	<div class="container-fluid fill-height">
		<nav class="navbarBasic navbarColor navbar navbar-expand-lg navbar-light position-absolute">
			<a class="navbar-brand text-light navbarColorTitre">ENI-Enchères</a>
		</nav>
		<div class="h-100 bg-light">
			<%=request.getAttribute("requestedUser")%>
		</div>
	</div>
</body>
</html>