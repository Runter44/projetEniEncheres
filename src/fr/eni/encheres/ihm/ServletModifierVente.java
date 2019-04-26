package fr.eni.encheres.ihm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.criteres.CritArticle;
import fr.eni.encheres.dal.DAOFactory;

@WebServlet("/modifier-vente/*")
public class ServletModifierVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArticleManager articleManager;
	private UserManager userManager;

	public ServletModifierVente() {
		articleManager = new ArticleManager();
		userManager = new UserManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			userManager.connectWithCookies(request.getCookies(), request);
		}
		if (request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("/projetEniEncheres");
		} else {
			try {
				int idArticle = Integer.parseInt(request.getPathInfo().substring(1));
				Article requestedArticle = articleManager.getArticleById(idArticle);
				if (requestedArticle != null && requestedArticle.getVendeur().getId() == ((Utilisateur) request.getSession().getAttribute("currentUser")).getId()) {
					request.setAttribute("requestedArticle", requestedArticle);
				}
			} catch (NumberFormatException e) {}
			request.getRequestDispatcher("/WEB-INF/pages/modifierVente.jsp").forward(request, response);
		}
	}

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

		boolean hasErrors = false;
		String errorMessage = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		int idArticle = Integer.parseInt(request.getPathInfo().substring(1));
		Article articleModif = articleManager.getArticleById(idArticle);
		request.setAttribute("requestedArticle", articleModif);

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
				articleModif.setCat(DAOFactory.getDAOCategorie().find(Integer.parseInt(categorieArticle)));
				articleModif.setDatesDebutEncheres(sdf.parse(debutEnchereArticle));
				articleModif.setDatesFinEncheres(sdf.parse(finEnchereArticle));
				articleModif.setDescription(descriptionArticle);
				articleModif.setMiseAPrix(Integer.parseInt(prixArticle));
				articleModif.setPrixVente(Integer.parseInt(prixArticle));
				articleModif.setNomArticle(nomArticle);
				articleModif.setVendeur((Utilisateur) request.getSession().getAttribute("currentUser"));
				articleModif.setRue(rueRetrait);
				articleModif.setCodePostal(cpRetrait);
				articleModif.setVille(villeRetrait);

				articleManager.updateArticle(articleModif);
				request.setAttribute("requestedArticle", articleModif);
				request.setAttribute("success", "La vente a bien été modifiée");
				response.sendRedirect("/projetEniEncheres");
			} else {
				request.setAttribute("error", errorMessage);
				request.getRequestDispatcher("/WEB-INF/pages/modifierVente.jsp").forward(request, response);
			}
		} catch (NumberFormatException | ParseException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/modifierVente.jsp").forward(request, response);
		}
		CritArticle critArticle = new CritArticle();
		critArticle.setDatesDebutEncheres(new Date());
		List<Article> lesArticles = articleManager.getListArticleByCrit(critArticle);
		request.getSession().setAttribute("lesArticles", lesArticles);	
	}
}
