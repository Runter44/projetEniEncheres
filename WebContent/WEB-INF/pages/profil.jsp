<%@include file="/WEB-INF/pages/Include/header.jsp"%>

<%@ page import="fr.eni.encheres.bo.Utilisateur"%>
<%
	Utilisateur user = (Utilisateur) request.getAttribute("requestedUser");
%>
<%
	boolean isMyProfile;
	try {
		isMyProfile = (user.getId() == ((Utilisateur) request.getSession().getAttribute("currentUser"))
				.getId());		
	} catch (Exception e) {
		isMyProfile = false;
	}
%>

<body class="bg-light">
	<div class="container-fluid p-0 h-100">
		<div class="h-100">
			<nav class="navbarColor navbar navbar-expand-lg navbar-dark w-100">
				<a class="navbar-brand text-light navbarColorTitre">ENI-Enchères</a>
				<%@include file="/WEB-INF/pages/Include/navbarButtons.jsp"%>
			</nav>
			<div class="paddingX15 text-center">
				<% if (isMyProfile) { %>
				<h1>Mon profil</h1>
				<% } else { %>
				<h1>Profil de <%=user.getPrenom()%>	<%=user.getNom()%></h1>
				<% } %>

				<table class="table">
					<tbody>
						<tr>
							<th scope="row" class="text-right w-50">Pseudo</th>
							<td class="w-50"><%=user.getPseudo()%></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Prénom</th>
							<td><%=user.getPrenom()%></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Nom</th>
							<td><%=user.getNom()%></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Email</th>
							<td><%=user.getEmail()%></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Téléphone</th>
							<td><%= "".equals(user.getTelephone()) ? "<span class='font-italic'>Non renseigné</span>" : user.getTelephone() %></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Rue</th>
							<td><%=user.getRue()%></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Code postal</th>
							<td><%=user.getCodePostal()%></td>
						</tr>
						<tr>
							<th scope="row" class="text-right">Ville</th>
							<td><%=user.getVille()%></td>
						</tr>
						<% if (isMyProfile) { %>
							<tr>
								<th scope="row" class="text-right">Crédit</th>
								<td><%=user.getCredit()%></td>
							</tr>
						<% } %>
					</tbody>
				</table>
				<% if (isMyProfile) { %>
					<div class="text-center">
						<a href="/projetEniEncheres/modification-compte" class="btn btn-dark">
							Modifier mes informations
						</a>
					</div>
				<% } %>
			</div>
		</div>
	</div>
</body>
</html>