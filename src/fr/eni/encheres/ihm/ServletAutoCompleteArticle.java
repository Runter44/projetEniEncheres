package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;

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
        String q = term.toLowerCase();

        articleManager.getArticleByName(q);
        
        response.getWriter().write("NA");
    }
}