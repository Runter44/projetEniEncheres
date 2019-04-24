package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.criteres.CritEnchere;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.impl.DAOEnchere;

public class EnchereManager {
	
	private static DAOEnchere daoEnchere;
	
	public EnchereManager() {
		daoEnchere = DAOFactory.getDAOEnchere();
	}
	
	public Enchere getEnchereById(int id) {
		return daoEnchere.find(id);
	}

	public List<Enchere> getAllEncheres() {
		return daoEnchere.findAll();
	}
	
	public Enchere addEnchere(Enchere enchere) {
		return daoEnchere.insert(enchere);
	}
	
	public boolean updateEnchere(Enchere enchere) {
		return daoEnchere.update(enchere);
	}
	
	public boolean deleteEnchere(Enchere enchere) {
		return daoEnchere.remove(enchere);
	}
	
	public List<Enchere> getListEnchereByCrit(CritEnchere critEnchere){
		return daoEnchere.findListEnchereCrit(critEnchere);
	}
	
}
