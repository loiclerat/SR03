package controllers;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	@Path("/create")
	@Produces("text/plain")
	public Response createuser(@QueryParam("nom") String nom,
			@QueryParam("prenom") String prenom,
			@QueryParam("mail") String mail,
			@QueryParam("login") String login,
			@QueryParam("password") String password,
			@QueryParam("adresse") String adresse,
			@QueryParam("cpostal") String cpostal,
			@QueryParam("ville") String ville){
		
		users.add(nom, prenom, mail, login, password, adresse, cpostal, ville);
		
		return Response
				   .status(200)
				   .entity("createuser is called, nom : " + nom + ", prenom : " + prenom
					+ ", mail" + mail + ", login" + login + ", password" + password + ", adresse" + adresse
					+ ", code postal" + cpostal + ", ville" + ville).build();
	}
	
	
	
	
	
	
}
