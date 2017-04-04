package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Commande;
import beans.LigneCommande;
import beans.Jeu;
import beans.Utilisateur;
import dao.JeuxDAO;
import dao.UtilisateursDao;

/**
 * Servlet implementation class GestionAchats
 */
@WebServlet("/GestionAchats")
public class GestionAchats extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionAchats() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if(action!=null){
						
			if(action.equals("showForm")){
				//recuperer la liste produits
				List<Jeu> lp = JeuxDAO.findAll();
				request.setAttribute("listeP", lp);
				//montrer la page achat
				request.getRequestDispatcher("achatForm.jsp").forward(request, response);
				
			}else if(action.equals("showPanier"))
			{
				//montre la liste dans une nouvelle page
				request.getRequestDispatcher("showPanier.jsp").forward(request,response);
			}else if(action.equals("deleteFromPanier")){
				int num = Integer.parseInt(request.getParameter("num"));
				Object o = request.getSession().getAttribute("panier");
				if(o!=null){
					Commande panier = (Commande)o;
					if(panier.getLignes().size()>num){
						panier.getLignes().remove(num);
						request.getSession().setAttribute("panier",panier);
						/** con l'ultima istruzione ripristiniamo 
						 * il paniere nella sessione**/
					}
				}
				request.getRequestDispatcher("showPanier.jsp").forward(request, response);
			}else if(action.equals("removePanier")){
				//elimino completamente la sessione panier
				//getSession.invalidate permette di invalidare tutte le variabili di sessione
				//nel nostro caso abbiamo una sola variabile di sessione: panier
				//e quindi la eliminiamo con removeAttribute
				request.getSession().removeAttribute("panier");
				request.getRequestDispatcher("showPanier.jsp").forward(request, response);
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recuperer les parametres: idProduit, qte
		int idProduit = Integer.parseInt(request.getParameter("idProd"));
		int qte = Integer.parseInt(request.getParameter("qte"));
		
		
		//ajouter ligne de commande dans le panier
		LigneCommande lc = new LigneCommande();
		lc.setQte(qte);
		lc.setJeu(JeuxDAO.find(idProduit));
		
		Commande panier = null;
		Object obj = request.getSession().getAttribute("panier");
		if(obj==null) panier = new Commande();
		else panier = (Commande) obj;
		
		int position=panier.getLignes().indexOf(lc);
		//indexOf mi restituisce la posizione dell'elemento lc nella lista panier
		if(position==-1)
			//se non ho ancora scelto quel prodotto lo aggiungo
			panier.getLignes().add(lc);
		else{
			//se il prodotto esiste già nel paniere aggiorno la quantità
			int newQty=lc.getQte() + panier.getLignes().get(position).getQte();
			panier.getLignes().get(position).setQte(newQty);
		}
		request.getSession().setAttribute("panier", panier);
		
		List<Jeu> lp = JeuxDAO.findAll();
		request.setAttribute("listeP", lp);
		//montrer la page achat
		request.getRequestDispatcher("achatForm.jsp").forward(request, response);
		
		
	}

}
