package domain;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingTest {
	

    @Test
    public void testAddGuest() {
        Guest guest = new Guest("testGuest", null, null, 0, null);
        List<Guest> guests = new ArrayList<>();
        Booking booking = new Booking(0, new Date(), new Date(), new Room(0, null, 0, 0, 0, null), guests);
        booking.addGuest(guest);
        assertTrue(booking.getGuests().contains(guest));
    }

    @Test
    public void testRemoveGuest() {
        Guest guest = new Guest("testGuest", null, null, 0, null);
        List<Guest> guests = new ArrayList<>();
        guests.add(guest);
        Booking booking = new Booking(0, new Date(), new Date(), new Room(0, null, 0, 0, 0, null), guests);
        booking.removeGuest(guest);
        assertFalse(booking.getGuests().contains(guest));
    }
    
    @Test
    public void testGetId() {
        int id = 0;
        Booking booking = new Booking(id, new Date(), new Date(), new Room(id, null, id, id, 0, null), new ArrayList<Guest>());
        assertEquals(id, booking.getId());
    }

    @Test
    public void testSetId() {
        int id = 0;
        Booking booking = new Booking(1, new Date(), new Date(), new Room(id, null, id, id, 0, null), new ArrayList<Guest>());
        booking.setId(id);
        assertEquals(id, booking.getId());
    }

    @Test
    public void testGetCheckinDate() {
        Date checkinDate = new Date();
        Booking booking = new Booking(0, checkinDate, new Date(), new Room(0, null, 0, 0, 0, null), new ArrayList<Guest>());
        assertEquals(checkinDate, booking.getCheckinDate());
    }

    @Test
    public void testSetCheckinDate() {
        Date checkinDate = new Date();
        Booking booking = new Booking(0, new Date(), new Date(), new Room(0, null, 0, 0, 0, null), new ArrayList<Guest>());
        booking.setCheckinDate(checkinDate);
        assertEquals(checkinDate, booking.getCheckinDate());
    }

    @Test
    public void testGetCheckoutDate() {
        Date checkoutDate = new Date();
        Booking booking = new Booking(0, new Date(), checkoutDate, new Room(0, null, 0, 0, 0, null), new ArrayList<Guest>());
        assertEquals(checkoutDate, booking.getCheckoutDate());
    }

    @Test
    public void testSetCheckoutDate() {
        Date checkoutDate = new Date();
        Booking booking = new Booking(0, new Date(), new Date(), new Room(0, null, 0, 0, 0, null), new ArrayList<Guest>());
        booking.setCheckoutDate(checkoutDate);
        assertEquals(checkoutDate, booking.getCheckoutDate());
    }

    @Test
    public void testGetRoom() {
        Room room = new Room(0, null, 0, 0, 0, null);
        Booking booking = new Booking(0, new Date(), new Date(), room, new ArrayList<Guest>());
        assertEquals(room, booking.getRoom());
    }

    @Test
    public void testSetRoom() {
        Room room = new Room(0, null, 0, 0, 0, null);
        Booking booking = new Booking(0, new Date(), new Date(), new Room(0, null, 0, 0, 0, null), new ArrayList<Guest>());
        booking.setRoom(room);
        assertEquals(room, booking.getRoom());
    }

    @Test
    public void testGetGuests() {
        List<Guest> guests = new ArrayList<>();
        Booking booking = new Booking(0, new Date(), new Date(), new Room(0, null, 0, 0, 0, null), guests);
        assertEquals(guests, booking.getGuests());
    }
    
    
    
}
