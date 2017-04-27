package controllers;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Commande;
import beans.Jeu;
import dao.CommandesDAO;
import dao.JeuxDAO;

@Path("/commandes")
public class CommandesController {
	
	private CommandesDAO commandes;
	
	public CommandesController(){
		commandes = new CommandesDAO();
	}
	
	@GET
	@Produces("application/json")
	public List<Commande> listCommandes() {
		return commandes.list(); 

	}
	
	@GET
	@Path("/{idCommande}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommande(@PathParam("idCommande") String id) { //@PathParam
		
		if(id == null) return Response.serverError().entity(id).build();
		
		try {
			commandes.get(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Commande not found for UID: " + id).build();
		}
		return Response.ok(commandes.get(Long.parseLong(id)), MediaType.APPLICATION_JSON).build();

	}
	
	@DELETE
	@Path("/{idCommande}")
	@Produces("application/json")
	public Response deleteCommande(@PathParam("idCommande") String id) {
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			commandes.delete(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Commande not found for UID: " + id).build();
		}
		return Response.ok().build();
	}
}
