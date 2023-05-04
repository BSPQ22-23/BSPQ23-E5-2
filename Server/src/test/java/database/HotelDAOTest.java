package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
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
    private Guest owner;
    
    @Before
    public void setUp() {
        hotelDAO = HotelDAO.getInstance();
        owner = new Guest("Aihnoa", "Gaspiz", "776978574J", 30, "Vitoria-Gasteiz");
        hotel = new Hotel("Test Hotel", "Test City", owner);
        hotel.addRoom(new Room(100, "Single", 1, 5, 10, hotel));
        hotelDAO.save(hotel);
        GuestDAO.getInstance().save(owner);
    }
    
    @Test
    public void tests() {
		/////////////////////////////////////////////////
		//                     GET BY NAME
		/////////////////////////////////////////////////
    	Hotel h = hotelDAO.getByName("Test Hotel").get(0);
    	assertNotNull(h);
    	assertEquals(hotel.getCity(), h.getCity());
    	assertEquals(hotel.getId(), h.getId());
    	assertEquals(hotel.getOwner(), h.getOwner());
    	assertEquals(hotel.getRooms(), h.getRooms());
    	assertEquals(hotel.getServices(), h.getServices());
    	hotel = h;
    	
        /////////////////////////////////////////////////
    	//                     FIND
    	/////////////////////////////////////////////////
    	h = hotelDAO.find(""+hotel.getId());
    	assertNotNull(h);
    	assertEquals(hotel.getCity(), h.getCity());
    	assertEquals(hotel.getId(), h.getId());
    	assertEquals(hotel.getOwner(), h.getOwner());
    	assertEquals(hotel.getRooms(), h.getRooms());
    	//assertEquals(hotel.getServices(), h.getServices());
        /////////////////////////////////////////////////
    	//                     GET ALL
    	/////////////////////////////////////////////////
        List<Hotel> hotels = hotelDAO.getAll();
        assertTrue(hotels.contains(hotel));
        
		/////////////////////////////////////////////////
		//                     GET BY OWNER
		/////////////////////////////////////////////////
        hotels = hotelDAO.getByOwner(owner);
        assertTrue(hotels.contains(hotel));
        
		/////////////////////////////////////////////////
		//                     DELETE
		/////////////////////////////////////////////////        
        hotelDAO.delete(hotel);
        hotels = hotelDAO.getAll();
        assertFalse(hotels.contains(hotel));
    }
  
}
