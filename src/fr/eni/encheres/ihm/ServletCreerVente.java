package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;

/**
 * Servlet implementation class ServletCreerVente
 */
@WebServlet("/vente-article")
public class ServletCreerVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreerVente() {
        super();
        userManager = new UserManager();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			if (!userManager.connectWithCookies(request.getCookies(), request)) {
				response.sendRedirect("/projetEniEncheres");
				return;
			}
		}
		request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
