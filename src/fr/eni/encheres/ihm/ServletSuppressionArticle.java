package fr.eni.encheres.ihm;

import java.io.IOException;

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

@WebServlet("/supprimer-article/*")
public class ServletSuppressionArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	private ArticleManager articleManager;
	private EnchereManager enchereManager;
	
    public ServletSuppressionArticle() {
		this.userManager = new UserManager();
		this.articleManager = new ArticleManager();
		this.enchereManager = new EnchereManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		if (request.getSession().getAttribute("currentUser") != null) {
			int idArticle = Integer.parseInt(request.getPathInfo().substring(1));
			Article requestedArticle = articleManager.getArticleById(idArticle);
			for (Enchere enchere : enchereManager.getAllEncheres()) {
				if (enchere.getArticle().getNoArticle() == requestedArticle.getNoArticle()) {
					System.out.println(enchereManager.deleteEnchere(enchere));
				}
			}
			articleManager.deleteArticle(requestedArticle);
		}
		response.sendRedirect("/projetEniEncheres");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
