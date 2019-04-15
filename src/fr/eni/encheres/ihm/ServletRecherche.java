package fr.eni.encheres.ihm;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;

/**
 * Servlet implementation class ServletRecherche
 */
@WebServlet("/ServletRecherche")
public class ServletRecherche extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRecherche() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("./");
		//		request.getRequestDispatcher("/WEB-INF/pages/myPage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		doGet(request, response);
		List<Vente> listeVentesRe = new ArrayList<>();
		List<Vente> listeTemp = null;

		Utilisateur user = (Utilisateur)request.getSession().getAttribute("user");
		//user.getNoUtilisateur();


		//recuperation du contenu de la barre de recherche
		String nomArticleRecherche = request.getParameter("paramBarreRecherche");
		EnchereManager manager = EnchereManager.getInstance();
		Vente venteVide = new Vente();
		//	***	recuperation des checkboxes	***

		//Voir mes ventes
		if (request.getParameter("paramVentes") != null) {

			venteVide.setVendeur(user);

			listeTemp = manager.getVentes(venteVide, "SELECTBYVENDEUR");
			
			//listeTemp = triParDateFuture(listeTemp,LocalDateTime.now());
			
			listeVentesRe = addListsVente(listeVentesRe, listeTemp);

			
			
		}

		//Voir mes encheres
		if (request.getParameter("paramEncheresEncours") != null) {

			venteVide.setVendeur(user);

			listeTemp = manager.getVentes(venteVide, "SELECTBYENCHEREUR");

			listeTemp = triParDateFuture(listeTemp, LocalDateTime.now().minusDays(1));

			listeVentesRe = addListsVente(listeVentesRe, listeTemp);

		}

		//Voir mes acquisitions
		if (request.getParameter("paramAquisitions") != null) {

			venteVide.setVendeur(user);
			//toutes les ventes ou j'ai une enchere
			listeTemp = manager.getVentes(venteVide, "SELECTBYENCHEREUR");

			//dont la date est passée

			listeTemp = triParDatePassee(listeTemp, LocalDateTime.now());

			//ou j'ai l'enchere la plus haute
			listeTemp = triParEncherisseurHaut(listeTemp, user.getNoUtilisateur());

			//on ajoute
			listeVentesRe = addListsVente(listeVentesRe, listeTemp);

		}

		//Voir les ventes en cours ou je n'ai pas d'encheres et je ne suis pas vendeur
		if (request.getParameter("paramAutresEncheres") != null) {

			venteVide.setVendeur(user);
			venteVide.setDatesFinEncheres(LocalDateTime.now());
			//toutes les ventes ou je n'ai pas une enchere ET dont la date n'est pas passée
			listeTemp = manager.getVentes(venteVide, "SELECTTOBROWSE");

			//on ajoute
			listeVentesRe = addListsVente(listeVentesRe, listeTemp);

		}

		//	**	tri des ventes recherchées	**
		//par nom
		listeVentesRe = triParNom(listeVentesRe, nomArticleRecherche);

		//par categorie
		listeVentesRe = triParCategorie(listeVentesRe, Integer.parseInt(request.getParameter("paramRechercheVenteCategorie")));
		
		request.setAttribute("listeCat", manager.getCategories());
		
		//listeVentesRe =  manager.getVentes(v,"SELECTBYNOM");
		//System.out.println(listeVentesRe);
		request.setAttribute("ventesRecherche", listeVentesRe);

		//		request.setAttribute("vente", vente);

		//System.out.println(nomArticleRecherche);

		request.setAttribute("pageTitle", "Ma Page");

		request.getRequestDispatcher("/WEB-INF/pages/myPage.jsp").forward(request, response);


	}

	public List<Vente> addListsVente (List<Vente> liste1, List<Vente> liste2) {

		for(Vente vente:liste2) {

			liste1.add(vente);

		}

		return liste1;

	}

	public List<Vente> triParNom(List<Vente> liste1,String nom) {

		List<Vente> liste2 = new ArrayList<>();

		for (Vente vente:liste1) {

			if (vente.getNomArticle().contains(nom)) {

				liste2.add(vente);				
			}

		}

		return liste2;

	}

	public List<Vente> triParDatePassee(List<Vente> liste1,LocalDateTime datetime) {

		List<Vente> liste2 = new ArrayList<>();

		for (Vente vente:liste1) {

			//Si la vente finit hier ou avant
			if (vente.getDatesFinEncheres().isBefore(datetime.minusDays(1))) {

				liste2.add(vente);				
			}

		}

		return liste2;

	}

	public List<Vente> triParDateFuture(List<Vente> liste1,LocalDateTime datetime) {

		List<Vente> liste2 = new ArrayList<>();

		for (Vente vente:liste1) {
			//si la vente finit ce soir ou plus tard
			if (vente.getDatesFinEncheres().isAfter(datetime)) {

				liste2.add(vente);				
			}

		}

		return liste2;

	}

	public List<Vente> triParEncherisseurHaut(List<Vente> liste1,int NoUtilisateur) {

		List<Vente> liste2 = new ArrayList<>();

		for (Vente vente:liste1) {

			if (vente.getEncheresParValeur().get(vente.getEncheres().size()-1).getUser().getNoUtilisateur() == NoUtilisateur) {

				liste2.add(vente);				
			}

		}

		return liste2;

	}

	public List<Vente> triParCategorie(List<Vente> liste1,int NoCategorie) {

		List<Vente> liste2 = new ArrayList<>();

		//si on cherche toutes les categories
		if (NoCategorie == -1) {

			liste2 = liste1;

		}
		else {
			for (Vente vente:liste1) {

				if (vente.getCat().getNoCategorie() == NoCategorie) {

					liste2.add(vente);				
				}

			}
		}


		return liste2;

	}



}
