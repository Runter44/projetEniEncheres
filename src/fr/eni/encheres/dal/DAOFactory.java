package fr.eni.encheres.dal;

import fr.eni.encheres.dal.impl.CategoriesDAOSqlServerImplEncheres;
import fr.eni.encheres.dal.impl.EncheresDAOSqlServerImplEncheres;
import fr.eni.encheres.dal.impl.RetraitsDAOSqlServerImpl;
import fr.eni.encheres.dal.impl.UtilisateurDAOSqlServerImpl;
import fr.eni.encheres.dal.impl.VenteDAOSqlServerImpl;

public class DAOFactory {
	
	public static CategoriesDAOSqlServerImplEncheres getCategoriesDAO(){
		return new CategoriesDAOSqlServerImplEncheres();
	}
	
	public static EncheresDAOSqlServerImplEncheres getEncheresDAO(){
		return new EncheresDAOSqlServerImplEncheres();
	}
	
	public static RetraitsDAOSqlServerImpl getRetraitsDAO(){
		return new RetraitsDAOSqlServerImpl();
	}
	
	public static UtilisateurDAOSqlServerImpl getUtilisateursDAO(){
		return new UtilisateurDAOSqlServerImpl();
	}
	
	public static VenteDAOSqlServerImpl getVenteDAO(){
		return new VenteDAOSqlServerImpl();
	}
}
