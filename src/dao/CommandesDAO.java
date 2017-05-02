package dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import beans.Commande;
import services.SessionFactoryUtil;

public class CommandesDAO implements DAOFactory<Commande>{

	
	public CommandesDAO(){}
	
	@Override
	public void add(Commande e) {
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
	public List<Commande> list() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<Commande> req = session.createQuery("select c from Commande c");
		List<Commande> commandes = req.getResultList();
		
		session.getTransaction().commit();
		return commandes;
	}

	@Override
	public Commande get(Long idEntity) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Object j = session.get(Commande.class, idEntity);
		if(j == null) throw new RuntimeException("Commande introvable");
		session.getTransaction().commit();
		return (Commande)j;
	}

	@Override
	public List<Commande> listByAttribute(String attribute) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<Commande> req = session.createQuery("from Commande j where j.titre like :x");
		req.setParameter("x", "%"+attribute+"%");
		
		List<Commande> commandes = req.getResultList();
		
		session.getTransaction().commit();
		return commandes;
	}

	@Override
	public void delete(Long idEntity) {
		// Sera remplacé par EJB
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//
		
		Object j = session.get(Commande.class, idEntity);
		if(j == null) throw new RuntimeException("Commande introuvable");
		
		session.delete(j);
		session.getTransaction().commit();
		
	}

	@Override
	public void update(Commande e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(e);
		session.getTransaction().commit();
		
	}

}
