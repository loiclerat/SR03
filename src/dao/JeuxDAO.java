package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import beans.Jeu;
import services.SessionFactoryUtil;

public class JeuxDAO implements DAOFactory<Jeu> {
	
	public JeuxDAO(){}
	
	@Override
	public void add(Jeu e) {
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
	public List<Jeu> list() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<Jeu> req = session.createQuery("select j from Jeu j");
		List<Jeu> jeux = req.list();
		
		session.getTransaction().commit();
		return jeux;
	}

	@Override
	public Jeu get(Long idEntity) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Object j = session.get(Jeu.class, idEntity);
		if(j == null) throw new RuntimeException("Jeu introvable");
		session.getTransaction().commit();
		return (Jeu)j;
	}

	@Override
	public List<Jeu> listByAttribute(String attribute) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//List<Jeu> jux = session.createQuery("FROM Jeu").list();
		Query<Jeu> req = session.createQuery("from Jeu j where j.titre like :x");
		req.setParameter("x", "%"+attribute+"%");
		
		List<Jeu> jeux = req.list();
		
		session.getTransaction().commit();
		return jeux;
	}

	@Override
	public void delete(Long idEntity) {
		// Sera remplacé par EJB
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//
		
		Object j = session.get(Jeu.class, idEntity);
		if(j == null) throw new RuntimeException("Jeu introvable");
		
		session.delete(j);
		session.getTransaction().commit();
		
	}

	@Override
	public void update(Jeu e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(e);
		session.getTransaction().commit();
		
	}

}
