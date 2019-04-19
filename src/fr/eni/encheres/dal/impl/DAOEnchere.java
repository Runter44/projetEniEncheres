/**
 * 
 */
package fr.eni.encheres.dal.impl;

import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.InterfaceDAO;


public class DAOEnchere implements InterfaceDAO<Enchere> {
	
	private static final String SELECT_ALL_CAT = "SELECT * FROM categories;";
	private static final String INSERT_CAT = "INSERT INTO categories (libelle) VALUES (?);";
	private static final String UPDATE_CAT = "UPDATE categories SET libelle = ?;";
	private static final String DELETE_CAT = "DELETE FROM categories WHERE no_categorie = ?;";
	@Override
	public Enchere find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Enchere> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Enchere insert(Enchere t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean update(Enchere t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean remove(Enchere t) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/*@Override
	public Enchere find(int id) {
		Enchere enchere = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_ENCHERE_ID);
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				enchere = new Enchere();
				enchere.s(result.getInt("no_categorie"));
				enchere.setLibelle(result.getString("libelle"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enchere;
	}

	@Override
	public List<Enchere> findAll() {
		List<Enchere> lesEncheres = new ArrayList<Enchere>();
		Enchere uneEnchere = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_CAT);
			

			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				uneEnchere = new Categorie();
				uneEnchere.setNoCategorie(result.getInt("no_categorie"));
				uneEnchere.setLibelle(result.getString("libelle"));
				
				lesEncheres.add(uneEnchere);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lesEncheres;
	}

	@Override
	public Enchere insert(Enchere enchere) {
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(INSERT_CAT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, enchere.getLibelle());
			

			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			if (res.next()) {
				enchere.setNoCategorie(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return enchere;
	}

	@Override
	public boolean update(Enchere enchere) {
		boolean updateRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(UPDATE_CAT);
			stmt.setString(1, enchere.getLibelle());
			stmt.executeUpdate();
			updateRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			updateRealiser = false;
		}
		return updateRealiser;
	}

	@Override
	public boolean remove(Enchere enchere) {
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
	}*/

}
