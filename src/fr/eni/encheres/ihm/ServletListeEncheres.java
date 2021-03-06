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
		if(null ==  request.getSession().getAttribute("lesArticles")) {
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
		
		String btnRadio = request.getParameter("grpBtnRad");
		if(StringUtils.isNotBlank(btnRadio)) {
			request.getSession().setAttribute("TabRad", btnRadio);
			if("achats".equals(btnRadio)) {
				if(null != request.getParameter("checkOuvertes")){
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(new Date());
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
				if(null != request.getParameter("checkRemporter")){
					critUtilisateur.setId(utilisateur.getId());
					critArticle.setDatesDebutEncheres(null);
					critArticle.setDatesFinEncheres(new Date());
					critEnchere.setEnCours(false);
					critEnchere.setVente(critArticle);
					mesEncheres.addAll(enchereManager.getListEnchereByCrit(critEnchere));
					
				}
				if(null != request.getParameter("checkMesEncheres")){
					critUtilisateur.setId(utilisateur.getId());
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(new Date());
					critEnchere.setVente(critArticle);
					critEnchere.setUser(critUtilisateur);
					mesEncheres.addAll(enchereManager.getListEnchereByCrit(critEnchere));
					List<Article> listeArticleEnchere = new ArrayList<Article>();
					for(Enchere uneEnchere : mesEncheres) {
						listeArticleEnchere.add(uneEnchere.getArticle());
					}
					addListToList(lesArticles,listeArticleEnchere);
				}
				
			}else if("mesVentes".equals(btnRadio)){
				if(null != request.getParameter("checkTerminer")){
					critArticle.setDatesDebutEncheres(null);
					critArticle.setDatesFinEncheres(new Date());
					critArticle.setTerminer(true);
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
				if(null != request.getParameter("checkNonDebuter")){
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(null);
					critArticle.setNonDebute(true);
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
				if(null != request.getParameter("checkEnCours")){
					critUtilisateur.setId(utilisateur.getId());
					critArticle.setDatesDebutEncheres(new Date());
					critArticle.setDatesFinEncheres(new Date());
					critArticle.setVendeur(critUtilisateur);
					addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
				}
			}
		}else {
			addListToList(lesArticles, articleManager.getListArticleByCrit(critArticle));
		}
	
		
		request.getSession().setAttribute("lesArticles", lesArticles);	
		
		request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
	}
	
	//Permet d'eviter les doublons dans la liste d'affichage
	private void addListToList(List<Article> laListRef, final List<Article> laListeAAdd){
		List<Article> aSupp = new ArrayList<Article>();
		for (Article article : laListRef) {
			for(Article articleAAdd : laListeAAdd) {
				if(article.getNoArticle() == articleAAdd.getNoArticle()) {
					aSupp.add(articleAAdd);
				}
			}
		}
		laListeAAdd.removeAll(aSupp);
		laListRef.addAll(laListeAAdd);
	}
}
