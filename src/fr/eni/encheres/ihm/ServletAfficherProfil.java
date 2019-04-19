package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;

@WebServlet("/profil/*")
public class ServletAfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;

    public ServletAfficherProfil() {
        super();
        userManager = new UserManager();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int idUser = Integer.parseInt(request.getPathInfo().substring(1));
			request.setAttribute("requestedUser", userManager.getUserById(idUser));
		} catch (NumberFormatException e) {}
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
