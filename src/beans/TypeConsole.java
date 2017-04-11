package beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="TypeConsole")
public class TypeConsole implements Serializable, Comparable<TypeConsole>{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="consoleId")
	private Long TypeConsoleId;
	@Column(name="nomConsole")
	private String nomConsole;
	
	/*@JoinTable(name = "Join_Jeu_TypeConsole", 
		joinColumns = {@JoinColumn(name = "consoleId")}, 
		inverseJoinColumns = {@JoinColumn(name = "jeuId")})
		*/
	@ManyToMany(mappedBy="consoles")
	private List<Jeu> jeux;
	
	public TypeConsole(){}
	
	
	public TypeConsole(String nomConsole) {
		super();
		this.nomConsole = nomConsole;
	}


	public TypeConsole(String nomConsole, List<Jeu> jeux) {
		super();
		this.nomConsole = nomConsole;
		this.jeux = jeux;
	}


	/**
	 * @return the jeux
	 */
	public List<Jeu> getJeux() {
		return jeux;
	}


	/**
	 * @param jeux the jeux to set
	 */
	public void setJeux(List<Jeu> jeux) {
		this.jeux = jeux;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return TypeConsoleId;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.TypeConsoleId = id;
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
