package beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="JEU")
public class Jeu implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="jeuId")
	private Long idJeu;
	
	@Column(name="titre")
	private String titre;
	
	@Column(name="url_image")
	private String url_image;
	
	@Column(name="prix")
	private Double prix;
	
	@ManyToMany
	@JoinTable(name = "Join_Jeu_TypeConsole", 
		joinColumns = {@JoinColumn(name = "jeuId")}, 
		inverseJoinColumns = {@JoinColumn(name = "consoleId")})
    private List<TypeConsole> consoles ;	
	
	
	public Jeu() {
		super();
		//tConsole = null;
	}
	
	public Jeu(String titre, String url_image, Double prix) {
		super();
		this.titre = titre;
		this.url_image = url_image;
		this.prix = prix;
	}
	
	public Jeu(String titre, String url_image, Double prix, List<TypeConsole> consoles) {
		super();
		this.titre = titre;
		this.url_image = url_image;
		this.prix = prix;
		this.consoles = consoles;
	}

	/**
	 * @return the consoles
	 */
	public List<TypeConsole> getConsoles() {
		return consoles;
	}

	/**
	 * @param consoles the consoles to set
	 */
	public void setConsoles(List<TypeConsole> consoles) {
		this.consoles = consoles;
	}

	/*
	public Jeu(Long idJeu, String titre, String url_image, Double prix, Collection<TypeConsole> tConsole) {
		super();
		this.idJeu = idJeu;
		this.titre = titre;
		this.url_image = url_image;
		this.prix = prix;
		this.tConsole = tConsole;
	}
	
	/**
	 * @return the tConsole
	 */
	/*
	public Collection<TypeConsole> gettConsole() {
		return tConsole;
	}

	/**
	 * @param tConsole the tConsole to set
	 
	public void settConsole(Collection<TypeConsole> tConsole) {
		this.tConsole = tConsole;
	}
	*/
	/**
	 * @return the id
	 */
	public Long getId() {
		return idJeu;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.idJeu = id;
	}
	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	/**
	 * @return the url_image
	 */
	public String getUrl_image() {
		return url_image;
	}
	/**
	 * @param url_image the url_image to set
	 */
	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}
	/**
	 * @return the prix
	 */
	public Double getPrix() {
		return prix;
	}
	/**
	 * @param prix the prix to set
	 */
	public void setPrix(Double prix) {
		this.prix = prix;
	}
}
