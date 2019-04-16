<%@include file="/WEB-INF/pages/Include/header.jsp" %>
<!-- <link rel="stylesheet" type="text/css" href="/css/monCss.css"/> -->
<!-- jumbotron text-center -->
<body class="body" style="text-align: center;">
	<div class="aa">
		<form method="post" action="Connexion" class="formConnexion">
			<p>Identifiant:</p>
			<input type="text" name="paramLoginUtilisateur"
				class="monHref" placeholder="nom de compte"><br>
			<p>Mot de passe:</p>
			<input type="password" name="paramMotDePasseUtilisateur" class="monHref"
				placeholder="mot de passe"><br> 
				<label style="font-size: 15px">Se souvenir de moi<input
				type="checkbox" value="Se souvenir de moi" name="paramRememberMe"
				class="" style="font-size: 1px"></label><br> 
				<input type="submit"
				value="Connexion" class="btn"><br>
		</form>
	</div>
	<!-- lien mot de passe oublié-->
	<form method="get" action="CreationCompte" class="connexion">
		<input type="submit" value="Creer un compte" style="text-align: center" class="btn">
	</form>
	<!-- <input type="label" > -->
</body>
</html>