package fr.eni.encheres.ihm;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.DateFormat;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;

@WebServlet("/NouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EnchereManager manager = EnchereManager.getInstance();
		
		request.setAttribute("listeCat", manager.getCategories());
		
		request.setAttribute("pageTitle", "Vendre");
		
		request.getRequestDispatcher("WEB-INF/pages/nouvelleVente.jsp").forward(request, response);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager manager = EnchereManager.getInstance();
		Vente vente = new Vente();


		if("Publier".equals(request.getParameter("bouton"))) {	//si on crée la vente

//System.out.println("publier");

			SimpleDateFormat heure = new SimpleDateFormat("HH:mm");
			String date = request.getParameter("paramNouvelleVenteDate") + " " + "23:59";



			//si les paramètres de base sont renseignés
			if(!(request.getParameter("paramNouvelleVenteNom") == "" || request.getParameter("paramNouvelleVenteDate") =="" ||
					request.getParameter("paramNouvelleVenteDescription") == "" || request.getParameter("paramNouvelleVentePrixInitial") == "" ||
					request.getParameter("paramNouvelleVentePrixInitial") == "")) {

				vente.setNomArticle(request.getParameter("paramNouvelleVenteNom"));
				vente.setDatesFinEncheres(DateFormat.TOFORMAT(date));
				vente.setDescription(request.getParameter("paramNouvelleVenteDescription"));
				vente.setMiseAPrix(Integer.parseInt(request.getParameter("paramNouvelleVentePrixInitial")));
				vente.setPrixVente(Integer.parseInt(request.getParameter("paramNouvelleVentePrixInitial")));

				vente.setVendeur((Utilisateur)request.getSession().getAttribute("user"));

				Categorie cat = manager.getCategorie(Integer.parseInt(request.getParameter("paramNouvelleVenteCategorie")));
				vente.setCat(cat);

				vente = manager.createVente(vente);	
				
				//si on a remplit un point de retrait pour la vente
				if (!(request.getParameter("paramNouvelleVenteRue") == "") || (request.getParameter("paramNouvelleVenteCodePostal") == "") 
						|| (request.getParameter("paramNouvelleVenteVille") == "")) {	
					
					Retrait retrait = new Retrait();
				
					retrait.setCodePostal(request.getParameter("paramNouvelleVenteCodePostal"));
					retrait.setRue(request.getParameter("paramNouvelleVenteRue"));
					retrait.setVille(request.getParameter("paramNouvelleVenteVille"));
					
					retrait.setVente(vente);
					
					manager.createRetrait(retrait);
				}
				
				//request.getRequestDispatcher("WEB-INF/pages/myPage.jsp").forward(request, response);
				response.sendRedirect("./");
			}
			else {

				request.setAttribute("Probleme", "Création impossible : vérifiez vos champs");
				doGet(request, response);

			}
			

		} else if(request.getParameter("bouton").equals("Enregistrer")) {	//si on modifie

			//System.out.println("enregistrer");
			//request.getRequestDispatcher("WEB-INF/pages/myPage.jsp").forward(request, response);
			response.sendRedirect("./");

		} else if(request.getParameter("bouton").equals("Annuler")){	//si on annule

			//System.out.println("annuler");
			//request.getRequestDispatcher("WEB-INF/pages/myPage.jsp").forward(request, response);
			response.sendRedirect("./");

		}

	}

}
