package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.LigneCommande;
import dao.JeuxDAO;

public class LigneCommandesDAO {
	public static int insert(LigneCommande lc) {
		int res = 0;
				
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "INSERT INTO LigneCommande(jeu_id, commande_id, quantity) VALUES(?,?,?,?)";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, lc.getCommande_id());
			ps.setInt(2, lc.getQte());
			ps.setInt(3, lc.getId());
			
			
			//Execution et traitement de la réponse
			res = ps.executeUpdate();
			
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public static int update(LigneCommande lc) {
		int res = 0;
		
		Connection cnx=null;
		
		try {
			// chargement du driver
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "UPDATE LigneCommande SET jeu_id=?, commande_id=?, quantity=? WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(2, lc.getCommande_id());
			ps.setInt(3, lc.getQte());
			ps.setInt(4, lc.getId());
			
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
			String sql = "DELETE FROM LigneCommande WHERE id=?";
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
	
	
	
	public static List<LigneCommande> findAll() {

		List<LigneCommande> lc = new ArrayList<LigneCommande>();
		Connection cnx=null;
		PreparedStatement ps = null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			
			//Requete
			String sql = "SELECT id, jeu_id, commande_id, quantity FROM LigneCommande ORDER BY id DESC";
			ps = cnx.prepareStatement(sql,
					ResultSet.TYPE_FORWARD_ONLY,
				    ResultSet.CONCUR_READ_ONLY,
				    ResultSet.CLOSE_CURSORS_AT_COMMIT
				   );

			ResultSet res = ps.executeQuery();

			while(res.next() == true){
				lc.add(new LigneCommande(
						res.getInt("id"),
						res.getInt("jeu_id"),
						res.getInt("commande_id"),
						res.getInt("quantity")));
			}
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("COUCOUCOUCOCUOCUCOUCOUCOCUOCUC"+e.getMessage());
				}
			}

			if (cnx != null) {
				try {
					cnx.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(" COUCOUCOUCOCUOCUCOUCOUCOCUOCUC " + e.getMessage());
				}
			}

		}

		return lc;
	}
	
	public static LigneCommande find(int id) {

		LigneCommande lc = null;
		
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

		
			//Requete
			String sql = "SELECT id,jeu_id, commande_id, quantity FROM LigneCommande WHERE id=?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			/*
			while(res.next()){
				lc = new LigneCommande(
						res.getInt("id"),
						//JeuxDAO.find(res.getInt("jeu_id")),
						res.getInt("commande_id"),
						res.getInt("quantity"));
				break;
			}
			*/
			
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lc;
	}
	
	public static List<LigneCommande> findAll(int start, int nbElts) {

		List<LigneCommande> lc = new ArrayList<LigneCommande>();
		
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
			// ou Class.forName(com.mysql.jdbc.Driver.class.getName());

		
			//Requete
			String sql = "SELECT id,jeu_id, commande_id, quantity FROM LigneCommande LIMIT ?,?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, nbElts);
			
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			/*
			while(res.next()){
				lc.add(new LigneCommande(
						res.getInt("id"),
						JeuxDAO.find(res.getInt("jeu_id")),
						res.getInt("commande_id"),
						res.getInt("quantity")));
			}
			*/
			res.close();
			ConnexionBDD.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lc;
	}
	
	public static int countLigneCommande(){
		
		int counter = 0;
		Connection cnx=null;
		try {
			cnx = ConnexionBDD.getInstance().getCnx();
		
			String sql = "SELECT COUNT(*) FROM LigneCommande";
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
