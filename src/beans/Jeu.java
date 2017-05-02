package beans;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="JEU")
public class Jeu implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
	
	@OneToMany
	@JoinTable(name = "Jeu_TypeConsole",
    	joinColumns = {@JoinColumn(name = "jeuId")},
    	inverseJoinColumns = {@JoinColumn(name = "TypeConsoleId")})
    private List<TypeConsole> consoles ;
	
	
	
	public Jeu() {
		super();
		consoles = new ArrayList<TypeConsole>();
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
	
	public void addConsole(TypeConsole c) {
		this.consoles.add(c);
	}
	
	public void removeConsole(Long idConsole) {
		int index = 0;

		for (Iterator<TypeConsole> i = this.consoles.iterator(); i.hasNext();) {
			TypeConsole item = i.next();

			if(item.getId().equals(idConsole))	break;
		    index++;
		}
		consoles.remove(index);
	}

	
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String init = "{\"jeuId\":\""+idJeu+"\", \"titre\":\""+titre+"\", \"url_image\":\""+url_image+"\", \"prix\":\""+prix+"\", \"consoles\":[";
		for(Iterator<TypeConsole> i = consoles.iterator(); i.hasNext(); ) {
			TypeConsole item = i.next();
		    init = init + item.toString();
		    if(i.hasNext()) init = init + ",";
		}
		init = init + "]}";
		return init;
	}
	
	
}
