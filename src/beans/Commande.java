package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import dao.JeuxDAO;
import dao.LigneCommandesDAO;

@Entity
@Table(name="COMMANDE")
public class Commande implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COMMANDE_ID")
	public Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private Utilisateur user;
	
	@Column(name="COMMANDE_PRICE")
	private Float price;
	
	@Column(name="COMMANDE_DATE")
	private Date dateCommande;
	
	@Column(name="COMMANDE_STATUS")
	private String statusCommande;
	
	@OneToMany(mappedBy = "commande_id", cascade = CascadeType.ALL)
	private List<LigneCommande> ligneCommandes = new ArrayList<LigneCommande>();

	public Commande(){
		super();
		statusCommande = "En cours";
	}
	
	public Commande(Utilisateur user, Float price, Date dateCommande) {
		super();
		this.user = user;
		this.price = price;
		this.dateCommande = dateCommande;
		statusCommande = "En cours";
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	// Comment� pour �viter les boucles infinies
	/*public Utilisateur getUser() {
		return user;
	}*/

	/**
	 * @param user the user to set
	 */
	public void setUser(Utilisateur user) {
		this.user = user;
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/**
	 * @return the dateCommande
	 */
	public String getDateCommande() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		return formatter.format(dateCommande);
	}

	/**
	 * @param date the dateCommande to set
	 */
	public void setDateCommande(Date date) {
		this.dateCommande = date;
	}

	/**
	 * @return the ligneCommandes
	 */
	public List<LigneCommande> getLigneCommandes() {
		return ligneCommandes;
	}

	/**
	 * @param ligneCommandes the ligneCommandes to set
	 */
	public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
		this.ligneCommandes = ligneCommandes;		
	}
	
	public void removeLigne(Long idJeu, Long idConsole) {
		int index = 0;
		LigneCommande item = null;
		for (Iterator<LigneCommande> i = this.ligneCommandes.iterator(); i.hasNext();) {
			item = i.next();
			if(item.getJeu_id().getId().equals(idJeu)) 
				if(item.getConsole_id().equals(idConsole))
					break;
		    index++;
		}
		
		ligneCommandes.remove(index);

		updatePrice();
	}
	
	 public void updatePrice(){
		 float prix = (float)0;

		 for(LigneCommande l : ligneCommandes){
			 prix = prix + l.getLinePrice();
		 }
		 this.price = prix;
	 }

	/**
	 * @return the statusCommande
	 */
	public String getStatusCommande() {
		return statusCommande;
	}

	/**
	 * @param statusCommande the statusCommande to set
	 */
	public void setStatusCommande(String statusCommande) {
		this.statusCommande = statusCommande;
	}
	
}
