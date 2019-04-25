<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<head>
<script src="/projetEniEncheres/js/listeEncheres.js"
	type="text/javascript"></script>
</head>
<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre"
				href="/projetEniEncheres">ENI-Enchères</a>
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
								value="achats" onclick="gestionCheckBox();">&nbsp;&nbsp;<label
								for="radAchats">Achats</label><br>&nbsp;&nbsp;&nbsp;<input
								type="checkBox" id="checkOuvertes" value="ouvertes">&nbsp;&nbsp;<label
								for="checkOuvertes">Enchères ouvertes</label><br>
							&nbsp;&nbsp;&nbsp;<input type="checkBox" id="checkMesEncheres"
								value="mesEncheres">&nbsp;&nbsp;<label
								for="checkMesEncheres">Mes enchères en cours</label><br>&nbsp;&nbsp;&nbsp;<input
								type="checkBox" id="checkRemporter" value="remporter">&nbsp;&nbsp;<label
								for="checkRemporter">Mes Enchères remportées</label>
						</div>
						<div class="col-md-6">
							<input type="radio" id="radMesVentes" name="grpBtnRad"
								value="mesVentes" onclick="gestionCheckBox();">&nbsp;&nbsp;<label
								for="radMesVentes">Mes ventes</label><br>
							&nbsp;&nbsp;&nbsp;<input type="checkBox" id="checkEnCours"
								value="enCours">&nbsp;&nbsp;<label for="checkEnCours">Mes
								ventes en cours</label><br>&nbsp;&nbsp;&nbsp;<input
								type="checkBox" id="checkNonDebuter" value="nonDebuter">
							&nbsp;&nbsp;<label for="checkNonDebuter">Mes ventes non débutées</label><br>
							&nbsp;&nbsp;&nbsp;<input type="checkBox" id="checkTerminer"
								value="terminer">&nbsp;&nbsp;<label for="checkTerminer">Mes
								ventes terminées</label>
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
										src="http://experia-agency.com/wp-content/uploads/2016/06/ench-pict-2.jpg">
								</div>
								<div class="col-md-6 col-sm-6">
									<a
										href="/projetEniEncheres/detail-vente/${ article.noArticle }">
										${ article.nomArticle } </a> <br> <label> Prix : ${ article.prixVente }
									</label> <br> <label> Fin de l'enchères : ${ article.datesFinEncheres }
									</label> <br> <label> Vendeur : <a
										href="/projetEniEncheres/detail-vente/${ article.vendeur.id }">
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