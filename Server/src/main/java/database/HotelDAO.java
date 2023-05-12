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

public class HotelDAO  extends DataAccessObjectBase implements IDataAccessObject<Hotel> {
	private static final HotelDAO INSTANCE = new HotelDAO();
	
	public static HotelDAO getInstance() {
		return INSTANCE;
	}
	/*
	@Override
	public void save(Hotel object) {
		// TODO Auto-generated method stub
		HotelDAO.getInstance().save(object);
		save(object);
	}
	*/
	@Override
	public boolean save(Hotel object) {
		return saveObject(object);
	}
	/**
	 * Delete a hotel
	 */
	@Override
	public void delete(Hotel object) {
		deleteObject(object);
	}
	/**
	 * Method that get a list of all the hotels
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
	 * Method that get a list Hotel by the name
	 * @param name
	 * @return a list of Hotel Names
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
    
    public List<Hotel> getByOwner(String owner) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Query<Hotel> q = pm.newQuery(Hotel.class, "ownerDni == :owner");
        List<Hotel> resultList = (List<Hotel>) q.setParameters(owner).executeList();
        pm.close();
        return resultList;
    }
	

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
		    //detatched.getRooms().forEach(v -> v.getBookings().forEach(b -> b.setRoom(pm.detachCopy(b.getRoom()))));
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

