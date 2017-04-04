package controllers;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class NbUsersListener
 *
 */
@WebListener
/** questa annotazione qui sopra può essere specificata nel file web.xml con il tag 
 * <listener> <listener-class>controllers.NbUsersListener</listener-class> </listener>**/

public class NbUsersListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public NbUsersListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se) {
        int nb=1;
    	Object obj = se.getSession().getServletContext().getAttribute("nbUsers");
    	
    	if(obj!=null){
    		nb+=(Integer)obj;
    	}
    	se.getSession().getServletContext().setAttribute("nbUsers", nb);
    	/** potevo fare una variabile sc=se.getSession().getServletContext() e 
    	 * utilizzarla senza **/
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se) {
     
    	ServletContext sc=se.getSession().getServletContext();
		Object obj = sc.getAttribute("nbUsers");
		
		int nb=0;
		if (obj != null) {
			nb = (Integer)obj - 1 ;
		}
		sc.setAttribute("nbUsers", nb);

    }
	
}
