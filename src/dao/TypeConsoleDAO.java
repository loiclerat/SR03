package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import beans.TypeConsole;
import services.SessionFactoryUtil;

public class TypeConsoleDAO implements DAOFactory<TypeConsole> {
	
	public static int countTypeConsole(){
		
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<TypeConsole> req = session.createQuery("from TypeConsole tc");
		int nbConsoles = req.list().size();
		
		session.getTransaction().commit();
		return nbConsoles;
	}
	

	@Override
	public void add(TypeConsole e) {
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
	public List<TypeConsole> list() {
		
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<TypeConsole> req = session.createQuery("from TypeConsole tc");
		List<TypeConsole> consoles = req.list();
		
		session.getTransaction().commit();
		return consoles;
	}

	@Override
	public TypeConsole get(Long idEntity) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Object tc = session.get(TypeConsole.class, idEntity);
		if(tc == null) throw new RuntimeException("Console introuvable");
		
		session.getTransaction().commit();
		return (TypeConsole)tc;
	}

	@Override
	public List<TypeConsole> listByAttribute(String attribute) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<TypeConsole> req = session.createQuery("from TypeConsole tc where tc.nomConsole like :x");
		req.setParameter("x", "%"+attribute+"%");
		
		List<TypeConsole> consoles = req.list();
		
		session.getTransaction().commit();
		return consoles;
	}
	
	public TypeConsole getByAttribute(String attribute) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query<TypeConsole> req = session.createQuery("from TypeConsole tc where tc.nomConsole = :x");
		req.setParameter("x", attribute);
		
		TypeConsole console = req.getSingleResult();
		
		session.getTransaction().commit();
		return console;
	}

	@Override
	public void delete(Long idEntity) {
		// Sera remplacé par EJB
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//
		
		Object tc = session.get(TypeConsole.class, idEntity);
		if(tc == null) throw new RuntimeException("Console introuvable");
		
		session.delete(tc);
		session.getTransaction().commit();
		
	}

	@Override
	public void update(TypeConsole e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(e);
		session.getTransaction().commit();
		
	}
}
