package database;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import domain.Guest;

/**
 * Class to allow the access to {@link domain.Guest Guest} objects in the database
 *
 */
public class GuestDAO extends DataAccessObjectBase implements IDataAccessObject<Guest>{
	private static final GuestDAO INSTANCE = new GuestDAO();
	/**
	 * Get the DAO instance for {@link domain.Guest Guest}s
	 * @return
	 */
	public static GuestDAO getInstance() {
		return INSTANCE;
	}
	private GuestDAO() {}
	/**
	 * Upload or update a {@link domain.Guest Guest} to the database
	 * @param object guest to store/update
	 * @return true if the transaction was successful
	 */
	@Override
	public boolean save(Guest object) {
		return saveObject(object);
		
	}
	
	/**
	 * Delete a {@link domain.Guest Guest} that has been retrieved from the database
	 * @param object detatched object to delete
	 */
	@Override
	public void delete(Guest object) {
		deleteObject(object);
		
	}

	/**
	 * Get all {@link domain.Guest Guest}s from the database
	 * @return the list of guests in the database
	 */
	@Override
	public List<Guest> getAll() {				
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<Guest> q = pm.newQuery(Guest.class);
		return q.executeList();
	}

	/**
	 * Get a {@link domain.Guest Guest} from the database by it's dni
	 * @param param dni of the guest
	 * @return the guest or null if it doesn't exist
	 */
	@Override
	public Guest find(String dni) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		tx.begin();
		Query<Guest> q = pm.newQuery(Guest.class, "dni == '" + dni.replace("'", "''")+"'");
		q.setUnique(true);
		Guest result =  (Guest)q.execute();
		tx.commit();
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}

		pm.close();
		return result;
	}
	/**
	 * Check if a {@link domain.Guest Guest} exists in the database by it's dni
	 * @param param dni of the guest
	 * @return true if the guest exists
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(String dni) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<Guest> q = pm.newQuery(Guest.class, "dni == '" + dni.replace("'", "''")+"'");
		return ((List<Guest>)q.execute()).size() != 0;
	}
	
}
