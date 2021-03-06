package beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;


@Entity
@Table(name="UTILISATEUR")
public class Utilisateur implements Serializable, Comparable<Utilisateur>{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userId")
	private Long id;
	@Column(name="nom")
	private String nom;
	@Column(name="prenom")
	private String prenom;
	@Column(name="mail")
	private String mail;
	@Column(name="login")
	private String login;
	@Column(name="password")
	private String password;
	@Column(name="adresse")
	private String adresse;
	@Column(name="cpostal")
	private String cpostal;
	@Column(name="ville")
	private String ville;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Commande> commandes = new ArrayList<Commande>();

	public Utilisateur() {

	}

	public Utilisateur(String nom, String prenom, String mail, String login, String password, String adresse,
			String cpostal, String ville) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.login = login;
		this.setPassword(password);
		this.adresse = adresse;
		this.cpostal = cpostal;
		this.ville = ville;
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}



	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}



	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}



	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}



	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}



	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		ConfigurablePasswordEncryptor encryptor = new ConfigurablePasswordEncryptor();
		encryptor.setAlgorithm("SHA-256");
		this.password = encryptor.encryptPassword(password);
	}



	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}



	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	/**
	 * @return the cpostal
	 */
	public String getCpostal() {
		return cpostal;
	}



	/**
	 * @param cpostal the cpostal to set
	 */
	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
	}



	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Utilisateur [nom=" + nom + ", prenom=" + prenom + "]";
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public int compareTo(Utilisateur o) {
		return this.nom.compareTo(o.nom);
	}

	/**
	 * @return the commandes
	 */
	public List<Commande> getCommandes() {
		return commandes;
	}

	/**
	 * @param commandes the commandes to set
	 */
	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}




}
