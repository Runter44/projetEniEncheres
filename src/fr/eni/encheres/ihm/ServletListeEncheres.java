package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Vente;

/**
 * Servlet implementation class ServletListeEncheres
 */
@WebServlet("/ListeEncheres")
public class ServletListeEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListeEncheres() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		EnchereManager manager = EnchereManager.getInstance();
		Categorie ListCategorie = (Categorie) manager.getCategories();
		
		request.setAttribute("listCategories", ListCategorie);
		
		request.setAttribute("pageTitle", "Mes Encheres");
		
		request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		EnchereManager manager = EnchereManager.getInstance();
		
		String cbVente = request.getParameter("paramVentes");
		String cbEnchereEnCours = request.getParameter("paramEncheresEnCours");
		String cbAquisitions = request.getParameter("paramAquisitions");
		String cbAutresEncheres = request.getParameter("paramAutresEncheres");
		
		Vente vente = (Vente) manager.getVentes();
		
		request.setAttribute("vente", vente);
		
		
		
	}

}
