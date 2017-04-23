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
	
	public TypeConsole(){}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{\"consoleId\":\""+TypeConsoleId+"\" , \"nomConsole\":\""+nomConsole+"\"}";
	}

	public TypeConsole(String nomConsole) {
		super();
		this.nomConsole = nomConsole;
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
