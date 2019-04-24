<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.dal.DAOFactory"%>
<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre"
				href="/projetEniEncheres">ENI-Enchères</a>
			<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
		</nav>
		<div class="paddingX10">
			<h1>Modifier l'article</h1>
			<c:if test="${ requestedArticle != null }">
				<c:if test="${error != null}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Des erreurs ont été détectées :</strong><br>
						<%=request.getAttribute("error")%>
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				<form method="post" action="">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="articleName">Nom de l'article</label> <input
								type="text" class="form-control" id="articleName"
								placeholder="Nom de l'article" name="articleName" maxlength="30"
								required="required" value="${ requestedArticle.nomArticle }">
						</div>
						<div class="form-group col-md-6">
							<label for="articleCategorie">Catégorie de l'article</label> <select
								class="form-control" id="articleCategorie"
								name="articleCategorie">
								<c:forEach items="<%=(new CategorieManager()).getAllCat()%>"
									var="categorie">
									<c:set var="isSelected" value="${ categorie.noCategorie == requestedArticle.cat.noCategorie ? \"selected\" : \"\" }"></c:set>
									<option value="${ categorie.getNoCategorie() }" ${ isSelected }>${ categorie.getLibelle() }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group col-md-6 ">
							<label for="articleDescription">Description de l'article</label>
							<textarea maxlength="300" class="form-control"
								id="articleDescription" name="articleDescription"
								placeholder="Description de l'article" rows="5"
								required="required">${ requestedArticle.description }</textarea>
						</div>
						<div class="form-group col-md-6">
							<div class="form-row mb-4">
								<c:set var="now" value="<%=new Date()%>" />
								<div class="col-md-6">
									<label for="articleDebutEnchere">Début de l'enchère</label> <input
										type="date" class="form-control" id="articleDebutEnchere"
										name="articleDebutEnchere" placeholder="AAAA-MM-JJ"
										required="required"
										min="<fmt:formatDate value="${now}" type="date" pattern="yyyy-MM-dd"/>"
										value="<fmt:formatDate value="${requestedArticle.datesDebutEncheres}" type="date" pattern="yyyy-MM-dd"/>">
								</div>
								<div class="col-md-6">
									<label for="articleFinEnchere">Fin de l'enchère</label> <input
										type="date" class="form-control" id="articleFinEnchere"
										name="articleFinEnchere" placeholder="AAAA-MM-JJ"
										required="required"
										min="<fmt:formatDate value="${now}" type="date" pattern="yyyy-MM-dd"/>"
										value="<fmt:formatDate value="${requestedArticle.datesFinEncheres}" type="date" pattern="yyyy-MM-dd"/>">
								</div>
							</div>
							<div>
								<label for="articlePrix">Prix de départ</label> <input
									type="number" class="form-control" id="articlePrix"
									name="articlePrix" required="required" min="0"
									placeholder="Prix de départ" value="${ requestedArticle.miseAPrix }">
							</div>
						</div>
						<fieldset class="col-md-12">
							<legend>Lieu de retrait</legend>
							<div class="form-group">
								<label for="articleRetraitRue">Adresse</label> <input
									type="text" class="form-control" id="articleRetraitRue"
									name="articleRetraitRue" required="required"
									placeholder="Adresse du lieu de retrait" maxlength="150"
									value="${ requestedArticle.rue }">
							</div>
							<div class="form-row">
								<div class="form-group col-md-3">
									<label for="articleRetraitRue">Code postal</label> <input
										type="text" class="form-control" id="articleRetraitCodePostal"
										name="articleRetraitCodePostal" placeholder="Code postal"
										maxlength="10"
										value="${ requestedArticle.codePostal }">
								</div>
								<div class="form-group col-md-9">
									<label for="articleRetraitRue">Ville</label> <input type="text"
										class="form-control" id="articleRetraitVille"
										name="articleRetraitVille" placeholder="Nom de la ville"
										maxlength="150"
										value="${ requestedArticle.ville }">
								</div>
							</div>
						</fieldset>
						<div class="col-md-12 text-center">
							<input type="submit" value="Sauvegarder" class="btn btn-success">
							<a href="/projetEniEncheres/supprimer-article/${ requestedArticle.noArticle }" class="btn btn-danger" onclick="return confirm('Êtes-vous sûr de bien vouloir supprimer cet article ? Toutes les enchères associées seront supprimées.');">Supprimer la vente</a>
						</div>
					</div>
				</form>
			</c:if>
			<c:if test="${ requestedArticle == null }">
				<p class="text-danger">L'article demandé n'existe pas ou ne vous appartient pas.</p>
			</c:if>
		</div>
	</div>
	<%@include file="/WEB-INF/pages/Include/footer.jsp"%>
</body>
</html>