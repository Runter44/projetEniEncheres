<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<head>
<script src="/projetEniEncheres/js/listeEncheres.js"
	type="text/javascript"></script>
</head>
<body class="bg-light" onload="gestionBtnRadCheckBox();">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre"
				href="/projetEniEncheres">ENI-Ench�res</a>
			<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
		</nav>
		<div class="paddingX10 text-left">
			<h1 class="text-center">Listes des encheres</h1>
			<br>
			<form method="post" action="liste-encheres">
				<label for="filtreNomArticle">Filtres :</label> <input
					id="filtreNomArticle" type="text" name="filtreNomArticle"
					class="form-control" placeholder="Le nom de l'article contient"
					value="${filtreNomArticle}"><br> <label for="">Categorie
					:</label> <select id="categorieValue" name="categorieValue"
					class="custom-select">
					<c:forEach var="cat" items="${listeCat}">
						<option value="${cat.getNoCategorie()}">${cat.getLibelle()}</option>
					</c:forEach>
				</select> <br> <br>
				<c:if test="${currentUser != null}">
					<div class="row">
						<div class="col-md-6">
							<input type="radio" id="radAchats" name="grpBtnRad"
								value="achats" onclick="gestionBtnRadCheckBox();" checked>&nbsp;&nbsp;<label
								for="radAchats">Achats</label><br>&nbsp;&nbsp;&nbsp;<input
								type="checkBox" id="checkOuvertes" name="checkOuvertes" value="ouvertes" checked>&nbsp;&nbsp;<label
								for="checkOuvertes">Ench�res ouvertes</label><br>
							&nbsp;&nbsp;&nbsp;<input type="checkBox" id="checkMesEncheres" name="checkMesEncheres"
								value="mesEncheres">&nbsp;&nbsp;<label
								for="checkMesEncheres">Mes ench�res en cours</label><br>&nbsp;&nbsp;&nbsp;<input
								type="checkBox" id="checkRemporter" name="checkRemporter" value="remporter">&nbsp;&nbsp;<label
								for="checkRemporter">Mes Ench�res remport�es</label>
						</div>
						<div class="col-md-6">
							<input type="radio" id="radMesVentes" name="grpBtnRad"
								value="mesVentes" onclick="gestionBtnRadCheckBox();">&nbsp;&nbsp;<label
								for="radMesVentes">Mes ventes</label><br>
							&nbsp;&nbsp;&nbsp;<input type="checkBox" id="checkEnCours" name="checkEnCours"
								value="enCours">&nbsp;&nbsp;<label for="checkEnCours">Mes
								ventes en cours</label><br>&nbsp;&nbsp;&nbsp;<input
								type="checkBox" id="checkNonDebuter" name="checkNonDebuter" value="nonDebuter">
							&nbsp;&nbsp;<label for="checkNonDebuter">ventes non d�but�es</label><br>
							&nbsp;&nbsp;&nbsp;<input type="checkBox" id="checkTerminer" name="checkTerminer"
								value="terminer">&nbsp;&nbsp;<label for="checkTerminer">
								ventes termin�es</label>
						</div>
					</div>
				</c:if>
				<br>
				<div class="text-center">
					<input type="submit" class="btn btn-outline-secondary"
						value="Rechercher">
				</div>
				<hr>
				<div class="row d-flex justify-content-center">
					<c:forEach items="${ lesArticles }" var="article"
						varStatus="status">
						<div class="col-md-5 h-20 m-3 cardEnchere">

							<div class="row">
								<div class="col-md-6 col-sm-6">
									<img class="img-fluid" alt="img de l'objet"
										src="${ article.imagePath }">
								</div>
								<div class="col-md-6 col-sm-6">
									<a
										href="/projetEniEncheres/detail-vente/${ article.noArticle }">
										${ article.nomArticle } </a> <br> <label> Prix : ${ article.prixVente }
									</label> <br> <label> Fin de l'ench�res : ${ article.datesFinEncheres }
									</label> <br> <label> Vendeur : <br><a
										href="/projetEniEncheres/profil/${article.vendeur.id }">
											${ article.vendeur.pseudo } </a>
									</label>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</form>
		</div>
	</div>
</body>
</html>