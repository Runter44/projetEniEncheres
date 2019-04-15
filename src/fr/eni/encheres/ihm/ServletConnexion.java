package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.ScheduleManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;


@WebServlet("/Connexion")
public class ServletConnexion extends HttpServlet  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ScheduleManager.getInstance();
		
		//Si on veut se déconnecter
		if ( req.getSession().getAttribute("user") != null && req.getParameter("Disconnect") != null) {
			
			req.getSession().setAttribute("user",null);
			
			Cookie connexCk1 = new Cookie("connexOne",null);
			connexCk1.setMaxAge(0);
			Cookie connexCk2 = new Cookie("connexTwo",null);
			connexCk2.setMaxAge(0);
			
			resp.addCookie(connexCk1);
			resp.addCookie(connexCk2);
			
		}

		
		doPost(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		EnchereManager manager = EnchereManager.getInstance();
		
		//si on tente de se connecter
		if (req.getParameter("paramLoginUtilisateur") != null && req.getParameter("paramMotDePasseUtilisateur") != null) {
			
			String motDePasse = Integer.toString(req.getParameter("paramMotDePasseUtilisateur").hashCode());
			//String motDePasse = req.getParameter("paramMotDePasseUtilisateur");
			String login = req.getParameter("paramLoginUtilisateur");

			Utilisateur retourConnect = manager.connectUser(login, motDePasse);
//System.out.println(motDePasse);
			//si on s'est connecté
			if(retourConnect != null) {
//System.out.println(retourConnect.getNom());
				req.getSession().setAttribute("user", retourConnect);
				
				//si on a choisit de se rappeler de soit
				if (req.getParameter("paramRememberMe") != null) {
					
					Cookie connexCk1 = new Cookie("connexOne",retourConnect.getPseudo());
					connexCk1.setMaxAge(999999999);
					Cookie connexCk2 = new Cookie("connexTwo",retourConnect.getMotDePasse());
					connexCk2.setMaxAge(999999999);
					resp.addCookie(connexCk1);
					resp.addCookie(connexCk2);
					
				}
				
			}
		}

		//si on s'est connecté
		if ( req.getSession().getAttribute("user") != null) {	

			Vente venteVide = new Vente();
			venteVide.setVendeur((Utilisateur) req.getSession().getAttribute("user"));

			//toutes les ventes dont on est le vendeur
			List<Vente> mesVentes = manager.getVentes(venteVide, "SELECTBYVENDEUR");

			//toutes les ventes dont on est l'enchereur
			for (Vente venteEnchere:manager.getVentes(venteVide, "SELECTBYENCHEREUR")) {

				mesVentes.add(venteEnchere);

			}

			req.setAttribute("ventes", mesVentes);

			req.setAttribute("pageTitle", "Ma Page");

			req.setAttribute("listeCat", manager.getCategories());
			
			req.getRequestDispatcher("/WEB-INF/pages/myPage.jsp").forward(req, resp);
		}
		else {
			
			req.setAttribute("pageTitle", "Bienvenue");
			
			req.getRequestDispatcher("/WEB-INF/pages/connexion.jsp").forward(req, resp);
		}

	}
}	
