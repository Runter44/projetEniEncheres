package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.InterfaceDAO;

public class DAOUtilisateur implements InterfaceDAO<Utilisateur> {

	private static final String SELECT_ONE_USER_ID = "SELECT * FROM utilisateurs WHERE no_utilisateur = ?;";
	private static final String SELECT_ONE_USER_PSEUDO = "SELECT * FROM utilisateurs WHERE pseudo = ?;";
	private static final String SELECT_ALL_USER = "SELECT * FROM utilisateurs;";
	private static final String INSERT_USER = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_USER = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, credit = ?, administrateur = ? WHERE no_utilisateur = ?;";
	private static final String DELETE_USER = "DELETE FROM utilisateurs WHERE no_utilisateur = ?;";

	@Override
	public Utilisateur find(int id) {
		Utilisateur user = null;
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_USER_ID);) {
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				user = new Utilisateur();

				user.setId(result.getInt("no_utilisateur"))
				.setPseudo(result.getString("pseudo"))
				.setPrenom(result.getString("prenom"))
				.setNom(result.getString("nom"))
				.setEmail(result.getString("email"))
				.setTelephone(result.getString("telephone"))
				.setRue(result.getString("rue"))
				.setCodePostal(result.getString("code_postal"))
				.setVille(result.getString("ville"))
				.setPassword(result.getString("mot_de_passe"))
				.setCredit(result.getInt("credit"))
				.setAdmin(result.getBoolean("administrateur"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public Utilisateur findByPseudo(String pseudo) {
		Utilisateur user = null;
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_USER_PSEUDO);) {
			stmt.setString(1, pseudo);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				user = new Utilisateur();

				user.setId(result.getInt("no_utilisateur"))
				.setPseudo(result.getString("pseudo"))
				.setPrenom(result.getString("prenom"))
				.setNom(result.getString("nom"))
				.setEmail(result.getString("email"))
				.setTelephone(result.getString("telephone"))
				.setRue(result.getString("rue"))
				.setCodePostal(result.getString("code_postal"))
				.setVille(result.getString("ville"))
				.setPassword(result.getString("mot_de_passe"))
				.setCredit(result.getInt("credit"))
				.setAdmin(result.getBoolean("administrateur"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<Utilisateur> findAll() {
		List<Utilisateur> users = new ArrayList<>();

		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_USER);) {

			ResultSet result = stmt.executeQuery();
			while (result != null && result.next()) {
				Utilisateur user = new Utilisateur();

				user.setId(result.getInt("no_utilisateur"))
				.setPseudo(result.getString("pseudo"))
				.setPrenom(result.getString("prenom"))
				.setNom(result.getString("nom"))
				.setEmail(result.getString("email"))
				.setTelephone(result.getString("telephone"))
				.setRue(result.getString("rue"))
				.setCodePostal(result.getString("code_postal"))
				.setVille(result.getString("ville"))
				.setPassword(result.getString("mot_de_passe"))
				.setCredit(result.getInt("credit"))
				.setAdmin(result.getBoolean("administrateur"));

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public Utilisateur insert(Utilisateur user) {
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, user.getPseudo());
			stmt.setString(2, user.getNom());
			stmt.setString(3, user.getPrenom());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getTelephone());
			stmt.setString(6, user.getRue());
			stmt.setString(7, user.getCodePostal());
			stmt.setString(8, user.getVille());
			stmt.setString(9, user.getPassword());
			stmt.setInt(10, user.getCredit());
			stmt.setBoolean(11, user.isAdmin());

			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			if (res.next()) {
				user.setId(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean update(Utilisateur user) {
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(UPDATE_USER);) {
			stmt.setString(1, user.getPseudo());
			stmt.setString(2, user.getNom());
			stmt.setString(3, user.getPrenom());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getTelephone());
			stmt.setString(6, user.getRue());
			stmt.setString(7, user.getCodePostal());
			stmt.setString(8, user.getVille());
			stmt.setString(9, user.getPassword());
			stmt.setInt(10, user.getCredit());
			stmt.setBoolean(11, user.isAdmin());
			stmt.setInt(12, user.getId());

			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean remove(Utilisateur user) {
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(DELETE_USER);) {
			stmt.setInt(1, user.getId());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
