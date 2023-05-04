package database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import api.APIUtils;
import domain.User;

/**
 * DAO for User class
 * @author a-rasines
 *
 */
public class UserDAO extends DataAccessObjectBase implements IDataAccessObject<User>{
	private static final UserDAO INSTANCE = new UserDAO();
	public static UserDAO getInstance() {
		return INSTANCE;
	}
	private UserDAO() {}
	@Override
	public void save(User object) {
		if(!GuestDAO.getInstance().exists(object.getLegalInfo().getDni()))
			GuestDAO.getInstance().save(object.getLegalInfo());
		saveObject(object);
		
	}

	@Override
	public void delete(User object) {
		GuestDAO.getInstance().delete(object.getLegalInfo());
		deleteObject(object);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {				
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<User> q = pm.newQuery(User.class);
		return (List<User>)q.execute(20);
	}

	@Override
	public User find(String dni) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		tx.begin();
		
		Query<User> q = pm.newQuery(User.class, "nick == '" + dni.replace("'", "''")+"'");
		q.setUnique(true);
		User result =  (User)q.execute();
		try {
			Class<?> c = Class.forName(APIUtils.decode("amF2YXguamRvLlBlcnNpc3RlbmNlTWFuYWdlcg=="));
			for(Method m : c.getMethods())
				if(m.getName().equals(APIUtils.decode("ZGV0YWNoQ29weQ==")))
					result = (User)m.invoke(pm, result);
			for(Method m : c.getMethods())
				if(m.getName().equals(APIUtils.decode("bWFrZVBlcnNpc3RlbnQ=")))
					m.invoke(pm, result);
			tx.commit();
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
		}
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}

		pm.close();
		return result;
	}
	public User find(String user, String password) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<User> q = pm.newQuery(User.class, "nick == '" + user.replace("'", "''")+"' && password == '" + password.replace("'", "''")+"'");
		q.setUnique(true);
		return (User)q.execute(20);
	}
	@SuppressWarnings("unchecked")
	public boolean exists(String user) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<User> q = pm.newQuery(User.class, "nick == '" + user.replace("'", "''")+"'");
		return ((List<User>)q.execute()).size() != 0;
	}

}
