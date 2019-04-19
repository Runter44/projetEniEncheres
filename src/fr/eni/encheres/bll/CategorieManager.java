package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.impl.DAOCategorie;

public class CategorieManager {
	
	private static DAOCategorie daoCategorie;
	
	public CategorieManager() {
		daoCategorie = DAOFactory.getDAOCategorie();
	}
	
	public Categorie getCatById(int id) {
		return daoCategorie.find(id);
	}
	
	public List<Categorie> getAllCat() {
		return daoCategorie.findAll();
	}
	
	public boolean updateCat(Categorie user) {
		return daoCategorie.update(user);
	}
	
	public boolean deleteCat(Categorie user) {
		return daoCategorie.remove(user);
	}
	
}
