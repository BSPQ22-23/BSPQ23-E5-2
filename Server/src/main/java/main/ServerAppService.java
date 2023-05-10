package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import database.BookingDAO;
import database.HotelDAO;
import database.UserDAO;
import domain.Booking;
import domain.Hotel;
import domain.Room;
import domain.User;

public class ServerAppService {
	/**
	 * Checks if a username it's not already taken and adds it to the database if possible
	 *  @param username Username of the registering account
	 *  @param password Password of the registering account
	 * @param string 
	 *  @return The account token
	 *  @throws IllegalArgumentException if there's a missing argument or an illegal value is sent
	 */
	public static String register(User user) {
		System.out.println("Register user: " +user.toString());
		if(user.getLegalInfo().getAge() == 0 || user.getLegalInfo().getCityOfProvenance() == "" || user.getLegalInfo().getDni() == "" || user.getLegalInfo().getName() == "" || user.getNick() == "" || user.getPassword() == "" || user.getLegalInfo().getSurname() == "") {
			System.out.println("Invalid field");
			throw new IllegalArgumentException("You must fill all fields");
		}else {
			if(UserDAO.getInstance().exists(user.getNick()))
				throw new IllegalArgumentException("User already exists");
			System.out.println("Registration authorized");
			UserDAO.getInstance().save(user);
			return Server.createSession(user);//TODO Change username for the User class instance
		}
	}
	/**
	 * Checks if a user can be logged in
	 * @param username Username of the logging account
	 * @param password Password of the logging account
	 * @return The account token or null if the user credentials aren't correct
	 */
	public static String login(String username, String password) {
		System.out.println("Login user: username= " +username+ " password= " +password);
		return Server.createSession(UserDAO.getInstance().find(username, password));//TODO Create the token if the user it's succesfully logged in
	}
	/**
	 * Adds a reservation to the database if all information it's correct
	 * @param author Person generating the reservation
	 * @param reservation Reservation to be made
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean reservationCreate(Booking reservation) {
		for(Date d = (Date)reservation.getCheckinDate().clone(); !d.after(reservation.getCheckoutDate()); d.setDate(d.getDate() + 1))
			if(BookingDAO.getInstance().hasReservationInRoomOnDate(reservation.getRoom(), d))
				return false;
		BookingDAO.getInstance().save(reservation);
		return true;
	}
	
	public static List<Booking> getReservationsByUser(User user){
		return BookingDAO.getInstance().getByAuthor(user.getLegalInfo());
	}
	
	public static List<Booking> getReservationsByHotel(User user, String hotelId){
		Hotel h = HotelDAO.getInstance().find(hotelId);
		List<Booking> bookings = new LinkedList<>();
		h.getRooms().forEach(v -> bookings.addAll(v.getBookings()));
		return bookings;
	}
	
	public static Booking getReservationById(String bookingID) {
		return BookingDAO.getInstance().find(bookingID);
	}
	public static boolean deleteReservation(User u, int bookingId) {
		Booking b = BookingDAO.getInstance().find(Integer.toString(bookingId));
		if(!b.getAuthor().equals(u.getLegalInfo()))
			return false;
		BookingDAO.getInstance().delete(b);
		return true;
	}
	public static boolean editReservation(User u, Booking b) {
		Booking toUpdate = BookingDAO.getInstance().find(Integer.toString(b.getId()));
		toUpdate.setCheckinDate(b.getCheckinDate());
		toUpdate.setCheckoutDate(b.getCheckoutDate());
		toUpdate.setGuests(b.getGuests());
		BookingDAO.getInstance().save(b);
		return false;
	}
	
	public static boolean createHotel(Hotel h) {
		if(h.getName().equals("") || h.getCity().equals("") || h.getRooms().size() == 0)
			return false;
		else
			HotelDAO.getInstance().save(h);
		return true;
	}
	public static List<Hotel> getHotels() {
		List<Hotel> h = HotelDAO.getInstance().getAll();
		List<Hotel> transientList = new ArrayList<>(h.size());
		h.forEach(v-> {
			Hotel h1 = new Hotel(v);
			List<Room> transientRooms = new ArrayList<>();
			h1.getRooms().forEach(w->{
				Room r = new Room(w);
				r.setHotel(null);//Avoid stack overflow
				r.setBookings(null);
			});//Do not ship bookings
			h1.setRooms(transientRooms);
			h1.setOwner(null);//No need to know private info of owner
			transientList.add(h1);
		});
		return transientList;
	}
	public static List<Hotel> getHotels(String query) {
		List<Hotel> h = HotelDAO.getInstance().getByName(query);
		h.forEach(v-> {
			v.getRooms().forEach(w->w.getBookings().clear());//Do not ship bookings
			v.setOwner(null);//No need to know private info of owner
		});
		return h;
	}
}
