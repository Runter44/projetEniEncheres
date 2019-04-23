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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.InterfaceDAO;

/**
 * @author amartin2018
 *
 */
public class DAOArticle implements InterfaceDAO<Article>{
	
	private static final String SELECT_ONE_ARTICLE_ID = "SELECT * FROM articles_vendus WHERE no_article = ?;";
	private static final String SELECT_ONE_ARTICLE_NAME = "SELECT * FROM articles_vendus WHERE nom_article = ?;";
	private static final String SELECT_ALL_ARTICLE = "SELECT * FROM articles_vendus;";
	private static final String INSERT_ARTICLE = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?,?);";
	private static final String UPDATE_ARTICLE = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_catgories = ? WHERE no_article = ?;";
	private static final String DELETE_ARTICLE = "DELETE FROM articles_vendus WHERE no_article = ?;";
	private static final String SELECT_LIST_ARTICLE_CRIT = "SELECT * FROM articles_vendus WHERE 1=1";

	
	@Override
	public Article find(int id) {
		Article articleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_ARTICLE_ID);
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				articleVendu = new Article();
			
				articleVendu.setNoVente(result.getInt("no_utilisateur"));
				articleVendu.setNomArticle(result.getString("nom_article"));
				articleVendu.setDescription(result.getString("description"));
				articleVendu.setDatesDebutEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_debut_encheres")));
				articleVendu.setDatesFinEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_fin_encheres")));
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return articleVendu;
	}
	
	public Article findByName(String name) {
		Article articleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_ARTICLE_NAME);
			stmt.setString(1, name);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				articleVendu = new Article();
				
				articleVendu.setNoVente(result.getInt("no_utilisateur"));
				articleVendu.setNomArticle(result.getString("nom_article"));
				articleVendu.setDescription(result.getString("description"));
				articleVendu.setDatesDebutEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_debut_encheres")));
				articleVendu.setDatesFinEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_fin_encheres")));
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return articleVendu;
	}

	@Override
	public List<Article> findAll() {
		List<Article> LesArticlesVendus = new ArrayList<Article>();
		Article unArticleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_ARTICLE);
			

			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				unArticleVendu = new Article();
				
				unArticleVendu.setNoVente(result.getInt("no_utilisateur"));
				unArticleVendu.setNomArticle(result.getString("nom_article"));
				unArticleVendu.setDescription(result.getString("description"));
				unArticleVendu.setDatesDebutEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_debut_encheres")));
				unArticleVendu.setDatesFinEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_fin_encheres")));
				unArticleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				unArticleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				unArticleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_catgories")));
				unArticleVendu.setCat(cat);
			
				//Ajout d'un article à la liste
				LesArticlesVendus.add(unArticleVendu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return LesArticlesVendus;
	}

	@Override
	public Article insert(Article articleVendu) {
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
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
	public boolean update(Article articleVendu) {
		boolean updateRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(UPDATE_ARTICLE);
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
	public boolean remove(Article articleVendu) {
		boolean deletRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = connexion.prepareStatement(DELETE_ARTICLE);
			stmt.setInt(1, articleVendu.getNoVente());
			stmt.executeUpdate();
			deletRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			deletRealiser = false;
		}
		return deletRealiser;
	}
	
	
	public List<Article> findListCrit(Article critArticle) {
		List<Article> LesArticlesVendus = new ArrayList<Article>();
		Article articleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {
			
			StringBuffer rqt = new StringBuffer(SELECT_LIST_ARTICLE_CRIT);
			
			if(critArticle != null) {
				if(StringUtils.isNotBlank(critArticle.getNomArticle())) {
					rqt.append(" and nom_article like %"+critArticle.getNomArticle()+"%");
				}
				if(critArticle.getCat().getNoCategorie() != null) {
					rqt.append(" and no_catgories = "+critArticle.getCat().getNoCategorie());
				}
			
				PreparedStatement stmt = connexion.prepareStatement(rqt.toString());
			
				ResultSet result = stmt.executeQuery();
				
				while(result != null && result.next()) {
					articleVendu = new Article();
					
					articleVendu.setNoVente(result.getInt("no_utilisateur"));
					articleVendu.setNomArticle(result.getString("nom_article"));
					articleVendu.setDescription(result.getString("description"));
					articleVendu.setDatesDebutEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_debut_encheres")));
					articleVendu.setDatesFinEncheres(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(result.getString("date_fin_encheres")));
					articleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
					articleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
					
					DAOUtilisateur daoUser = new DAOUtilisateur();
					Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
					articleVendu.setVendeur(user);
					
					DAOCategorie daoCat = new DAOCategorie();
					Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_catgories")));
					articleVendu.setCat(cat);
					
					LesArticlesVendus.add(articleVendu);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return LesArticlesVendus;
	}

}
