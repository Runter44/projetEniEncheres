<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<!-- <link href="/pages/css/myPage.css" rel="stylesheet"> -->

<body class="aa">
	<span style="text-align: left">Bonjour
		${sessionScope.user.getPseudo()}</span>
	<br />
	<span id="filtres"></span>
	<span style="text-align: right;" ><a href="./Connexion?Disconnect=" class="liens">Déconnexion</a><a href="NouvelleVente" class="liens">Vendre un article</a> 
	<a href="./Profil" class="liens">Mon profil</a></span>

<form method="post" action="ServletRecherche">
	<div class="div" style="text-align: left">
		<h2>Filtres:</h2>
		<input type="checkbox" id="" name="paramVentes" <c:if test="${!empty param.paramVentes}">checked</c:if>>
		<label for="ventes">Mes ventes</label><br> 
		<input type="checkbox" id="" name="paramEncheresEncours" <c:if test="${!empty param.paramEncheresEncours}">checked</c:if>> 
		<label for="">Mes enchères en cours</label><br> 
		<input type="checkbox" id="" name="paramAquisitions" <c:if test="${!empty param.paramAquisitions}">checked</c:if>> 
		<label for="">Mes aquisitions</label><br> 
		<input type="checkbox" id="" name="paramAutresEncheres" <c:if test="${!empty param.paramAutresEncheres}">checked</c:if>> 
		<label for="">Autres enchères</label><br>

		Categorie:<select name="paramRechercheVenteCategorie" class="listeDeroulante">
	  				<option value="-1">tout</option>
					<c:forEach var="cat" items="${requestScope.listeCat}">
						<option value="${cat.getNoCategorie()}">${cat.getLibelle()}</option>
					</c:forEach>
				</select>

	</div>

	
		<input type="text" width="auto" name="paramBarreRecherche" class = "champsSaisie" placeholder="Nom d'un article"> 
		<input type="submit" value="Rechercher" class="btn" >
		<div class="d-block p-2 bg-white text-dark" >
		
			
			<!-- 	<input type="text" width="auto"> -->

			
	  
			
	</div>
	</form>
	<div>
		<%-- 		${requestScope.ventes} --%>
		<!--<div class="row">
			<c:forEach var="v" items="${requestScope.ventesRecherche}">
				<div class="elementListe">
					  <%@include file="/WEB-INF/pages/Include/VenteBloc.jsp"%>
				</div>
			</c:forEach>
		</div>-->


	</div>

</body>
</html>