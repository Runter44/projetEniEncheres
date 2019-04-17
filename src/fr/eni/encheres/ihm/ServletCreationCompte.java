package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/creation-compte")
public class ServletCreationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserManager userManager;

	public ServletCreationCompte() {
		super();
		userManager = new UserManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") != null) {
			response.sendRedirect("/projetEniEncheres");
		} else {
			request.getRequestDispatcher("/WEB-INF/pages/creationCompte.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nom = request.getParameter("nomUtilisateur");
		String prenom = request.getParameter("prenomUtilisateur");
		String pseudo = request.getParameter("pseudoUtilisateur");
		String email = request.getParameter("emailUtilisateur");
		String telephone = request.getParameter("telUtilisateur");
		String rue = request.getParameter("rueUtilisateur");
		String codePostal = request.getParameter("cpUtilisateur");
		String ville = request.getParameter("villeUtilisateur");
		String mdp = request.getParameter("mdpUtilisateur");
		String confirm = request.getParameter("mdpConfirmation");

		String errorMessage = "";
		boolean hasError = false;

		if ((nom == null || nom == "") || (prenom == null || prenom == "") || (pseudo == null || pseudo == "")
				|| (email == null || email == "") || (rue == null || rue == "")
				|| (codePostal == null || codePostal == "") || (ville == null || ville == "")
				|| (mdp == null || mdp == "") || (confirm == null || confirm == "")) {
			hasError = true;
			errorMessage += "Tous les champs marqués d'une * doivent être renseignés. <br>";
		}

		if (prenom != null && prenom.length() > 50) {
			hasError = true;
			errorMessage += "Le prénom renseigné est trop long. <br>";
		}
		if (nom != null && nom.length() > 50) {
			hasError = true;
			errorMessage += "Le nom renseigné est trop long. <br>";
		}
		if (pseudo != null && pseudo.length() > 50) {
			hasError = true;
			errorMessage += "Le pseudo renseigné est trop long. <br>";
		}
		if (email != null && email.length() > 150) {
			hasError = true;
			errorMessage += "L'email renseigné est trop long. <br>";
		}
		if (telephone != null && telephone.length() > 15) {
			hasError = true;
			errorMessage += "Le téléphone renseigné est trop long. <br>";
		}
		if (rue != null && rue.length() > 150) {
			hasError = true;
			errorMessage += "La rue renseignée est trop longue. <br>";
		}
		if (codePostal != null && codePostal.length() > 10) {
			hasError = true;
			errorMessage += "Le code postal renseigné est trop long. <br>";
		}
		if (ville != null && ville.length() > 150) {
			hasError = true;
			errorMessage += "La ville renseignée est trop longue. <br>";
		}
		if (mdp != null && (mdp.length() < 6 || mdp.length() > 150)) {
			hasError = true;
			errorMessage += "Le mot de passe doit faire entre 6 et 150 caractères. <br>";
		}
		if (pseudo != null && userManager.getUserByPseudo(pseudo) != null) {
			hasError = true;
			errorMessage += "Cet identifiant est déjà utilisé. <br>";
		}
		if (mdp != null && confirm != null && !mdp.equals(confirm)) {
			hasError = true;
			errorMessage += "La confirmation de mot de passe n'est pas identique. ";
		}

		if (!hasError) {
			Utilisateur user = new Utilisateur();
			user.setNom(nom).setPrenom(prenom).setPseudo(pseudo).setEmail(email).setTelephone(telephone).setRue(rue)
					.setCodePostal(codePostal).setVille(ville).setPassword(mdp).setAdmin(false).setCredit(0);

			user = userManager.addNewUser(user);
			
			if (userManager.connectUser(user, mdp, request)) {
				response.sendRedirect("/projetEniEncheres");
			} else {
				response.sendRedirect("connexion");
			}
		} else {
			request.setAttribute("error", errorMessage);
			request.getRequestDispatcher("/WEB-INF/pages/creationCompte.jsp").forward(request, response);
		}
	}
}
