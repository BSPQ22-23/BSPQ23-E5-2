package database;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import domain.Booking;
import domain.Room;
/**
 * DAO for Booking class
 * @author maitanegarcia
 *
 */

public class BookingDAO extends DataAccessObjectBase implements IDataAccessObject<Booking> {

	private static BookingDAO instance;	
	
	private BookingDAO() { }
	
	public static BookingDAO getInstance() {
		if (instance == null) {
			instance = new BookingDAO();
		}		
		
		return instance;
	}	
	/**
	 * This method saves a reservation
	 */
	@Override
	public void save(Booking object) {
		// TODO Auto-generated method stub
		super.saveObject(object);
		
	}
/**
 * Thus method deletes a reservation
 */
	@Override
	public void delete(Booking object) {
		//object.getRoom().getBookings().remove(object);
		super.deleteObject(object);
		
	}
	
/**
 * This method gets a list of all the reservations 
 */
	@Override
	public List<Booking> getAll() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<Booking> q = pm.newQuery(Booking.class);
		List<Booking> ListBooking = (List<Booking>) q.executeList();
		pm.close();
		return ListBooking;
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
