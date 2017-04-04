package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class ConnexionBDD {

	private static volatile ConnexionBDD instance;
	private Connection cnx; 
	
	private ConnexionBDD() {
		try {
			// Pour utiliser le confProperties
			//Properties p = new Properties();
			//p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("confBDD.properties"));
			
			
				
			// chargement du driver
//			Class.forName(p.getProperty("driver"));
//			cnx = DriverManager.getConnection(p.getProperty("url"),
					//p.getProperty("user"), p.getProperty("pwd"));
			Class.forName("com.mysql.jdbc.Driver");  
			cnx=DriverManager.getConnection("jdbc:mysql://localhost:8889/td2","root","root"); 			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	} 
	
	public static synchronized ConnexionBDD getInstance() {
		if(instance==null)
			instance = new ConnexionBDD();
		
		return instance;
	}

	public Connection getCnx() {
		return cnx;
	}

	public void closeCnx() throws SQLException{
		if(cnx!=null){
			cnx.close();
			instance=null;
		}
	}
}
