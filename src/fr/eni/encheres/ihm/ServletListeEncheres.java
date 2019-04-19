package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;

@WebServlet("/liste-encheres")
public class ServletListeEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private CategorieManager categorieManager;
	
    public ServletListeEncheres() {
        super();
        categorieManager = new CategorieManager();
        
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Categorie> LesCats = categorieManager.getAllCat();
		Categorie toutes = new Categorie();
		
		//Entrée supplémentaire.
		toutes.setLibelle("Toutes");
		toutes.setNoCategorie(0);
		LesCats.add(0, toutes);
		
		request.getSession().setAttribute("listeCat", LesCats);
		request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
