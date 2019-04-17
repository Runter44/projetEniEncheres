<%@include file="/WEB-INF/pages/Include/header.jsp"%>

<body>
	<c:if test="${requestScope.utilisateurInvalide}">
		CRÉATION DE COMPTE INVALIDE
	</c:if>
	${requestScope.Probleme}
	<div class="container-fluid">
		<div id="divCreationCompte" class="container">
			<h2 class="text-center">Création compte</h2>
			<div class="row">
				<div class="col-sm">
					<label for="nom" class="lblTitre">Nom :</label><input id="nom"
						class="form-control" type="text" required name="nomUtilisateur"
						value="${param.paramNouveauCompteNom}"><br> <label
						for="prenom" class="lblTitre">Prénom :</label><input id="prenom"
						class="form-control" type="text" required name="prenomUtilisateur"
						value="${param.paramNouveauComptePrenom}"><br> <label
						for="pseudo" class="lblTitre">Pseudo :</label><input id="pseudo"
						class="form-control" type="text" required name="pseudoUtilisateur"
						value="${param.paramNouveauComptePseudo}"><br> <label
						for="email" class="lblTitre">Email :</label><input id="email"
						class="form-control" type="text" required name="emailUtilisateur"
						value="${param.paramNouveauCompteEmail}"><br> <label
						for="tel" class="lblTitre">Telephone :</label><input id="tel"
						class="form-control" type="text" name="telUtilisateur"
						value="${param.paramNouveauCompteTelephone}">
				</div>
				<div class="col-sm">
					<label for="rue" class="lblTitre">Rue :</label><input id="rue"
						type="text" class="form-control" required name="rueUtilisateur"
						value="${param.paramNouveauCompteRue}"><br> <label
						for="cp" class="lblTitre">CP :</label><input id="cp" type="text"
						class="form-control" required name="cpUtilisateur"
						value="${param.paramNouveauCompteCP}"><br> <label
						for="ville" class="lblTitre">Ville :</label><input id="ville"
						class="form-control" type="text" required name="villeUtilisateur"
						value="${param.paramNouveauCompteVille}"><br> <label
						for="mdp" class="lblTitre">Mot de passe :</label><input id="mdp"
						class="form-control" type="password" required
						name="mdpUtilisateur"><br> <label for="confirmation"
						class="lblTitre">Confirmation mot de passe :</label><input class="form-control"
						id="confirmation" type="password" name="mdpConfirmation">
				</div>
			</div>
			<hr>
			<div class="row text-center">
				<div class="col-sm">
					<form method="post" action="">
						<input type="submit" value="Confirmer"
							class="btn btn-outline-secondary">
					</form>
				</div>
				<div class="col-sm">
					<a href="connexion" class="btn btn-link">Annuler</a>
				</div>
			</div>
			<% if (request.getAttribute("error") != null) { %>
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
		  <strong>Des erreurs ont été détectées :</strong><br>
		  <%= request.getAttribute("error") %>
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
	<% } %>
			
		</div>
	</div>
</body>
</html>