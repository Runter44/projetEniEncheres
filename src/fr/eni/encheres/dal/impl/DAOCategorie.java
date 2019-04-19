/**
 * 
 */
package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.InterfaceDAO;


public class DAOCategorie implements InterfaceDAO<Categorie> {
	
	private static final String SELECT_ONE_CAT_ID = "SELECT * FROM categories WHERE no_categorie = ?;";
	private static final String SELECT_ALL_CAT = "SELECT * FROM categories;";
	private static final String INSERT_CAT = "INSERT INTO categories (libelle) VALUES (?);";
	private static final String UPDATE_CAT = "UPDATE categories SET libelle = ?;";
	private static final String DELETE_CAT = "DELETE FROM categories WHERE no_categorie = ?;";

	
	@Override
	public Categorie find(int id) {
		Categorie categorie = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_CAT_ID);
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				categorie = new Categorie();
				categorie.setNoCategorie(result.getInt("no_categorie"));
				categorie.setLibelle(result.getString("libelle"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorie;
	}

	@Override
	public List<Categorie> findAll() {
		List<Categorie> lesCategorie = new ArrayList<Categorie>();
		Categorie uneCategorie = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_CAT);
			

			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				uneCategorie = new Categorie();
				uneCategorie.setNoCategorie(result.getInt("no_categorie"));
				uneCategorie.setLibelle(result.getString("libelle"));
				
				lesCategorie.add(uneCategorie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lesCategorie;
	}

	@Override
	public Categorie insert(Categorie categorie) {
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(INSERT_CAT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, categorie.getLibelle());
			

			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			if (res.next()) {
				categorie.setNoCategorie(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categorie;
	}

	@Override
	public boolean update(Categorie categorie) {
		boolean updateRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(UPDATE_CAT);
			stmt.setString(1, categorie.getLibelle());
			stmt.executeUpdate();
			updateRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			updateRealiser = false;
		}
		return updateRealiser;
	}

	@Override
	public boolean remove(Categorie categorie) {
		boolean deletRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(DELETE_CAT);
			stmt.setInt(1, categorie.getNoCategorie());
			stmt.executeUpdate();
			deletRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			deletRealiser = false;
		}
		return deletRealiser;
	}

}
