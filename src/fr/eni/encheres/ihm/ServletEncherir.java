package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.criteres.CritEnchere;


@WebServlet("/detail-vente/*")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnchereManager enchereManager; 
	private ArticleManager articleManager;
	private UserManager userManager;

	public ServletEncherir() {
		super();
		enchereManager = new EnchereManager();
		articleManager = new ArticleManager();
		userManager = new UserManager();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		try {
			CritEnchere critEnchere = new CritEnchere();
			Article critArticle = new Article();

			critArticle.setNoArticle(Integer.parseInt(request.getPathInfo().substring(1)));
			critEnchere.setVente(critArticle);

			critEnchere.setOrderBy("montant_enchere");
			critEnchere.setSensTri("DESC");		

			request.setAttribute("Enchere",enchereManager.getListEnchereByCrit(critEnchere).get(0));

		} catch (NumberFormatException e) {}

		request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Enchere enchere;
		Utilisateur DernierEncherrisseur;
		Article articleEnCour;
		CritEnchere critEnchere;
		Article critArticle;
		boolean enchereOk;
		String messageError;

		//Init Atribut Enchere pour JSP
		critEnchere = new CritEnchere();
		critArticle = new Article();
		critArticle.setNoArticle(Integer.parseInt(request.getPathInfo().substring(1)));
		critEnchere.setVente(critArticle);
		critEnchere.setOrderBy("montant_enchere");
		critEnchere.setSensTri("DESC");		

		request.setAttribute("Enchere",enchereManager.getListEnchereByCrit(critEnchere).get(0));

		//Debut traitement
		articleEnCour = articleManager.getArticleById(Integer.parseInt(request.getPathInfo().substring(1)));
		critArticle = new Article();

		critArticle.setNoArticle(articleEnCour.getNoArticle());
		critEnchere = new CritEnchere();

		critEnchere.setValeur(articleEnCour.getPrixVente());
		critEnchere.setVente(critArticle);
		critEnchere.setOrderBy("montant_enchere");
		critEnchere.setSensTri("DESC");		
		DernierEncherrisseur = null;

		if (enchereManager.getListEnchereByCrit(critEnchere).size()>0){
			DernierEncherrisseur = enchereManager.getListEnchereByCrit(critEnchere).get(0).getUser();
		}

		messageError = "";
		enchereOk = true;
		enchere = new Enchere();

		enchere.setArticle(articleEnCour);
		enchere.setDateEnchere(new Date());
		enchere.setUser((Utilisateur)request.getSession().getAttribute("currentUser"));
		enchere.setValeur(Integer.parseInt(request.getParameter("prixPropose")));

		if(enchere.getDateEnchere().compareTo(enchere.getArticle().getDatesDebutEncheres()) < 0 ){
			enchereOk = false;
			messageError += "L'enchère n'a pas encore commencée. ";
		}
		if (enchere.getDateEnchere().compareTo(enchere.getArticle().getDatesFinEncheres()) > 0){
			enchereOk = false;
			messageError += "L'enchère est terminée. ";
		}
		if (enchere.getValeur() <= enchere.getArticle().getPrixVente()){
			enchereOk = false;
			messageError += "Votre enchère n'est pas assez haute. ";
		}		
		if(DernierEncherrisseur != null ){
			if (enchere.getUser().getId() == DernierEncherrisseur.getId()){
				enchereOk = false;
				messageError += "Vous ne pouvez pas enchérir sur votre propre enchère. ";
			}
		}
		if (enchere.getUser().getId() == enchere.getArticle().getVendeur().getId()){
			enchereOk = false;
			messageError += "Vous ne pouvez pas enchérir sur une de vos ventes. ";
		}
		if (enchere.getUser().getCredit() < enchere.getArticle().getPrixVente()){
			enchereOk = false;
			messageError += "Vous n'avez pas assez de crédit. ";
		}

		if(enchereOk){

			//Ajout de la nouvelle enchère
			enchereManager.addEnchere(enchere);	

			//Créditaion de l'ancien enchérisseur
			if (DernierEncherrisseur != null){		
				DernierEncherrisseur.setCredit(DernierEncherrisseur.getCredit() + articleEnCour.getNoArticle());
				userManager.updateUser(DernierEncherrisseur);
			}

			//débit du nouvel utilisateur
			enchere.getUser().setCredit(enchere.getUser().getCredit() - enchere.getValeur());
			userManager.updateUser(enchere.getUser());


			articleEnCour.setPrixVente(enchere.getValeur());

			articleManager.updateArticle(articleEnCour);
			messageError += "Votre enchère a bien été prise en compte.";

			request.setAttribute("succes", messageError);
			request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jsp").forward(request, response);
		}else{
			request.setAttribute("error", messageError);
			request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jsp").forward(request, response);
		}
	}
}
