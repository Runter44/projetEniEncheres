<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body>
	<div class="container-fluid fill-height">
		<nav
			class="navbarBasic navbarColor navbar navbar-expand-lg navbar-light">
			<a class="navbar-brand navbarColorTitre" style="color: white;">ENI-Enchères</a>
		</nav>
		<div class="divMainContent">
			<div id="divCreationCompte">
				<h2 class="text-center">Modification du profil</h2>
				<br>
				<form method="post" action="">
					<div class="row">
						<div class="col-sm">
							<label for="nom" class="lblTitre">Nom<span
								class="asterisk">*</span> :
							</label><input id="nom" maxlength="50" class="form-control" type="text"
								required name="nomUtilisateur" value="${param.nomUtilisateur}"><br>
							<label for="prenom" class="lblTitre">Prénom :
							</label><input id="prenom" maxlength="50" class="form-control"
								type="text" required name="prenomUtilisateur"
								value="${param.prenomUtilisateur}"><br> <label
								for="email" class="lblTitre">Email :
							</label><input id="email" maxlength="150" class="form-control"
								type="text" required name="emailUtilisateur"
								value="${param.emailUtilisateur}"><br> <label
								for="mdpActuel" class="lblTitre">Mot de passe Actuel :</label><input
								maxlength="150" class="form-control" id="mdpActuel"
								type="password" name="mdpActuel"><br> <label
								for="newMdpUtilisateur" class="lblTitre">Nouveau mot de passe :
							</label><input maxlength="150" id="newMdpUtilisateur" class="form-control"
								type="password" required name="newMdpUtilisateur"><br>
							<label for="confirmation" class="lblTitre">Confirmation
								mot de passe :
							</label><input maxlength="150" class="form-control" id="confirmation"
								type="password" name="mdpConfirmation">
						</div>
						<div class="col-sm">
							<label for="pseudo" class="lblTitre">Pseudo<span
								class="asterisk">*</span> :
							</label><input id="pseudo" maxlength="50" class="form-control"
								type="text" required name="pseudoUtilisateur"
								value="${param.pseudoUtilisateur}"><br> <label
								for="rue" class="lblTitre">Rue<span class="asterisk">*</span>
								:
							</label><input maxlength="150" id="rue" type="text" class="form-control"
								required name="rueUtilisateur" value="${param.rueUtilisateur}"><br>
							<label for="cp" class="lblTitre">CP<span class="asterisk">*</span>
								:
							</label><input maxlength="10" id="cp" type="text" class="form-control"
								required name="cpUtilisateur" value="${param.cpUtilisateur}"><br>
							<label for="ville" class="lblTitre">Ville<span
								class="asterisk">*</span> :
							</label><input maxlength="150" id="ville" class="form-control"
								type="text" required name="villeUtilisateur"
								value="${param.villeUtilisateur}"><br> <label
								for="tel" class="lblTitre">Telephone :</label><input id="tel"
								maxlength="15" class="form-control" type="text"
								name="telUtilisateur" value="${param.telUtilisateur}">
						</div>
					</div>
					<hr>
					<div class="row text-center">
						<div class="col-sm">
							<input type="submit" value="Confirmer"
								class="btn btn-outline-secondary">
						</div>
						<div class="col-sm">
							<a href="connexion" class="btn btn-link">Annuler</a>
						</div>
					</div>
				</form>
				<%
					if (request.getAttribute("error") != null) {
				%>
				<div class="alert alert-danger alert-dismissible fade show"
					role="alert">
					<strong>Des erreurs ont été détectées :</strong><br>
					<%=request.getAttribute("error")%>
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<%
					}
				%>

			</div>
		</div>
	</div>
</body>
</html>