package database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Booking;
import domain.Guest;

public class GuestDAOTest {
	private GuestDAO guestDAO;
	private Guest guest;

	@Before
	public void setUp() throws Exception {
		guestDAO = GuestDAO.getInstance();
		guest = new Guest("maitane", "a", "22222222A", 50, null);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		guestDAO.save(guest);
		List<Guest> guestList = guestDAO.getAll();
		assertTrue(guestList.contains(guest));
	}

	@Test
	public void testDelete() {
		guestDAO.save(guest);
		guestDAO.delete(guest);
		List<Guest> guestList = guestDAO.getAll();
		assertFalse(guestList.contains(guest));
		
	}

	@Test
	public void testGetAll() {
		guestDAO.save(guest);
		List<Guest> guestList = guestDAO.getAll();
		assertTrue(guestList.contains(guest));
	}
/**
 * Get a guest by dni
 */
	@Test
	public void testFind() {
	guestDAO.save(guest);
	Guest findGuest = guestDAO.find(guest.getDni());
	assertEquals(guest.getDni(), findGuest.getDni());
	assertNotNull(findGuest);
	}

	@Test
	public void testExists() {
		guestDAO.save(guest);
		boolean exist = guestDAO.exists(guest.getDni());
		assertTrue(exist);
		//assertFalse(exist);

	}

}
