package controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.MailingTools;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("contact.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String exp = request.getParameter("exp");

		String regex = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$";
		boolean validEmail = Pattern.matches(regex, exp);
		if (!validEmail) {
			request.setAttribute("errorMsg", "Attention adresse invalide");
		} else {

			String sujet = request.getParameter("sujet");
			String msg = request.getParameter("msg");

			if (exp == null || exp.trim().equals("")) {
				request.setAttribute("errorMsg",
						"Attention, l'expediteur est vide");
			} else if (sujet == null || sujet.trim().equals("") || msg == null
					|| msg.trim().equals("")) {
				request.setAttribute("errorMsg",
						"Attention, le message ou le sujet est vide");
			} else {
				try {
					MailingTools.sendMail(exp, sujet, msg);
					request.setAttribute("errorMsg", "Message envoyé");
				} catch (MessagingException e) {
					request.setAttribute("errorMsg",
							"Erreur d'envoi" + e.getMessage());
				}
				request.getRequestDispatcher("contact.jsp").forward(request,
						response);
			}
		}

	}

}
