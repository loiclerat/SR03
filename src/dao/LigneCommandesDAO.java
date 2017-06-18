package dao;

import dao.CommandesDAO;
import services.SessionFactoryUtil;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import beans.Commande;
import beans.Jeu;
import beans.LigneCommande;
import beans.TypeConsole;

public class LigneCommandesDAO implements DAOFactory<LigneCommande>{

	@Override
	public void add(LigneCommande e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.save(e);
		
		session.getTransaction().commit();
		
	}

	@Override
	public List<LigneCommande> list() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("select lc from LigneCommande lc");
		List<LigneCommande> panier = req.getResultList();
		
		session.getTransaction().commit();
		return panier;
	}

	public LigneCommande get(Commande idCommande, Jeu idJeu, Long idConsole) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("select lc from LigneCommande lc where lc.commande_id=:c and lc.jeu_id=:j and lc.console_id=:s");
		req.setParameter("c", idCommande.getId());  
		req.setParameter("j", idJeu.getId());
		req.setParameter("s", idConsole);
		LigneCommande ligne = req.getSingleResult();
		
		if(ligne == null) throw new RuntimeException("LigneCommande introuvable");
		session.getTransaction().commit();
		return ligne;
	}
	
	public int get(Commande idCommande, Jeu idJeu, String cons) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("select lc from LigneCommande lc where lc.commande_id=:c and lc.jeu_id=:j and console_id=:x");
		req.setParameter("c", idCommande.getId());  
		req.setParameter("j", idJeu.getId());
		req.setParameter("x", cons);
		LigneCommande ligne = req.getSingleResult();
		
		if(ligne == null) //throw new RuntimeException("LigneCommande introuvable");
			return -2;
		else return ligne.getQuantity();
	}

	@Override
	public List<LigneCommande> listByAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<LigneCommande> listByCommandeID(Commande c){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("from LigneCommande lc where lc.commande_id =:x");
		req.setParameter("x", c);
		
		List<LigneCommande> panier = req.getResultList();
		
		session.getTransaction().commit();
		return panier;
	}
	

	@Override
	public void delete(Long idEntity) {

		
	}
	
	public void delete(Commande c, Jeu j, TypeConsole console){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		
		String sql = "DELETE FROM LIGNECOMMANDE WHERE JEU_ID = "+ j.getId().toString() +" AND COMMANDE_ID = "+ c.getId().toString() +" AND CONSOLE_ID = "+ console.getId().toString();
		NativeQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		
		/*
		TypedQuery<LigneCommande> req = session.createQuery("delete from LigneCommande where commande_id=:c and jeu_id=:j and console_id=:p ");
		req.setParameter("c", c.getId());  
		req.setParameter("j", j.getId().toString());
		req.setParameter("p", console.getId());
		req.executeUpdate();
		
		LigneCommande l = new LigneCommande();
		l.setCommande_id(c);
		l.setConsole_id(console.getId());
		l.setJeu_id(j);
		session.delete(get(c, j, console.toString()));
		*/
		session.getTransaction().commit();
	}
	
	public void deleteByCommande(Commande c){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("delete from LigneCommande where commande_id=:x ");
		req.setParameter("c", c.getId());  
		req.executeUpdate();
		
		session.getTransaction().commit();
	}

	@Override
	public void update(LigneCommande e) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(e);
		session.getTransaction().commit();
		
	}

	@Override
	public LigneCommande get(Long idEntity) {
		// TODO Auto-generated method stub
		return null;
	}

}
