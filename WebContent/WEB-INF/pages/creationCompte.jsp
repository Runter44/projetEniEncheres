<%@include file="/WEB-INF/pages/Include/header.jsp" %>

<body>
	<c:if test="${requestScope.utilisateurInvalide}">
		CRÉATION DE COMPTE INVALIDE
	</c:if>
	${requestScope.Probleme}
	<form method="post" action="">
		<label for="nom" class="labelAlign">Nom:</label><input id="nom" type="text" name="paramNouveauCompteNom" value="${param.paramNouveauCompteNom}"><br>
		<label for="prenom" class="labelAlign">Prénom:</label><input id="prenom" type="text" name="paramNouveauComptePrenom" value="${param.paramNouveauComptePrenom}"><br>
		<label for="pseudo" class="labelAlign">Pseudo:</label><input id="pseudo" type="text" name="paramNouveauComptePseudo" value="${param.paramNouveauComptePseudo}"><br>
		<label for="email" class="labelAlign">Email:</label><input id="email" type="text" name="paramNouveauCompteEmail" value="${param.paramNouveauCompteEmail}"><br>
		<label for="tel" class="labelAlign">Telephone:</label><input id="tel" type="text" name="paramNouveauCompteTelephone" value="${param.paramNouveauCompteTelephone}"><br>
		<label for="rue" class="labelAlign">Rue:</label><input id="rue" type="text" name="paramNouveauCompteRue" value="${param.paramNouveauCompteRue}"><br>
		<label for="cp" class="labelAlign">CP:</label><input id="cp" type="text" name="paramNouveauCompteCP" value="${param.paramNouveauCompteCP}"><br>
		<label for="ville" class="labelAlign">Ville:</label><input id="ville" type="text" name="paramNouveauCompteVille" value="${param.paramNouveauCompteVille}"><br>
		<label for="mdp" class="labelAlign">Mot de passe:</label><input id="mdp" type="password" name="paramNouveauCompteMDP"><br>
		<label for="confirmation" class="labelAlign">Confirmation:</label><input id="confirmation" type="password" name="paramNouveauCompteConfirmationMDP"><br> 
		<input type="submit" value="CreationCompte" class="btn">
		<!-- <input type="submit" value="Annuler"> -->
	</form>
	
	<form method="get" action="Connexion" class="btn">
		<input type="submit" value="Annuler" class="btn">
	</form>

</body>
</html>