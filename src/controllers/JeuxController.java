package controllers;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Jeu;
import beans.TypeConsole;
import dao.JeuxDAO;
import dao.TypeConsoleDAO;


@Path("/games")
public class JeuxController {
	
	private JeuxDAO jeux;
	
	public JeuxController(){
		jeux = new JeuxDAO();
	}
	
	@GET
	@Produces("application/json")
	public List<Jeu> listGames() {
		return jeux.list(); 

	}
	
	@GET
	@Path("/{idGame}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGame(@PathParam("idGame") String id) { //@PathParam
		
		if(id == null) return Response.serverError().entity(id).build();
		
		try {
			jeux.get(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Game not found for UID: " + id).build();
		}
		return Response.ok(jeux.get(Long.parseLong(id)), MediaType.APPLICATION_JSON).build();

	}
	
	@DELETE
	@Path("/{idGame}")
	@Produces("application/json")
	public Response deleteGame(@PathParam("idGame") String id) {
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			jeux.delete(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("User not found for UID: " + id).build();
		}
		return Response.ok().build();
	}
	
}
