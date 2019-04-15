package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class ConnectionProvider {
	
	private static DataSource dataSource;
	
	static {
		try {
//System.out.println("init");
			Context context = new InitialContext();
//System.out.println("init2");
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/cxion_pool_db");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Impossible d'accéder à la base de données");
		}
		
	}
	
	public static Connection getConnection() throws SQLException
	{
		
		return dataSource.getConnection();
		
	}

}
