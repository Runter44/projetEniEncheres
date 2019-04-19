package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletSuppressionCompte
 */
@WebServlet("/suppression-compte")
public class ServletSuppressionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserManager userManager;
	Utilisateur utilisateur;
	
    public ServletSuppressionCompte() {
        super();     
		this.userManager = new UserManager();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		utilisateur = (Utilisateur)request.getSession().getAttribute("currentUser");
		userManager.deleteUser(utilisateur);
		response.sendRedirect("deconnexion");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
