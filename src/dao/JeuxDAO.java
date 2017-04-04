package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Jeu;
import beans.TypeConsole;

public class JeuxDAO {
	
	public static int insert(Jeu j) {
		int res = 0;
				
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "INSERT INTO Jeu(titre, url_image, prix, typeConsole_id) VALUES(?,?,?,?)";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, j.getTitre());
			ps.setString(2, j.getUrl_image());
			ps.setFloat(3, j.getPrix());
			ps.setInt(4, j.gettConsole().getId());
			
			
			//Execution et traitement de la réponse
			res = ps.executeUpdate();
			
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public static int update(Jeu j) {
		int res = 0;
		
		Connection cnx=null;
		
		try {
			// chargement du driver
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "UPDATE Jeu SET titre=?, url_image=?, prix=?,typeConsole_id=?  WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, j.getTitre());
			ps.setString(2, j.getUrl_image());
			ps.setFloat(3, j.getPrix());
			ps.setInt(4, j.gettConsole().getId());
			ps.setInt(5, j.getId());
			
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
			String sql = "DELETE FROM Jeu WHERE id=?";
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
	
	
	
	public static List<Jeu> findAll() {

		List<Jeu> lj = new ArrayList<Jeu>();
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

			
			//Requete
			String sql = "SELECT id, titre, url_image, prix, typeConsole_id FROM Jeu";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			int id, typeConsole_id;
			String titre, url_image;
			Float prix;
			while(res.next()){
				id = res.getInt("id");
				titre = res.getString("titre");
				url_image = res.getString("url_image");
				prix = res.getFloat("prix");
				typeConsole_id = res.getInt("typeConsole_id");
				lj.add(new Jeu(id, titre, url_image, prix, typeConsole_id));
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//

		return lj;
	}
	
	public static Jeu find(int id) {

		Jeu j = null;
		
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

		
			//Requete
			String sql = "SELECT id,titre, url_image, prix,typeConsole_id FROM Jeu WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				j = new Jeu(
						res.getInt("id"),
						res.getString("titre"),
						res.getString("url_image"),
						res.getFloat("prix"),
						res.getInt("typeConsole_id"));
				break;
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return j;
	}
	public static List<Jeu> findAll(int start, int nbElts) {

		List<Jeu> lj = new ArrayList<Jeu>();
		
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

		
			//Requete
			String sql = "SELECT id,titre, url_image, prix,typeConsole_id FROM Jeu LIMIT ?,?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, nbElts);
			
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				lj.add(new Jeu(
						res.getInt("id"),
						res.getString("titre"),
						res.getString("url_image"),
						res.getFloat("prix"),
						res.getInt("typeConsole_id")));
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lj;
	}
	
	public static int countTypeConsole(){
		
		int counter = 0;
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
		
			String sql = "SELECT COUNT(*) FROM Jeu";
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
