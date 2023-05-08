package database;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import domain.Booking;
import domain.Guest;
import domain.Room;
/**
 * DAO for Booking class
 * @author maitanegarcia
 *
 */

public class BookingDAO extends DataAccessObjectBase implements IDataAccessObject<Booking> {

	private static BookingDAO instance = new BookingDAO();	
	
	private BookingDAO() { }
	
	public static BookingDAO getInstance() {
		return instance;
	}	
	/**
	 * This method saves a reservation
	 */
	@Override
	public void save(Booking object) {
		saveObject(object);
		
	}
/**
 * Thus method deletes a reservation
 */
	@Override
	public void delete(Booking object) {
		super.deleteObject(object);
		
	}
	
/**
 * This method gets a list of all the reservations 
 */
	@Override
	public List<Booking> getAll() {
		 PersistenceManager pm = pmf.getPersistenceManager();
		    Transaction tx = pm.currentTransaction();

		    List<Booking> result = null;

		    try {
		        tx.begin();

		        Query<Booking> q = pm.newQuery(Booking.class);
		        result = (List<Booking>) q.executeList();

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
	public Booking find(String param) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();

	    Booking result = null;

	    try {
	        tx.begin();

	        Query<Booking> q = pm.newQuery(Booking.class);
	        q.setFilter("id == userParam");
	        q.declareParameters("String userParam");
	        q.setUnique(true);
	        result = (Booking) q.execute(param);

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
	public List<Booking> getByAuthor(Guest author){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    List<Booking> result = null;
	    try {
	        tx.begin();
	        Query<Booking> q = pm.newQuery(Booking.class, "author == authorDNI");
	        q.declareParameters("domain.Guest authorDNI");
	        result = q.setParameters(author).executeList();
	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("Error: " + ex.getMessage());
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }

	        pm.close();
	    }
	    return result;
	}	
	
	public boolean hasReservationInRoomOnDate(Room room, Date date) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();

	    boolean hasReservation = false;

	    try {
	        tx.begin();

	        Query<Booking> q = pm.newQuery(Booking.class);
	        q.setFilter("room == roomParam && checkinDate <= dateParam && checkoutDate >= dateParam");
	        q.declareParameters("Room roomParam, java.util.Date dateParam");
	        q.setUnique(true);
	        Booking reservation = (Booking) q.execute(room, date);

	        if (reservation != null) {
	            hasReservation = true;
	        }

	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("Error: " + ex.getMessage());
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }

	        pm.close();
	    }

	    return hasReservation;
	}


	
	
}
