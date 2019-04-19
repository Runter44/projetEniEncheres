<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.dal.DAOFactory"%>
<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre" href="/projetEniEncheres">ENI-Enchères</a>
			<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
		</nav>
		<div class="padding5X15">
			<h1>Nouvelle vente</h1>
			<form>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="articleName">Nom de l'article</label>
						<input type="text" class="form-control" id="articleName" placeholder="Nom de l'article">
					</div>
					<div class="form-group col-md-6">
						<label for="articleCategorie">Catégorie de l'article</label>
						<select type="text" class="form-control" id="articleCategorie">
						<c:forEach items="<%= (new CategorieManager()).getAllCat() %>" var="categorie">
							<option value="${ categorie.getNoCategorie() }">${ categorie.getLibelle() }</option>
						</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-6">
						<label for="articleDescription">Description de l'article</label>
						<textarea class="form-control" id="articleDescription" placeholder="Description de l'article">
						</textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 
	<h1>Nouvelle vente</h1>
	<p style="font-style: ">${requestScope["Probleme"]}</p>
	<form method="post" action="">
		<div>
			<label for="article" class="labelAlign"> Article:</label><input id="article" type="text" name="paramNouvelleVenteNom" class="champsFormulaire"><br>
			<label for="description" class="labelAlign">Description:</label><input id="description" type="text" name="paramNouvelleVenteDescription"><br>
			<label for="photo" class="labelAlign">Photo de l'article:</label><input id="photo" type="submit" name="bouton" value="UPLOADER" class="btn" ><input type="file" name="pic" accept="image/*" ><br>
			<label for="misePrix" class="labelAlign">Mise à prix:</label><input id="misePrix" type="number" name="paramNouvelleVentePrixInitial" min="1" value="100"><br>
			
       <label for="categorie" class="labelAlign">Categorie:</label>
       <select id="categorie" name="paramNouvelleVenteCategorie" style="margin-bottom: 5px;margin-left: 4px ">
      		<c:forEach var="cat" items="${requestScope.listeCat}">
				<option value="${cat.getNoCategorie()}">${cat.getLibelle()}</option>
			</c:forEach>
		</select><br>
		
		<label for="finEnchere" class="labelAlign" style="float: left;">Fin de l'enchère:</label>
		<input id="finEnchere" type="date" name="paramNouvelleVenteDate">
		
		</div>		
		<h2>Retrait (Optionnel)</h2>
		<div style="border-style:solid; border-color: black; width:310px">
			<label for="rue" class="labelAlign">Rue:</label><input id="rue" type="text" name="paramNouvelleVenteRue" value="${sessionScope.user.getRue()}"><br>
			<label for="cp" class="labelAlign">Code postal:</label><input id="cp" type="text" name="paramNouvelleVenteCodePostal" value="${sessionScope.user.getCodePostal()}"><br>	
			<label for="ville" class="labelAlign">Ville:</label><input id="ville" type="text" name="paramNouvelleVenteVille" value="${sessionScope.user.getVille()}"><br>
		</div>
		
		<div>
			<input type="submit" name="bouton" value="Publier" class="btn">
			<input type="submit" name="bouton" value="Annuler" class="btn">	
		</div>
	</form>	
	-->
</body>
</html>