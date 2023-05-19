package database;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
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
	@SuppressWarnings("unchecked")
	public List<Image> find(String oid, ImageType type) {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    List<Image> output = new LinkedList<>();
	    try {
	        tx.begin();
	        Query<Image> q = pm.newQuery(Image.class);
	        q.setFilter("oid == inOid && type == inType");
	        q.declareParameters("java.lang.String inOid, "+ImageType.class.getName()+" inType");
	        q.setUnique(true);
	        Object result = q.execute(oid, type);
	        if(result instanceof Image) {
	        	Image detatchedResult = pm.detachCopy((Image)result);
	        	BufferedImage bi = ((Image)result).getImage();
	        	ColorModel cm = bi.getColorModel();
	        	boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	        	WritableRaster raster = bi.copyData(null);
	        	detatchedResult.setImage(new BufferedImage(cm, raster, isAlphaPremultiplied, null), detatchedResult.getFormat());
	        	output.add(detatchedResult);
	        } else
	        	((List<Image>) result).forEach(v -> {
	        		Image detatchedResult = pm.detachCopy(v);
	        		ColorModel cm = detatchedResult.getImage().getColorModel();
		        	boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		        	WritableRaster raster = detatchedResult.getImage().copyData(null);
		        	detatchedResult.setImage(new BufferedImage(cm, raster, isAlphaPremultiplied, null), detatchedResult.getFormat());
		        	output.add(detatchedResult);
	        	});
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
	    return output;
	}		
}
