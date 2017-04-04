package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.UtilisateursDao;

public class Commande implements Serializable{
	
	private int id;
	private List<LigneCommande> lignes;
	private Utilisateur user;
	private Date date_commande;


	public Commande(int id, int userID, Date date){
		lignes = new ArrayList<LigneCommande>();
		this.id = id;
		this.user = UtilisateursDao.find(userID);
		this.date_commande = date;
	}

	public Commande(Utilisateur u) {
		lignes = new ArrayList<LigneCommande>();
		user = u;
		date_commande = new Date();
	}
	
	public Commande() {
		lignes = new ArrayList<LigneCommande>();
		user = new Utilisateur();
		date_commande = new Date();
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 */
	
	/**
	 * @return the date_commande
	 */
	public Date getDate_commande() {
		return date_commande;
	}

	/**
	 * @param date_commande the date_commande to set
	 */
	public void setDate_commande(Date date_commande) {
		this.date_commande = date_commande;
	}

	/**
	 * @return the user
	 */
	public Utilisateur getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public List<LigneCommande> getLignes() {
		return lignes;
	}

	public void setLignes(List<LigneCommande> lignes) {
		this.lignes = lignes;
	}
	
	public double getTotal(){
		double total = 0;
	
		for (LigneCommande lc : lignes){
			total+=lc.getTotal();
		}
		//for(int i=0;i<lignes.size();i++){
		//	total = total + lignes.get(i).getTotal();
		//}
		return total;
	}
	
}
