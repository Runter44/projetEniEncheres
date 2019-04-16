
	<form method="post" action="">
		<label class="labelAlign" for="">Nom:</label><input type="text" name="paramCompteNom" value="${sessionScope.user.getNom()}"><br>
		<label class="labelAlign" for="">Prénom:</label><input type="text" name="paramComptePrenom" value="${sessionScope.user.getPrenom()}"><br>
		<label class="labelAlign" for="">Pseudo:</label><input type="text" name="paramComptePseudo" value="${sessionScope.user.getPseudo()}"><br>
		<label class="labelAlign" for="">Email:</label><input type="text" name="paramCompteEmail" value="${sessionScope.user.getEmail()}"><br>
		<label class="labelAlign" for="">Telephone:</label><input type="text" name="paramCompteTelephone" value="${sessionScope.user.getTelephone()}"><br>
		<label class="labelAlign" for="">Rue:</label><input type="text" name="paramCompteRue" value="${sessionScope.user.getRue()}"><br>
		<label class="labelAlign" for="">CP:</label><input type="text" name="paramCompteCP" value="${sessionScope.user.getCodePostal()}"><br>
		<label class="labelAlign" for="">Ville:</label><input type="text" name="paramCompteVille" value="${sessionScope.user.getVille()}"><br>
		<label class="labelAlign" for="">Mot de passe:</label><input type="password" name="paramCompteMDP"><br>
		<label class="labelAlign" for="">Confirmation:</label><input type="password" name="paramCompteConfirmationMDP"><br> 
		<input type="submit" value="Enregistrer" name="enregistrer" class="btn"> 
		<input type="submit" value="Supprimmer compte" class="btn">
	</form>
	<form method="post" action="/TrocEncheresV1/" >
		<input type="submit" value="Retour" class="btn">
	</form>