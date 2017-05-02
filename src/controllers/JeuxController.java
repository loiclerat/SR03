package controllers;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Jeu;
import beans.TypeConsole;
import beans.Utilisateur;
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
	
	@POST
	@Produces("text/plain")
	public Response createGame(@QueryParam("titre") String titre,
			@QueryParam("url_image") String url_image,
			@QueryParam("prix") String prix){
		
		
		Jeu newJeu = new Jeu(titre, url_image, Double.parseDouble(prix));
		jeux.add(newJeu);
		
		return Response
				   .status(200)
				   .entity("AddGame is called, titre : " + titre + ", url : " + url_image
					+ ", prix : " + prix).build();
		
	}
	
	@PUT
	@Path("/{idGame}")
	@Produces("text/plain")
	public Response updateGame(@PathParam("idGame") Long id,
			@QueryParam("titre") String titre,
			@QueryParam("url_image") String url_image,
			@QueryParam("prix") Double prix){
		
		Jeu j;
		
		try {
			j = jeux.get(id);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Game not found for ID : "+ id).build();
		}
		
		j.setPrix(prix);
		j.setTitre(titre);
		j.setUrl_image(url_image);
		
		
		jeux.update(j);
		
		return Response
				   .status(200)
				   .entity("UpdateGame is called, titre : " + titre + ", url : " + url_image
					+ ", prix : " + prix).build();
		
	}
	
	@PUT
	@Path("/{idGame}/console/{idConsole}")
	@Produces("text/plain")
	public Response addConsole(@PathParam("idGame") Long id,
			@PathParam("idConsole") Long idConsole){
		
		Jeu j;
		
		try {
			j = jeux.get(id);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Game not found for ID : "+ id).build();
		}
		
		TypeConsole tc;
		TypeConsoleDAO consoles = new TypeConsoleDAO();
		try {
			tc = consoles.get(idConsole);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Console not found for ID : "+ idConsole).build();
		}
		j.addConsole(tc);
		jeux.update(j);
		
		return Response
				   .status(200)
				   .entity("Console "+ tc.getNomConsole() +" ajouté au jeu " + j.getTitre()).build();
		
	}
	
	@DELETE
	@Path("/{idGame}/console/{idConsole}")
	@Produces("text/plain")
	public Response removeConsole(@PathParam("idGame") Long id,
			@PathParam("idConsole") Long idConsole){
		
		Jeu j;
		
		try {
			j = jeux.get(id);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Game not found for ID : "+ id).build();
		}
		
		TypeConsole tc;
		TypeConsoleDAO consoles = new TypeConsoleDAO();
		try {
			tc = consoles.get(idConsole);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Console not found for ID : "+ idConsole).build();
		}
		j.removeConsole(idConsole);
		jeux.update(j);
		
		return Response
				   .status(200)
				   .entity("Console "+ tc.getNomConsole() +" retiré au jeu " + j.getTitre()).build();
		
	}
	
}
