<%@include file="/WEB-INF/pages/Include/header.jsp" %>

<body>
	<form method="post" action="Creation">
		<h2 class="text-center">Listes des encheres</h2>
		<div>
			<label for="filtreValue">Filtres :</label>
			<input id="filtreValue" 
				type="text" 
				name="filtreValue" 
				class="form-control" 
				placeholder="Le nom de l'article contient">
		
			<label for="">Categorie :</label>
			<select name="categorieValue" class="NA">
				<c:forEach var="cat" items="${requestScope.listeCat}">
					<option value="${cat.getNoCategorie()}">${cat.getLibelle()}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			<input type="radio" name="radAchats" value="achats">
			
			<label for="">Achats</label>
				<input type="checkBox" name="checkOuvertes" value="ouvertes">
				<label for="checkOuvertes">Enchères ouvertes</label>
				<input type="checkBox" name="checkMesEncheres" value="mesEncheres">
				<label for="checkMesEncheres">Mes enchères en cours</label>
				<input type="checkBox" name="checkRemporter" value="remporter">
				<label for="checkRemporter">Mes Enchères remportées</label>
				<input type="radio" name="radMesVentes" value="ventes">
			
			<label for="">Mes ventes</label>
				<input type="checkBox" name="checkEnCours" value="enCours">
				<label for="checkEnCours">Mes ventes en cours</label>
				<input type="checkBox" name="checkNonDebuter" value="nonDebuter">
				<label for="checkNonDebuter">Mes ventes non débutées</label>
				<input type="checkBox" name="checkTerminer" value="terminer">
				<label for="checkTerminer">Mes ventes terminées</label>
		</div>
		
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