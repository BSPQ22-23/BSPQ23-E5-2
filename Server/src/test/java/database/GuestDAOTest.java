package database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.Guest;

public class GuestDAOTest {
	private GuestDAO guestDAO;
	private Guest guest;

	@Before
	public void setUp() throws Exception {
		guestDAO = GuestDAO.getInstance();
		guest = new Guest("maitane", "a", "22222222A", 50, null);
		System.out.println('a');
		try {
			guestDAO.save(guest);
		} catch(Exception e) {}
		
	}

	@Test
	public void tests() throws Exception {
		boolean exist = guestDAO.exists(guest.getDni());
		assertTrue(exist);
		guest = guestDAO.find(guest.getDni());
		assertEquals(guest.getDni(), guest.getDni());
		assertNotNull(guest);
		List<Guest> guestList = guestDAO.getAll();
		assertTrue(guestList.contains(guest));
		guestDAO.delete(guest);
		guestList = guestDAO.getAll();
		assertFalse(guestList.contains(guest));
	}

}
