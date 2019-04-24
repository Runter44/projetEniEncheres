<%@include file="/WEB-INF/pages/Include/header.jsp"%> 
<%@ page import="fr.eni.encheres.bo.Article"%>
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
			<div class="row border rounded">
				<div class="col-md-4">
					<img class="img-fluid" alt="img de l'objet" src="http://experia-agency.com/wp-content/uploads/2016/06/ench-pict-2.jpg">
				</div>
				<div class="col-md-8">
				<table class="table">
					<tbody>
						<tr>
							<th scope="row" class="text-right w-50">Nom de l'article : </th>
							<td class="w-50">${Enchere.article.nom_article}</td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Description :</th>
							<td>${Enchere.article.desciption}</td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Mise prix :</th>
							<td>${Enchere.article.miseAPrix}</td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Meilleure offre :</th>
							<td></td>
						</tr>
					</tbody>
				</table>
				</div>				
			</div>
			
			
			</form>
		</div>
	</div>


</body>
</html>