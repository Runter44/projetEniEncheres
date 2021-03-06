package fr.eni.encheres.ihm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/nouvelle-vente")
@MultipartConfig
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

		Part filePart = request.getPart("articlePhoto");
		String fileName = filePart != null ? filePart.getSubmittedFileName() : "";

		boolean hasErrors = false;
		String errorMessage = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (StringUtils.isBlank(nomArticle) || (StringUtils.isNotBlank(nomArticle) && nomArticle.length() > 30)) {
				hasErrors = true;
				errorMessage += "Le nom de l'article est obligatoire et doit faire au plus 30 caract�res.<br>";
			}
			if (StringUtils.isBlank(categorieArticle) || (StringUtils.isNotBlank(categorieArticle) && StringUtils.isNumeric(categorieArticle) && (new CategorieManager()).getCatById(Integer.parseInt(categorieArticle)) == null)) {
				hasErrors = true;
				errorMessage += "La cat�gorie s�lectionn�e n'existe pas.<br>";
			}
			if (StringUtils.isBlank(descriptionArticle) || (StringUtils.isNotBlank(nomArticle) && descriptionArticle.length() > 300)) {
				hasErrors = true;
				errorMessage += "La description est obligatoire et doit faire au plus 300 caract�res.<br>";
			}
			if ((StringUtils.isBlank(debutEnchereArticle) || StringUtils.isBlank(finEnchereArticle)) || ((StringUtils.isNotBlank(debutEnchereArticle) && StringUtils.isNotBlank(finEnchereArticle) && sdf.parse(debutEnchereArticle).compareTo(sdf.parse(finEnchereArticle)) > 0))) {
				hasErrors = true;
				errorMessage += "Les dates sont obligatoires et la date de fin doit �tre post�rieure � la date de d�but.<br>";
			}
			if (StringUtils.isBlank(prixArticle) || !StringUtils.isNumeric(prixArticle)) {
				hasErrors = true;
				errorMessage += "Le prix de l'article doit �tre un nombre.<br>";
			}
			if (StringUtils.isBlank(rueRetrait) || (StringUtils.isNotBlank(rueRetrait) && rueRetrait.length() > 150)) {
				hasErrors = true;
				errorMessage += "Le nom de la rue est obligatoire et doit faire au plus 150 caract�res.<br>";
			}
			if (StringUtils.isBlank(cpRetrait) || (StringUtils.isNotBlank(cpRetrait) && cpRetrait.length() > 10)) {
				hasErrors = true;
				errorMessage += "Le code postal est obligatoire et doit faire au plus 10 caract�res.<br>";
			}
			if (StringUtils.isBlank(villeRetrait) || (StringUtils.isNotBlank(villeRetrait) && villeRetrait.length() > 150)) {
				hasErrors = true;
				errorMessage += "Le nom de la ville est obligatoire et doit faire au plus 150 caract�res.<br>";
			}
			
			try (
					OutputStream out = new FileOutputStream(new File(getServletContext().getRealPath("/uploads") + File.separator + fileName));
					InputStream filecontent = filePart.getInputStream();
				) {
				if (!(fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("jpeg "))) {
					hasErrors = true;
					errorMessage += "Le format du fichier n'est pas valide. Il doit �tre au format PNG ou JPG.<br>";
				}
				
				if (!hasErrors) {
					int read = 0;
			        final byte[] bytes = new byte[1024];

			        while ((read = filecontent.read(bytes)) != -1) {
			            out.write(bytes, 0, read);
			        }
				}
			} catch (FileNotFoundException|NullPointerException e) {
				hasErrors = true;
				errorMessage += e.getMessage() + "<br>";
			}
			
			if (!hasErrors) {
				Article nouvelArticle = new Article();

				nouvelArticle.setCat((new CategorieManager()).getCatById(Integer.parseInt(categorieArticle)));
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
				nouvelArticle.setImagePath("/projetEniEncheres/uploads/" + fileName);

				nouvelArticle = articleManager.addArticle(nouvelArticle);
				response.sendRedirect("/projetEniEncheres/detail-vente/" + nouvelArticle.getNoArticle());
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
