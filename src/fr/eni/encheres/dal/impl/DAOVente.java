/**
 * 
 */
package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.InterfaceDAO;

/**
 * @author amartin2018
 *
 */
public class DAOVente implements InterfaceDAO<Vente>{
	
	private static final String SELECT_ONE_SELL_ID = "SELECT * FROM articles_vendus WHERE no_article = ?;";
	private static final String SELECT_ONE_SELL_NAME = "SELECT * FROM articles_vendus WHERE nom_article = ?;";
	private static final String SELECT_ALL_SELL = "SELECT * FROM articles_vendus;";
	private static final String INSERT_SELL = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_catgories) VALUES (?,?,?,?,?,?,?,?);";
	private static final String UPDATE_SELL = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_catgories = ? WHERE no_article = ?;";
	private static final String DELETE_SELL = "DELETE FROM articles_vendus WHERE no_article = ?;";

	
	@Override
	public Vente find(int id) {
		Vente articleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_SELL_ID);
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				articleVendu = new Vente();

				articleVendu.setNoVente(result.getInt("no_utilisateur"));
				articleVendu.setNomArticle(result.getString("nom_article"));
				articleVendu.setDescription(result.getString("description"));
				articleVendu.setDatesDebutEncheres(Timestamp.valueOf(result.getString("date_debut_encheres")));
				articleVendu.setDatesFinEncheres(Timestamp.valueOf(result.getString("date_fin_encheres")));
				articleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				articleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				articleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_catgories")));
				articleVendu.setCat(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleVendu;
	}
	
	public Vente findByName(String name) {
		Vente articleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_SELL_NAME);
			stmt.setString(1, name);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				articleVendu = new Vente();

				articleVendu.setNoVente(result.getInt("no_utilisateur"));
				articleVendu.setNomArticle(result.getString("nom_article"));
				articleVendu.setDescription(result.getString("description"));
				articleVendu.setDatesDebutEncheres(Timestamp.valueOf(result.getString("date_debut_encheres")));
				articleVendu.setDatesFinEncheres(Timestamp.valueOf(result.getString("date_fin_encheres")));
				articleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				articleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				articleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_catgories")));
				articleVendu.setCat(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleVendu;
	}

	@Override
	public List<Vente> findAll() {
		List<Vente> LesArticlesVendus = new ArrayList<Vente>();
		Vente unArticleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_SELL);
			

			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				unArticleVendu = new Vente();

				unArticleVendu.setNoVente(result.getInt("no_utilisateur"));
				unArticleVendu.setNomArticle(result.getString("nom_article"));
				unArticleVendu.setDescription(result.getString("description"));
				unArticleVendu.setDatesDebutEncheres(Timestamp.valueOf(result.getString("date_debut_encheres")));
				unArticleVendu.setDatesFinEncheres(Timestamp.valueOf(result.getString("date_fin_encheres")));
				unArticleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				unArticleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				unArticleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_catgories")));
				unArticleVendu.setCat(cat);
				
				//Ajout d'un article � la liste
				LesArticlesVendus.add(unArticleVendu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return LesArticlesVendus;
	}

	@Override
	public Vente insert(Vente articleVendu) {
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(INSERT_SELL, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, articleVendu.getNomArticle());
			stmt.setString(2, articleVendu.getDescription());
			Date debut = new Date(articleVendu.getDatesDebutEncheres().getTime());
			stmt.setDate(3, debut);
			Date fin = new Date(articleVendu.getDatesFinEncheres().getTime());
			stmt.setDate(4, fin);
			stmt.setInt(5, articleVendu.getMiseAPrix());
			stmt.setInt(6, articleVendu.getPrixVente());
			stmt.setInt(7, articleVendu.getVendeur().getId());
			stmt.setInt(8, articleVendu.getCat().getNoCategorie());

			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			if (res.next()) {
				articleVendu.setNoVente(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articleVendu;
	}

	@Override
	public boolean update(Vente articleVendu) {
		boolean updateRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(UPDATE_SELL);
			stmt.setString(1, articleVendu.getNomArticle());
			stmt.setString(2, articleVendu.getDescription());
			Date debut = new Date(articleVendu.getDatesDebutEncheres().getTime());
			stmt.setDate(3, debut);
			Date fin = new Date(articleVendu.getDatesFinEncheres().getTime());
			stmt.setDate(4, fin);
			stmt.setInt(5, articleVendu.getMiseAPrix());
			stmt.setInt(6, articleVendu.getPrixVente());
			stmt.setInt(7, articleVendu.getVendeur().getId());
			stmt.setInt(8, articleVendu.getCat().getNoCategorie());

			stmt.executeUpdate();
			updateRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			updateRealiser = false;
		}
		return updateRealiser;
	}

	@Override
	public boolean remove(Vente articleVendu) {
		boolean deletRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(DELETE_SELL);
			stmt.setInt(1, articleVendu.getNoVente());
			stmt.executeUpdate();
			deletRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			deletRealiser = false;
		}
		return deletRealiser;
	}

}
