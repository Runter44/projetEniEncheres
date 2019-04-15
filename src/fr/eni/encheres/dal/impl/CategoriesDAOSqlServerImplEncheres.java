package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.DateFormat;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;

public class CategoriesDAOSqlServerImplEncheres implements InterfaceDAO<Categorie>{

	public static Map<String,String> CONSTANTE = new HashMap<>(); 
	
	static {	//initialisation des constantes
		
		CONSTANTE.put("SELECT", "SELECT * FROM categories WHERE no_categorie = ?");
		CONSTANTE.put("SELECTALL", "SELECT * FROM categories");
		CONSTANTE.put("INSERT", "INSERT INTO categories values (?)");
		CONSTANTE.put("UPDATE", "UPDATE categories SET libelle=? WHERE no_categorie=?");
		
	}
	/*
	private final String INSERT = "INSERT INTO categories values (?)";
	public final String SELECT = "SELECT * FROM categories WHERE no_categorie = ?";
	*/
	@Override
	public Categorie select(Categorie cat,String sql) {
		Connection conn = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("SELECT"));
			stmt.setInt(1, cat.getNoCategorie());			

			
			//Executer la requête
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				cat.setLibelle(rs.getString("libelle"));
				
			} else {
				cat = null;
			}
			
		//Gestion des erreurs	
			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally {
			//Fermer la connexion
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cat;
	}

	@Override
	public List<Categorie> selectAll() {
		Connection conn = null;
		List<Categorie> catListe = new ArrayList<>();
		Categorie cat = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			Statement stmt = conn.createStatement();
						
			//Executer la requête
			ResultSet rs = stmt.executeQuery(CONSTANTE.get("SELECTALL"));
			
			while(rs.next()) {
				cat = new Categorie();
				cat.setLibelle(rs.getString("libelle"));
				cat.setNoCategorie(rs.getInt("no_categorie"));
				
				catListe.add(cat);
			}
			
		//Gestion des erreurs			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally {
			//Fermer la connexion
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return catListe;
	}

	@Override
	public boolean remove(int id) {
		return false;
		
	}

	@Override
	public boolean update(Categorie categorie) {
		boolean retour = true;
		Connection conn = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();			
			
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("UPDATE"));
			
			stmt.setString(1, categorie.getLibelle());			
			stmt.setInt(2, categorie.getNoCategorie());
			
			
			//Executer la requête
			stmt.executeUpdate();			
			
		//Gestion des erreurs	
			
		} catch (SQLException e) {
			retour=false;
			e.printStackTrace();			
		}finally {
			//Fermer la connexion
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return retour;	
		
	}

	@Override
	public int insert(Categorie categorie) {
		Connection conn = null;
		int id = 0;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("INSERT"), PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, categorie.getLibelle());			
			
			//Executer la requête
			stmt.executeUpdate();
			
			//RecupÃ©rer l'identifiant crÃ©Ã©
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				id = rs.getInt(1);
			}
			
		//Gestion des erreurs	
			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally {
			//Fermer la connexion
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	@Override
	public List<Categorie> selectAll(Categorie t, String requete) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Categorie t, String requete) {
		// TODO Auto-generated method stub
		return false;
	}

}
