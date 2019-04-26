package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.criteres.CritArticle;
import fr.eni.encheres.criteres.CritEnchere;

@WebServlet("/liste-encheres")
public class ServletListeEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private CategorieManager categorieManager;
	private ArticleManager articleManager;
	private EnchereManager enchereManager;
	private UserManager userManager;
	
	
    public ServletListeEncheres() {
        super();
        categorieManager = new CategorieManager();
        articleManager = new ArticleManager();
        enchereManager = new EnchereManager();
        userManager = new UserManager();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		
		List<Categorie> LesCats = categorieManager.getAllCat();
		Categorie toutes = new Categorie();
		//Entrée supplémentaire.
		toutes.setLibelle("Toutes");
		toutes.setNoCategorie(0);
		LesCats.add(0, toutes);
		request.getSession().setAttribute("listeCat", LesCats);
		if( null ==  request.getSession().getAttribute("lesArticles") || ((List<Categorie>) request.getSession().getAttribute("lesArticles")).size() == 0) {
			CritArticle critArticle = new CritArticle();
			critArticle.setDatesDebutEncheres(new Date());
			List<Article> lesArticles = articleManager.getListArticleByCrit(critArticle);
			request.getSession().setAttribute("lesArticles", lesArticles);	
		}
		request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		
		List<Categorie> LesCats = categorieManager.getAllCat();
		Categorie toutes = new Categorie();
		//Entrée supplémentaire.
		toutes.setLibelle("Toutes");
		toutes.setNoCategorie(0);
		LesCats.add(0, toutes);
		request.getSession().setAttribute("listeCat", LesCats);
		
		CritEnchere critEnchere = new CritEnchere();
		Utilisateur critUtilisateur = new Utilisateur();
		Categorie critCategorie = new Categorie();
		CritArticle critArticle = new CritArticle();
		List<Article> lesArticles = new ArrayList<Article>();
		List<Enchere> mesEncheres = new ArrayList<Enchere>();
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("currentUser");
		
		if(request.getParameter("filtreNomArticle") != null) {
			critArticle.setNomArticle(request.getParameter("filtreNomArticle"));
		}
		if(request.getParameter("categorieValue") != null && !request.getParameter("categorieValue").equals("0") ) {
			critCategorie = categorieManager.getCatById(Integer.parseInt(request.getParameter("categorieValue")));
			critArticle.setCat(critCategorie);
		}
		critEnchere.setVente(critArticle);
		critEnchere.setUser(critUtilisateur);
		
		String btnRadio = request.getParameter("grpBtnRad");		
		if(StringUtils.isNotBlank(btnRadio)) {
			if("achats".equals(btnRadio)) {
				if(null != request.getParameter("checkOuvertes")){
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(new Date());
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
				if(null != request.getParameter("checkMesEncheres")){
					critUtilisateur.setId(utilisateur.getId());
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(new Date());
					critEnchere.setVente(critArticle);
					critEnchere.setUser(critUtilisateur);
					mesEncheres.addAll(enchereManager.getListEnchereByCrit(critEnchere));
				}
				if(null != request.getParameter("checkRemporter")){
					critUtilisateur.setId(utilisateur.getId());
					critArticle.setDatesDebutEncheres(null);
					critArticle.setDatesFinEncheres(new Date());
					critEnchere.setVente(critArticle);
					
				}
			}else if("mesVentes".equals(btnRadio)){
				if(null != request.getParameter("checkEnCours")){
					critUtilisateur.setId(utilisateur.getId());
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(new Date());
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
				if(null != request.getParameter("checkNonDebuter")){
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setNonDebute(true);
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
				if(null != request.getParameter("checkTerminer")){
					critArticle.setDatesFinEncheres(new Date());
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
			}
		}
	
		
		request.getSession().setAttribute("lesArticles", lesArticles);	
		
		request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
	}
	
	//Permet d'eviter les doublons dans la liste d'affichage
	private void addListToList(List<Article> laListRef, final List<Article> laListeAAdd){
		for (Article article : laListeAAdd) {
			if(!laListRef.contains(article)) {
				laListRef.add(article);
			}
		}
	}
}
