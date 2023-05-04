package database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Guest;

public class GuestDAOTest {
	private GuestDAO guestDAO;
	private Guest guest;

	@Before
	public void setUp() throws Exception {
		guestDAO = GuestDAO.getInstance();
		guest = new Guest("Lorem", "Ipsum", "22222222A", 50, "Vitoria-Gasteiz");
		try {
			guestDAO.save(guest);
		} catch(Exception e) {}
		
	}

	@Test
	public void testExists() throws Exception {
		boolean exist = guestDAO.exists(guest.getDni());
		assertTrue(exist);
	}
	@Test
	public void testFind() {
		guest = guestDAO.find(guest.getDni());
		assertEquals(guest.getDni(), guest.getDni());
		assertNotNull(guest);
	}
	@Test
	public void testGetAll() {
		List<Guest> guestList = guestDAO.getAll();
		assertTrue(guestList.contains(guest));
	}
	
	@After
	public void finish() {
		guest = guestDAO.find(guest.getDni());
		guestDAO.delete(guest);
		List<Guest> guestList = guestDAO.getAll();
		assertFalse(guestList.contains(guest));
	}

}
