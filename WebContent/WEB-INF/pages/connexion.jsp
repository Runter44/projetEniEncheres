<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<!-- <link rel="stylesheet" type="text/css" href="/css/monCss.css"/> -->
<!-- jumbotron text-center -->
<body class="body">
	<div class="container-fluid">
		<div class="container divConnexion">
			<div class="content">
				<form method="post" action="connexion" class="formConnexion">

					<label for="login" class="lblTitre">Identifiant :</label> <input
						id="login" type="text" name="login" class="form-control"
						placeholder="Nom de compte"><br> <label
						for="password" class="lblTitre">Mot de passe :</label> <input
						id="password" type="password" name="motDePasse"
						class="form-control" placeholder="Mot de passe"><br>


					<!--  <label style="font-size: 15px">Se souvenir de moi<input 
				type="checkbox" value="Se souvenir de moi" name="paramRememberMe"
				class="" style="font-size: 1px"></label><br> -->



					<div class="text-center">
						<input type="submit" value="Connexion"
							class=" btn btn-outline-secondary">
							<br>
						<a href ="creationCompte.jsp" class="btn btn-link">Créer un
						compte</a>
					</div>

				</form>

				<%
					if (request.getAttribute("errorConnexion") != null
							&& (boolean) request.getAttribute("errorConnexion") == true) {
				%>
				<div class="alert alert-danger alert-dismissible fade show"
					role="alert">
					<strong>Erreur</strong> Identifiant ou mot de passe incorrect
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<%
					}
				%>

			</div>
		</div>
	</div>
</body>
</html>