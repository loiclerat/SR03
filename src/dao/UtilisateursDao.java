package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import org.hibernate.Session;

import beans.Jeu;
import beans.Utilisateur;
import services.SessionFactoryUtil;

public class UtilisateursDao implements DAOFactory<Utilisateur>{

	public static int countUsers(){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		int nbUsers = session.createQuery("from Utilisateur u").list().size();

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
		}*/

		session.getTransaction().commit();

	}


	public void add(String nom, String prenom, String mail, String login, String password, String adresse, String cpostal, String ville) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Utilisateur e = new Utilisateur(nom, prenom, mail, login, password, adresse, cpostal, ville);
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

		TypedQuery<Utilisateur> req = session.createQuery("from Utilisateur u");
		List<Utilisateur> users = req.getResultList();

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

		List<Utilisateur> users = (List<Utilisateur>) session.createQuery("from Utilisateur u where u.login like :x").setParameter("x", "%"+attribute+"%").list();

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
