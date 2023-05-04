package domain;

import org.json.JSONObject;

import remote.APIUtils;


public class Service {
    private String name;
    private String description;
    private double price;

    public static Service fromJSON(JSONObject o) {
    	return new Service(
    			APIUtils.decode(o.getString("name")), 
    			APIUtils.decode(o.getString("description")),
    			o.getDouble("price")
    		);
    }
    
    public Service(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
