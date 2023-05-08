package domain;

import org.json.JSONObject;

import api.APIUtils;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class Service {

    private String name;
    private String description;
    private double price;

    public static Service fromJSON(JSONObject object) {
        return new Service(
                APIUtils.decode(object.getString("name")),
                APIUtils.decode(object.getString("description")),
                object.getDouble("price")
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
