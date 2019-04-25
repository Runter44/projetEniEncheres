package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/modifier-article/*")
public class ServletModifierArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticleManager articleManager;
	private UserManager userManager;

    public ServletModifierArticle() {
    	articleManager = new ArticleManager();
    	userManager = new UserManager();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		if (request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("/projetEniEncheres");
		} else {
			try {
				int idArticle = Integer.parseInt(request.getPathInfo().substring(1));
				Article requestedArticle = articleManager.getArticleById(idArticle);
				if (requestedArticle != null && requestedArticle.getVendeur().getId() == ((Utilisateur) request.getSession().getAttribute("currentUser")).getId()) {
					request.setAttribute("requestedArticle", requestedArticle);
				}
			} catch (NumberFormatException e) {}
			request.getRequestDispatcher("/WEB-INF/pages/modifierVente.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
