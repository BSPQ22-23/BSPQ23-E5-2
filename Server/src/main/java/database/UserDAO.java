package database;

import java.util.List;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import domain.User;

/**
 * Class to allow the access to {@link domain.User User} objects in the database
 *
 */
public class UserDAO extends DataAccessObjectBase implements IDataAccessObject<User>{
	private static final UserDAO INSTANCE = new UserDAO();
	/**
	 * Get the DAO instance for {@link domain.User User}s
	 * @return
	 */
	public static UserDAO getInstance() {
		return INSTANCE;
	}
	private UserDAO() {}
	/**
	 * Upload or update a {@link domain.User User} to the database
	 * @param object user to store/update
	 * @return true if the transaction was successful
	 */
	@Override
	public boolean save(User object) {
		if(!GuestDAO.getInstance().exists(object.getLegalInfo().getDni()))
			GuestDAO.getInstance().save(object.getLegalInfo());
		return saveObject(object);
		
	}
	/**
	 * Delete a {@link domain.User User} that has been retrieved from the database
	 * @param object detatched object to delete
	 */
	@Override
	public void delete(User object) {
		GuestDAO.getInstance().delete(object.getLegalInfo());
		deleteObject(object);
		
	}
	/**
	 * Get all {@link domain.User User}s from the database
	 * @return the list of users in the database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {				
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<User> q = pm.newQuery(User.class);
		return (List<User>)q.execute(20);
	}
	/**
	 * Get a {@link domain.User User} from the database by it's id
	 * @param param id of the user
	 * @return the user or null if it doesn't exist
	 */
	@Override
	public User find(String dni) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		tx.begin();
		
		Query<User> q = pm.newQuery(User.class, "nick == '" + dni.replace("'", "''")+"'");
		q.setUnique(true);
		User result =  (User)q.execute();
		tx.commit();
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}

		pm.close();
		return result;
	}
	/**
	 * Get a {@link domain.User User} from the database by it's authentication data
	 * @param user nick of the user
	 * @param password encrypted password using {@link domain.PasswordEncryption#encryptPassword(String) PasswordEncryption#encryptPassword}
	 * @return the user or null if it doesn't exist
	 */
	public User find(String user, String password) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<User> q = pm.newQuery(User.class, "nick == '" + user.replace("'", "''")+"' && password == '" + password.replace("'", "''")+"'");
		q.setUnique(true);
		return (User)q.execute(20);
	}
	/**
	 * Checks if a username it's already taken
	 * @param user nick to search for
	 * @return true if it already exists in the database
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(String user) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<User> q = pm.newQuery(User.class, "nick == '" + user.replace("'", "''")+"'");
		return ((List<User>)q.execute()).size() != 0;
	}

}
