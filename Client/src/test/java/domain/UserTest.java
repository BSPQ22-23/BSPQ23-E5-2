package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        Guest guest = new Guest("John", "Doe", "12345678A", 30, "New York");
        user = new User("johndoe", "password123", guest, false);
    }

    @Test
    public void testGetNick() {
        assertEquals("johndoe", user.getNick());
    }

    @Test
    public void testSetNick() {
        user.setNick("janedoe");
        assertEquals("janedoe", user.getNick());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword123");
        assertEquals("newpassword123", user.getPassword());
    }

    @Test
    public void testGetLegalInfo() {
        Guest guest = user.getLegalInfo();
        assertEquals("John", guest.getName());
        assertEquals("Doe", guest.getSurname());
        assertEquals("12345678A", guest.getDni());
        assertEquals(30, guest.getAge());
        assertEquals("New York", guest.getCityOfProvenance());
    }

    @Test
    public void testSetLegalInfo() {
        Guest newGuest = new Guest("Jane", "Doe", "87654321B", 25, "Los Angeles");
        user.setLegalInfo(newGuest);
        assertEquals("Jane", user.getLegalInfo().getName());
        assertEquals("Doe", user.getLegalInfo().getSurname());
        assertEquals("87654321B", user.getLegalInfo().getDni());
        assertEquals(25, user.getLegalInfo().getAge());
        assertEquals("Los Angeles", user.getLegalInfo().getCityOfProvenance());
    }

    @Test
    public void testIsHotelOwner() {
        assertFalse(user.isHotelOwner());
    }

    @Test
    public void testSetHotelOwner() {
        user.setHotelOwner(true);
        assertTrue(user.isHotelOwner());
    }
}

