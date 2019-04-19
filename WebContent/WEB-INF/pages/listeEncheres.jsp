<%@include file="/WEB-INF/pages/Include/header.jsp" %>
<head>
	<script src="/projetEniEncheres/js/listeEncheres.js" type="text/javascript"></script>
</head>
<body>
	<form method="post" action="Creation">
		<h2 class="text-center">Listes des encheres</h2>
		<div>
			<label for="filtreNomArticle">Filtres :</label>
			<input id="filtreNomArticle" 
				type="text" 
				name="filtreNomArticle" 
				class="form-control" 
				placeholder="Le nom de l'article contient">
		
			<label for="">Categorie :</label>
			<select name="categorieValue" class="NA">
				<c:forEach var="cat" items="${listeCat}">
					<option value="${cat.getNoCategorie()}">${cat.getLibelle()}</option>
				</c:forEach>
			</select>
		</div>
		<c:if test="${currentUser != null}">
			<div>
				<input type="radio" id="radAchats" name="grpBtnRad" value="achats" onclick="gestionCheckBox()">
				<label for="radAchats">Achats</label>
					<input type="checkBox" id="checkOuvertes" value="ouvertes">
					<label for="checkOuvertes">Enchères ouvertes</label>
					<input type="checkBox" id="checkMesEncheres" value="mesEncheres">
					<label for="checkMesEncheres">Mes enchères en cours</label>
					<input type="checkBox" id="checkRemporter" value="remporter">
					<label for="checkRemporter">Mes Enchères remportées</label>
				
				<input type="radio" id="radMesVentes" name="grpBtnRad" value="mesVentes" onclick="gestionCheckBox()">
				<label for="radMesVentes">Mes ventes</label>
					<input type="checkBox" id="checkEnCours" value="enCours">
					<label for="checkEnCours">Mes ventes en cours</label>
					<input type="checkBox" id="checkNonDebuter" value="nonDebuter">
					<label for="checkNonDebuter">Mes ventes non débutées</label>
					<input type="checkBox" id="checkTerminer" value="terminer">
					<label for="checkTerminer">Mes ventes terminées</label>
			</div>
		</c:if>
		<c:if test="${true}">	
			<div>
				<div>
					<img alt="img de l'objet" src="">
				</div>
				<div>
					<a>Lien vers l'objet</a>
					<label>Prix :</label>
					<label>Fin de l'enchères :</label>
					<label>Vendeur :</label>
				</div>
			</div>
		</c:if>
		
		<c:if test="${true}">
			<div>
				<div>
					<img alt="img de l'objet" src="">
				</div>
				<div>
					<a>Lien vers l'objet</a>
					<label>Prix :</label>
					<label>Fin de l'enchères :</label>
					<label>Vendeur :</label>
				</div>
			</div>
		</c:if>	
			
			
			

		<input type="submit" value="Rechercher"> 
		
	</form>
</body>
</html>