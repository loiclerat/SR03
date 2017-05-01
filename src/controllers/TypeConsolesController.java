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
import dao.TypeConsoleDAO;

@Path("/consoles")
public class TypeConsolesController {
	
	private TypeConsoleDAO consoles;
	
	public TypeConsolesController(){
		consoles = new TypeConsoleDAO();
	}
	
	@GET
	@Produces("application/json")
	public List<TypeConsole> listConsoles() {
		return consoles.list(); 

	}
	
	@GET
	@Path("/{idConsole}")
	@Produces("application/json")
	public Response getuser(@PathParam("idConsole") String id) { //@PathParam
		
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			consoles.get(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Console not found for ID: " + id).build();
		}
		return Response.ok(consoles.get(Long.parseLong(id)), MediaType.APPLICATION_JSON).build();

	}
	
	@DELETE
	@Path("/{idConsole}")
	@Produces("application/json")
	public Response deleteuser(@PathParam("idConsole") String id) {
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			consoles.delete(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Console not found for ID: " + id).build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Produces("text/plain")
	public Response createConsole(@QueryParam("nom") String nomConsole){
		
		
		TypeConsole newConsole = new TypeConsole(nomConsole);
		consoles.add(newConsole);
		
		return Response
				   .status(200)
				   .entity("AddConsole is called, nom : " + nomConsole).build();
		
	}
	
	@PUT
	@Path("/{consoleId}")
	@Produces("text/plain")
	public Response updateConsole(@PathParam("consoleId") Long id,
			@QueryParam("nom") String nomConsole){
		
		TypeConsole t;
		
		try {
			t = consoles.get(id);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Console not found for ID : "+ id).build();
		}
		
		t.setNomConsole(nomConsole);
		
		
		consoles.update(t);
		
		return Response
				   .status(200)
				   .entity("UpdateConsole is called, id : " + id + ", nom : " + nomConsole).build();
		
	}

}
