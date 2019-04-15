package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/CreationCompte")
public class ServletCreationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("pageTitle", "Bienvenue parmi nous !");
		
		request.getRequestDispatcher("/WEB-INF/pages/creationCompte.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = new Utilisateur();
		EnchereManager enchereManager = EnchereManager.getInstance();
		
		utilisateur.setPseudo(request.getParameter("paramNouveauComptePseudo"));
		utilisateur.setNom(request.getParameter("paramNouveauCompteNom"));
		utilisateur.setPrenom(request.getParameter("paramNouveauComptePrenom"));
		utilisateur.setEmail(request.getParameter("paramNouveauCompteEmail"));
		utilisateur.setTelephone(request.getParameter("paramNouveauCompteTelephone"));
		utilisateur.setRue(request.getParameter("paramNouveauCompteRue"));
		utilisateur.setCodePostal(request.getParameter("paramNouveauCompteCP"));
		utilisateur.setVille(request.getParameter("paramNouveauCompteVille"));
		utilisateur.setMotDePasse(Integer.toString(request.getParameter("paramNouveauCompteMDP").hashCode()));
		//utilisateur.setMotDePasse(request.getParameter("paramNouveauCompteMDP"));
		utilisateur.setCredit(0);
		utilisateur.setAdministrateur(false);
		
		//On vérifie si le mot de passe et le mot de passe de confirmation sont égaux et si il n'y as pas de champs vide
		if(request.getParameter("paramNouveauCompteMDP").equals(request.getParameter("paramNouveauCompteConfirmationMDP")) &&
		   !(request.getParameter("paramNouveauComptePseudo").equals("") || request.getParameter("paramNouveauCompteNom").equals("") ||
		   request.getParameter("paramNouveauComptePrenom").equals("") || request.getParameter("paramNouveauCompteEmail").equals("") ||
		   request.getParameter("paramNouveauCompteTelephone").equals("") || request.getParameter("paramNouveauCompteRue").equals("") ||
		   request.getParameter("paramNouveauCompteCP").equals("") || request.getParameter("paramNouveauCompteMDP").equals("") ||
		   request.getParameter("paramNouveauCompteMDP").equals(""))) {
				
				Utilisateur userDoublon = new Utilisateur();
				userDoublon.setEmail(request.getParameter("paramNouveauCompteEmail"));
				userDoublon.setPseudo(request.getParameter("paramNouveauComptePseudo"));
				userDoublon.setTelephone(request.getParameter("paramNouveauCompteTelephone"));
			
				List<Utilisateur> doublons = enchereManager.getUtilisateurs(userDoublon, "SELECTFORDOUBLON");
				
				if (!(doublons.size() >= 1)) {	//Si il n'y a pas de doublon dans la base (email, pseudo, telephone
					
					enchereManager.createUtilisateur(utilisateur);
					request.setAttribute("utilisateurInvalide",false);
					
					request.setAttribute("pageTitle", "Vous y êtes presque !");
					
					request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp").forward(request, response);	
					
				}
				else {	//si il y a un doublon
System.out.println("Tentative doublon");
					if (request.getParameter("paramNouveauCompteEmail").equals(doublons.get(0).getEmail())) {
						
						request.setAttribute("Probleme", "Cet email est déjà utilisé");
						
					}
					else if (request.getParameter("paramNouveauComptePseudo").equals(doublons.get(0).getPseudo())) {
						
						request.setAttribute("Probleme", "Ce pseudo est déjà utilisé");
						
					}
					else if (request.getParameter("paramNouveauCompteTelephone").equals(doublons.get(0).getTelephone())) {
						
						request.setAttribute("Probleme", "Ce téléphone est déjà utilisé");
						
					}
					
					request.setAttribute("utilisateurInvalide",true);
					this.doGet(request,response);
				}
			
						
		} else {
			request.setAttribute("utilisateurInvalide",true);
			this.doGet(request,response);
		}
		
	}
}
