package fr.eni.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.InterfaceDAO;

public class UtilisateurDAOSqlServerImpl implements InterfaceDAO<Utilisateur>{
	

	
	public static Map<String,String> CONSTANTE = new HashMap<>(); 
	
	static {	//initialisation des constantes
		
		CONSTANTE.put("SELECT", "SELECT * FROM utilisateurs where pseudo=? AND mot_de_passe=?");
		CONSTANTE.put("SELECTBYID", "SELECT * FROM utilisateurs where no_utilisateur=?");
		CONSTANTE.put("SELECTALL", "SELECT * FROM utilisateurs");
		CONSTANTE.put("SELECTFORDOUBLON", "SELECT * FROM utilisateurs where pseudo=? OR telephone=? OR email=?");
		CONSTANTE.put("INSERT", "INSERT INTO utilisateurs values (?,?,?,?,?,?,?,?,?,?,?)");
		CONSTANTE.put("UPDATE", "UPDATE utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, "
							+ "	rue=?, code_postal=?, ville=?, mot_de_passe=?,"
							+ " credit=?, administrateur=? WHERE no_utilisateur=?");
		
	}
/*
	public static final String SELECT = "SELECT * FROM utilisateurs where pseudo=(?) AND mot_de_passe=(?)";
	public static final String SELECTBYID = "SELECT * FROM utilisateurs where no_utilisateur=?";
	public static final String SELECTALL = "SELECT * FROM utilisateurs";
	public static final String INSERT = "INSERT INTO utilisateurs values (?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE = "UPDATE utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, "
			+ "											  rue=?, code_postal=?, ville=?, mot_de_passe=?,"
			+ "      									  credit=?, administrateur=? WHERE no_utilisateur=?"; 
*/
	@Override
	public Utilisateur select(Utilisateur utilisateur, String sql) {
		Connection conn = null;
//System.out.println("Select User");
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			//Préparer la requête
			if ( "SELECT".equals(sql)) {
//System.out.println("Select by pseudo/mdp "+utilisateur.getPseudo()+" "+utilisateur.getMotDePasse() );
				stmt = conn.prepareStatement(CONSTANTE.get("SELECT"));
				stmt.setString(1, utilisateur.getPseudo());			
				stmt.setString(2, utilisateur.getMotDePasse());
			}
			else if ("SELECTBYID".equals(sql)) {
//System.out.println("Select by id");
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTBYID"));
				stmt.setInt(1, utilisateur.getNoUtilisateur());
			}
			
			
			//Executer la requête
			if (stmt != null) {
				rs = stmt.executeQuery();
			}
			
			if(rs != null && rs.next()) {
//System.out.println("Trouvé");
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
			} else {
				utilisateur = null;
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
		return utilisateur;
	}

	@Override
	public List<Utilisateur> selectAll() {
		Connection conn = null;
		List<Utilisateur> utilisateurListe = new ArrayList<>();
		Utilisateur utilisateur = new Utilisateur();
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			Statement stmt = conn.createStatement();
						
			//Executer la requête
			ResultSet rs = stmt.executeQuery(CONSTANTE.get("SELECTALL"));
			
			while(rs.next()) {
				
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				
				utilisateurListe.add(utilisateur);
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
		return utilisateurListe;
	}

	@Override
	public int insert(Utilisateur utilisateur) {
		Connection conn = null;
		int id = 0;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("INSERT"), PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, utilisateur.getPseudo());			
			stmt.setString(2, utilisateur.getNom());
			stmt.setString(3, utilisateur.getPrenom());			
			stmt.setString(4, utilisateur.getEmail());
			stmt.setString(5, utilisateur.getTelephone());
			stmt.setString(6, utilisateur.getRue());
			stmt.setString(7, utilisateur.getCodePostal());
			stmt.setString(8, utilisateur.getVille());
			stmt.setString(9, utilisateur.getMotDePasse());
			stmt.setInt(10, utilisateur.getCredit());
			stmt.setBoolean(11, utilisateur.isAdministrateur());
			
			
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
	public boolean update(Utilisateur utilisateur) {
		boolean retour = true;
		Connection conn = null;
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();			
			
			//Préparer la requête
			PreparedStatement stmt = conn.prepareStatement(CONSTANTE.get("UPDATE"));
			stmt.setString(1, utilisateur.getPseudo());			
			stmt.setString(2, utilisateur.getNom());
			stmt.setString(3, utilisateur.getPrenom());			
			stmt.setString(4, utilisateur.getEmail());
			stmt.setString(5, utilisateur.getTelephone());
			stmt.setString(6, utilisateur.getRue());
			stmt.setString(7, utilisateur.getCodePostal());
			stmt.setString(8, utilisateur.getVille());
			stmt.setString(9, utilisateur.getMotDePasse());
			stmt.setInt(10, utilisateur.getCredit());
			stmt.setBoolean(11, utilisateur.isAdministrateur());
			
			stmt.setInt(12, utilisateur.getNoUtilisateur());
			
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
		return false;
	}

	@Override
	public List<Utilisateur> selectAll(Utilisateur user, String sql) {
		
		Connection conn = null;
		List<Utilisateur> utilisateurListe = new ArrayList<>();
		Utilisateur utilisateur = new Utilisateur();
		PreparedStatement stmt = null;
		
		try {
			//Récupérer une connexion
			conn = ConnectionProvider.getConnection();
	
			//Préparer la requête
			
			if ("SELECTFORDOUBLON".equals(sql)){
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTFORDOUBLON"));
				
				stmt.setString(1, user.getPseudo());	
				stmt.setString(2, user.getTelephone());	
				stmt.setString(3, user.getEmail());	
				
			}
			else  {
				stmt = conn.prepareStatement(CONSTANTE.get("SELECTALL"));
			}
			//Executer la requête
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				
				utilisateurListe.add(utilisateur);
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
		return utilisateurListe;
		
	}

	@Override
	public boolean remove(Utilisateur t, String requete) {
		// TODO Auto-generated method stub
		return false;
	}	

}
