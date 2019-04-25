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
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.criteres.CritArticle;

@WebServlet(name = "AutoCompleteNomArticle", urlPatterns = {"/AutoCompleteNomArticle"})
public class ServletAutoCompleteArticle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ArticleManager articleManager;
	
    public ServletAutoCompleteArticle() {
        super();
        articleManager = new ArticleManager();
        
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String term = request.getParameter("term");
        String nomArticle = term.toLowerCase();
        String cat = request.getParameter("categorieValue");
        Integer catId = Integer.parseInt(cat);
        if(catId == 0) {
        	catId = null;
        }
        
        CritArticle articleRechercher = new CritArticle();
        articleRechercher.setNomArticle(nomArticle);
        Categorie catArticle = new Categorie();
        catArticle.setNoCategorie(catId);
        articleRechercher.setCat(catArticle);
        List<Article> lesArticles= articleManager.getListArticleByCrit(articleRechercher);
        List<String> lesNomDArticles = new ArrayList<String>(); 
        
        for(Article unArticle : lesArticles){
        	lesNomDArticles.add(unArticle.getNomArticle());
        }
        
        
        String json = new Gson().toJson(lesNomDArticles);
        
        response.getWriter().write(json);
    }
}