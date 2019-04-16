	<div>
		<ul>
			<c:forEach var="v" items="${vente}">
				<label>${v.getNomArticle()}</label><label></label>
				Prix:<label>${v.getPrixVente()}</label>
				Fin de l'enchère:<label>${v.getDatesFinEncheres()}</label>
				Retrait:<label>lieu de retrait</label>
				Vendeur:<label>${v.getVendeur()}</label>
			</c:forEach>
		</ul>
	
	</div>