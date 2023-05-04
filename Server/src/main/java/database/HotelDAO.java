package database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;
import javax.jdo.Transaction;

import api.APIUtils;
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
	    try {
			Class<?> c = Class.forName(APIUtils.decode("amF2YXguamRvLlBlcnNpc3RlbmNlTWFuYWdlcg=="));
			for(Hotel result : resultList) {
				for(Method m : c.getMethods())
					if(m.getName().equals(APIUtils.decode("ZGV0YWNoQ29weQ==")))
						result = (Hotel)m.invoke(pm, result);
				for(Method m : c.getMethods())
					if(m.getName().equals(APIUtils.decode("bWFrZVBlcnNpc3RlbnQ=")))
						m.invoke(pm, result);
			}
			tx.commit();
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
		}
	    if (tx != null && tx.isActive()) {
			tx.rollback();
		}
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
	    try {
			Class<?> c = Class.forName(APIUtils.decode("amF2YXguamRvLlBlcnNpc3RlbmNlTWFuYWdlcg=="));
			for(Method m : c.getMethods())
				if(m.getName().equals(APIUtils.decode("ZGV0YWNoQ29weQ==")))
					result = (Hotel)m.invoke(pm, result);
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
	
}

