package beans;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import dao.JeuxDAO;

@Entity
@Table(name="LigneCommande")
public class LigneCommande implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COMMANDE_ID")
	private Commande commande_id;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="JEU_ID")
	private Jeu jeu_id;
	
	@Column(name="QUANTITY")
	private Integer quantity;
	
	@Column(name="CONSOLE_ID")
	private Long console_id;

		
	public LigneCommande(Commande commande_id, Jeu jeu_id, Integer quantity, Long console_id) {
		super();
		this.commande_id = commande_id;
		this.jeu_id = jeu_id;
		this.quantity = quantity;
		this.console_id = console_id;
	}

	/**
	 * @return the commande_id
	 */
	/*
	public Commande getCommande_id() {
		return commande_id;
	}
	*/

	/**
	 * @param commande_id the commande_id to set
	 */
	public void setCommande_id(Commande commande_id) {
		this.commande_id = commande_id;
	}

	public LigneCommande() {
		super();
	}

	/**
	 * @return the jeu_id
	 */
	public Jeu getJeu_id() {
		return jeu_id;
	}
	

	/**
	 * @param jeu_id the jeu_id to set
	 */
	public void setJeu_id(Jeu jeu_id) {
		this.jeu_id = jeu_id;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Float getLinePrice(){
		return (float)(quantity * jeu_id.getPrix());
	}

	/**
	 * @return the console_id
	 */
	public Long getConsole_id() {
		return console_id;
	}

	/**
	 * @param console_id the console_id to set
	 */
	public void setConsole_id(Long console_id) {
		this.console_id = console_id;
	}
	

}
