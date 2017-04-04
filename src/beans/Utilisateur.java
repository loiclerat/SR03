package beans;
import java.io.Serializable;


public class Utilisateur implements Serializable, Comparable<Utilisateur>{

		private int id;
		private String nom;
		private String prenom;
		private String mail;
		private String login;
		private String password;
		private String adresse;
		private String cpostal;
		private String ville;		
		
		
		public Utilisateur() {
	
		}
		
		public Utilisateur(int id, String nom, String prenom, String mail, String login, String password, String adresse, String cpostal, String ville) {
			super();
			this.nom = nom;
			this.prenom = prenom;
			this.mail = mail;
			this.login = login;
			this.password = password;
			this.adresse = adresse;
			this.cpostal = cpostal;
			this.ville = ville;
			this.id = id;
		}


		public String getNom() {
			return nom;
		}
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
			this.password = password;
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

		/**
		 * @param ville the ville to set
		 */
		public void setVille(String ville) {
			this.ville = ville;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

		@Override
		public int compareTo(Utilisateur o) {
			return this.nom.compareTo(o.nom);
		}
		
		
		
		
}
