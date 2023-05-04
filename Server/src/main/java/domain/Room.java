package domain;

import java.util.List;

import org.json.JSONObject;

import api.APIUtils;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable = "true")
public class Room {

    @PrimaryKey
    @Persistent
    private int roomNumber;
    private String type;
    private int numMaxGuests;
    private int spaceInMeters;
    private Hotel hotel; 
    private float prize;
    @Join
    private List<Booking> bookings;

    public static Room fromJSON(JSONObject object) {
        Room res = new Room(
                object.getInt("roomNumber"),
                APIUtils.decode(object.getString("type")),
                object.getInt("numMaxGuests"),
                object.getInt("spaceInMeters"),
                object.getFloat("prize"),
                object.keySet().contains("hotel") ? Hotel.fromJSON(object.getJSONObject("hotel")) : null
        );
        if (object.keySet().contains("bookings")) {
            for (Object o : object.getJSONArray("bookings")) {
                res.addBooking(Booking.fromJSON((JSONObject) o));
            }
        }
        return res;
    }

    public Room(int roomNumber, String type, int numMaxGuests, int spaceInMeters, float prize, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.numMaxGuests = numMaxGuests;
        this.spaceInMeters = spaceInMeters;
        this.hotel = hotel;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumMaxGuests() {
        return numMaxGuests;
    }

    public void setNumMaxGuests(int numMaxGuests) {
        this.numMaxGuests = numMaxGuests;
    }

    public int getSpaceInMeters() {
        return spaceInMeters;
    }

    public void setSpaceInMeters(int spaceInMeters) {
        this.spaceInMeters = spaceInMeters;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

