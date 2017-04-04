package beans;

import java.io.Serializable;
import beans.Jeu;
import dao.JeuxDAO;

@SuppressWarnings("serial")
public class LigneCommande implements Serializable{


	
	private int id;
	private Jeu jeu;
	private int commande_id;
	private int qte;
	
	
	public LigneCommande(int id, Jeu jeu, int commande_id, int qte) {
		super();
		this.jeu = jeu;
		this.id = id;
		this.commande_id = commande_id;
		this.qte = qte;
	}
	
	public LigneCommande(int id, int jeu_id, int commande_id, int qte) {
		super();
		this.jeu = JeuxDAO.find(jeu_id);
		this.id = id;
		this.commande_id = commande_id;
		this.qte = qte;
	}
	
	/**
	 * @param jeu
	 * @param qte
	 */
	
	public LigneCommande(Jeu jeu, int qte) {
		super();
		this.jeu = jeu;
		this.qte = qte;
	}
	/**
	 * 
	 */
	public LigneCommande() {
		super();
	}
	public Jeu getJeu() {
		return jeu;
	}
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public double getTotal(){
		//bisogna recuperare il costo di un singolo jeuotto
		double prix = jeu.getPrix();
		return (prix*this.qte);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jeu == null) ? 0 : jeu.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigneCommande other = (LigneCommande) obj;
		if (jeu == null) {
			if (other.jeu != null)
				return false;
		} else if (!jeu.equals(other.jeu))
			//va a controllare se i due jeuotti sono uguali utilizzando
			//l'equals di Jeu che abbiamo ridefinito in precedenza
			return false;
		return true;
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
	 * @return the commande_id
	 */
	public int getCommande_id() {
		return commande_id;
	}
	/**
	 * @param commande_id the commande_id to set
	 */
	public void setCommande_id(int commande_id) {
		this.commande_id = commande_id;
	}
	
	
}
