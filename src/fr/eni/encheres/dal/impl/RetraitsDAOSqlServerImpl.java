package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.InterfaceDAO;

public class RetraitsDAOSqlServerImpl implements InterfaceDAO<Retrait>{

	public static Map<String,String> CONSTANTE = new HashMap<>(); 

	static {	//initialisation des constantes

		CONSTANTE.put("SELECTBYID", "SELECT * FROM retraits where no_vente=?");
		CONSTANTE.put("SELECTALL", "SELECT * FROM retraits");
		CONSTANTE.put("INSERT", "INSERT INTO retraits values (?,?,?,?)");
		CONSTANTE.put("UPDATE", "UPDATE retraits SET rue=?, code_postal=?, ville=? WHERE no_vente=?");
		CONSTANTE.put("DELETE", "DELETE FROM retraits WHERE no_vente =(?)");

	}

	@Override
	public Retrait select(Retrait retrait, String requete) {
		Connection conn = null;
		//System.out.println("Select User");
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
			PreparedStatement stmt = null;
			ResultSet rs = null;

			if(requete.equals("SELECTBYID")) {
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTBYID"));					
				stmt.setInt(1, retrait.getVente().getNoVente());
			}

			rs = stmt.executeQuery();

			if(rs != null && rs.next()) {
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));						
			} else {
				retrait = null;
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
		return retrait;
	}

	@Override
	public List<Retrait> selectAll() {
		return null;
	}

	@Override
	public int insert(Retrait retrait) {
		Connection conn = null;
		int id = 0;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();

			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("INSERT"), PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, retrait.getVente().getNoVente());
			stmt.setString(2, retrait.getRue());			
			stmt.setString(3, retrait.getCodePostal());
			stmt.setString(4, retrait.getVille());			

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
	public boolean update(Retrait t) {
		return false;
	}

	@Override
	public boolean remove(int id) {
		return false;
	}

	@Override
	public List<Retrait> selectAll(Retrait t, String requete) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Retrait retrait, String sql) {
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
				stmt.setInt(1, retrait.getVente().getNoVente());
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
