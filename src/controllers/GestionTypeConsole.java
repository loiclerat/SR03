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
	private TypeConsoleDAO tcController;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionTypeConsole() {
		super();
		tcController = new TypeConsoleDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		List<TypeConsole> ltc = tcController.list();
		Long id = (long)0;
		
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
				tcController.delete(id);
			} else if (action.equals("modifier")) {
				request.setAttribute("tcModif", tcController.get(id));
			} else if (action.equals("sort")) {
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

		List<TypeConsole> listeTC = tcController.list();

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

			TypeConsole tc = new TypeConsole(nomConsole);
			String idStr = request.getParameter("id");
			if (idStr != null && !idStr.trim().equals("")) {
				tc.setId(Long.parseLong(idStr));
				tcController.update(tc);
			} else {
				tcController.add(tc);
			}

		}
		request.setAttribute("listeTC", listeTC);
		request.getRequestDispatcher("TypeConsolesList.jsp").forward(request, response);
	}

}
