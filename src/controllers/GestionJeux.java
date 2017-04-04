package controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Jeu;
import beans.NameComp;
import beans.UsernameComp;
import dao.JeuxDAO;

/**
 * Servlet implementation class GestionJeux
 */
@WebServlet("/GestionJeux")
public class GestionJeux extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionJeux() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Jeu> lj = JeuxDAO.findAll();
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
				JeuxDAO.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("jModif", JeuxDAO.find(id));
			} else if (action.equals("sort")) {
				// ordina la lista implicitamente utilizzando il metodo
				// compareTo dell'interfaccia Comparable (vedere la classe
				// Users)
				Collections.sort(lj);
			}
		}

		// recuperer une liste d'utilisateurs

		request.setAttribute("listeJ", lj);

		// rediriger vers une page
		request.getRequestDispatcher("JeuxList.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Jeu> listeJ = JeuxDAO.findAll();

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

			String titre = request.getParameter("titre");
			String url_image = request.getParameter("url_image");
			Float prix = Float.parseFloat(request.getParameter("prix"));
			int typeConsole_id = Integer.parseInt(request.getParameter("typeConsole_id"));

			Jeu j = new Jeu(0, titre, url_image, prix, typeConsole_id);
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				j.setId(Integer.parseInt(idStr));
				JeuxDAO.update(j);
			} else {
				JeuxDAO.insert(j);
			}

		}
		request.setAttribute("listeJ", listeJ);
		request.getRequestDispatcher("JeuxList.jsp").forward(request, response);
	}


}
