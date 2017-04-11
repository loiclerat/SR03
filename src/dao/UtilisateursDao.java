package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import beans.Jeu;
import beans.Utilisateur;
import services.SessionFactoryUtil;

public class UtilisateursDao implements DAOFactory<Utilisateur>{

	
	
	public static int countUsers(){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<Utilisateur> req = session.createQuery("from Utilisateur u");
		int nbUsers = req.list().size();
		
		session.getTransaction().commit();
		return nbUsers;
	}

	@Override
	public void add(Utilisateur e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//try {
			session.save(e);
		/*} catch (Exception e1) {
			session.getTransaction().rollback();
			e1.printStackTrace();
		}
		*/
		session.getTransaction().commit();
		
	}

	@Override
	public List<Utilisateur> list() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<Utilisateur> req = session.createQuery("from Utilisateur u");
		List<Utilisateur> users = req.list();
		
		session.getTransaction().commit();
		return users;
	}

	@Override
	public Utilisateur get(Long idEntity) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Object u = session.get(Utilisateur.class, idEntity);
		if(u == null) throw new RuntimeException("Utilisateur introuvable");
		
		session.getTransaction().commit();
		return (Utilisateur)u;
	}

	@Override
	public List<Utilisateur> listByAttribute(String attribute) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//List<Jeu> jux = session.createQuery("FROM Jeu").list();
		Query<Utilisateur> req = session.createQuery("from Utilisateur u where u.login like :x");
		req.setParameter("x", "%"+attribute+"%");
		
		List<Utilisateur> users = req.list();
		
		session.getTransaction().commit();
		return users;
	}

	@Override
	public void delete(Long idEntity) {
		// Sera remplacé par EJB
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//
		
		Object u = session.get(Utilisateur.class, idEntity);
		if(u == null) throw new RuntimeException("Utilisateur introvable");
		
		session.delete(u);
		session.getTransaction().commit();
		
	}

	@Override
	public void update(Utilisateur e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(e);
		session.getTransaction().commit();
		
	}
	
}
