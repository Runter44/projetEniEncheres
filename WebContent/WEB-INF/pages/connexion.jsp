<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body>
	<div class="container-fluid fill-height">
		<nav class="navbarBasic navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="/projetEniEncheres">ENI-Enchères</a>
		</nav>
		<div class="divMainContent">
			<div id="connexionContent">
				<form method="post" action="connexion">
					<div class="form-group">
						<label for="login"  class="lblTitre">Pseudonyme :</label> <input
							id="login" type="text" name="login" class="form-control"
							placeholder="Nom de compte"><br> <label
							for="password" class="lblTitre">Mot de passe :</label> <input
							id="password" type="password" name="motDePasse"
							class="form-control" placeholder="Mot de passe"><br>
						<div class="form-group form-check">
							<input type="checkbox" class="form-check-input"
								value="Se souvenir de moi" id="rememberMe" name="rememberMe">
							<label for="rememberMe" class="form-check-label">Se
								souvenir de moi</label><br>
						</div>
						<div class="text-center">
							<input type="submit" value="Connexion"
								class="btn btn-outline-secondary"> <br> <a
								href="creation-compte" class="btn btn-link">Créer un compte</a>
						</div>
					</div>
					<%
						if (request.getAttribute("errorConnexion") != null
								&& (boolean) request.getAttribute("errorConnexion") == true) {
					%>
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur</strong> Pseudonyme ou mot de passe incorrect
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					
					
					<%
						}
					%>
				</form>
			</div>
		</div>
	</div>
</body>
</html>