<%@include file="/WEB-INF/pages/Include/header.jsp" %>
<body>
	<h1>Détail vente</h1>
	
	<c:if test="${requestScope.enchereTermine}">
		<h2>Enchéres terminé</h2>
		<c:if test="${requestScope.derniereEnchere}">
			<h2>Vous avez remporté la vente</h2>
		</c:if>
		<c:if test="${requestScope.vendeur}">
			<h2>${acheteur.getPseudo()} a remporté la vente</h2>
		</c:if>
	</c:if>
	<div style="width: 200px; height: 200px;border: solid;">
	
	
	</div>
	<div>
		${sessionScope.venteSelection.getNomArticle()}<br>
		<label class="textAlign">Description :</label> ${sessionScope.venteSelection.getDescription()}<br>
		
		<c:if test="${requestScope.acheteur!=null}">
			<label class="textAlign">Meilleure Offre :</label> ${enchere.getValeur()} par ${acheteur.getPseudo()}<br>
		</c:if>
		
		<label class="textAlign">Mise à prix:</label>  ${sessionScope.venteSelection.getMiseAPrix()} points <br>
		<label class="textAlign">Fin de l'enchére :</label> ${sessionScope.venteSelection.getDatesFinEncheres()}<br>
		
		<c:if test="${retraitArticle != null }">
			<label class="textAlign">Retrait :</label> ${retraitArticle.getRue()}<a> ,</a><br>
			${retraitArticle.getCodePostal()} ${retraitArticle.getVille()}<br>
		</c:if>
		
		<label class="textAlign">Vendeur :</label> ${sessionScope.venteSelection.getVendeur().getPseudo()}<br>
		<c:if test="${!requestScope.enchereTermine}">
			<c:if test="${!requestScope.vendeur}">
				<c:if test="${!requestScope.derniereEnchere}">
					<form method="post" action="Encherir">
						Ma proposition : <input type = "number" name = "proposition" value="${requestScope.enchere.getValeur()}">
						<input type="submit" name="encherir" value="encherir" class="btn"><br>			
					</form>
				</c:if>
				<c:if test="${requestScope.derniereEnchere}">
					<form method="post" action="AnnulerDerniereEnchere">					
						<input type="submit" value="Annuler ma derniére enchére" class="btn" >
					</form>
				</c:if>	
			</c:if>
			<c:if test="${requestScope.vendeur}">
				<form method="post" action="AnnulerVente">
					<input type="submit" value="Annuler ma vente">
				</form>
			</c:if>
		</c:if>
		<form method="get" action="./">
			<input type="submit" value="Retour" class="btn">
		</form>
	</div>
</body>
</html>