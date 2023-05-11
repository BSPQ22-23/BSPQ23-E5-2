package database;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import api.APIUtils;
import domain.Booking;
import domain.Guest;
import domain.Hotel;
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
	    Class<?> c = null;
		Class<?> e = null;
		Class<?> f = null;
		try {
			c = Class.forName(APIUtils.decode("amF2YXguamRvLlBlcnNpc3RlbmNlTWFuYWdlcg=="));
			e = Class.forName(APIUtils.decode("ZG9tYWluLkJvb2tpbmc="));
			f = Class.forName(APIUtils.decode("ZG9tYWluLlJvb20="));
		} catch (ClassNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	    Booking result = null;
	    Object d = null;
	    try {
	    	Method m1 = e.getMethod(APIUtils.decode("Z2V0Um9vbQ=="));
	        Method m2 = e.getMethod(APIUtils.decode("c2V0Um9vbQ=="), Room.class);
	        Method m3 = c.getMethod(APIUtils.decode("ZGV0YWNoQ29weQ=="), Object.class);
	        Method m6 = f.getMethod(APIUtils.decode("Z2V0SG90ZWw="));
	        Method m7 = f.getMethod(APIUtils.decode("c2V0SG90ZWw="), Hotel.class);
	        tx.begin();

	        Query<Booking> q = pm.newQuery(Booking.class);
	        q.setFilter("id == userParam");
	        q.declareParameters("String userParam");
	        q.setUnique(true);
	        result = (Booking) q.execute(param);
	        d = m3.invoke(pm, result);
	        m2.invoke(d, m3.invoke(pm, m1.invoke(result)));
	        m7.invoke(m1.invoke(d), m3.invoke(pm, m6.invoke(m1.invoke(result))));
	        
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
	    return (Booking) d;
	}
	public List<Booking> getByAuthor(Guest author){
		PersistenceManager pm = pmf.getPersistenceManager();
		Class<?> c = null;
		Class<?> d = null;
		Class<?> e = null;
		Class<?> f = null;
		try {
			c = Class.forName(APIUtils.decode("amF2YXguamRvLlBlcnNpc3RlbmNlTWFuYWdlcg=="));
			d = Class.forName(APIUtils.decode("amF2YS51dGlsLkxpc3Q="));
			e = Class.forName(APIUtils.decode("ZG9tYWluLkJvb2tpbmc="));
			f = Class.forName(APIUtils.decode("ZG9tYWluLlJvb20="));
		} catch (ClassNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	    Transaction tx = pm.currentTransaction();
		List<Booking> g = new ArrayList<>();
	    try {
	        tx.begin();
	        Query<Booking> q = pm.newQuery(Booking.class, "author == authorDNI");
	        q.declareParameters("domain.Guest authorDNI");
	        List<Booking> result = q.setParameters(author).executeList();
	        Method m1 = d.getMethod(APIUtils.decode("YWRk"), Object.class);
	        Method m2 = d.getMethod(APIUtils.decode("Z2V0"), int.class);
	        Method m3 = c.getMethod(APIUtils.decode("ZGV0YWNoQ29weQ=="), Object.class);
	        Method m4 = e.getMethod(APIUtils.decode("Z2V0Um9vbQ=="));
	        Method m5 = e.getMethod(APIUtils.decode("c2V0Um9vbQ=="), Room.class);
	        Method m6 = f.getMethod(APIUtils.decode("Z2V0SG90ZWw="));
	        Method m7 = f.getMethod(APIUtils.decode("c2V0SG90ZWw="), Hotel.class);
	        for(int i = 0; i < result.size(); i++) {
	        	m1.invoke(g, m3.invoke(pm, m2.invoke(result, i)));
	        	m5.invoke(m2.invoke(g, i), m3.invoke(pm, m4.invoke(m2.invoke(result, i))));
	        	m7.invoke(m4.invoke(m2.invoke(g, i)), m3.invoke(pm, m6.invoke(m4.invoke(m2.invoke(result, i)))));
	        }
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
	    return g;
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
