package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

    private Room room;

    @Before
    public void setUp() {
        room = new Room(101, "double", 2, 20, 1, null);
    }

    @Test
    public void testGetRoomNumber() {
        assertEquals(101, room.getRoomNumber());
    }

    @Test
    public void testSetRoomNumber() {
        room.setRoomNumber(102);
        assertEquals(102, room.getRoomNumber());
    }

    @Test
    public void testGetType() {
        assertEquals("double", room.getType());
    }

    @Test
    public void testSetType() {
        room.setType("single");
        assertEquals("single", room.getType());
    }

    @Test
    public void testGetNumMaxGuests() {
        assertEquals(2, room.getNumMaxGuests());
    }

    @Test
    public void testSetNumMaxGuests() {
        room.setNumMaxGuests(1);
        assertEquals(1, room.getNumMaxGuests());
    }

    @Test
    public void testGetSpaceInMeters() {
        assertEquals(20, room.getSpaceInMeters());
    }

    @Test
    public void testSetSpaceInMeters() {
        room.setSpaceInMeters(25);
        assertEquals(25, room.getSpaceInMeters());
    }
}



