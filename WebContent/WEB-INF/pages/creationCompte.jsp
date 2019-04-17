<%@include file="/WEB-INF/pages/Include/header.jsp" %>

<body>
	<c:if test="${requestScope.utilisateurInvalide}">
		CRÉATION DE COMPTE INVALIDE
	</c:if>
	${requestScope.Probleme}
	<form method="post" action="">
		<label for="nom" class="lblTitre">Nom:</label><input id="nom" type="text" name="nomUtilisateur" value="${param.paramNouveauCompteNom}"><br>
		<label for="prenom" class="lblTitre">Prénom:</label><input id="prenom" type="text" name="prenomUtilisateur" value="${param.paramNouveauComptePrenom}"><br>
		<label for="pseudo" class="lblTitre">Pseudo:</label><input id="pseudo" type="text" name="pseudoUtilisateur" value="${param.paramNouveauComptePseudo}"><br>
		<label for="email" class="lblTitre">Email:</label><input id="email" type="text" name="emailUtilisateur" value="${param.paramNouveauCompteEmail}"><br>
		<label for="tel" class="lblTitre">Telephone:</label><input id="tel" type="text" name="telUtilisateur" value="${param.paramNouveauCompteTelephone}"><br>
		<label for="rue" class="lblTitre">Rue:</label><input id="rue" type="text" name="rueUtilisateur" value="${param.paramNouveauCompteRue}"><br>
		<label for="cp" class="lblTitre">CP:</label><input id="cp" type="text" name="cpUtilisateur" value="${param.paramNouveauCompteCP}"><br>
		<label for="ville" class="lblTitre">Ville:</label><input id="ville" type="text" name="villeUtilisateur" value="${param.paramNouveauCompteVille}"><br>
		<label for="mdp" class="lblTitre">Mot de passe:</label><input id="mdp" type="password" name="mdpUtilisateur"><br>
		<label for="confirmation" class="lblTitre">Confirmation:</label><input id="confirmation" type="password" name="paramNouveauCompteConfirmationMDP"><br> 
		<input type="submit" value="CreationCompte" class="btn">
		<!-- <input type="submit" value="Annuler"> -->
	</form>
	
	<form method="get" action="Connexion" class="btn">
		<input type="submit" value="Annuler" class="btn">
	</form>

</body>
</html>