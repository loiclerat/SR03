package services;

import beans.Commande;

public class CustomResponse {
	private int status;
	private int id;
	private Commande commande;
	
	public CustomResponse(){
		
	}
	
	public int getStatus(){
		return status;
	}
	
	public int getId(){
		return id;
	}
	
	public void setStatus(int s){
		status = s;
	}
	
	public void setId(int i){
		id = i;
		if (i != -1)
			status = 200;
		else
			status = 404;
	}

	/**
	 * @return the commande
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * @param commande the commande to set
	 */
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
}
