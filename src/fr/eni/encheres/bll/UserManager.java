package fr.eni.encheres.bll;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.impl.DAOUtilisateur;

public class UserManager {
	
	private static DAOUtilisateur daoUtilisateur;
	
	public UserManager() {
		daoUtilisateur = DAOFactory.getDAOUtilisateur();
	}
	
	public Utilisateur getUserById(int id) {
		return daoUtilisateur.find(id);
	}
	
	public Utilisateur getUserByPseudo(String pseudo) {
		return daoUtilisateur.findByPseudo(pseudo);
	}
	
	public List<Utilisateur> getAllUsers() {
		return daoUtilisateur.findAll();
	}
	
	public Utilisateur addNewUser(Utilisateur user) {
		user.setPassword(hashPassword(user.getPassword()));
		return daoUtilisateur.insert(user);
	}

	public boolean updateUser(Utilisateur user) {
		return daoUtilisateur.update(user);
	}
	
	public boolean deleteUser(Utilisateur user) {
		return daoUtilisateur.remove(user);
	}
	
	public boolean connectUser(Utilisateur utilisateur, String mdp, HttpServletRequest request) {
		if (daoUtilisateur.findByPseudo(utilisateur.getPseudo()) != null) {
			if (utilisateur.getPassword().equals(mdp)) {
				request.getSession().setAttribute("currentUser", utilisateur);
				return true;
			}
		}
		return false;
	}
	
	private String hashPassword(String plainPassword) {
		return plainPassword;
	}

	public boolean connectWithCookies(Cookie[] allCookies, HttpServletRequest request) {
		Cookie cookieLogin = null, cookieMdp = null;
		if (allCookies != null) {
			for (Cookie cookie : allCookies) {
				if ("login".equals(cookie.getName())) {
					cookieLogin = cookie;
				}
				if ("mdp".equals(cookie.getName())) {
					cookieMdp = cookie;
				}
			}
			if (cookieLogin != null && cookieMdp != null) {
				Utilisateur user = this.getUserByPseudo(cookieLogin.getValue());
				if (user != null) {
					if (this.connectUser(user, cookieMdp.getValue(), request)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
