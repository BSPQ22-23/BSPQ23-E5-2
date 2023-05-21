package database;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import api.APIUtils;
import domain.Booking;

/**
 * DAO for Hotel class
 * @author maitanegarcia
 *
 */

import domain.Hotel;
import domain.Room;
/**
 * Class to allow the access to {@link domain.Hotel Hotel} objects in the database
 *
 */
public class HotelDAO  extends DataAccessObjectBase implements IDataAccessObject<Hotel> {
	private static final HotelDAO INSTANCE = new HotelDAO();
	
	/**
	 * Get the DAO instance for {@link domain.Hotel Hotel}s
	 * @return
	 */
	public static HotelDAO getInstance() {
		return INSTANCE;
	}
	/**
	 * Upload or update a {@link domain.Hotel Hotel} to the database
	 * @param object hotel to store/update
	 * @return true if the transaction was successful
	 */
	@Override
	public boolean save(Hotel object) {
		return saveObject(object);
	}
	/**
	 * Delete a {@link domain.Hotel Hotel} that has been retrieved from the database
	 * @param object detatched object to delete
	 */
	@Override
	public void delete(Hotel object) {
		deleteObject(object);
	}
	/**
	 * Get all {@link domain.Hotel Hotel}s from the database
	 * @return the list of hotels in the database
	 */
	@Override
	public List<Hotel> getAll() {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();

	    List<Hotel> result = null;

	    try {
	        tx.begin();

	        Query<Hotel> q = pm.newQuery(Hotel.class);
	        result = (List<Hotel>) q.executeList();

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
	 * Get {@link domain.Hotel Hotel}s from the database that contain the string in their name
	 * @param name string to search in the name
	 * @return a list of hotels with the query in their name
	 */
	public List<Hotel> getByName (String name) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    tx.begin();
	    Query<Hotel> q = pm.newQuery(Hotel.class,APIUtils.decode("bmFtZS50b0xvd2VyQ2FzZSgpLmluZGV4T2YocV9uYW1lKSA+PSAwIHx8IG5hbWUudG9Mb3dlckNhc2UoKSA9PSBxX25hbWU="));
	    q.declareParameters("String q_name");
	    q.setUnique(false);
	    List<Hotel> resultList =  (List<Hotel>) q.setParameters(name.toLowerCase()).executeList();
		tx.commit();
	    if (tx != null && tx.isActive()) {
			tx.rollback();
		}
	    pm.close();
	    return resultList;
	} 
	/**
	 * Get {@link domain.Hotel Hotel}s from the database that are owned by the guest with the id provided
	 * @param name id of the owner
	 * @return a list of hotels with the owner of the parameter
	 */
    public List<Hotel> getByOwner(String owner) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Query<Hotel> q = pm.newQuery(Hotel.class, "ownerDni == :owner");
        List<Hotel> resultList = (List<Hotel>) q.setParameters(owner).executeList();
        pm.close();
        return resultList;
    }
	
    /**
	 * Get a {@link domain.Hotel Hotel} from the database by it's id
	 * @param param id of the hotel
	 * @return the hotel or null if it doesn't exist
	 */
	@Override
	public Hotel find(String param) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
		Hotel result = null;
		Hotel detatched = null;
		try {
			tx.begin();
		    Query<Hotel> q = pm.newQuery(Hotel.class, "id == "+param.replace("'", "''"));  // Se crea una consulta JDO para la clase Hotel, filtrando el nombre 
		    q.setParameters(param.toLowerCase());
		    q.setUnique(true);
		    result = (Hotel) q.execute();
		    detatched = pm.detachCopy(result);
		    final Hotel _detatched = detatched;
		    LinkedList<Room> detatchedRooms = new LinkedList<>();
		    result.getRooms().forEach(v-> {
		    	Room r = pm.detachCopy(v);
		    	r.setHotel(_detatched);
		    	LinkedList<Booking> detatchedBookings = new LinkedList<>();
		    	v.getBookings().forEach(b->detatchedBookings.add(pm.detachCopy(b)));
		    	detatchedBookings.forEach(b->b.setRoom(r));
		    	r.setBookings(detatchedBookings);
		    	detatchedRooms.add(r);
		    });
		    detatched.setRooms(detatchedRooms);
		    tx.commit();
		    detatched.getRooms().forEach(v->System.out.println(v.getBookings()));
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("$ Error finding a hotel: " + ex.getMessage());
		}
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
		pm.close();
		return detatched;
	}
	
}

