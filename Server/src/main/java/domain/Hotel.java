package domain;

import java.util.LinkedList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.json.JSONObject;

import api.APIUtils;
import database.GuestDAO;

@PersistenceCapable(detachable="true")
public class Hotel {
	
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", city=" + city + ", info=" + info 
				 + ", services=" + services + "]";
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private int id;
    private String name;
    private String city;
    private String info;
    private String ownerDni;
    @NotPersistent
    private Guest owner;
    @Join
    @Persistent(mappedBy="hotel", dependentElement="true", defaultFetchGroup="true")
    private LinkedList<Room> rooms;
    @Join
    @Persistent(dependentElement="true", defaultFetchGroup="true")
    private LinkedList<Service> services;
    
    public static Hotel fromJSON(JSONObject obj) {
    	Hotel result = new Hotel(
    			APIUtils.decode(obj.getString("name")),
    			APIUtils.decode(obj.getString("city")),
    			APIUtils.decode(obj.getString("info")),
    			obj.keySet().contains("owner")?Guest.fromJSON(obj.getJSONObject("owner")):null
    		);
    	if(obj.keySet().contains("rooms"))
    		for(Object o : obj.getJSONArray("rooms"))    result.addRoom(Room.fromJSON((JSONObject)o));
    	if(obj.keySet().contains("services"))
    		for(Object o : obj.getJSONArray("services")) result.addService(Service.fromJSON((JSONObject)o));
    	if(obj.keySet().contains("id"))
    		result.setId(obj.getInt("id"));
    	return result;
    	
    }
    public Hotel(Hotel h) {
    	this.name = h.name;
    	this.city = h.city;
    	this.info = h.info;
    	this.id = h.id;
    	this.owner = h.owner;
    	this.ownerDni = h.ownerDni;
    	this.rooms = h.rooms;
    	this.services = h.services;
    }

	public Hotel(String name, String city, String info, Guest owner) {
        this.name = name;
        this.city = city;
        this.info = info;
        this.owner = owner;
        ownerDni = owner == null? null : owner.getDni();
        rooms = new LinkedList<>();
        services = new LinkedList<>();
    }

	public void setId(int id) {
		this.id = id;
	}
    public int getId() {
		return id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LinkedList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(LinkedList<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public LinkedList<Service> getServices() {
        return services;
    }

    public void setServices(LinkedList<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(Service service) {
        services.remove(service);
    }
    
    public Guest getOwner() {
    	if(owner == null && ownerDni != null)
    		owner = GuestDAO.getInstance().find(ownerDni);
		return owner;
	}

	public void setOwner(Guest owner) {
		this.owner = owner;
		ownerDni = owner == null? null : owner.getDni();
	}
	
	public boolean equals(Object o) {
		return o instanceof Hotel && ((Hotel)o).getId() == id;
	}
}

