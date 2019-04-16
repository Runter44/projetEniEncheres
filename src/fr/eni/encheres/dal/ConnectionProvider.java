package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionProvider {
	
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver introuvable");
		}
	}
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection("jdbc:sqlserver://10.27.137.18:1433;databasename=PROJET_ENCHERE", "sa", "Pa$$w0rd");
	}

}
