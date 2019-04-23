package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;


@WebServlet("/detail-vente/*")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ArticleManager articleManager; 

    public ServletEncherir() {
        super();
        articleManager = new ArticleManager();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int idArticle = Integer.parseInt(request.getPathInfo().substring(1));
			request.setAttribute("requestedVente", articleManager.getArticleById(idArticle));
		} catch (NumberFormatException e) {}
		request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jps").forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
