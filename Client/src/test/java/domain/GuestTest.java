package domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuestTest {

    private Guest guest;

    @Before
    public void setUp() {
        guest = new Guest("John", "Doe", "12345678A", 30, "New York");
    }

    @Test
    public void testGetName() {
        assertEquals("John", guest.getName());
    }

    @Test
    public void testSetName() {
        guest.setName("Jane");
        assertEquals("Jane", guest.getName());
    }

    @Test
    public void testGetSurname() {
        assertEquals("Doe", guest.getSurname());
    }

    @Test
    public void testSetSurname() {
        guest.setSurname("Smith");
        assertEquals("Smith", guest.getSurname());
    }

    @Test
    public void testGetDni() {
        assertEquals("12345678A", guest.getDni());
    }

    @Test
    public void testSetDni() {
        guest.setDni("87654321B");
        assertEquals("87654321B", guest.getDni());
    }

    @Test
    public void testGetAge() {
        assertEquals(30, guest.getAge());
    }

    @Test
    public void testSetAge() {
        guest.setAge(35);
        assertEquals(35, guest.getAge());
    }

    @Test
    public void testGetCityOfProvenance() {
        assertEquals("New York", guest.getCityOfProvenance());
    }

    @Test
    public void testSetCityOfProvenance() {
        guest.setCityOfProvenance("Los Angeles");
        assertEquals("Los Angeles", guest.getCityOfProvenance());
    }
}


