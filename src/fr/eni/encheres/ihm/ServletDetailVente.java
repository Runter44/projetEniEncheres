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
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;

@WebServlet("/DetailVente")
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EnchereManager manager = EnchereManager.getInstance();
		
		//attribut pour savoir si l'utilisateur courant a fait la plus haute ench�re
		request.setAttribute("derniereEnchere", false);
		//attribut pour savoir si l'utilisateur courant est le vendeur
		request.setAttribute("vendeur", false);
		//attribut pour savoir si la vente est termin�
		request.setAttribute("enchereTermine", false);
		
		//On r�cup�re la vente d'ou on vient et on cr�� les autres objets dont on vas avoir besoin
		Retrait retrait = new Retrait();
		Utilisateur acheteur = null;
		Enchere enchere = null;
		Vente vente = null;
		if(request.getParameter("id")!=null)
			vente = manager.getVente(Integer.parseInt(request.getParameter("id")));
		else
			vente = manager.getVente((int) request.getAttribute("id"));
		
		//On param�tre le retrait avec la vente r�cup�rer
		retrait.setVente(vente);
		
		//On r�cup�re le retrait correspondant � la vente
		retrait = manager.getRetrait(retrait, "SELECTBYID");
		
		//On r�cup�re l'ench�re la plus �lev�
		enchere = manager.getHauteEnchere(vente.getNoVente(),"SELECTALLBYVENTE");
		//on r�cup�re l'utilisateur qui a fait l'ench�re la plus �lev�
		if(enchere!=null)
			acheteur = manager.getUtilisateur(enchere.getUser().getNoUtilisateur());
				
		//On v�rifie si l'utilisateur est le vendeur
		if(vente.getVendeur().getNoUtilisateur() == ((Utilisateur) request.getSession().getAttribute("user")).getNoUtilisateur()) {
			request.setAttribute("vendeur", true);
		}
		
		//On v�rifie si l'utilisateur a fait l'ench�re la plus �lev�
		if(acheteur != null && acheteur.getNoUtilisateur() == ((Utilisateur) request.getSession().getAttribute("user")).getNoUtilisateur()) {
			request.setAttribute("derniereEnchere", true);
		}
		
		if((vente.getDatesFinEncheres().plusDays(1)).isBefore(LocalDateTime.now())) {
			request.setAttribute("enchereTermine", true);
		}
		
		request.setAttribute("acheteur", acheteur);
		request.setAttribute("retraitArticle",retrait);
		request.setAttribute("enchere", enchere);
		request.getSession().setAttribute("venteSelection", vente);
		
		request.setAttribute("pageTitle", "Vente de " + vente.getNomArticle());
		
		request.getRequestDispatcher("WEB-INF/pages/detailVente.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
