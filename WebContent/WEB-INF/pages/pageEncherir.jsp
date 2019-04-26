<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre"
				href="/projetEniEncheres">ENI-Enchères</a>
			<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
		</nav>
		<div class="paddingX10 text-left">
			<c:choose>
				<c:when test="${Date lt Enchere.article.datesFinEncheres}">
					<h1 class="text-center">Détail vente</h1>
				</c:when>
				<c:otherwise>
					<c:if
						test="${empty currentUser.id || currentUser.id != Enchere.user.id || currentUser.id != Enchere.article.vendeur.id}">
						<h1 class="text-center">L'enchère est terminée</h1>
					</c:if>
					<c:if test="${currentUser.id == Enchere.user.id}">
						<h3 class="text-center">Vous avez remporté la vente</h3>
					</c:if>
					<c:if test="${currentUser.id == Enchere.article.vendeur.id}">
						<h3 class="text-center">${Enchere.user.pseudo}a remporté
							l'enchère !</h3>
					</c:if>
				</c:otherwise>
			</c:choose>
			<br>
			<form method="post" action="">
				<div class="row border rounded">
					<div class="col-md-4">
						<img class="img-fluid" alt="img de l'objet"
							src="${Enchere.article.imagePath}">
					</div>
					<div class="col-md-8 text-center">
						<table class="table">
							<tbody>
								<tr>
									<th scope="row" class="text-right w-50">Nom de l'article :
									</th>
									<td class="w-50">${Enchere.article.nomArticle}</td>
								</tr>
								<tr>
									<th scope="row" class="text-right">Description :</th>
									<td>${Enchere.article.description}</td>
								</tr>
								<tr>
									<th scope="row" class="text-right">Description :</th>
									<td>${Enchere.article.cat.libelle}</td>
								</tr>
								<tr>
									<th scope="row" class="text-right">Mise prix :</th>
									<td>${Enchere.article.miseAPrix} pts</td>
								</tr>
								<tr>
									<th scope="row" class="text-right">Meilleure offre :</th>
									<c:choose>
										<c:when test="${Enchere.valeur == 0}">
											<td>Aucune enchère</td>
										</c:when>
										<c:otherwise>
											<td>${Enchere.valeur} pts par ${Enchere.user.pseudo}</td>
										</c:otherwise>
									</c:choose>
								</tr>
								<tr>
									<th scope="row" class="text-right">Fin de l'enchère :</th>
									<td>${Enchere.article.datesFinEncheres}</td>
								</tr>
								<tr>
									<th scope="row" class="text-right">Retrait :</th>
									<td>${Enchere.article.rue}<br>${Enchere.article.codePostal}
										${Enchere.article.ville}
									</td>
								</tr>
								<tr>
									<th scope="row" class="text-right">Vendeur :</th>
									<td>${Enchere.article.vendeur.pseudo}</td>
								</tr>
								<c:if test="${Date gt Enchere.article.datesDebutEncheres}">
									<c:if test="${Date gt Enchere.article.datesFinEncheres}">
										<c:if test="${currentUser.id == Enchere.user.id}">
											<tr>
												<th scope="row" class="text-right">Tél :</th>
												<c:choose>
													<c:when test="${empty Enchere.article.vendeur.telephone}">
														<td>Non renseigné</td>
													</c:when>
													<c:otherwise>
														<td>${Enchere.article.vendeur.telephone}</td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:if>
									</c:if>
								</c:if>
							</tbody>
						</table>
						<c:if test="${Date gt Enchere.article.datesDebutEncheres}">
							<c:if test="${Date lt Enchere.article.datesFinEncheres}">
								<c:if
									test="${currentUser != null && currentUser.id != Enchere.article.vendeur.id}">
									<div class="row">
										<div class="col-md-4 mt-2">
											<label>Ma proposition :</label>
										</div>
										<div class="col-md-4 mt-2">
											<input type="number" class="form-control" id="articlePrix"
												name="prixPropose" required="required" min="0"
												placeholder="Prix de départ">
										</div>
										<div class="col-md-4 mt-2">
											<input type="submit" value="Enchérir" class="btn btn-success">
										</div>
									</div>
								</c:if>
								<c:if
									test="${currentUser != null && currentUser.id == Enchere.article.vendeur.id}">
									<a
										href="/projetEniEncheres/modifier-vente/${Enchere.article.noArticle}"
										class="btn btn-dark"> Modifier la vente </a>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${Date gt Enchere.article.datesDebutEncheres}">
							<c:if test="${Date gt Enchere.article.datesFinEncheres}">
								<c:if
									test="${empty currentUser.id || currentUser.id != Enchere.user.id && currentUser.id != Enchere.article.vendeur.id}">
									<a href="/projetEniEncheres" class="btn btn-dark"> Retour </a>
								</c:if>
								<c:if test="${currentUser.id == Enchere.user.id}">
									<a href="/projetEniEncheres" class="btn btn-dark"> Retour </a>
								</c:if>
								<c:if test="${currentUser.id == Enchere.article.vendeur.id}">
									<a href="/projetEniEncheres" class="btn btn-dark"> Retrait
										effectué </a>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${error != null}">
							<div class="alert alert-danger alert-dismissible fade show"
								role="alert">
								<strong>Des erreurs ont été détectées :</strong><br>
								${error}
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
						</c:if>

						<c:if test="${succes != null}">
							<div class="alert alert-success alert-dismissible fade show"
								role="alert">
								<strong>Votre enchère a bien été enregistrée</strong><br>
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
						</c:if>
					</div>
				</div>
			</form>
		</div>
	</div>


</body>
</html>