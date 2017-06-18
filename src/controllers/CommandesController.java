package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
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
import beans.Utilisateur;
import dao.CommandesDAO;
import dao.JeuxDAO;
import dao.LigneCommandesDAO;
import dao.TypeConsoleDAO;
import dao.UtilisateursDao;
import services.CustomRequest;
import services.CustomResponse;
import services.LigneRequest;

@Path("/commande")
public class CommandesController {
	
	private CommandesDAO commandes;
	
	public CommandesController(){
		commandes = new CommandesDAO();
	}
	
	
	/** Valider le panier **/
	@GET
	@Path("/validateCurrent/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateCurrent(@PathParam("login") String login, @HeaderParam("Authorization") String authorizationHeader) {
		
		// Valider le token d'authentification
        if (!AuthenticationManager.checkToken(authorizationHeader))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        
		if(login == null)
			return Response.serverError().entity(login).build();
		
		try {
			Utilisateur user = getUserByLogin(login);
			
			Commande c = commandes.getCurrent(user.getId());
			
			if (c.getPrice() == 0)
				return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

			c.setStatusCommande("termine");
			commandes.update(c);
			
			return Response.ok().build();
			
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
		}
	}
	
	
	/** R�cup�rer le contenu du panier **/
	@GET
	@Path("/getCurrent/{login}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getCurrentCommande(@PathParam("login") String login, @HeaderParam("Authorization") String authorizationHeader) { 
		
		// Valider le token d'authentification
        if (!AuthenticationManager.checkToken(authorizationHeader))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        
		if(login == null)
			return Response.serverError().entity(login).build();
		
		try {
			Utilisateur user = getUserByLogin(login);
			
			Commande c = commandes.getCurrent(user.getId());
			
			if (c == null)
			{
				Date d = new Date();
				
				c = new Commande(user, (float)0, d);
				commandes.add(c);
			}
			
			return Response.ok(c, MediaType.APPLICATION_JSON).build();
			
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
		}
	}
	
	
	/** Ajout d'une ligne au panier **/
	@POST
	@Path("/ligne")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createLigne(@FormParam("login") String login,
								@FormParam("idJeu") String idJeu,
								@FormParam("qty") String qty,
								@FormParam("nomConsole") String nomConsole,
								@HeaderParam("Authorization") String authorizationHeader
			){

        // Valider le token d'authentification
        if (!AuthenticationManager.checkToken(authorizationHeader))
            return Response.status(Response.Status.UNAUTHORIZED).build();
		
		if(login == "null")
			return Response.serverError().entity(login).build();
		
		try {
			Utilisateur user = getUserByLogin(login);

			Commande c = commandes.getCurrent(user.getId());
			if (c == null)
			{
				Date d = new Date();
				
				c = new Commande(user, (float)0, d);
				commandes.add(c);
			}

			Long id = getConsoleIdFromName(nomConsole);
			
			LigneCommandesDAO lignes = new LigneCommandesDAO();
			JeuxDAO jeux = new JeuxDAO();
			
			LigneCommande l = new LigneCommande(
					c, 
					jeux.get(Long.parseLong(idJeu)), 
					Integer.parseInt(qty), 
					id
			);

			lignes.add(l);
			c.updatePrice();
			commandes.update(c);
			
			CustomResponse crep = new CustomResponse();
			crep.setStatus(200);
			
			return Response.ok(crep, MediaType.APPLICATION_JSON).build();
			
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
		}		
	}
	
	
	/** Suppression d'une ligne du panier **/
	@DELETE
	@Path("user/{login}/jeu/{idJeu}/console/{idConsole}")
	@Produces("text/plain")
	public Response removeLigne(@PathParam("idJeu") Long idJeu,
			@PathParam("idConsole") Long idConsole,
			@PathParam("login") String login,
			@HeaderParam("Authorization") String authorizationHeader)
	{
		
		// Valider le token d'authentification
        if (!AuthenticationManager.checkToken(authorizationHeader))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        
		Commande c = null;
		// Récupération de la commande
		try {
			Utilisateur user = getUserByLogin(login);

			c = commandes.getCurrent(user.getId());

		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Commande not found for ID : ").build();
		}
		
		// Récupération du jeu
		Jeu j;
		JeuxDAO jeux = new JeuxDAO();
		try {
			j = jeux.get(idJeu);
		} catch(RuntimeException e){
			return Response
					   .status(404)
					   .entity("Jeu not found for ID : "+ idJeu).build();
		}
		
		// Récupération du jeu
		TypeConsole tc;
		TypeConsoleDAO consoles = new TypeConsoleDAO();
		try {
			tc = consoles.get(idConsole);
		} catch(RuntimeException e){
			return Response
			   .status(404)
			   .entity("Console not found for ID : "+ idConsole).build();
		}
		
		c.removeLigne(idJeu, idConsole);
		
		LigneCommandesDAO lignes = new LigneCommandesDAO();
		lignes.delete(c, j, tc);
		
		c.updatePrice();
		commandes.update(c);
		
		CustomResponse crep = new CustomResponse();
		crep.setStatus(200);
		
		return Response.ok(crep, MediaType.APPLICATION_JSON).build();
		
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
	
	private Long getConsoleIdFromName(String nom) {
		TypeConsoleDAO consoles = new TypeConsoleDAO();

		List<TypeConsole> listConsoles = consoles.list();
		for (int i=0 ; i<listConsoles.size() ; i++){
			
			if (listConsoles.get(i).getNomConsole().matches(nom)){
				return listConsoles.get(i).getId();
			}
		}
		return new Long(0);
	}
	
	private Utilisateur getUserByLogin(String login) {
		UtilisateursDao users = new UtilisateursDao();
		
		List<Utilisateur> listUsers = users.list();
		for (int i=0 ; i<listUsers.size() ; i++){
			if (listUsers.get(i).getLogin().matches(login)){
				return listUsers.get(i);
			}
		}
		return null;
	}	
	
	
	
	
	
	/** ==============================================================================**/
	/** Pas utilis� dans le client Web **/
	
	@GET
	@Produces("application/json")
	public List<Commande> listCommandes() {
		return commandes.list(); 
	}
	
	@GET
	@Path("/{idCommande}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommande(@PathParam("idCommande") String id) {
		
		if(id == null) return Response.serverError().entity(id).build();
		
		try {
			commandes.get(Long.parseLong(id));
		}catch(RuntimeException e){
			return Response.status(Response.Status.NOT_FOUND).entity("Commande not found for UID: " + id).build();
		}
		return Response.ok(commandes.get(Long.parseLong(id)), MediaType.APPLICATION_JSON).build();

	}
	
	@PUT
	@Path("{idCommande}/ligne")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLigne(@PathParam("idCommande") Long idCommande,
			LigneRequest lreq){
		
		Commande c = commandes.get(idCommande);
		LigneCommandesDAO lignes = new LigneCommandesDAO();
		JeuxDAO jeux = new JeuxDAO();
		LigneCommande l = new LigneCommande(
				c, 
				jeux.get(Long.parseLong(lreq.getIdJeu())), 
				lreq.getQty(), 
				Long.parseLong(lreq.getIdConsole())
		);
		
		lignes.update(l);
		c.updatePrice();
		commandes.update(c);
		
		CustomResponse crep = new CustomResponse();
		crep.setStatus(200);
		
		return Response.ok(crep, MediaType.APPLICATION_JSON).build();
		
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
	
	/** ======================================= **/
	/** ======================================= **/
}
