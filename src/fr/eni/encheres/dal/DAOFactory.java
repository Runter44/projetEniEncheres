package fr.eni.encheres.dal;

import fr.eni.encheres.dal.impl.DAOArticle;
import fr.eni.encheres.dal.impl.DAOCategorie;
import fr.eni.encheres.dal.impl.DAOEnchere;
import fr.eni.encheres.dal.impl.DAOUtilisateur;

public class DAOFactory {
	
	private static DAOUtilisateur daoUtilisateur;
	private static DAOCategorie daoCategorie;
	private static DAOArticle daoArticle;
	private static DAOEnchere daoEnchere;
	
	public static DAOUtilisateur getDAOUtilisateur() {
		if (daoUtilisateur == null) {
			daoUtilisateur = new DAOUtilisateur();
		}
		return daoUtilisateur;
	}
	
	public static DAOCategorie getDAOCategorie() {
		if (daoCategorie == null) {
			daoCategorie = new DAOCategorie();
		}
		return daoCategorie;
	}
	
	public static DAOArticle getDAOArticle() {
		if (daoArticle == null) {
			daoArticle = new DAOArticle();
		}
		return daoArticle;
	}
	
	public static DAOEnchere getDAOEnchere() {
		if (daoEnchere == null) {
			daoEnchere = new DAOEnchere();
		}
		return daoEnchere;
	}
}
