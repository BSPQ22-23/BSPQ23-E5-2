package database;

import java.util.List;

/**
 * Base for DAO methods
 * @param <DomainObject>
 */
public interface IDataAccessObject<DomainObject> {
	public boolean save(DomainObject object);
	public void delete(DomainObject object);
	public List<DomainObject> getAll();
	public DomainObject find(String param);
	
	
}
