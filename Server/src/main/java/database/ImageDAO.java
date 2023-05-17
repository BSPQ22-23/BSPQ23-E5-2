package database;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import domain.Image;
import domain.Image.ImageType;
/**
 * DAO for Image class
 * @author maitanegarcia
 *
 */

public class ImageDAO extends DataAccessObjectBase implements IDataAccessObject<Image> {

	private static ImageDAO instance = new ImageDAO();	
	
	private ImageDAO() { }
	
	public static ImageDAO getInstance() {
		return instance;
	}	
	/**
	 * This method saves a reservation
	 */
	@Override
	public boolean save(Image object) {
		return saveObject(object);
		
	}
/**
 * Thus method deletes a reservation
 */
	@Override
	public void delete(Image object) {
		super.deleteObject(object);
		
	}
	
/**
 * This method gets a list of all the reservations 
 */
	@Override
	public List<Image> getAll() {
		 PersistenceManager pm = pmf.getPersistenceManager();
		    Transaction tx = pm.currentTransaction();

		    List<Image> result = null;

		    try {
		        tx.begin();

		        Query<Image> q = pm.newQuery(Image.class);
		        result = (List<Image>) q.executeList();

		        tx.commit();
		    } catch (Exception ex) {
		        System.out.println("Error: " + ex.getMessage());
		        ex.printStackTrace();
		    } finally {
		        if (tx != null && tx.isActive()) {
		            tx.rollback();
		        }

		        pm.close();
		    }

		    return result;
	}
	

	@Override
	public Image find(String param) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    Image result = null;
	    try {
	        tx.begin();

	        Query<Image> q = pm.newQuery(Image.class);
	        q.setFilter("id == userParam");
	        q.declareParameters("String userParam");
	        q.setUnique(true);
	        result = (Image) q.execute(param);
	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("Error: " + ex.getMessage());
	        ex.printStackTrace();
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }

	        pm.close();
	    }
	    return result;
	}
	public List<Image> find(String oid, ImageType type) {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    List<Image> result = null;
	    try {
	        tx.begin();

	        Query<Image> q = pm.newQuery(Image.class);
	        q.setFilter("oid == inOid && type == inType");
	        q.declareParameters("String inOid, domain.Image.ImageType inType");
	        q.setUnique(true);
	        result = q.setParameters(oid, type).executeList();
	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("Error: " + ex.getMessage());
	        ex.printStackTrace();
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }

	        pm.close();
	    }
	    return result;
	}		
}
