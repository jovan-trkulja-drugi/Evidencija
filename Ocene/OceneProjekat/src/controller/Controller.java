package controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.Ispit;
import model.Predispitnaobaveza;
import model.Predmet;
import utils.PersistenceUtil;

public class Controller {
	
	private static EntityManager em = null;
	private static EntityTransaction et = null;
	
	static {
		em = PersistenceUtil.getEntityManager();
		et = em.getTransaction();
	}
	
	public static boolean createEntityManager() {
		
		try {
			em = null;
			et = null;
			
			em = PersistenceUtil.getEntityManager();
			
			et = em.getTransaction();
			return true;
			
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean closeConnection() {
		try {
			if(em != null) {
				em.close();
				return true;
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean insertSubject(Predmet p) {
		
		try {
			
			et.begin();
			
			em.persist(p);
			
			em.flush();
						
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			
			et.rollback();
			return false;
			
		}
	}
	
	public static List<Predmet> getSubjects() {
		
		return em.createQuery("select p from Predmet p", Predmet.class).getResultList();

	}
	
	public static boolean insertColloquim(Predispitnaobaveza po) {
		
		try {
			
			et.begin();
			
			em.persist(po);
			
			em.flush();
			
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
			
		}
	}
	
	public static boolean connectSubjectColloquim(Predmet p, Predispitnaobaveza po) {
		
		try {
			
			et.begin();
			
			p = em.merge(p);
			
			p.addPredispitnaobaveza(po);
			
			em.persist(p);
			
			em.flush();
			
			et.commit();
			
			return true;
			
		}  catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
			
		}
	}
	
	public static boolean insertExam(Ispit i) {
		
		try {
			
			et.begin();
			
			em.persist(i);
			
			em.flush();
			
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			
			et.rollback();
			return false;
			
		}
	}
	
	public static boolean connectSubjectExam(Predmet p, Ispit i) {
		
		try {
			
			et.begin();
			
			p = em.merge(p);
			
			p.addIspit(i);
			
			int ocena = i.getOcena();
			
			if(ocena == 5) {
				p.setPolozen("Ne");
			} else {
				p.setPolozen("Da");
			}
			
			em.persist(p);
			
			em.flush();
			
			et.commit();
			
			return true;
			
		}  catch(Exception e) {
			
			et.rollback();
			return false;
			
		}
	}
	
	public static Stream<Ispit> getIspitStream() {
		
		return em.createQuery("select i from Ispit i where i.ocena > 5", Ispit.class)
				 .getResultList()
				 .stream();
		
	}
	
	public static boolean updateColloquim(Predispitnaobaveza po, double bodovi) {

		try {
			
			et.begin();
			
			po = em.merge(po);
			em.remove(po);
			
			po.setBrBodova(bodovi);
			
			em.persist(po);
			
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static boolean updateColloquim(Predispitnaobaveza po, Date datum, double bodovi) {
		
		try {
			
			et.begin();
			
			po = em.merge(po);
			em.remove(po);
			
			po.setBrBodova(bodovi);
			po.setDatum(datum);
			
			em.persist(po);
			
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		} 
	}
	
	public static boolean updateExam(Ispit i, double bod, double bodoviU, int ocena) {
		
		try {
			
			et.begin();
			
			i = em.merge(i);
			em.remove(i);
			
			i.setBrBodova(bod);
			i.setUkupnoBodova(bodoviU);
			i.setOcena(ocena);
			
			em.persist(i);
			
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static boolean updateExam(Ispit i, double bod, double bodoviU, int ocena, Date date) {
		
		try {
			
			et.begin();
			
			i = em.merge(i);
			em.remove(i);
			
			i.setBrBodova(bod);
			i.setUkupnoBodova(bodoviU);
			i.setOcena(ocena);
			i.setDatum(date);
			
			em.persist(i);
			
			et.commit();
			
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}

	public static List<Predispitnaobaveza> getColloquiums(Predmet p) {
		
		return em.createQuery("select po from Predispitnaobaveza po where po.predmet.idPredmet=:id", Predispitnaobaveza.class)
				 .setParameter("id", p.getIdPredmet())
				 .getResultList();
	
	}
	
	public static List<Ispit> getExams(Predmet p) {
		
		return em.createQuery("select i from Ispit i where i.predmet.idPredmet=:id", Ispit.class)
		         .setParameter("id", p.getIdPredmet())
		         .getResultList();
	
	}
	
	public static boolean changeNameSubject(Predmet p, String vr) {

		try {
			
			et.begin();
			
			p = em.merge(p);
			em.remove(p);
			
			p.setNazPred(vr);
			
			em.persist(p);
			
			et.commit();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static boolean changeYearSubject(Predmet p, int vr) {

		try {
			
			et.begin();
			
			p = em.merge(p);
			em.remove(p);
			
			p.setGodina(vr);
			
			em.persist(p);
			
			et.commit();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static boolean changeStatusSubject(Predmet p, String vr) {
		
		try {
			
			et.begin();
			
			p = em.merge(p);
			em.remove(p);
			
			p.setStatus(vr);
			
			em.persist(p);
			
			et.commit();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static boolean changeProfessorSubject(Predmet p, String vr) {
		
		try {
			
			et.begin();
			
			p = em.merge(p);
			em.remove(p);
			
			p.setNazProf(vr);
			
			em.persist(p);
			
			et.commit();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static boolean changeESPBSubject(Predmet p, int vr) {
		
		try {
			
			et.begin();
			
			p = em.merge(p);
			em.remove(p);
			
			p.setBrEspb(vr);;
			
			em.persist(p);
			
			et.commit();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			et.rollback();
			return false;
		}
	}
	
	public static Predmet findPredmet(String nazPredmeta) {
		
		return em.createQuery("select p from Predmet p where p.nazPred like :predmet", Predmet.class)
				 .setParameter("predmet", nazPredmeta)
				 .getResultList()
				 .stream()
				 .findFirst()
				 .orElse(null);
				
	}

}
