package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.HotelDAO;
import domain.Guest;
import domain.Hotel;
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
        owner = new Guest("gorka", "d", "123456789", 30, "City");
        hotel = new Hotel("Test Hotel", "Test City", owner);
    }
    
    @Test
    public void testSave() {
        hotelDAO.save(hotel);
        List<Hotel> hotels = hotelDAO.getAll();
        assertTrue(hotels.contains(hotel));
    }
    
    @Test
    public void testDelete() {
        hotelDAO.save(hotel);
        hotelDAO.delete(hotel);
        List<Hotel> hotels = hotelDAO.getAll();
        assertFalse(hotels.contains(hotel));
    }
    
    @Test
    public void testGetAll() {
        hotelDAO.save(hotel);
        List<Hotel> hotels = hotelDAO.getAll();
        assertTrue(hotels.contains(hotel));
    }
    
    @Test
    public void testGetByName() {
        hotelDAO.save(hotel);
        List<Hotel> hotels = hotelDAO.getbyName("Test Hotel");
        assertTrue(hotels.contains(hotel));
    }
    
    @Test
    public void testAddHotel() {
        hotelDAO.addHotel(hotel);
        List<Hotel> hotels = hotelDAO.getAll();
        assertTrue(hotels.contains(hotel));
    }
    
    @Test
    public void testFindHotelsByOwner() {
        hotelDAO.save(hotel);
        List<Hotel> hotels = hotelDAO.findHotelsByOwner(owner);
        assertTrue(hotels.contains(hotel));
    }
    
    @Test
    public void testOwnerExists() {
        hotelDAO.save(hotel);
        boolean exists = hotelDAO.ownerExists("gorka");
        assertTrue(exists);
    }
  
}
