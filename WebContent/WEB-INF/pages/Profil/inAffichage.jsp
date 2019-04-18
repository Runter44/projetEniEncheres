<!-- <link rel="stylesheet" type="text/css" href="/WEB-INF/css/myPage.css"> -->

	<div class="centrerElement">
		<label class="textAlign">Nom:</label>${sessionScope.user.getNom()}<br>
		<label class="textAlign">Prénom:</label>${sessionScope.user.getPrenom()}<br>
		<label class="textAlign">Pseudo:</label>	${sessionScope.user.getPseudo()}<br>
		<label class="textAlign">Points:</label>	${sessionScope.user.getCredit()}<br>
		<label class="textAlign">Email:</label>		${sessionScope.user.getEmail()}<br>
		<label class="textAlign">Telephone:</label>	${sessionScope.user.getTelephone()}<br>
		<label class="textAlign">Rue:</label>		${sessionScope.user.getRue()}<br>
		<label class="textAlign">CP:</label>		${sessionScope.user.getCodePostal()}<br>
		<label class="textAlign">Ville:</label>		${sessionScope.user.getVille()}<br>
	</div>	
	<form method="post" action="">
		<input type="submit" value="Modifier" name="modif" class="btn"> 
		<input type="submit" value="Supprimer compte" class="btn">
	</form>
	