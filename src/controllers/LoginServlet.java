package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("loginForm.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recuperation parametres username et pwd
		String u = request.getParameter("username");
		String p = request.getParameter("pwd");
		
		String cible = "loginForm.jsp";
		if(u==null || p==null || u.trim().equals("")
				|| p.trim().equals("")){
			request.setAttribute("errorMsg","Attention...");
		}
		//sinon errorMsg => loginForm.jsp
		else{
			if(u.equals("admin")&&p.equals("admin"))
			{
				cible = "espaceAdmin.jsp";
				request.getSession().setAttribute("nomV", "admin");
				
				//Logger.getLogger("rootLogger").log(Level.INFO, u+"s'est connectÈ depuis : "+request.getRemoteAddr());
			}
			else
				request.setAttribute("errorMsg", "identifiants incorrects");
		}
		request.getRequestDispatcher(cible).forward(request, response);
	}

}
