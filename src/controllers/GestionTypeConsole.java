package controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.TypeConsole;

import dao.TypeConsoleDAO;

/**
 * Servlet implementation class GestionUsers
 */
@WebServlet("/GestionTypeConsole")
public class GestionTypeConsole extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionTypeConsole() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<TypeConsole> ltc = TypeConsoleDAO.findAll();
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
				TypeConsoleDAO.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("tcModif", TypeConsoleDAO.find(id));
			} else if (action.equals("sort")) {
				// ordina la lista implicitamente utilizzando il metodo
				// compareTo dell'interfaccia Comparable (vedere la classe
				// Users)
				Collections.sort(ltc);
			}
		}

		// recuperer une liste d'utilisateurs

		request.setAttribute("listeTC", ltc);

		// rediriger vers une page
		request.getRequestDispatcher("TypeConsolesList.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<TypeConsole> listeTC = TypeConsoleDAO.findAll();

		String action = request.getParameter("action");

		if (action != null) {
			if (action.equals("sort")) {
				String sortType = request.getParameter("sortType");
				// TODO : Create TCNameComp and TCComp
				/*if (sortType.equals("1"))
					Collections.sort(listeTC, new NameComp());
				else if (sortType.equals("2"))
					Collections.sort(listeU, new UsernameComp());
				*/
			}
		} else {

			String nomConsole = request.getParameter("nomConsole");

			TypeConsole tc = new TypeConsole(0, nomConsole);
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				tc.setId(Integer.parseInt(idStr));
				TypeConsoleDAO.update(tc);
			} else {
				TypeConsoleDAO.insert(tc);
			}

		}
		request.setAttribute("listeTC", listeTC);
		request.getRequestDispatcher("TypeConsolesList.jsp").forward(request, response);
	}

}
