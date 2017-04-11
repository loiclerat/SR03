package controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.NameComp;
import beans.UsernameComp;
import beans.Utilisateur;

import dao.UtilisateursDao;

/**
 * Servlet implementation class GestionUsers
 */
@WebServlet("/GestionUsers")
public class GestionUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateursDao uController;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionUsers() {
		super();
		uController = new UtilisateursDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Utilisateur> lu = uController.list();
		
		Long id = (long) 0;
		String action = request.getParameter("action");
		if (action != null) {
			String idCh = request.getParameter("id");
			if (idCh != null) {
				try {
					id = Long.parseLong(idCh);
				} catch (Exception e) {

				}
			}

			if (action.equals("supprimer")) {
				uController.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("uModif", uController.get(id));
			} else if (action.equals("sort")) {
				// ordina la lista implicitamente utilizzando il metodo
				// compareTo dell'interfaccia Comparable (vedere la classe
				// Users)
				Collections.sort(lu);
			}
		}

		// recuperer une liste d'utilisateurs

		request.setAttribute("listeU", lu);

		// rediriger vers une page
		request.getRequestDispatcher("UsersList.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Utilisateur> listeU = uController.list();

		String action = request.getParameter("action");

		if (action != null) {
			if (action.equals("sort")) {
				String sortType = request.getParameter("sortType");
				if (sortType.equals("1"))
					Collections.sort(listeU, new NameComp());
				else if (sortType.equals("2"))
					Collections.sort(listeU, new UsernameComp());
			}
		} else {

			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String mail = request.getParameter("mail");
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String adresse = request.getParameter("adresse");
			String cpostal = request.getParameter("cpostal");
			String ville = request.getParameter("ville");

			Utilisateur u = new Utilisateur(nom, prenom, mail, login, password, adresse, cpostal, ville);
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				u.setId(Long.parseLong(idStr));
				uController.update(u);
			} else {
				uController.add(u);
			}

		}
		request.setAttribute("listeU", listeU);
		request.getRequestDispatcher("UsersList.jsp").forward(request, response);
	}

}
