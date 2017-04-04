package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Utilisateur;

public class UtilisateursDao {

	public static int insert(Utilisateur u) {
		int res = 0;
				
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "INSERT INTO utilisateur(nom,prenom,mail,login, password, adresse, cpostal, ville) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, u.getNom());
			ps.setString(2, u.getPrenom());
			ps.setString(3, u.getMail());
			ps.setString(4, u.getLogin());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getAdresse());
			ps.setString(7, u.getCpostal());
			ps.setString(8, u.getVille());
			
			
			//Execution et traitement de la réponse
			res = ps.executeUpdate();
			
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public static int update(Utilisateur u) {
		int res = 0;
		
		Connection cnx=null;
		
		try {
			// chargement du driver
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "UPDATE utilisateur SET nom=?,prenom=?,mail=?,login=?, password=?, adresse=?, cpostal=?, ville=? WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, u.getNom());
			ps.setString(2, u.getPrenom());
			ps.setString(3, u.getMail());
			ps.setString(4, u.getLogin());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getAdresse());
			ps.setString(7, u.getCpostal());
			ps.setString(8, u.getVille());
			ps.setInt(9, u.getId());
			
			//Execution et traitement de la réponse
			res = ps.executeUpdate();
			
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public static int delete(int id) {
		int res = 0;
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

				
			//Requete
			String sql = "DELETE FROM utilisateur WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1,id);
			
			//Execution et traitement de la réponse
			res = ps.executeUpdate();
			
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	
	public static List<Utilisateur> findAll() {
		/*
		 * List<beans.Utilisateur> lu = new ArrayList<Utilisateur>(); lu.add(new
		 * Utilisateur(1,"nom1","tel1","username1","pwd1")); lu.add(new
		 * Utilisateur(2,"nom2","tel2","username2","pwd2")); lu.add(new
		 * Utilisateur(3,"nom3","tel3","username3","pwd3"));
		 */

		List<Utilisateur> lu = new ArrayList<Utilisateur>();
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

			
			//Requete
			String sql = "SELECT id,nom,prenom,mail,login, password, adresse, cpostal, ville FROM utilisateur";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				lu.add(new Utilisateur(
						res.getInt("id"),
						res.getString("nom"),
						res.getString("prenom"),
						res.getString("mail"),
						res.getString("login"),
						res.getString("password"),
						res.getString("adresse"),
						res.getString("cpostal"),
						res.getString("ville")));
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//

		return lu;
	}
	
	public static Utilisateur find(int id) {
		/*
		 * List<beans.Utilisateur> lu = new ArrayList<Utilisateur>(); lu.add(new
		 * Utilisateur(1,"nom1","tel1","username1","pwd1")); lu.add(new
		 * Utilisateur(2,"nom2","tel2","username2","pwd2")); lu.add(new
		 * Utilisateur(3,"nom3","tel3","username3","pwd3"));
		 */

		Utilisateur u = null;
		
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

		
			//Requete
			String sql = "SELECT id,nom,prenom,mail,login, password, adresse, cpostal, ville FROM utilisateur WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				u = new Utilisateur(
						res.getInt("id"),
						res.getString("nom"),
						res.getString("prenom"),
						res.getString("mail"),
						res.getString("login"),
						res.getString("password"),
						res.getString("adresse"),
						res.getString("cpostal"),
						res.getString("ville"));
				break;
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//

		return u;
	}
	public static List<Utilisateur> findAll(int start, int nbElts) {
		/*
		 * List<beans.Utilisateur> lu = new ArrayList<Utilisateur>(); lu.add(new
		 * Utilisateur(1,"nom1","tel1","username1","pwd1")); lu.add(new
		 * Utilisateur(2,"nom2","tel2","username2","pwd2")); lu.add(new
		 * Utilisateur(3,"nom3","tel3","username3","pwd3"));
		 */

		List<Utilisateur> lu = new ArrayList<Utilisateur>();
		
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

		
			//Requete
			String sql = "SELECT id,nom,prenom,mail,login, password, adresse, cpostal, ville FROM utilisateur LIMIT ?,?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, nbElts);
			
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				lu.add(new Utilisateur(
						res.getInt("id"),
						res.getString("nom"),
						res.getString("prenom"),
						res.getString("mail"),
						res.getString("login"),
						res.getString("password"),
						res.getString("adresse"),
						res.getString("cpostal"),
						res.getString("ville")));
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//

		return lu;
	}
	
	public static int countUsers(){
		
		int counter = 0;
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
		
			String sql = "SELECT COUNT(*) FROM utilisateur";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
			 counter = res.getInt("COUNT(*)");
			 break;
				
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return counter;
	}
	
}
