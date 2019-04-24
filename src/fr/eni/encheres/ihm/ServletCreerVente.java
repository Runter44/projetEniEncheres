package fr.eni.encheres.ihm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;

@WebServlet("/vente-article")
public class ServletCreerVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserManager userManager;
	private ArticleManager articleManager;

	public ServletCreerVente() {
		userManager = new UserManager();
		articleManager = new ArticleManager();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			if (!userManager.connectWithCookies(request.getCookies(), request)) {
				response.sendRedirect("/projetEniEncheres");
				return;
			}
		}
		request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			if (!userManager.connectWithCookies(request.getCookies(), request)) {
				response.sendRedirect("/projetEniEncheres");
				return;
			}
		}
		String nomArticle = request.getParameter("articleName"),
				categorieArticle = request.getParameter("articleCategorie"),
				descriptionArticle = request.getParameter("articleDescription"),
				debutEnchereArticle = request.getParameter("articleDebutEnchere"),
				finEnchereArticle = request.getParameter("articleFinEnchere"),
				prixArticle = request.getParameter("articlePrix"),
				rueRetrait = request.getParameter("articleRetraitRue"),
				cpRetrait = request.getParameter("articleRetraitCodePostal"),
				villeRetrait = request.getParameter("articleRetraitVille");

		//Part part = request.getPart("articlePhoto");

		boolean hasErrors = false;
		String errorMessage = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (StringUtils.isBlank(nomArticle) || (StringUtils.isNotBlank(nomArticle) && nomArticle.length() > 30)) {
				hasErrors = true;
				errorMessage += "Le nom de l'article est obligatoire et doit faire au plus 30 caractères.<br>";
			}
			if (StringUtils.isBlank(categorieArticle) || (StringUtils.isNotBlank(categorieArticle) && StringUtils.isNumeric(categorieArticle) && (new CategorieManager()).getCatById(Integer.parseInt(categorieArticle)) == null)) {
				hasErrors = true;
				errorMessage += "La catégorie sélectionnée n'existe pas.<br>";
			}
			if (StringUtils.isBlank(descriptionArticle) || (StringUtils.isNotBlank(nomArticle) && descriptionArticle.length() > 300)) {
				hasErrors = true;
				errorMessage += "La description est obligatoire et doit faire au plus 300 caractères.<br>";
			}
			if ((StringUtils.isBlank(debutEnchereArticle) || StringUtils.isBlank(finEnchereArticle)) || ((StringUtils.isNotBlank(debutEnchereArticle) && StringUtils.isNotBlank(finEnchereArticle) && sdf.parse(debutEnchereArticle).compareTo(sdf.parse(finEnchereArticle)) > 0))) {
				hasErrors = true;
				errorMessage += "Les dates sont obligatoires et la date de fin doit être postérieure à la date de début.<br>";
			}
			if (StringUtils.isBlank(prixArticle) || !StringUtils.isNumeric(prixArticle)) {
				hasErrors = true;
				errorMessage += "Le prix de l'article doit être un nombre.<br>";
			}
			if (StringUtils.isBlank(rueRetrait) || (StringUtils.isNotBlank(rueRetrait) && rueRetrait.length() > 150)) {
				hasErrors = true;
				errorMessage += "Le nom de la rue est obligatoire et doit faire au plus 150 caractères.<br>";
			}
			if (StringUtils.isBlank(cpRetrait) || (StringUtils.isNotBlank(cpRetrait) && cpRetrait.length() > 10)) {
				hasErrors = true;
				errorMessage += "Le code postal est obligatoire et doit faire au plus 10 caractères.<br>";
			}
			if (StringUtils.isBlank(villeRetrait) || (StringUtils.isNotBlank(villeRetrait) && villeRetrait.length() > 150)) {
				hasErrors = true;
				errorMessage += "Le nom de la ville est obligatoire et doit faire au plus 150 caractères.<br>";
			}
			
			if (!hasErrors) {
				Article nouvelArticle = new Article();

				nouvelArticle.setCat(DAOFactory.getDAOCategorie().find(Integer.parseInt(categorieArticle)));
				nouvelArticle.setDatesDebutEncheres(sdf.parse(debutEnchereArticle));
				nouvelArticle.setDatesFinEncheres(sdf.parse(finEnchereArticle));
				nouvelArticle.setDescription(descriptionArticle);
				nouvelArticle.setMiseAPrix(Integer.parseInt(prixArticle));
				nouvelArticle.setPrixVente(Integer.parseInt(prixArticle));
				nouvelArticle.setNomArticle(nomArticle);
				nouvelArticle.setVendeur((Utilisateur) request.getSession().getAttribute("currentUser"));
				nouvelArticle.setRue(rueRetrait);
				nouvelArticle.setCodePostal(cpRetrait);
				nouvelArticle.setVille(villeRetrait);

				articleManager.addArticle(nouvelArticle);
				response.sendRedirect("/projetEniEncheres");
			} else {
				request.setAttribute("error", errorMessage);
				request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
			}
		} catch (NumberFormatException | ParseException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
		}
	}
}
