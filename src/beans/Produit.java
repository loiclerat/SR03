package beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Produit implements Serializable, Comparable<Produit>{

	private int id;
	private String description;
	private double prix;
	/**
	 * @param id
	 * @param description
	 * @param prix
	 */
	public Produit(int id, String description, double prix) {
		super();
		this.id = id;
		this.description = description;
		this.prix = prix;
	}
	/**
	 * 
	 */
	public Produit() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		return result;
	}
	/*
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	*/
	@Override
	public boolean equals(Object obj) {
		if(obj==null||(!(obj instanceof Produit)))
			return false;
		Produit tmp = (Produit)obj;
		return((this.id==tmp.id) && this.description.equals(tmp.getDescription()));
	}
	@Override
	public int compareTo(Produit o) {
		if(this.prix==o.prix)
			return 0;
		else if(this.prix>o.prix)
			return 1;
		else 
			return -1;
	}
	
	
	
	
}
