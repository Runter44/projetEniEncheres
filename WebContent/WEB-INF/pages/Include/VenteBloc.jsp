<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//A APPELER AVEC 
	//<c:forEach var="v" items="${requestScope.ventes}">
	//<%@include file="/WEB-INF/pages/Include/header.jsp" 
	//</c:forEach>
%>
<!--  
<p>
	<label><a href="DetailVente?id=${v.getNoVente()}">${v.getNomArticle()}</a></label>
	Prix: <label>${v.getPrixVente()} points</label><br /> Fin de l'enchère:
	<label>${v.getDatesFinEncheres()}</label><br /> Retrait:
	<c:if test="${!empty v.getRetrait().getRue()}">
		<label>${v.getRetrait().getRue()}</label>
		<label>${v.getRetrait().getCodePostal()}</label>
		<label>${v.getRetrait().getVille()}</label>
	</c:if>
	<c:if test="${empty v.getRetrait().getRue()}">
		<label>Non-renseigné</label>
	</c:if>

	Vendeur: <label><a
		href="./Profil?utilisateur=${v.getVendeur().getNoUtilisateur()}">${v.getVendeur().getPseudo()}</a></label>
</p>
-->
<div style="min-width: 600px; max-height: 300px" class="text-right">

	<div class="row" >
		
		<div class="image">
<!-- 			style="background: url('image/hal90002-1.png') no-repeat center center; background-size: cover;"> -->
				<p><img src="image/hal90002-1.png" width="150px" height="150px" alt="" style="text-align: left" title="" /></p>
<!-- 			<img alt="" src="image/hal90002-1.png" -->
<!-- 				style="filter: alpha(opacity = 0); opacity: 0"> -->
		</div>
		
		<div class="row" >
			<div class="col-sm-4">
				<div class="row">
					<div class="col-sm-4">Annulation</div>
				</div>
				<div class="row">
					<div class="col-sm-4">
						<a href="DetailVente?id=${v.getNoVente()}">${v.getNomArticle()}</a>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-4">Prix : ${v.getPrixVente()} points</div>
					<!-- <div class="col-sm-2">Classement</div>-->
				</div>
				<div class="row">
					<div class="col-sm-4">Fin : ${v.getDatesFinEncheres()}</div>
				</div>
				<div class="row">
					<div class="col-sm-4">
						<c:if test="${!empty v.getRetrait().getRue()}">
							<label>${v.getRetrait().getRue()}</label>
							<label>${v.getRetrait().getCodePostal()}</label>
							<label>${v.getRetrait().getVille()}</label>
						</c:if>
						<c:if test="${empty v.getRetrait().getRue()}">
							<label>Non-renseigné</label>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-4">
						Vendeur : <a
							href="./Profil?utilisateur=${v.getVendeur().getNoUtilisateur()}">${v.getVendeur().getPseudo()}</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>