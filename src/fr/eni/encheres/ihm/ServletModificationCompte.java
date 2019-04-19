package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UserManager;


@WebServlet("/modification-compte")
public class ServletModificationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserManager userManager;

	public ServletModificationCompte() {
		super();
		userManager = new UserManager();
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("/projetEniEncheres");
		} else {
			request.getRequestDispatcher("/WEB-INF/pages/modificationCompte.jsp").forward(request, response);
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom ;
		String prenom ;
		String pseudo ;
		String email ;
		String telephone ;
		String rue ;
		String codePostal ;
		String ville ;	
		String currentMdp ;
		String newMdp ;
		String confirm;
		String errorMessage;
		String password ;
		Utilisateur userActuel;	
		Utilisateur newUser;
		boolean hasError;

		nom = request.getParameter("nomUtilisateur");
		prenom = request.getParameter("prenomUtilisateur");
		pseudo = request.getParameter("pseudoUtilisateur");
		email = request.getParameter("emailUtilisateur");
		telephone = request.getParameter("telUtilisateur");
		rue = request.getParameter("rueUtilisateur");
		codePostal = request.getParameter("cpUtilisateur");
		ville = request.getParameter("villeUtilisateur");	
		currentMdp = request.getParameter("mdpActuel");
		newMdp = request.getParameter("newMdpUtilisateur");
		confirm = request.getParameter("mdpConfirmation");
		
		errorMessage = "";
		hasError = false;	
		userActuel = new Utilisateur();	
		userActuel = userManager.getUserById(((Utilisateur)request.getSession().getAttribute("currentUser")).getId());

		if(newMdp != null && newMdp != ""){
			password = newMdp;
		}else{
			password = userActuel.getPassword();
		}
		
		newUser = new Utilisateur(userActuel.getId(),pseudo,password,nom,prenom,email,telephone,rue,codePostal,ville,userActuel.getCredit(),userActuel.isAdmin());
		
		if (!newUser.equalsUser(userActuel)){

			if ((nom == null || nom == "") || (prenom == null || prenom == "") || (pseudo == null || pseudo == "")
					|| (email == null || email == "") || (rue == null || rue == "")
					|| (codePostal == null || codePostal == "") || (ville == null || ville == "")) {
				hasError = true;
				errorMessage += "Tous les champs marqués d'une * doivent être renseignés. <br>";
			}

			if (prenom != null && prenom.length() > 50) {
				hasError = true;
				errorMessage += "Le prénom renseigné est trop long. <br>";
			}
			if (nom != null && nom.length() > 50) {
				hasError = true;
				errorMessage += "Le nom renseigné est trop long. <br>";
			}
			if (pseudo != null && pseudo.length() > 50) {
				hasError = true;
				errorMessage += "Le pseudo renseigné est trop long. <br>";
			}
			if (email != null && email.length() > 150) {
				hasError = true;
				errorMessage += "L'email renseigné est trop long. <br>";
			}
			if (telephone != null && telephone.length() > 15) {
				hasError = true;
				errorMessage += "Le téléphone renseigné est trop long. <br>";
			}
			if (rue != null && rue.length() > 150) {
				hasError = true;
				errorMessage += "La rue renseignée est trop longue. <br>";
			}
			if (codePostal != null && codePostal.length() > 10) {
				hasError = true;
				errorMessage += "Le code postal renseigné est trop long. <br>";
			}
			if (ville != null && ville.length() > 150) {
				hasError = true;
				errorMessage += "La ville renseignée est trop longue. <br>";
			}

			if (currentMdp != null && currentMdp != "" ) {

				if (!currentMdp.equals(userActuel.getId())){
					hasError = true;
					errorMessage += "Le mot de passe actuel saisi est érroné. <br>";
				}else{
					if (newMdp.length() < 6 || newMdp.length() > 150){
						hasError = true;
						errorMessage += "Le mot de passe doit faire entre 6 et 150 caractères. <br>";					
					}else{					
						if (newMdp != null && confirm != null && !newMdp.equals(confirm)) {
							hasError = true;
							errorMessage += "La confirmation de mot de passe n'est pas identique. ";
						}
					}
				}
			}

			if (pseudo != null && !pseudo.equals(userActuel.getPseudo())){
				if (pseudo != null && userManager.getUserByPseudo(pseudo) != null) {
					hasError = true;
					errorMessage += "Cet identifiant est déjà utilisé. <br>";
				}	
			}	


			if (!hasError) {

				userActuel.setNom(nom).setPrenom(prenom).setPseudo(pseudo).setEmail(email).setTelephone(telephone).setRue(rue)
				.setCodePostal(codePostal).setVille(ville);

				if (currentMdp != null && currentMdp != ""){
					userActuel.setPassword(newMdp);
				}

				userManager.updateUser(userActuel);

				request.setAttribute("currentUser",userActuel);

				request.setAttribute("succes", errorMessage);
				request.getRequestDispatcher("/WEB-INF/pages/modificationCompte.jsp").forward(request, response);

			} else {
				request.setAttribute("error", errorMessage);
				request.getRequestDispatcher("/WEB-INF/pages/modificationCompte.jsp").forward(request, response);
			}
		}else{
			request.getRequestDispatcher("/WEB-INF/pages/modificationCompte.jsp").forward(request, response);
		}

	}

}
