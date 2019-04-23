package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;

@WebServlet("/vente-article")
public class ServletCreerVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;

    public ServletCreerVente() {
        super();
        userManager = new UserManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			if (!userManager.connectWithCookies(request.getCookies(), request)) {
				response.sendRedirect("/projetEniEncheres");
				return;
			}
		}
		request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomArticle = request.getParameter("articleName"),
				categorieArticle = request.getParameter("articleCategorie"),
				descriptionArticle = request.getParameter("articleDescription"),
				debutEnchereArticle = request.getParameter("articleDebutEnchere"),
				finEnchereArticle = request.getParameter("articleFinEnchere"),
				prixArticle = request.getParameter("articlePrix"),
				rueRetrait = request.getParameter("articleRetraitRue"),
				cpRetrait = request.getParameter("articleRetraitCodePostal"),
				villeRetrait = request.getParameter("articleRetraitVille");
		
		
	}

}
