package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
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
	
	
    public ServletListeEncheres() {
        super();
        categorieManager = new CategorieManager();
        articleManager = new ArticleManager();
        enchereManager = new EnchereManager();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		CritEnchere critEnchere = new CritEnchere();
		Article critArticle = new Article();
		Utilisateur critUtilisateur = new Utilisateur();
		
		if(request.getParameter("filtreNomArticle") != null) {
			critArticle.setNomArticle(request.getParameter("filtreNomArticle"));
		}
		if(request.getParameter("categorieValue") != null) {
			request.getParameter("categorieValue");
			//critArticle.setCat();
		}
		if(request.getParameter("categorieValue") != null) {
			request.getParameter("categorieValue");
			//critArticle.setCat();
		}
	
		
			
		critEnchere.setVente(critArticle);
		critEnchere.setUser(critUtilisateur);
		
		List<Enchere> lesEncheres = enchereManager.getListEnchereByCrit(critEnchere);
		request.getSession().setAttribute("lesEncheres", lesEncheres);
		
		request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
	}
}
