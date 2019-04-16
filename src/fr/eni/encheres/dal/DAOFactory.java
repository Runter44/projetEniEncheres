package fr.eni.encheres.dal;

import fr.eni.encheres.dal.impl.DAOUtilisateur;

public class DAOFactory {
	
	private static DAOUtilisateur daoUtilisateur;
	
	public static DAOUtilisateur getDAOUtilisateur() {
		if (daoUtilisateur == null) {
			daoUtilisateur = new DAOUtilisateur();
		}
		return daoUtilisateur;
	}
	
}
