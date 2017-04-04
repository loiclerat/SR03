package beans;

import java.io.Serializable;
import beans.TypeConsole;
import dao.TypeConsoleDAO;

public class Jeu implements Serializable, Comparable<Jeu>{
	
	private int id;
	private String titre;
	private String url_image;
	private float prix;
	private TypeConsole tConsole;
	
	public Jeu(){}
	
	public Jeu(int id, String titre, String url_image, float prix, TypeConsole tConsole) {
		super();
		this.id = id;
		this.titre = titre;
		this.url_image = url_image;
		this.prix = prix;
		this.tConsole = tConsole;
	}
	
	public Jeu(int id, String titre, String url_image, float prix, int tConsole) {
		super();
		this.id = id;
		this.titre = titre;
		this.url_image = url_image;
		this.prix = prix;
		this.tConsole = TypeConsoleDAO.find(tConsole);
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
	public float getPrix() {
		return prix;
	}



	/**
	 * @param prix the prix to set
	 */
	public void setPrix(float prix) {
		this.prix = prix;
	}



	/**
	 * @return the tConsole
	 */
	public TypeConsole gettConsole() {
		return tConsole;
	}



	/**
	 * @param tConsole the tConsole to set
	 */
	public void settConsole(TypeConsole tConsole) {
		this.tConsole = new TypeConsole(tConsole.getId(), tConsole.getNomConsole());
	}
	
	public void settConsole(int TypeConsoleID) {
		TypeConsole tmp = TypeConsoleDAO.find(TypeConsoleID);
		this.tConsole = new TypeConsole(tmp.getId(), tmp.getNomConsole());
	}



	@Override
	public int compareTo(Jeu j) {
		// TODO Auto-generated method stub
		return this.titre.compareTo(j.titre);
	}

}
