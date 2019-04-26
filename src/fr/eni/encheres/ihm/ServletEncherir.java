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
import fr.eni.encheres.criteres.CritArticle;
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
			CritEnchere critEnchere;
			CritArticle critArticle ;
			Enchere uneEnchere;
		    Date maDate;
			int idArticle;

			critEnchere = new CritEnchere();
			critArticle = new CritArticle();
			maDate = new Date();
			idArticle = Integer.parseInt(request.getPathInfo().substring(1));

			critArticle.setNoArticle(idArticle);
			critEnchere.setVente(critArticle);

			critEnchere.setOrderBy("montant_enchere");
			critEnchere.setSensTri("DESC");		
			if (enchereManager.getListEnchereByCrit(critEnchere).size()>0){
				request.setAttribute("Enchere",enchereManager.getListEnchereByCrit(critEnchere).get(0));
				request.setAttribute("Date", maDate);
			}else{
				uneEnchere = new Enchere ();
				uneEnchere.setArticle(articleManager.getArticleById(idArticle));
				request.setAttribute("Enchere",uneEnchere);
			}
		} catch (NumberFormatException e) {
			
		}
		request.setAttribute("Date",new Date());
		request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Enchere enchere;
		Utilisateur DernierEnchere;
		Article articleEnCour;
		CritEnchere critEnchere;
		CritArticle critArticle;
		boolean enchereOk;
		String messageError;

		//Debut traitement
		articleEnCour = articleManager.getArticleById(Integer.parseInt(request.getPathInfo().substring(1)));
		critArticle = new CritArticle();

		critArticle.setNoArticle(articleEnCour.getNoArticle());
		critEnchere = new CritEnchere();

		critEnchere.setValeur(articleEnCour.getPrixVente());
		critEnchere.setVente(critArticle);
		critEnchere.setOrderBy("montant_enchere");
		critEnchere.setSensTri("DESC");		
		DernierEnchere = null;

		if (enchereManager.getListEnchereByCrit(critEnchere).size()>0){
			DernierEnchere = enchereManager.getListEnchereByCrit(critEnchere).get(0).getUser();
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
		if(DernierEnchere != null ){
			if (enchere.getUser().getId() == DernierEnchere.getId()){
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

			Utilisateur vendeur;

			vendeur = new Utilisateur();
			vendeur = enchere.getArticle().getVendeur();

			//Ajout de la nouvelle enchère
			enchereManager.addEnchere(enchere);	

			//Créditaion de l'ancien enchérisseur
			if (DernierEnchere != null){		
				DernierEnchere.setCredit(DernierEnchere.getCredit() + articleEnCour.getNoArticle());
				userManager.updateUser(DernierEnchere);
				vendeur.setCredit(vendeur.getCredit() - articleEnCour.getNoArticle());
			}

			//débit du nouvel utilisateur
			enchere.getUser().setCredit(enchere.getUser().getCredit() - enchere.getValeur());
			userManager.updateUser(enchere.getUser());
			vendeur.setCredit(vendeur.getCredit() + enchere.getValeur());

			articleEnCour.setPrixVente(enchere.getValeur());
			articleManager.updateArticle(articleEnCour);
			
			userManager.updateUser(vendeur);
			
			messageError += "Votre enchère a bien été prise en compte.";

			request.setAttribute("succes", messageError);
		}else{
			request.setAttribute("error", messageError);
		}

		//Init Atribut Enchere pour JSP
		critEnchere = new CritEnchere();
		critArticle = new CritArticle();
		critArticle.setNoArticle(Integer.parseInt(request.getPathInfo().substring(1)));
		critEnchere.setVente(critArticle);
		critEnchere.setOrderBy("montant_enchere");
		critEnchere.setSensTri("DESC");		
		request.setAttribute("Enchere",enchereManager.getListEnchereByCrit(critEnchere).get(0));
		request.setAttribute("Date",new Date());
		request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jsp").forward(request, response);
	}
}
