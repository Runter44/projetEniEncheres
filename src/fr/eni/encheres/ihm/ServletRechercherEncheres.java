package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;

@WebServlet("/Rechercher")
public class ServletRechercherEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
	private ArticleManager articleManager;

    public ServletRechercherEncheres() {
        super();
        userManager = new UserManager();
        articleManager = new ArticleManager();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String term = request.getParameter("term");
        String nomArticle = term.toLowerCase();
        String cat = request.getParameter("categorieValue");
        Integer catId = Integer.parseInt(cat);
        if(catId == 0) {
        	catId = null;
        }
        
        Article articleRechercher = new Article();
        articleRechercher.setNomArticle(nomArticle);
        Categorie catArticle = new Categorie();
        catArticle.setNoCategorie(catId);
        articleRechercher.setCat(catArticle);
        //List<Article> lesArticles= articleManager.getListArticleByCrit(articleRechercher);
        List<String> lesNomDArticles = new ArrayList<String>(); 
        
        /*for(Article unArticle : lesArticles){
        	lesNomDArticles.add(unArticle.getNomArticle());
        }*/
        
        
        String json = new Gson().toJson(lesNomDArticles);
        request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
