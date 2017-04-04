package controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.MailingTools;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/UseCookies")
public class UseCookies extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UseCookies() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Cookie[] listeCookies = request.getCookies();
		request.setAttribute("listeCookies", listeCookies);
		
		request.getRequestDispatcher("listeCookies.jsp").forward(request, response);
		
	}	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String name = request.getParameter("nomCookie");
		String value = request.getParameter("valCookie");
		Cookie c = new Cookie(name,value);
		c.setMaxAge(30);
		response.addCookie(c);
		
		doGet(request, response);		
	
	}

		
}
