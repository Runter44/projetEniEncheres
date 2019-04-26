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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.criteres.CritArticle;
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
	private static final String INSERT_ARTICLE = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, rue, code_postal, ville, image_path) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_ARTICLE = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_categorie = ?, rue = ?, code_postal = ?, ville = ? WHERE no_article = ?;";
	private static final String DELETE_ARTICLE = "DELETE FROM articles_vendus WHERE no_article = ?;";
	private static final String SELECT_LIST_ARTICLE_CRIT = "SELECT * FROM articles_vendus WHERE 1=1";

	
	@Override
	public Article find(int id) {
		Article articleVendu = null;
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_ARTICLE_ID);) {
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				articleVendu = new Article();
			
				articleVendu.setNoArticle(result.getInt("no_article"));
				articleVendu.setNomArticle(result.getString("nom_article"));
				articleVendu.setDescription(result.getString("description"));
				articleVendu.setDatesDebutEncheres(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("date_debut_encheres")));
				articleVendu.setDatesFinEncheres(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("date_fin_encheres")));
				articleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				articleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				articleVendu.setRue(result.getString("rue"));
				articleVendu.setCodePostal(result.getString("code_postal"));
				articleVendu.setVille(result.getString("ville"));
				articleVendu.setImagePath(result.getString("image_path"));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				articleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_categorie")));
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
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_ARTICLE_NAME);) {
			stmt.setString(1, name);

			ResultSet result = stmt.executeQuery();
			if (result != null && result.next()) {
				articleVendu = new Article();
				
				articleVendu.setNoArticle(result.getInt("no_article"));
				articleVendu.setNomArticle(result.getString("nom_article"));
				articleVendu.setDescription(result.getString("description"));
				articleVendu.setDatesDebutEncheres(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("date_debut_encheres")));
				articleVendu.setDatesFinEncheres(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("date_fin_encheres")));
				articleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				articleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				articleVendu.setRue(result.getString("rue"));
				articleVendu.setCodePostal(result.getString("code_postal"));
				articleVendu.setVille(result.getString("ville"));
				articleVendu.setImagePath(result.getString("image_path"));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				articleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_categorie")));
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
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_ARTICLE);) {
			

			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				unArticleVendu = new Article();
				
				unArticleVendu.setNoArticle(result.getInt("no_article"));
				unArticleVendu.setNomArticle(result.getString("nom_article"));
				unArticleVendu.setDescription(result.getString("description"));
				unArticleVendu.setDatesDebutEncheres(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("date_debut_encheres")));
				unArticleVendu.setDatesFinEncheres(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("date_fin_encheres")));
				unArticleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
				unArticleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
				unArticleVendu.setRue(result.getString("rue"));
				unArticleVendu.setCodePostal(result.getString("code_postal"));
				unArticleVendu.setVille(result.getString("ville"));
				unArticleVendu.setImagePath(result.getString("image_path"));
				
				DAOUtilisateur daoUser = new DAOUtilisateur();
				Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
				unArticleVendu.setVendeur(user);
				
				DAOCategorie daoCat = new DAOCategorie();
				Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_categorie")));
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
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);) {
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
			stmt.setString(9, articleVendu.getRue());
			stmt.setString(10, articleVendu.getCodePostal());
			stmt.setString(11, articleVendu.getVille());
			stmt.setString(12, articleVendu.getImagePath());

			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			if (res.next()) {
				articleVendu.setNoArticle(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articleVendu;
	}

	@Override
	public boolean update(Article articleVendu) {
		boolean updateRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(UPDATE_ARTICLE);) {
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
			stmt.setString(9, articleVendu.getRue());
			stmt.setString(10, articleVendu.getCodePostal());
			stmt.setString(11, articleVendu.getVille());
			stmt.setInt(12, articleVendu.getNoArticle());

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
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(DELETE_ARTICLE);) {
			stmt.setInt(1, articleVendu.getNoArticle());
			stmt.executeUpdate();
			deletRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			deletRealiser = false;
		}
		return deletRealiser;
	}
	
	
	public List<Article> findListCrit(CritArticle critArticle) {
		List<Article> lesArticlesVendus = new ArrayList<Article>();
		Article articleVendu = null;
		
		List<Object> listValues = new ArrayList<>();
		try (Connection connexion = ConnectionProvider.getConnection()) {
			
			StringBuffer rqt = new StringBuffer(SELECT_LIST_ARTICLE_CRIT);
			
			if(critArticle != null) {
				if(StringUtils.isNotBlank(critArticle.getNomArticle())) {
					rqt.append(" and nom_article like ?");
					listValues.add(critArticle.getNomArticle());
				}
				if(critArticle.getCat() != null) {
					if(critArticle.getCat().getNoCategorie() != null) {
						rqt.append(" and no_categorie = ?");
						listValues.add(critArticle.getCat().getNoCategorie());
					}
				}
				if(critArticle.getVendeur() != null) {
					if(critArticle.getVendeur().getId() != null) {
						rqt.append(" and no_utilisateur = ?");
						listValues.add(critArticle.getVendeur().getId());
					}
				}
				if(critArticle.getDatesDebutEncheres() != null) {
					if(critArticle.isNonDebute()) {
						rqt.append(" and date_debut_encheres > ?");
					}else {
						rqt.append(" and date_debut_encheres <= ?");
					}
					listValues.add(critArticle.getDatesDebutEncheres());
				}
				if(critArticle.getDatesFinEncheres() != null) { 
					if(critArticle.isTerminer()) {
						rqt.append(" and date_fin_encheres < ?");
					}else {
						rqt.append(" and date_fin_encheres > ?");
					}
					
					listValues.add(critArticle.getDatesFinEncheres());
				}
				
			
				PreparedStatement stmt = connexion.prepareStatement(rqt.toString());
				
				int compteur = 0;
				for (Iterator<Object> iterator = listValues.iterator(); iterator.hasNext();) {
					Object object = iterator.next();
					compteur++;
					if(object.getClass() == Integer.class ) {
						stmt.setInt(compteur, (Integer) object);
					}
					if(object.getClass() == String.class ) {
						stmt.setString(compteur, "%"+object.toString()+"%");
					}
					if(object.getClass() == java.util.Date.class ) {
						java.sql.Date sqlDate = new Date(((java.util.Date) object).getTime());
						stmt.setDate(compteur, sqlDate);
					}
				}
				
				ResultSet result = stmt.executeQuery();
				
				while(result != null && result.next()) {
					articleVendu = new Article();
					
					articleVendu.setNoArticle(result.getInt("no_article"));
					articleVendu.setNomArticle(result.getString("nom_article"));
					articleVendu.setDescription(result.getString("description"));
					articleVendu.setDatesDebutEncheres(result.getDate("date_debut_encheres"));
					articleVendu.setDatesFinEncheres(result.getDate("date_fin_encheres"));
					articleVendu.setMiseAPrix(Integer.parseInt(result.getString("prix_initial")));
					articleVendu.setPrixVente(Integer.parseInt(result.getString("prix_vente")));
					articleVendu.setRue(result.getString("rue"));
					articleVendu.setCodePostal(result.getString("code_postal"));
					articleVendu.setVille(result.getString("ville"));
					articleVendu.setImagePath(result.getString("image_path"));
					
					DAOUtilisateur daoUser = new DAOUtilisateur();
					Utilisateur user = daoUser.find(Integer.parseInt(result.getString("no_utilisateur")));
					articleVendu.setVendeur(user);
					
					DAOCategorie daoCat = new DAOCategorie();
					Categorie cat = daoCat.find(Integer.parseInt(result.getString("no_categorie")));
					articleVendu.setCat(cat);
					
					lesArticlesVendus.add(articleVendu);
				}
				
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lesArticlesVendus;
	}

}
