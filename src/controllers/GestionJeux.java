package controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;


import beans.Jeu;
import dao.JeuxDAO;

/**
 * Servlet implementation class GestionJeux
 */
@WebServlet("/GestionJeux")
public class GestionJeux extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//private JeuxDAO jController  = new JeuxDAO();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionJeux() {
		super();
		//jController  = new JeuxDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, HibernateException {
		JeuxDAO jController  = new JeuxDAO();
		List<Jeu> lj = jController.list();
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
				jController.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("jModif", jController.get(id));
			} else if (action.equals("sort")) {
				// ordina la lista implicitamente utilizzando il metodo
				// compareTo dell'interfaccia Comparable (vedere la classe
				// Users)
				//Collections.sort(lj);
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
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JeuxDAO jController  = new JeuxDAO();
		List<Jeu> listeJ = jController.list();

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
			Double prix = Double.parseDouble(request.getParameter("prix"));
			//Long typeConsole_id = Long.parseLong(request.getParameter("typeConsole_id"));

			Jeu j = new Jeu(titre, url_image, prix); //, typeConsole_id
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				j.setId(Long.parseLong(idStr));
				jController.update(j);
			} else {
				jController.add(j);
			}

		}
		request.setAttribute("listeJ", listeJ);
		request.getRequestDispatcher("JeuxList.jsp").forward(request, response);
	}


}
