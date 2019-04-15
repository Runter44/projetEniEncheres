package fr.eni.encheres.ihm;

import java.io.IOException;

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


@WebServlet("/AnnulerVente")
public class ServletAnnulerVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager manager = EnchereManager.getInstance();
		//recuperation de la vente a effacer
		Vente vente = (Vente)request.getSession().getAttribute("venteSelection");	
		Utilisateur user = (Utilisateur)request.getSession().getAttribute("user");

		request.setAttribute("pageTitle", "Vente - Annulation");

		//safety : etes vous sur
		//si on est bien le createur de la vente
		if (user.getNoUtilisateur()==vente.getVendeur().getNoUtilisateur()) {
			//Oui :
			//recréditer le plus haut enchéreur
			Enchere enchereHaute = manager.getHauteEnchere(vente.getNoVente(), "SELECTALLBYVENTE");

			//si il existe au moins une enchere
			if (enchereHaute != null) {

				enchereHaute.getUser().setCredit(enchereHaute.getValeur() + enchereHaute.getUser().getCredit());

				manager.setUtilisateur(enchereHaute.getUser());
			}

			//effacer le retrait  de cette vente
			Retrait retraitVide = new Retrait();
			retraitVide.setVente(vente);
			manager.remRetrait(retraitVide,"DELETE");

			//effacer les encheres de cette vente DELETEBYVENTE
			Enchere enchereVide = new Enchere();
			enchereVide.setVente(vente);
			manager.remEnchere(enchereVide,"DELETEBYVENTE");

			//effacer la vente
			manager.remVente(vente, "DELETE");
		}

		response.sendRedirect("./");
		//request.getRequestDispatcher("WEB-INF/pages/detailVente.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./");
	}
	
}
