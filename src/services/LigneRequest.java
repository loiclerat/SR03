package services;

public class LigneRequest {
	private String idJeu;
	private String idConsole;
	private int qty;
	
	
	public LigneRequest() {
		super();
	}
	
	/**
	 * @return the idJeu
	 */
	public String getIdJeu() {
		return idJeu;
	}
	/**
	 * @param idJeu the idJeu to set
	 */
	public void setIdJeu(String idJeu) {
		this.idJeu = idJeu;
	}
	/**
	 * @return the idConsole
	 */
	public String getIdConsole() {
		return idConsole;
	}
	/**
	 * @param idConsole the idConsole to set
	 */
	public void setIdConsole(String idConsole) {
		this.idConsole = idConsole;
	}
	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
