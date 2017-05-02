package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

import beans.Commande;
import beans.Jeu;
import beans.LigneCommande;
import beans.TypeConsole;
import dao.CommandesDAO;
import dao.JeuxDAO;
import dao.LigneCommandesDAO;
import dao.TypeConsoleDAO;
import dao.UtilisateursDao;

@Path("/commande")
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
	public Response deleteCommande(@PathParam("idCommande") Long id) {
		if(id == null)
			return Response.serverError().entity(id).build();
		
		try {
			commandes.get(id).getLigneCommandes().clear();
			commandes.delete(id);
			
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Commande not found for UID: " + id).build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Path("/user/{userid}")
	@Produces("text/plain")
	public Response createCommande(@PathParam("userid") Long userid){
		
		Commande c;
		UtilisateursDao users = new UtilisateursDao();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date d = new Date();
		
		Commande newCommande = new Commande(users.get(userid), (float)0, d);
		commandes.add(newCommande);
		
		return Response
				   .status(200)
				   .entity("AddCommande is called, user : " + userid + ", date : "+ formatter.format(d)).build();
		
	}
	
	@PUT
	@Path("/{commandeId}")
	@Produces("text/plain")
	public Response updateCommande(@PathParam("commandeId") Long id,
			@QueryParam("user") Long userid,
			@QueryParam("date") String date){
		
		Commande c;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			c = commandes.get(id);
			
		} catch(RuntimeException e){
			
			return Response
					   .status(404)
					   .entity("Commande not found for ID : "+ id).build();
		}
		UtilisateursDao users = new UtilisateursDao();
		try {
			c.setDateCommande(formatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			c.setDateCommande(new Date());
		}
		c.setUser(users.get(userid));
		
		
		
		commandes.update(c);
		
		return Response
				   .status(200)
				   .entity("UpdateCommande is called, id : " + id + ", userid : " + userid+", date : "+formatter.format(date)).build();
		
	}
	
	
	@POST
	@Path("{idCommande}/ligne")
	@Produces("text/plain")
	public Response createLigne(@PathParam("idCommande") Long idCommande,
			@QueryParam("idJeu") Long idJeu,
			@QueryParam("qty") int quantity,
			@QueryParam("idConsole") Long console_id){
		
		Commande c = commandes.get(idCommande);
		LigneCommandesDAO lignes = new LigneCommandesDAO();
		JeuxDAO jeux = new JeuxDAO();
		LigneCommande l = new LigneCommande(c, jeux.get(idJeu), quantity, console_id);
		
		lignes.add(l);
		c.updatePrice();
		commandes.update(c);
		return Response
				   .status(200)
				   .entity("CreateLigne is called, Commande n° : " + idCommande + ", jeu : "+ jeux.get(idJeu).getTitre() 
						   + ", quantity : " + quantity).build();
		
	}
	
	@DELETE
	@Path("/{idCommande}/jeu/{idJeu}")
	@Produces("text/plain")
	public Response removeLigne(@PathParam("idCommande") Long id,
			@PathParam("idJeu") Long idJeu){
		
		Commande c = null;
		
		try {
			c = commandes.get(id);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Commande not found for ID : "+ id).build();
		}
		
		Jeu j;
		JeuxDAO consoles = new JeuxDAO();
		try {
			j = consoles.get(idJeu);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Jeu not found for ID : "+ idJeu).build();
		}
		c.removeLigne(j.getId());
		LigneCommandesDAO lignes = new LigneCommandesDAO();
		lignes.delete(c, j);
		c.updatePrice();
		commandes.update(c);
		
		return Response
				   .status(200)
				   .entity("Jeu "+ j.getTitre().toString() +" retiré a la commande " + c.getId().toString()).build();
		
	}
	
	private Float getPrix(Long idCommande){
		Commande c = commandes.get(idCommande);
		Float price = (float)0;
		for (Iterator<LigneCommande> i = c.getLigneCommandes().iterator(); i.hasNext();) {
		    LigneCommande item = i.next();
		    
		    price = price + item.getLinePrice();
		}
		return price;
	}
}
