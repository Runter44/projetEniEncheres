<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body>
	<div class="container-fluid fill-height">
		<nav class="navbarBasic navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">ENI-Enchères</a>
			<!--  <button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>-->

			<!--  <div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav mr-auto">
						
					</ul>
					<form class="form-inline my-2 my-lg-0">
						<input class="form-control mr-sm-2" type="search"
							placeholder="Search" aria-label="Search">
						<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
					</form>
				</div>-->
		</nav>
		<div class="divMainContent">
			<div id="connexionContent">
				<form method="post" action="connexion" class="formConnexion">
					<div class="form-group">
						<label for="login" class="lblTitre">Identifiant :</label> <input
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
								class=" btn btn-outline-secondary"> <br> <a
								href="creation-compte" class="btn btn-link">Créer un compte</a>
						</div>
					</div>
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
				</form>
			</div>
		</div>
	</div>
</body>
</html>