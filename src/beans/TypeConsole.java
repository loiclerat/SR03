package beans;

import java.io.Serializable;

public class TypeConsole implements Serializable, Comparable<TypeConsole>{
	
	private int id;
	private String nomConsole;
	
	public TypeConsole(){}
	
	public TypeConsole(int id, String nomConsole) {
		super();
		this.id = id;
		this.nomConsole = nomConsole;
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
	 * @return the nomConsole
	 */
	public String getNomConsole() {
		return nomConsole;
	}
	/**
	 * @param nomConsole the nomConsole to set
	 */
	public void setNomConsole(String nomConsole) {
		this.nomConsole = nomConsole;
	}
	@Override
	public int compareTo(TypeConsole o) {
		return this.nomConsole.compareTo(o.nomConsole);
	}

}
