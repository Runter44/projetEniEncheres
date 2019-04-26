<%@include file="/WEB-INF/pages/Include/header.jsp"%>
<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
			<a class="navbar-brand text-light navbarColorTitre" href="/projetEniEncheres">ENI-Enchères</a>
			<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
		</nav>
		<div class="paddingX10 text-left">
			<h1 class="text-center">Modification du profil</h1>
			<br>
			<form method="post" action="">
				<div class="row">
					<div class="col-sm">
						<label for="nom" class="lblTitre">Nom : </label><input id="nom"
							maxlength="50" class="form-control" type="text" required
							name="nomUtilisateur" value="${currentUser.nom}"><br>
						<label for="prenom" class="lblTitre">Prénom : </label><input
							id="prenom" maxlength="50" class="form-control" type="text"
							required name="prenomUtilisateur" value="${currentUser.prenom}"><br>
						<label for="email" class="lblTitre">Email : </label><input
							id="email" maxlength="150" class="form-control" type="text"
							required name="emailUtilisateur" value="${currentUser.email}"><br>
						<label for="mdpActuel" class="lblTitre">Mot de passe
							Actuel :</label><input maxlength="150" class="form-control"
							id="mdpActuel" type="password" name="mdpActuel"><br>
						<label for="newMdpUtilisateur" class="lblTitre">Nouveau
							mot de passe : </label><input maxlength="150" id="newMdpUtilisateur"
							class="form-control" type="password" name="newMdpUtilisateur"><br>
						<label for="confirmation" class="lblTitre">Confirmation
							mot de passe : </label><input maxlength="150" class="form-control"
							id="confirmation" type="password" name="mdpConfirmation">
					</div>
					<div class="col-sm">
						<label for="pseudo" class="lblTitre">Pseudo : </label><input
							id="pseudo" maxlength="50" class="form-control" type="text"
							required name="pseudoUtilisateur" value="${currentUser.pseudo}"><br>
						<label for="rue" class="lblTitre">Rue : </label><input
							maxlength="150" id="rue" type="text" class="form-control"
							required name="rueUtilisateur" value="${currentUser.rue}"><br>
						<label for="cp" class="lblTitre">CP : </label><input
							maxlength="10" id="cp" type="text" class="form-control" required
							name="cpUtilisateur" value="${currentUser.codePostal}"><br>
						<label for="ville" class="lblTitre">Ville : </label><input
							maxlength="150" id="ville" class="form-control" type="text"
							required name="villeUtilisateur" value="${currentUser.ville}"><br>
						<label for="tel" class="lblTitre">Telephone :</label><input
							id="tel" maxlength="15" class="form-control" type="text"
							name="telUtilisateur" value="${currentUser.telephone}"><br>
						<label for="Credit" class="lblTitre">Crédit : </label><input
							id="Credit" maxlength="15" class="form-control" type="text"
							name="creditUtilisateur" disabled value="${currentUser.credit}">
					</div>
				</div>
				<hr>
				<div class="row text-center">
					<div class="col-sm">
						<a href="suppression-compte" id="btnSupprimerCompte"
							class="btn btn-danger"
							onClick="return confirm('Voulez-vous vraiment supprimer votre compte ?');">Supprimer
							mon compte</a>
					</div>
					<div class="col-sm">
						<input type="submit" value="Confirmer" class="btn btn-success">
					</div>
				</div>
			</form>
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
					<strong>Les modifiacations ont bien enregistrées</strong><br>
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>