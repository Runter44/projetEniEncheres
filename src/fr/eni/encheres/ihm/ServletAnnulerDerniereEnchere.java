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

@WebServlet("/AnnulerDerniereEnchere")
public class ServletAnnulerDerniereEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.sendRedirect("./");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EnchereManager manager = EnchereManager.getInstance();
		
		Vente vente = (Vente)request.getSession().getAttribute("venteSelection");	
		
		boolean validation = false;
		
		//on recharge la vente pour que les données soient fraiches
		vente = manager.getVente(vente.getNoVente());
		Enchere enchereVide = new Enchere();
		enchereVide.setVente(vente);
		vente.setEncheres(manager.getEncheres(enchereVide, "SELECTALLBYVENTE"));
		
		
		Utilisateur user = (Utilisateur)request.getSession().getAttribute("user");
		
		request.setAttribute("pageTitle", "Enchere - Annulation");
		
		Enchere enchereSuperieure = vente.getEncheresParValeur().get(vente.getEncheresParValeur().size()-1);
		
		//si on est bien l'encherisseur le plus haut de cette vente
		if (user.getNoUtilisateur()==enchereSuperieure.getUser().getNoUtilisateur()) {
			
			//rembourser l'enchere
			enchereSuperieure.getUser().setCredit(enchereSuperieure.getUser().getCredit() + enchereSuperieure.getValeur());

			EnchereManager.getInstance().setUtilisateur(enchereSuperieure.getUser());
			
			request.getSession().setAttribute("user", enchereSuperieure.getUser());
			
			//effacer enchere, 
			EnchereManager.getInstance().remEnchere(enchereSuperieure,"DELETE");

			//chercher enchere suivante
			enchereSuperieure = EnchereManager.getInstance().getHauteEnchere(vente.getNoVente(), "SELECTALLBYVENTE");
			
			//tant que l'on a des encheres et que l'on a pas trouvé une nouvelle enchere valide
			while (validation==false && enchereSuperieure != null) {
				
				//si l'encherisseur a les moyens de payer, ET que la date enchere n'était pas trop tard
				if (enchereSuperieure.getValeur() <= enchereSuperieure.getUser().getCredit() || enchereSuperieure.getDateEnchere().compareTo(LocalDateTime.now() ) >=0 ) {
					
					//on change le prix en court de la vente
					vente.setPrixVente(enchereSuperieure.getValeur());

					EnchereManager.getInstance().setVente(vente);
					
					//on enleve le credit de l'encherisseur
					enchereSuperieure.getUser().setCredit(enchereSuperieure.getUser().getCredit() - enchereSuperieure.getValeur());
					
					EnchereManager.getInstance().setUtilisateur(enchereSuperieure.getUser());
					
					validation = true;

				}

				//si l'enchere n'est plus valide
				else {

					//Si non : effacer enchere, 
					EnchereManager.getInstance().remEnchere(enchereSuperieure,"DELETE");

					//chercher enchere suivante
					enchereSuperieure = EnchereManager.getInstance().getHauteEnchere(vente.getNoVente(), "SELECTALLBYVENTE");

				}

			}
			
			//si on a pas trouvé d'enchere valide
			if (validation==false && enchereSuperieure == null) {
				
				//remettre la vente a son prix de depart
				vente.setPrixVente(vente.getMiseAPrix());
				EnchereManager.getInstance().setVente(vente);
				
			}
			
		}
		
		response.sendRedirect("./");
		
		//request.getRequestDispatcher("WEB-INF/pages/detailVente.jsp").forward(request, response);
		
	}

}
