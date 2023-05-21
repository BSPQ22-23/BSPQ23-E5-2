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
 * Class to allow the access to {@link domain.Image Image} objects in the database
 *
 */
public class ImageDAO extends DataAccessObjectBase implements IDataAccessObject<Image> {

	private static ImageDAO instance = new ImageDAO();	
	
	private ImageDAO() { }
	
	/**
	 * Get the DAO instance for {@link domain.Image Image}s
	 * @return
	 */
	public static ImageDAO getInstance() {
		return instance;
	}	
	/**
	 * Upload or update an {@link domain.Image Image} to the database
	 * @param object image to store/update
	 * @return true if the transaction was successful
	 */
	@Override
	public boolean save(Image object) {
		return saveObject(object);
		
	}
	/**
	 * Delete an {@link domain.Image Image} that has been retrieved from the database
	 * @param object detatched object to delete
	 */
	@Override
	public void delete(Image object) {
		super.deleteObject(object);
		
	}
	
	/**
	 * Get all {@link domain.Image Image}s from the database
	 * @return the list of images in the database
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
	
	/**
	 * Get an {@link domain.Image Image} from the database by it's id
	 * @param param id of the image
	 * @return the image or null if it doesn't exist
	 */
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
	/**
	 * Get an {@link domain.Image Image} from the database by the id of the object the image is attached to and the ctx of the image
	 * @param oid id of the object the image is attached to
	 * @param type ctx of the image
	 * @return the image or null if it doesn't exist
	 */
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
