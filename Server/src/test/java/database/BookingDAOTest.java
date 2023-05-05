package database;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import domain.Booking;
import domain.Guest;
import domain.Hotel;
import domain.Room;

public class BookingDAOTest {
	private BookingDAO bookingDAO;
	private Booking booking;
	private static Room room;
	private static Guest guest;
	private static Hotel hotel;
	
	

	@BeforeClass
	public static void start() throws Exception {
		guest = new Guest("gorka", "kk", "23232323A", 67, "Vitoria-Gasteiz");
		if(!GuestDAO.getInstance().exists(guest.getDni()))
			GuestDAO.getInstance().save(guest);
		else
			guest = GuestDAO.getInstance().find(guest.getDni());
		hotel = new Hotel("Hotel Lakua", "Vitoria-Gasteiz", guest);
		room = new Room(100, "Single", 0, 0, 0, hotel);
		hotel.addRoom(room);
		if(HotelDAO.getInstance().getByOwner(guest).size() == 0)
			HotelDAO.getInstance().save(hotel);
		else
			hotel = HotelDAO.getInstance().getByOwner(guest).get(0);
		
	}
	@Before
	public void setup() {
		bookingDAO = BookingDAO.getInstance();
		booking = new Booking(
				new Date(System.currentTimeMillis() - 24000000), 
				new Date(System.currentTimeMillis() + 2400000000l), 
				room, 
				List.of(guest), 
				guest);
		BookingDAO.getInstance().save(booking);
	}

	@After
	public void tearDown() throws Exception {
		bookingDAO.delete(booking);
		List<Booking> listBooking = bookingDAO.getAll();
		assertFalse(listBooking.contains(booking));
	}
	
	@Test
	public void testGetAll() {
		List<Booking> listBooking = bookingDAO.getAll();
		assertTrue(listBooking.contains(booking));
	}
	
	@AfterClass
	public static void end() {
		GuestDAO.getInstance().delete(guest);
	}

	@Test
	public void testFind() {
        Booking findBooking = bookingDAO.find(""+booking.getId());
        assertEquals(guest.getName(), findBooking.getAuthor().getName());
		
	}
	
	@Test
	public void testHasReservationInRoomOnDate() {
		Date d = new Date(System.currentTimeMillis());		
		
		assertTrue("Reservation in room on date", bookingDAO.hasReservationInRoomOnDate(room, d));
		
	}

}
