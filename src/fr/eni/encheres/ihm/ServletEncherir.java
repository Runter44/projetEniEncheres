package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.criteres.CritEnchere;


@WebServlet("/detail-vente/*")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnchereManager enchereManager; 
	private UserManager userManager;

	public ServletEncherir() {
		super();
		enchereManager = new EnchereManager();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		try {
			CritEnchere critEnchere = new CritEnchere();
			Article critArticle = new Article();

			critArticle.setNoArticle(Integer.parseInt(request.getPathInfo().substring(1)));
			critEnchere.setVente(critArticle);
			critEnchere.setOrderBy("montant_enchere");
			critEnchere.setSensTri("ASC");		

			request.setAttribute("Enchere",enchereManager.getListEnchereByCrit(critEnchere).get(0));

		} catch (NumberFormatException e) {}

		request.getRequestDispatcher("/WEB-INF/pages/pageEncherir.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
