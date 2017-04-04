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

import beans.Commande;
import dao.CommandesDAO;

/**
 * Servlet implementation class GestionUsers
 */
@WebServlet("/GestionCommandes")
public class GestionCommandes extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionCommandes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Commande> lc = CommandesDAO.findAll();
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
				CommandesDAO.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("cModif", CommandesDAO.find(id));
			} else if (action.equals("sort")) {
				// ordina la lista implicitamente utilizzando il metodo
				// compareTo dell'interfaccia Comparable (vedere la classe
				// Users)
				//Collections.sort(lc);
			}
		}

		// recuperer une liste d'utilisateurs

		request.setAttribute("listeC", lc);

		// rediriger vers une page
		request.getRequestDispatcher("CommandesList.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Commande> listeC = CommandesDAO.findAll();

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

			int user_id = Integer.parseInt(request.getParameter("user_id"));
			Date date_commande = Date.valueOf(request.getParameter("date_commande"));

			Commande c = new Commande(0, user_id, date_commande);
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				c.setId(Integer.parseInt(idStr));
				CommandesDAO.update(c);
			} else {
				CommandesDAO.insert(c);
			}

		}
		request.setAttribute("listeC", listeC);
		request.getRequestDispatcher("CommandesList.jsp").forward(request, response);
	}
}