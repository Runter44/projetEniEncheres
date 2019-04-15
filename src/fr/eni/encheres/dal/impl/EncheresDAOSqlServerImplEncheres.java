package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.encheres.bo.DateFormat;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;

public class EncheresDAOSqlServerImplEncheres implements InterfaceDAO<Enchere> {
	// public final String SELECT = "select * from encheres where date_enchere = ";
	// public final String SELECTALL = "select * from encheres";
	// public final String INSERT = "";

	public static Map<String, String> CONSTANTE = new HashMap<>();
	static {
		CONSTANTE.put("SELECT", "SELECT*FROM encheres WHERE no_utilisateur = (?)");
		CONSTANTE.put("SELECTBYID", "SELECT * FROM encheres where no_utilisateur=(?)");
		CONSTANTE.put("SELECTALL", "SELECT * FROM encheres");
		CONSTANTE.put("SELECTALLBYUTILISATEUR", "SELECT * FROM encheres where no_utilisateur =(?)");
		CONSTANTE.put("SELECTALLBYVENTE", "SELECT * FROM encheres where no_vente =(?)");
		CONSTANTE.put("INSERT", "INSERT INTO encheres (date_enchere,valeur,no_utilisateur,no_vente)values (?,?,?,?)");
		CONSTANTE.put("UPDATE","UPDATE encheres SET valeur=? WHERE no_utilisateur=? AND no_vente=?");
		CONSTANTE.put("DELETE", "DELETE FROM encheres WHERE no_utilisateur = (?) AND no_vente =(?)");
		CONSTANTE.put("DELETEBYVENTE", "DELETE FROM encheres WHERE no_vente =(?)");
		CONSTANTE.put("DELETEBYENCHEREUR", "DELETE FROM encheres WHERE no_utilisateur =(?)");
	
	
	}

	@Override
	public Enchere select(Enchere enchere, String sql) {
		ResultSet rs = null;
		Connection conn = null;
		// Utilisateur user = new Utilisateur();
		try {
			// Récupérer une connexion
			conn = ConnectionProvider.getConnection();

			// Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("SELECT"));
			stmt.setInt(1, enchere.getUser().getNoUtilisateur());

			// Executer la requête
			if (stmt != null) {
				rs = stmt.executeQuery();
			}

			if (rs != null && rs.next()) {

				enchere.setDateEnchere(DateFormat.TOFORMAT(rs.getString("date_enchere")));
				// recherche utilisateur
				Utilisateur v = new Utilisateur();
				v.setNoUtilisateur(rs.getInt("no_utilisateur"));

				enchere.setUser(DAOFactory.getUtilisateursDAO().select(v, "SELECT"));

				// recherche de la vente
				Vente ve = new Vente();
				ve.setNoVente(rs.getInt("no_vente"));

				enchere.setVente(DAOFactory.getVenteDAO().select(ve, "SELECT"));
			} else {
				enchere = null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

		return enchere;
	}

	@Override
	public List<Enchere> selectAll(Enchere enchere, String sql) {
		Connection conn = null;
		ArrayList<Enchere> enchereListe = new ArrayList<>();
		Enchere monEnchere = null;		
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionProvider.getConnection();

			// Par utilisateur
			if ("SELECTALLBYUTILISATEUR".equals(sql)) {
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTALLBYUTILISATEUR"));

				stmt.setInt(1, enchere.getUser().getNoUtilisateur());

			}
			// par vendeur
			else if ("SELECTALLBYVENTE".equals(sql)) {
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTALLBYVENTE"));
				
				stmt.setInt(1, enchere.getVente().getNoVente());
			}

			if (stmt != null) {
				rs = stmt.executeQuery();
			}

			while (rs!=null && rs.next()) {
				
				monEnchere = new Enchere();
				monEnchere.setDateEnchere(DateFormat.SqlDateTOLocalDateTime(rs.getDate("date_enchere")));
				
				monEnchere.setValeur(rs.getInt("valeur"));
				
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));				
				monEnchere.setUser(DAOFactory.getUtilisateursDAO().select(vendeur, "SELECTBYID"));
				
				Vente vente = new Vente();
				vente.setNoVente(rs.getInt("no_vente"));
				monEnchere.setVente(DAOFactory.getVenteDAO().select(vente, "SELECT"));
				/*
				System.out.println("date: " + rs.getDate("date_enchere") +
								   "\nvaleur: " + rs.getInt("valeur") +
								   "\nNo Utilisateur: " + rs.getInt("no_utilisateur") +
								   "\nNo Vente: " + rs.getInt("no_vente") + "\n");
				*/
				
				enchereListe.add(monEnchere);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

		return enchereListe;
	}

	@Override
	public List<Enchere> selectAll() {
		return null;
	}

	@Override
	public int insert(Enchere enchere) {
		Connection conn = null;
		int id = 0;

		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("INSERT"),
														   PreparedStatement.RETURN_GENERATED_KEYS);

			java.sql.Date sqlDate = java.sql.Date.valueOf(enchere.getDateEnchere().toLocalDate());
			stmt.setDate(1, sqlDate);
			stmt.setInt(2, enchere.getValeur());
			stmt.setInt(3, enchere.getUser().getNoUtilisateur());
			stmt.setInt(4, enchere.getVente().getNoVente());

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean update(Enchere enchere) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("UPDATE"));

			stmt.setInt(1, enchere.getValeur());
			stmt.setInt(2, enchere.getUser().getNoUtilisateur());
			stmt.setInt(3, enchere.getVente().getNoVente());

			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

		return true;
	}

	@Override
	public boolean remove(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Enchere enchere, String sql) {
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
				stmt.setInt(1, enchere.getUser().getNoUtilisateur());
				stmt.setInt(2, enchere.getVente().getNoVente());
				break;
			case "DELETEBYVENTE" :
				stmt = conn.prepareStatement(CONSTANTE.get("DELETEBYVENTE"));
				stmt.setInt(1, enchere.getVente().getNoVente());
				break;
			case "DELETEBYENCHEREUR" :
				stmt = conn.prepareStatement(CONSTANTE.get("DELETEBYENCHEREUR"));
				stmt.setInt(1, enchere.getUser().getNoUtilisateur());
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
