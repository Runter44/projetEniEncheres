package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletProfil
 */
@WebServlet("/Profil")
public class ServletProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		EnchereManager manager = EnchereManager.getInstance();

		if (request.getParameter("utilisateur") == null) {	//si on voit son propre profil

			//request.setAttribute("user", request.getSession().getAttribute("user"));

			request.setAttribute("pageTitle", "Mon Profil");
			
			request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);

		}
		else  {	//si on voit le profil de quelqu'un d'autre

			int idToSee = Integer.parseInt(request.getParameter("utilisateur"));
	//System.out.println(idToSee);
			Utilisateur toSee = manager.getUtilisateur(idToSee);
			
			request.setAttribute("toSee", toSee);
			
			request.setAttribute("pageTitle", "Profil de " + toSee.getPseudo());
			
			request.getRequestDispatcher("/WEB-INF/pages/profilAutre.jsp").forward(request, response);

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EnchereManager manager = EnchereManager.getInstance();

		Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");

		//si on se connecte
		if (request.getParameter("user") == null) {	

			request.setAttribute("user", request.getSession().getAttribute("user"));

			//TEST si la modification est valide
			if (request.getParameter("enregistrer") != null && request.getParameter("paramCompteMDP") != null 
					&& request.getParameter("paramCompteMDP").equals(request.getParameter("paramCompteConfirmationMDP"))) {	

				Utilisateur modifiedUser = new Utilisateur();
				System.out.println("Modification utilisateur");

				modifiedUser.setNoUtilisateur(user.getNoUtilisateur());

				//si le champ est vide, on reprend l'ancienne valeur
				if ("".equals(request.getParameter("paramCompteNom"))) {	

					modifiedUser.setNom(user.getNom());

				}
				else {

					modifiedUser.setNom(request.getParameter("paramCompteNom"));

				}

				if ("".equals(request.getParameter("paramComptePrenom"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setPrenom(user.getPrenom());

				}
				else {

					modifiedUser.setPrenom(request.getParameter("paramComptePrenom"));

				}

				if ("".equals(request.getParameter("paramComptePseudo"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setPseudo(user.getPseudo());

				}
				else {

					modifiedUser.setPseudo(request.getParameter("paramComptePseudo"));

				}

				if ("".equals(request.getParameter("paramCompteEmail"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setEmail(user.getEmail());

				}
				else {

					modifiedUser.setEmail(request.getParameter("paramCompteEmail"));

				}

				if ("".equals(request.getParameter("paramCompteTelephone"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setTelephone(user.getTelephone());

				}
				else {

					modifiedUser.setTelephone(request.getParameter("paramCompteTelephone"));

				}

				if ("".equals(request.getParameter("paramCompteRue"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setRue(user.getRue());

				}
				else {

					modifiedUser.setRue(request.getParameter("paramCompteRue"));

				}

				if ("".equals(request.getParameter("paramCompteCP"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setCodePostal(user.getCodePostal());

				}
				else {

					modifiedUser.setCodePostal(request.getParameter("paramCompteCP"));

				}

				if ("".equals(request.getParameter("paramCompteVille"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setVille(user.getVille());

				}
				else {

					modifiedUser.setVille(request.getParameter("paramCompteVille"));

				}

				if ("".equals(request.getParameter("paramCompteMDP"))) {	//si le champ est vide, on reprend l'ancienne valeur

					modifiedUser.setMotDePasse(user.getMotDePasse());

				}
				else {

					modifiedUser.setMotDePasse(Integer.toString(request.getParameter("paramCompteMDP").hashCode()));

				}

				//si on réussit à modifier l'utilisateur
				if (manager.setUtilisateur(modifiedUser)) {

					request.getSession().setAttribute("user", modifiedUser);

				}

			}

			request.setAttribute("pageTitle", "Mon profil");
			
			request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);

		}
		else  {	//si on voit le profil de quelqu'un d'autre

			int idToSee = Integer.parseInt(request.getParameter("utilisateur"));
			
			Utilisateur toSee = manager.getUtilisateur(idToSee);
			
			request.setAttribute("toSee", toSee);
			
			request.setAttribute("pageTitle", "Profil de " + toSee.getPseudo());
			
			request.getRequestDispatcher("/WEB-INF/pages/profilAutre.jsp").forward(request, response);

		}

	}

}
