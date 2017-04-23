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

}
