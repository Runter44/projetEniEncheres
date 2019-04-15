package fr.eni.encheres.ihm;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;

@WebServlet("/Encherir")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletDetailVente detailVente = new ServletDetailVente();
		EnchereManager manager = EnchereManager.getInstance();
		Vente vente = (Vente)request.getSession().getAttribute("venteSelection");		
		Enchere enchere = new Enchere();
		Enchere encherisseurPrecedent = null;
		
		enchere.setVente(vente);
		enchere.setUser((Utilisateur) request.getSession().getAttribute("user"));
		
		
		//si la proposition est superieure au prix de vente ET l'encherisseur a assez de credit
		if(request.getParameter("proposition")!=null && Integer.parseInt(request.getParameter("proposition")) > vente.getPrixVente() &&
		  (enchere.getUser().getCredit() - Integer.parseInt(request.getParameter("proposition"))) >= 0) {
			
			enchere.setDateEnchere(LocalDateTime.now());
			enchere.setValeur(Integer.parseInt(request.getParameter("proposition")));
			
			encherisseurPrecedent = manager.getHauteEnchere(enchere.getVente().getNoVente(), "SELECTALLBYVENTE");
			
			//Si un utilisateur a déja fait une offre, on le rembourse
			if(encherisseurPrecedent != null) {
				encherisseurPrecedent.getUser().setCredit(encherisseurPrecedent.getUser().getCredit() + encherisseurPrecedent.getValeur());
				manager.setUtilisateur(encherisseurPrecedent.getUser());
			}
			
			
			if(manager.getUtilisateurEnchere(enchere)) { //Si l'utilisateur a déja une enchére sur cette vente,
				manager.setEnchere(enchere);			 //On l'update
			} else {									 //Sinon
				manager.createEnchere(enchere);			 //On créée une enchére
			}
			
			//On met à jour les crédits de l'utilisateur
			enchere.getUser().setCredit(enchere.getUser().getCredit() - enchere.getValeur());
			manager.setUtilisateur(enchere.getUser());
			
			//On met à jour le prix de vente de l'article
			vente.setPrixVente(enchere.getValeur());
			manager.setVente(vente);
			
			request.getSession().setAttribute("user", enchere.getUser());
		}	
		else {
			
			request.setAttribute("Probleme", "<h1>Enchere refusée</h1>");
			
		}
		
		request.setAttribute("id", enchere.getVente().getNoVente());
		
		detailVente.doGet(request, response);
	}

}
