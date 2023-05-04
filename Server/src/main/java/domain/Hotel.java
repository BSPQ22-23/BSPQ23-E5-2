package domain;

import java.util.ArrayList;
import java.util.List;

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
		return "Hotel [id=" + id + ", name=" + name + ", city=" + city + ", ownerDni=" + ownerDni
				+ ", rooms=" + rooms + ", services=" + services + "]";
	}

	@PrimaryKey
	@Persistent
	private int id;
    private String name;
    private String city;
    private String ownerDni;
    @NotPersistent
    private Guest owner;
    @Join
    @Persistent(mappedBy="hotel", dependentElement="true", defaultFetchGroup="true")
    private List<Room> rooms = new ArrayList<>();
    @Join
    @Persistent(dependentElement="true", defaultFetchGroup="true")
    private List<Service> services = new ArrayList<>();
    
    public static Hotel fromJSON(JSONObject obj) {
    	Hotel result = new Hotel(
    			APIUtils.decode(obj.getString("name")),
    			APIUtils.decode(obj.getString("city")),
    			obj.keySet().contains("owner")?Guest.fromJSON(obj.getJSONObject("owner")):null
    		);
    	if(obj.keySet().contains("rooms"))
    		for(Object o : obj.getJSONArray("rooms"))    result.addRoom(Room.fromJSON((JSONObject)o));
    	if(obj.keySet().contains("services"))
    		for(Object o : obj.getJSONArray("services")) result.addService(Service.fromJSON((JSONObject)o));
    	return result;
    	
    }

	public Hotel(String name, String city, Guest owner) {
        this.name = name;
        this.city = city;
        this.owner = owner;
        this.ownerDni = owner.getDni();
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
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
		this.ownerDni = owner.getDni();
	}
	
	public boolean equals(Object o) {
		return o instanceof Hotel && ((Hotel)o).getId() == id;
	}
}

