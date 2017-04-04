package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Jeu;
import beans.LigneCommande;
import dao.JeuxDAO;
import dao.LigneCommandesDAO;

@WebServlet("/GestionLigneCommandes")
public class GestionLigneCommandes extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionLigneCommandes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<LigneCommande> lc = LigneCommandesDAO.findAll();
		int id = 0;
		String action = request.getParameter("action");
		if (action != null) {
			String idCh = request.getParameter("id");
			if (idCh != null) {
				try {
					id = Integer.parseInt(idCh);
				} catch (Exception e) {

				}
			}

			if (action.equals("supprimer")) {
				LigneCommandesDAO.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("lcModif", LigneCommandesDAO.find(id));
			} else if (action.equals("sort")) {
				// ordina la lista implicitamente utilizzando il metodo
				// compareTo dell'interfaccia Comparable (vedere la classe
				// Users)
				//Collections.sort(lc);
			}
		}

		// recuperer une liste d'utilisateurs

		request.setAttribute("listeLC", lc);

		// rediriger vers une page
		request.getRequestDispatcher("LigneCommandesList.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<LigneCommande> listeLC = LigneCommandesDAO.findAll();

		String action = request.getParameter("action");

		if (action != null) {
			if (action.equals("sort")) {
				String sortType = request.getParameter("sortType");
				/*
				if (sortType.equals("1"))
					Collections.sort(listeJ, new NameComp());
				else if (sortType.equals("2"))
					Collections.sort(listeJ, new UsernameComp());
				*/
			}
		} else {

			int jeu_id = Integer.parseInt(request.getParameter("jeu_id"));
			int commande_id = Integer.parseInt(request.getParameter("commande_id"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			LigneCommande c = new LigneCommande(0, JeuxDAO.find(jeu_id), commande_id, quantity);
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				c.setId(Integer.parseInt(idStr));
				LigneCommandesDAO.update(c);
			} else {
				LigneCommandesDAO.insert(c);
			}

		}
		request.setAttribute("listeLC", listeLC);
		request.getRequestDispatcher("LigneCommandesList.jsp").forward(request, response);
	}
}
