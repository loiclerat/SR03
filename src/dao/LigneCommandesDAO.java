package dao;

import dao.CommandesDAO;
import services.SessionFactoryUtil;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import beans.Commande;
import beans.Jeu;
import beans.LigneCommande;

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

	public LigneCommande get(Commande idCommande, Jeu idJeu) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("select lc from LigneCommande lc where lc.commande_id=:c and lc.jeu_id=:j ");
		req.setParameter("c", idCommande.getId());  
		req.setParameter("j", idJeu.getId());
		LigneCommande ligne = req.getSingleResult();
		
		if(ligne == null) throw new RuntimeException("LigneCommande introuvable");
		session.getTransaction().commit();
		return ligne;
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
	
	public void delete(Commande c, Jeu j){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("delete from LigneCommande where commande_id=:x and jeu_id=:j ");
		req.setParameter("c", c.getId());  
		req.setParameter("j", j.getId());
		req.executeUpdate();
		
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
	
	public void deleteByJeu(Jeu j){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		TypedQuery<LigneCommande> req = session.createQuery("delete from LigneCommande where jeu_id=:x ");
		req.setParameter("j", j.getId());  
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
