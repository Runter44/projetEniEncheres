/**
 * 
 */
package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.criteres.CritEnchere;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;


public class DAOEnchere implements InterfaceDAO<Enchere> {

	private static final String SELECT_ALL_ENCHERES = "SELECT * FROM encheres;";
	private static final String SELECT_ONE_ENCHERE_ARTICLE_ID = "SELECT * FROM encheres where no_article = ?;";

	private static final String INSERT_ENCHERE = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_ENCHERE = "UPDATE encheres SET date_enchere=?, montant_enchere=? where no_utilisateur=?, no_article=?;";
	private static final String DELETE_ENCHERE = "DELETE FROM encheres WHERE no_article = ? AND no_utilisateur = ?;";

	private static final String SELECT_LIST_ENCHERE_CRIT = "SELECT *"
			+ " FROM encheres E"
			+ " JOIN articles_vendus A ON E.no_article=A.no_article"
			+ " JOIN utilisateurs U ON E.no_utilisateur=U.no_utilisateur"
			+ " WHERE 1=1";

	private DAOArticle daoArticle;
	private DAOUtilisateur daoUtilisateur;


	public DAOEnchere() {
		daoArticle = DAOFactory.getDAOArticle();
		daoUtilisateur = DAOFactory.getDAOUtilisateur();
	}

	@Override
	public Enchere find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	public Enchere findByNoArticle(int id) {
		Enchere enchere = null;
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ONE_ENCHERE_ARTICLE_ID);) {
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				Article article = daoArticle.find(id);
				Utilisateur utilisateur = daoUtilisateur.find(result.getInt("no_utilisateur"));
				enchere = new Enchere();
				enchere.setArticle(article);
				enchere.setUser(utilisateur);
				enchere.setValeur(result.getInt("montant_enchere"));
				enchere.setDateEnchere(new Date(result.getString("date_enchere")));
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
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(SELECT_ALL_ENCHERES);) {
			ResultSet result = stmt.executeQuery();
			while(result != null && result.next()) {
				Article article = daoArticle.find(result.getInt("no_article"));
				Utilisateur utilisateur = daoUtilisateur.find(result.getInt("no_utilisateur"));
				uneEnchere = new Enchere();
				uneEnchere.setArticle(article);
				uneEnchere.setUser(utilisateur);
				uneEnchere.setValeur(result.getInt("montant_enchere"));
				uneEnchere.setDateEnchere(new SimpleDateFormat("yyyy-MM-dd").parse(result.getString("date_enchere")));

				lesEncheres.add(uneEnchere);
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return lesEncheres;
	}

	@Override
	public Enchere insert(Enchere enchere) {
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(INSERT_ENCHERE);) {
			stmt.setInt(1, enchere.getUser().getId());
			stmt.setInt(2, enchere.getArticle().getNoArticle());
			java.sql.Date sqlDate = new java.sql.Date(enchere.getDateEnchere().getTime());
			stmt.setDate(3, sqlDate);
			stmt.setInt(4, enchere.getValeur());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return enchere;
	}

	@Override
	public boolean update(Enchere enchere) {
		boolean updateRealiser = false;
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(UPDATE_ENCHERE);) {
			java.sql.Date sqlDate = new java.sql.Date(enchere.getDateEnchere().getTime());
			stmt.setDate(1, sqlDate);
			stmt.setInt(2, enchere.getValeur());
			stmt.setInt(3, enchere.getUser().getId());
			stmt.setInt(4, enchere.getArticle().getNoArticle());
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
		try (Connection connexion = ConnectionProvider.getConnection();
				PreparedStatement stmt = connexion.prepareStatement(DELETE_ENCHERE);) {
			stmt.setInt(1, enchere.getArticle().getNoArticle());
			stmt.setInt(2, enchere.getUser().getId());

			stmt.executeUpdate();
			deletRealiser = true;
		} catch (SQLException e) {
			e.printStackTrace();
			deletRealiser = false;
		}
		return deletRealiser;
	}

	public List<Enchere> findListEnchereCrit(CritEnchere critEnchere) {
		List<Enchere> lesEncheres = new ArrayList<Enchere>();
		Enchere enchere = null;
		try (Connection connexion = ConnectionProvider.getConnection()) {

			StringBuffer rqt = new StringBuffer(SELECT_LIST_ENCHERE_CRIT);

			if(critEnchere != null) {
				if(critEnchere.getVente() != null) {
					if(critEnchere.getVente().getNoArticle() != null) {
						rqt.append(" and A.no_article = "+critEnchere.getVente().getNoArticle());
					}
					if(StringUtils.isNotBlank(critEnchere.getVente().getNomArticle())) {
						rqt.append(" and A.nom_article like '%"+critEnchere.getVente().getNomArticle()+"%'");
					}
					if(critEnchere.getVente().getCat() != null) {
						rqt.append(" and A.no_categorie = "+critEnchere.getVente().getCat().getNoCategorie());
					}
					if(critEnchere.getVente().getDatesDebutEncheres() != null) {
						if(critEnchere.isNonDebute()) {
							rqt.append(" and A.date_debut_encheres < '"+critEnchere.getVente().getDatesDebutEncheres()+"'");
						}else {
							rqt.append(" and A.date_debut_encheres >= '"+critEnchere.getVente().getDatesDebutEncheres()+"'");
						}
					}
					if(critEnchere.getVente().getDatesFinEncheres() != null) {
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						String today =  df.format(new Date(critEnchere.getVente().getDatesFinEncheres().getTime()));
						rqt.append(" and A.date_fin_encheres < '"+today+"'");
					}
				}
				if(critEnchere.getUser() != null) {
					if(critEnchere.getUser().getId() != null) {
						rqt.append(" and U.no_utilisateur = "+critEnchere.getUser().getId());
					}
				}
				if(critEnchere.isEnCours()) {
					if(critEnchere.getDateEnchere() != null) {	
						rqt.append(" and A.date_fin_encheres > "+critEnchere.getDateEnchere());
					}
				}else {
					if(critEnchere.getDateEnchere() != null) {
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						String today =  df.format(new Date());
						rqt.append(" and A.date_fin_encheres <= '"+today+"'");
					}
				}

				if(critEnchere.getValeur() != 0){
					rqt.append(" and E.montant_enchere = "+critEnchere.getValeur());		
				}
				if(critEnchere.getOrderBy() != null){

						if(critEnchere.getSensTri() != null){
							rqt.append(" ORDER BY "+critEnchere.getOrderBy()+" "+critEnchere.getSensTri());
						}else{
							rqt.append(" ORDER BY "+critEnchere.getOrderBy());
						}				
					}
			
					PreparedStatement stmt = connexion.prepareStatement(rqt.toString());

					ResultSet result = stmt.executeQuery();

					while(result != null && result.next()) {
						Article article = daoArticle.find(result.getInt("no_article"));
						Utilisateur utilisateur = daoUtilisateur.find(result.getInt("no_utilisateur"));
						enchere = new Enchere();
						enchere.setArticle(article);
						enchere.setUser(utilisateur);
						enchere.setValeur(result.getInt("montant_enchere"));
						enchere.setDateEnchere(new SimpleDateFormat("yyyy-MM-dd").parse(result.getString("date_enchere")));
						lesEncheres.add(enchere);	
					}
					
					result.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return lesEncheres;
		}

}
	
