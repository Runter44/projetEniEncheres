package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
       
    public ServletConnexion() {
        super();
        this.userManager = new UserManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") != null) {
			response.sendRedirect("/projetEniEncheres");
		} else {
			request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("motDePasse");
		
		Utilisateur user = userManager.getUserByPseudo(login);
		
		if (user != null && userManager.connectUser(user, password, request)) {
			response.sendRedirect("/projetEniEncheres");
		} else {
			request.setAttribute("errorConnexion", true);
			request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp").forward(request, response);
		}
	}
}
