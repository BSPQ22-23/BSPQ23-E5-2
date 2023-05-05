package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Guest;
import domain.Hotel;
import domain.Room;
/**
 * JUnit Test DAO for Hotel class
 * @author gorkademiguel
 *
 */
public class HotelDAOTest {
    private HotelDAO hotelDAO;
    private Hotel hotel;
    private static Guest owner;
    
    @BeforeClass
    public static void start() {
    	owner = new Guest("Aihnoa", "Gaspiz", "776978574J", 30, "Vitoria-Gasteiz");
    	GuestDAO.getInstance().save(owner);
    }
    
    @Before
    public void setUp() {
        hotelDAO = HotelDAO.getInstance();
        hotel = new Hotel("Test Hotel", "Test City", owner);
        hotel.addRoom(new Room(100, "Single", 1, 5, 10, hotel));
        hotelDAO.save(hotel);
    }
    
    @Test
    public void testGetByName() {
    	Hotel h = hotelDAO.getByName("Test Hotel").get(0);
    	assertNotNull(h);
    	assertEquals(hotel.getCity(), h.getCity());
    	assertEquals(hotel.getId(), h.getId());
    	assertEquals(hotel.getOwner(), h.getOwner());
    	assertEquals(hotel.getRooms(), h.getRooms());
    	assertEquals(hotel.getServices(), h.getServices());
    }
    @Test
    public void testFind() {  
    	Hotel h = hotelDAO.find(""+hotel.getId());
    	assertNotNull(h);
    	assertEquals(hotel.getCity(), h.getCity());
    	assertEquals(hotel.getId(), h.getId());
    	assertEquals(hotel.getOwner(), h.getOwner());
    	assertEquals(hotel.getRooms(), h.getRooms());
    	assertEquals(hotel.getServices(), h.getServices());
    }
    @Test
    public void testGetAll() {
        List<Hotel> hotels = hotelDAO.getAll();
        assertTrue(hotels.contains(hotel));
    }
    @Test
    public void testGetByOwner() {
        List<Hotel> hotels = hotelDAO.getByOwner(owner);
        assertTrue(hotels.contains(hotel));       
    }
    @After
    public void deleteHotel() {
    	hotel = hotelDAO.find(""+hotel.getId());
    	hotelDAO.delete(hotel);
    	List<Hotel> hotels = hotelDAO.getAll();
        assertFalse(hotels.contains(hotel));
    }
    
    @AfterClass
    public static void tearUp() {
    	GuestDAO.getInstance().delete(GuestDAO.getInstance().find(owner.getDni()));
    }
  
}
