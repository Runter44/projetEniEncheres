package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.DateFormat;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;

public class VenteDAOSqlServerImpl implements InterfaceDAO<Vente>{
	
	public static Map<String,String> CONSTANTE = new HashMap<>(); 
	
	static {

		CONSTANTE.put("SELECT", "SELECT * FROM ventes "
				+ "									LEFT JOIN retraits" + 
				"									ON retraits.no_vente = ventes.no_vente "
				+ "									WHERE ventes.no_vente = ?"
				+ "										");
		
		CONSTANTE.put("SELECTALL", "SELECT * FROM ventes JOIN retraits"  
				+ "										ON retraits.no_vente = ventes.no_vente " 
				+ "				 ");
		
		CONSTANTE.put("INSERT", "INSERT INTO ventes(nomarticle ,description, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) values (?,?,?,?,?,?,?)");
		
		CONSTANTE.put("UPDATE", "UPDATE ventes SET nomarticle=? ,description=?, date_fin_encheres=?,"+
				"prix_initial=?, prix_vente=?, no_utilisateur=?,"+
				"no_categorie=? WHERE no_vente=?");
		
		CONSTANTE.put("SELECTBYVENDEUR", "SELECT * FROM ventes "
				+ "							LEFT JOIN retraits "
				+ "								ON retraits.no_vente = ventes.no_vente "
				+ "							WHERE no_utilisateur = ?");
		
		CONSTANTE.put("SELECTBYENCHEREUR", "SELECT v.no_vente,v.nomarticle ,v.description, v.date_fin_encheres, v.prix_initial,"
				+ "									 v.prix_vente, v.no_utilisateur, v.no_categorie "
				+ "								FROM ventes v"
				+ "								JOIN encheres e"
				+ "									ON v.no_vente = e.no_vente"
				+ "								LEFT JOIN retraits 	"
				+ "									ON retraits.no_vente = v.no_vente	"
				+ "								WHERE e.no_utilisateur = ?");
		
		CONSTANTE.put("SELECTBYDATE", "SELECT * FROM ventes "
				+ "							LEFT JOIN retraits "
				+ "								ON retraits.no_vente = ventes.no_vente "
				+ "							WHERE date_fin_encheres = ?");
		
		CONSTANTE.put("SELECTBYNOM", "SELECT * FROM ventes "
				+ "							LEFT JOIN retraits "
				+ "								ON retraits.no_vente = ventes.no_vente "
				+ "							WHERE nomarticle like ?");
		
		CONSTANTE.put("SELECTTOBROWSE", "SELECT v.no_vente,v.nomarticle ,v.description, v.date_fin_encheres, v.prix_initial," + 
				"				+ 									 v.prix_vente, v.no_utilisateur, v.no_categorie "
				+ "						FROM ventes v"
				+ "							LEFT JOIN retraits "
				+ "								ON retraits.no_vente = v.no_vente "
				+ "							LEFT JOIN encheres"
				+ "								ON v.no_vente = encheres.no_vente AND encheres.no_utilisateur <> ?"
				+ "							WHERE date_fin_encheres >= ?"
				+ "								AND v.no_utilisateur <> ?"
				+ "								AND v.no_vente NOT IN (SELECT e.no_vente FROM encheres e " 
				+ "		 												WHERE e.no_utilisateur = ?)"
				+ "								 "
				+ "							GROUP BY v.no_vente,v.nomarticle ,v.description, v.date_fin_encheres, v.prix_initial," 
				+ "													 v.prix_vente, v.no_utilisateur, v.no_categorie");
		;
		CONSTANTE.put("DELETE", "DELETE FROM ventes WHERE no_vente =(?)");
		
	}
/*
	public static final String SELECT = "SELECT * FROM vente WHERE no_vente = ?";
	public static final String SELECTALL = "SELECT * FROM vente";
	public static final String INSERT = "INSERT INTO vente(nomarticle ,description, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) values (?,?,?,?,?,?,?)";
	public static final String UPDATE = "UPDATE vente SET nomarticle=? ,description=?, date_fin_encheres=?,"+
									"prix_initial=?, prix_vente=?, no_utilisateur=?,"+
									"no_categorie=?"; 
*/
	@Override
	public Vente select(Vente vente, String sql) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			if ("SELECT".equals(sql)) {
				stmt = conn.prepareStatement(CONSTANTE.get("SELECT"));
				stmt.setInt(1, vente.getNoVente());	
				
			}

			
			//Executer la requête
			if (stmt != null) {
				rs = stmt.executeQuery();
			}
			

			
			if(rs != null && rs.next()) {
				
				vente.setNomArticle(rs.getString("nomarticle"));// ,
				vente.setDescription(rs.getString("description"));
				vente.setDatesFinEncheres(DateFormat.SqlDateTOLocalDateTime(rs.getDate("date_fin_encheres")));
				vente.setMiseAPrix(rs.getInt("prix_initial"));
				vente.setPrixVente(rs.getInt("prix_vente"));
				
				//Recherche du vendeur
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				
				vente.setVendeur(DAOFactory.getUtilisateursDAO().select(vendeur,"SELECTBYID"));
				
				//Recherche de la categorie
				Categorie cat = new Categorie();
				cat.setNoCategorie(rs.getInt("no_categorie"));
				
				vente.setCat(DAOFactory.getCategoriesDAO().select(cat,"SELECT"));
				
				//Ajout du retrait
				Retrait retraitTmp = new Retrait();
				retraitTmp.setCodePostal(rs.getString("code_postal"));
				retraitTmp.setRue(rs.getString("rue"));
				retraitTmp.setVille(rs.getString("ville"));
				retraitTmp.setVente(vente);
				//, , , , , 
			} else {
				vente = null;
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
		return vente;
	}
	
	@Override
	public List<Vente> selectAll() {
		Connection conn = null;
		List<Vente> venteListe = new ArrayList<>();
		Vente vente = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			Statement stmt = conn.createStatement();
						
			//Executer la requête
			ResultSet rs = stmt.executeQuery(CONSTANTE.get("SELECTALL"));
			
			while(rs.next()) {
				vente = new Vente();
				System.out.println(rs.getString("date_fin_encheres"));
				
				vente.setNomArticle(rs.getString("nomarticle"));// ,
				vente.setDescription(rs.getString("description"));
				vente.setDatesFinEncheres(DateFormat.SqlDateTOLocalDateTime(rs.getDate("date_fin_encheres")));
				vente.setMiseAPrix(rs.getInt("prix_initial"));
				vente.setPrixVente(rs.getInt("prix_vente"));
				
				//Recherche du vendeur
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				
				vente.setVendeur(DAOFactory.getUtilisateursDAO().select(vendeur,"SELECTBYID"));
				
				//Recherche de la categorie
				Categorie cat = new Categorie();
				cat.setNoCategorie(rs.getInt("no_categorie"));
				
				vente.setCat(DAOFactory.getCategoriesDAO().select(cat,"SELECT"));
				
				//Ajout du retrait
				Retrait retraitTmp = new Retrait();
				retraitTmp.setCodePostal(rs.getString("code_postal"));
				retraitTmp.setRue(rs.getString("rue"));
				retraitTmp.setVille(rs.getString("ville"));
				retraitTmp.setVente(vente);
				
				venteListe.add(vente);
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
		return venteListe;
	}


	@Override
	public int insert(Vente vente) {
		Connection conn = null;
		int id = 0;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("INSERT"), PreparedStatement.RETURN_GENERATED_KEYS);
			//nomarticle ,description, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie
			stmt.setString(1, vente.getNomArticle());			
			stmt.setString(2, vente.getDescription());
			java.sql.Date sqlDate = java.sql.Date.valueOf(vente.getDatesFinEncheres().toLocalDate());
			stmt.setDate(3, sqlDate);			
			stmt.setInt(4, vente.getMiseAPrix());
			stmt.setInt(5, vente.getPrixVente());
			stmt.setInt(6, vente.getVendeur().getNoUtilisateur());
			stmt.setInt(7, vente.getCat().getNoCategorie());

			
			
			//Executer la requête
			stmt.executeUpdate();
			
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
	public boolean update(Vente vente) {
		boolean retour = true;
		Connection conn = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();			
			
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("UPDATE"));
			//nomarticle ,description, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie
			stmt.setString(1, vente.getNomArticle());			
			stmt.setString(2, vente.getDescription());
			java.sql.Date sqlDate = java.sql.Date.valueOf(vente.getDatesFinEncheres().toLocalDate());
			stmt.setDate(3, sqlDate);			
			stmt.setInt(4, vente.getMiseAPrix());
			stmt.setInt(5, vente.getPrixVente());
			stmt.setInt(6, vente.getVendeur().getNoUtilisateur());
			stmt.setInt(7, vente.getCat().getNoCategorie());
			stmt.setInt(8, vente.getNoVente());
			
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
	public boolean remove(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Vente> selectAll(Vente vente, String sql) {

		Connection conn = null;
		List<Vente> venteListe = new ArrayList<>();
		Vente venteTmp = new Vente();
		PreparedStatement stmt = null;
		Enchere enchereVide;

		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();

			//Préparer la requête
			switch(sql) {
			case "SELECTBYVENDEUR":

				stmt = conn.prepareStatement(CONSTANTE.get("SELECTBYVENDEUR"));

				stmt.setInt(1, vente.getVendeur().getNoUtilisateur());	
				break;

			case "SELECTBYENCHEREUR":
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTBYENCHEREUR"));
				stmt.setInt(1, vente.getVendeur().getNoUtilisateur());	
				break;
				
			case "SELECTBYNOM":
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTBYNOM"));
				stmt.setString(1, "%"+vente.getNomArticle()+"%");	
				break;
			case "SELECTBYDATE":
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTBYDATE"));
				stmt.setDate(1, DateFormat.TOSQLDATE(vente.getDatesFinEncheres()));	
				break;
			case "SELECTTOBROWSE":
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTTOBROWSE"));
				//nomarticle, datefin, vendeurNumber
				stmt.setString(1, vente.getNomArticle());	
				stmt.setDate(2, DateFormat.TOSQLDATE(vente.getDatesFinEncheres()));	
				stmt.setInt(3, vente.getVendeur().getNoUtilisateur());	
				stmt.setInt(4, vente.getVendeur().getNoUtilisateur());	
				break;
			}


			//Executer la requête
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				venteTmp = new Vente();
//System.out.println("vente trouvee "+rs.getString("nomarticle"));
				venteTmp.setNoVente(rs.getInt("no_vente"));
				venteTmp.setNomArticle(rs.getString("nomarticle"));
				venteTmp.setDescription(rs.getString("description"));
				venteTmp.setDatesFinEncheres(DateFormat.SqlDateTOLocalDateTime(rs.getDate("date_fin_encheres")));
				venteTmp.setMiseAPrix(rs.getInt("prix_initial"));
				venteTmp.setPrixVente(rs.getInt("prix_vente"));

				//Recherche du vendeur
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));

				venteTmp.setVendeur(DAOFactory.getUtilisateursDAO().select(vendeur,"SELECTBYID"));

				//Recherche de la categorie
				Categorie cat = new Categorie();
				cat.setNoCategorie(rs.getInt("no_categorie"));

				venteTmp.setCat(DAOFactory.getCategoriesDAO().select(cat,"SELECT"));

				//Ajout du retrait
				Retrait retraitTmp = new Retrait();
				
				//si cette vente a un point de retrait
				if (hasColumn(rs, "code_postal")) {
					retraitTmp.setCodePostal(rs.getString("code_postal"));
					retraitTmp.setRue(rs.getString("rue"));
					retraitTmp.setVille(rs.getString("ville"));
				}
				retraitTmp.setVente(venteTmp);
				venteTmp.setRetrait(retraitTmp);
				
				//ajout des encheres de la vente
				enchereVide = new Enchere();
				enchereVide.setVente(venteTmp);
				List<Enchere> listEnchere = new ArrayList<>();
				
				//on cherche les encheres
				for (Enchere enchereTmp:DAOFactory.getEncheresDAO().selectAll(enchereVide,"SELECTALLBYVENTE")) {
					
					listEnchere.add(enchereTmp);
					
				}
				
				venteTmp.setEncheres(listEnchere);
				
				venteListe.add(venteTmp);
				
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
		return venteListe;
	}

	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    for (int x = 1; x <= columns; x++) {
	        if (columnName.equals(rsmd.getColumnName(x))) {
	            return true;
	        }
	    }
	    return false;
	}

	@Override
	public boolean remove(Vente vente, String sql) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int result = 0;
		Connection conn = null;
		boolean retour = true;
		// Utilisateur user = new Utilisateur();
		try {
			// Récupérer une connexion
			conn = ConnectionProvider.getConnection();

			// Préparer la requête
			switch (sql) {
			
			case "DELETE" :
				stmt = conn.prepareStatement(CONSTANTE.get("DELETE"));
				stmt.setInt(1, vente.getNoVente());
				break;
			
			}
			// Executer la requête
			if (stmt != null) {
				result = stmt.executeUpdate();
			}

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			retour = false;
			e.printStackTrace();
		} finally {
			// Fermer la connexion
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return retour;
	}

}
