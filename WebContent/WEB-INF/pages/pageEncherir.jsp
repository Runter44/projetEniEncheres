<%@include file="/WEB-INF/pages/Include/header.jsp"%> 
<%@ page import="fr.eni.encheres.bo.Article"%>
<%
	Article article = (Article) request.getAttribute("requestedArticle");
%>
<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre"
				href="/projetEniEncheres">ENI-Enchères</a>
			<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
		</nav>
		<div class="paddingX10 text-left">
			<h1 class="text-center">Détail vente</h1>
			<br>
			<form method="post" action="Creation">
			<div class="row">
				<div class="col-md-6">
					<img class="img-fluid" alt="img de l'objet" src="http://experia-agency.com/wp-content/uploads/2016/06/ench-pict-2.jpg">
				</div>
				<div class="col-md-6">
				<h3><%=article.getNomArticle()%></h3>
				</div>				
			</div>
			
			
			</form>
		</div>
	</div>


</body>
</html>