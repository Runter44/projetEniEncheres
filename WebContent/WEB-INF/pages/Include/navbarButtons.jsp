<%@ page import="fr.eni.encheres.bo.Utilisateur"%>
<% Utilisateur connectedUser = (Utilisateur) request.getSession().getAttribute("currentUser"); %>
<button class="navbar-toggler text-light" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Ouvrir le menu">
  	<span class="navbar-toggler-icon"></span>
</button>
<div class="collapse navbar-collapse float-right" id="navbarSupportedContent">
	<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
    </ul>
	<form class="my-2 my-lg-0">
		<% if (connectedUser == null) { %>
			<a href="/projetEniEncheres/connexion" class="btn btn-outline-success mx-2 mb-2 mb-md-0 d-block d-md-inline-block">Connexion</a>
			<a href="/projetEniEncheres/creation-compte" class="btn btn-outline-warning mx-2 mb-2 mb-md-0 d-block d-md-inline-block">Inscription</a>
		<% } else { %>
			<a href="/projetEniEncheres/liste-encheres" class="btn btn-outline-info mx-2 mb-2 mb-md-0 d-block d-md-inline-block">Enchères</a>
			<a href="/projetEniEncheres/vente-article" class="btn btn-outline-warning mx-2 mb-2 mb-md-0 d-block d-md-inline-block">Vendre un article</a>
			<a href="/projetEniEncheres/profil/<%= connectedUser.getId() %>" class="btn btn-outline-success mx-2 mb-2 mb-md-0 d-block d-md-inline-block">Mon compte</a>
			<a href="/projetEniEncheres/deconnexion" class="btn btn-outline-danger mx-2 mb-2 mb-md-0 d-block d-md-inline-block">Déconnexion</a>
		<% } %>
	</form>
</div>