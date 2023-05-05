package database;

import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;
import javax.jdo.Transaction;

import domain.Guest;

/**
 * DAO for Hotel class
 * @author maitanegarcia
 *
 */

import domain.Hotel;

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
	public void save(Hotel object) {
		saveObject(object);
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
		Query<Hotel> q = pm.newQuery(Hotel.class);
		List<Hotel> ListHotel = (List<Hotel>) q.executeList();
		return ListHotel;
		
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
	    Query<Hotel> q = pm.newQuery(Hotel.class,"name.toLowerCase().matches(\".*\"+:name.toLowerCase()+\".*\")");
	    q.setUnique(false);
	    List<Hotel> resultList =  (List<Hotel>) q.execute(name);
		tx.commit();
	    if (tx != null && tx.isActive()) {
			tx.rollback();
		}
	    pm.close();
	    return resultList;
	} 
    
    public List<Hotel> getByOwner(Guest owner) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Query<Hotel> q = pm.newQuery(Hotel.class, "ownerDni == '"+owner.getDni().replace("'", "''")+"'");
        List<Hotel> resultList = (List<Hotel>) q.execute();
        return resultList;
    }
	

	@Override
	public Hotel find(String param) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
		tx.begin();
	    Query<Hotel> q = pm.newQuery(Hotel.class, "id == "+param.replace("'", "''"));  // Se crea una consulta JDO para la clase Hotel, filtrando el nombre 
	    q.setParameters(param.toLowerCase());
	    q.setUnique(true);
	    Hotel result = (Hotel) q.execute();  
	    tx.commit();
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
		pm.close();
		return result;
	}
	
}

