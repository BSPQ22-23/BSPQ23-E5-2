package domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import remote.APIUtils;

	public class Hotel {
		private int id;
	    private String name;
	    private String city;
	    private List<Room> rooms;
	    private List<Service> services;
	    private transient BufferedImage icon; //Transported independently
	    private transient String iconFormat;

    public static Hotel fromJSON(JSONObject obj) {
    	Hotel result = new Hotel(
    			obj.getInt("id"),
    			APIUtils.decode(obj.getString("name")),
    			APIUtils.decode(obj.getString("city"))
    		);
    	if(obj.keySet().contains("rooms"))
    		for(Object o : obj.getJSONArray("rooms"))    result.addRoom(Room.fromJSON((JSONObject)o));
    	if(obj.keySet().contains("services"))
    		for(Object o : obj.getJSONArray("services")) result.addService(Service.fromJSON((JSONObject)o));
    	return result;
    }
    public Hotel(int id, String name, String city) {
    	this.id = id;
        this.name = name;
        this.city = city;
        this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
        this.icon = null;
        this.iconFormat = null;
        
    }
    public Hotel(int id, String name, String city, BufferedImage icon, String iconFormat) {
    	this.id = id;
        this.name = name;
        this.city = city;
        this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
        this.icon = icon;
        this.iconFormat = iconFormat;
        
    }
    
    public BufferedImage getIcon() {
    	return icon;
    }
    
    public String getIconFormat() {
    	return iconFormat;
    }
    
    public void setIcon(BufferedImage icon, String iconFormat) {
    	this.icon = icon;
    	this.iconFormat = iconFormat;
    }
    
    public void setIcon(byte[] pixelArray, String format) throws IOException {
    	this.icon = ImageIO.read(new ByteArrayInputStream(pixelArray));
    	this.iconFormat = format;
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
    
}

