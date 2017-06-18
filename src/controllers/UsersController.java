package controllers;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import beans.Utilisateur;
import dao.UtilisateursDao;


@Path("/users")
public class UsersController {
	
	private UtilisateursDao users;

	
	public UsersController(){
		users = new UtilisateursDao();
	}
	
	@GET
	@Produces("application/json")
	public List<Utilisateur> listUsers() {
		return users.list(); 

	}
	
	@GET
	@Path("/{idUser}")
	@Produces("application/json")
	public Response getuser(@PathParam("idUser") String id) { //@PathParam
		
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			users.get(Long.parseLong(id));
			
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("User not found for UID: " + id).build();
		}
		return Response.ok(users.get(Long.parseLong(id)), MediaType.APPLICATION_JSON).build();

	}
	
	@DELETE
	@Path("/{idUser}")
	@Produces("application/json")
	public Response deleteuser(@PathParam("idUser") String id) {
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			users.delete(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("User not found for UID: " + id).build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Produces("text/plain")
	public Response createuser(@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("mail") String mail,
			@FormParam("login") String login,
			@FormParam("password") String password,
			@FormParam("adresse") String adresse,
			@FormParam("cpostal") String cpostal,
			@FormParam("ville") String ville){
		
		if (getUserByLogin(login) != null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		// Echappement des caract�res HTML
		nom = StringUtils.replaceEach(nom, new String[]{"&", "<", ">", "\"", "'", "/"}, new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		prenom = StringUtils.replaceEach(prenom, new String[]{"&", "<", ">", "\"", "'", "/"}, new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		mail = StringUtils.replaceEach(mail, new String[]{"&", "<", ">", "\"", "'", "/"}, new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		login = StringUtils.replaceEach(login, new String[]{"&", "<", ">", "\"", "'", "/"},	new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		adresse = StringUtils.replaceEach(adresse, new String[]{"&", "<", ">", "\"", "'", "/"},	new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		cpostal = StringUtils.replaceEach(cpostal, new String[]{"&", "<", ">", "\"", "'", "/"},	new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		ville = StringUtils.replaceEach(ville, new String[]{"&", "<", ">", "\"", "'", "/"}, new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		
		Utilisateur newUser = new Utilisateur(nom, prenom, mail, login, password, adresse, cpostal, ville);
		users.add(newUser);
		
		return Response
				   .status(200)
				   .entity("createuser is called, nom : " + nom + ", prenom : " + prenom
					+ ", mail : " + mail + ", login : " + login + ", password : " + password + ", adresse : " + adresse
					+ ", code postal : " + cpostal + ", ville : " + ville).build();
		
	}
	
	@PUT
	@Path("/{idUser}")
	@Produces("text/plain")
	public Response updateUser(@PathParam("idUser") Long id,
			@QueryParam("nom") String nom,
			@QueryParam("prenom") String prenom,
			@QueryParam("mail") String mail,
			@QueryParam("login") String login,
			@QueryParam("password") String password,
			@QueryParam("adresse") String adresse,
			@QueryParam("cpostal") String cpostal,
			@QueryParam("ville") String ville){
		
		Utilisateur u;
		try {
			u = users.get(id);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("User not found for ID : "+ id).build();
		}
			
		u.setNom(nom);
		u.setPrenom(prenom);
		u.setMail(mail);
		u.setLogin(login);
		u.setPassword(password);
		u.setAdresse(adresse);
		u.setCpostal(cpostal);
		u.setVille(ville);
		users.update(u);
		
		return Response
				   .status(200)
				   .entity("UpdateUser is called, nom : " + nom + ", prenom : " + prenom
					+ ", mail : " + mail + ", login : " + login + ", password : " + password + ", adresse : " + adresse
					+ ", code postal : " + cpostal + ", ville : " + ville).build();
		
	}
	
	@POST
	@Path("/authenticate")
	@Produces("text/plain")
	public Response connect(@FormParam("login") String login, @FormParam("password") String password){
		
		login = StringUtils.replaceEach(login, new String[]{"&", "<", ">", "\"", "'", "/"},	new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"});
		
		try {
            if (checkPassword(login, password)){
            	System.out.println("PASS OK");
            // Create a token for the client
            String token = AuthenticationManager.createToken(login);

            // Return the token on the response
            return Response.ok(token).build();
            }
            else{
            	return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }		
	}

	private boolean checkPassword(String login, String password) {		
		ConfigurablePasswordEncryptor encryptor = new ConfigurablePasswordEncryptor();
		encryptor.setAlgorithm("SHA-256");
		
		Utilisateur u;
		try {
			u = getUserByLogin(login);
		} catch(RuntimeException e){
			return false;
		}
		
		if (encryptor.checkPassword(password, u.getPassword()))
			return true;
		else
			return false;
	}

	private Utilisateur getUserByLogin(String login) {
		List<Utilisateur> listUsers = users.list();
		for (int i=0 ; i<listUsers.size() ; i++){
			if (listUsers.get(i).getLogin().matches(login)){
				return listUsers.get(i);
			}
		}
		return null;
	}	
}
