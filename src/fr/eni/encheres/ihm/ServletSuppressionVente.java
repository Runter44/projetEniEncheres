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
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.criteres.CritArticle;

@WebServlet("/supprimer-vente/*")
public class ServletSuppressionVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	private ArticleManager articleManager;
	private EnchereManager enchereManager;

	public ServletSuppressionVente() {
		this.userManager = new UserManager();
		this.articleManager = new ArticleManager();
		this.enchereManager = new EnchereManager();
	}

	@Override
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
		CritArticle critArticle = new CritArticle();
		critArticle.setDatesDebutEncheres(new Date());
		List<Article> lesArticles = articleManager.getListArticleByCrit(critArticle);
		request.getSession().setAttribute("lesArticles", lesArticles);
		response.sendRedirect("/projetEniEncheres");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
