package database;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDOHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
		PrintStream out = new PrintStream(new FileOutputStream("output.log"));
		System.setOut(out);
		System.setErr(out);
		guest = new Guest("gorka", "kk", "23232323A", 67, "Vitoria-Gasteiz");
		GuestDAO.getInstance().save(guest);
		hotel = new Hotel("Hotel Lakua", "Vitoria-Gasteiz", "Increible desayuno", guest);
		room = new Room(100, "Single", 0, 0, 0, hotel);
		hotel.addRoom(room);
		HotelDAO.getInstance().save(hotel);
		
	}
	@Before
	public void setup() {
		LinkedList<Guest> g = new LinkedList<>();
		g.add(guest);
		bookingDAO = BookingDAO.getInstance();
		booking = new Booking(
				new Date(System.currentTimeMillis() - 24000000), 
				new Date(System.currentTimeMillis() + 2400000000l), 
				room, 
				g, 
				guest);
		System.out.println("hotel: " + JDOHelper.getObjectState(hotel) + " " + hotel);
		System.out.println("room: "  + JDOHelper.getObjectState(room)  + " " + room );
		System.out.println("guest: " + JDOHelper.getObjectState(guest) + " " + guest);
		System.out.println(g);
		BookingDAO.getInstance().save(booking);
		System.out.println(JDOHelper.getObjectState(booking));
//		System.out.println(booking);
	}

	@Test
	public void getByAuthor() {
		List<Booking> bookings = bookingDAO.getByAuthor(guest);
		assertEquals(1, bookings.size());
		assertTrue(bookings.contains(booking));
	}
	@Test
	public void testGetAll() {
		List<Booking> listBooking = bookingDAO.getAll();
		assertTrue(listBooking.contains(booking));
	}
	
	@Test
	public void testFind() {
        Booking findBooking = bookingDAO.find(""+booking.getId());
        assertEquals("Find", guest.getName(), findBooking.getAuthor().getName());
		
	}
	@Test
	public void testHasReservationInRoomOnDate() {
		Date d = new Date(System.currentTimeMillis());		
		
		assertTrue("Reservation in room on date", bookingDAO.hasReservationInRoomOnDate(room, d));
	}
	
	@After
	public void tearDown() throws Exception {
		bookingDAO.delete(booking);
		List<Booking> listBooking = bookingDAO.getAll();
		assertFalse(listBooking.contains(booking));
	}
	
	@AfterClass
	public static void end() {
		HotelDAO.getInstance().delete(hotel);
		GuestDAO.getInstance().delete(guest);
	}

}
