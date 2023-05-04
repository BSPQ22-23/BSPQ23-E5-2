package database;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Guest;
import domain.User;

public class UserDAOTest {
	private UserDAO dao;
	private User user;

	@Before
	public void setUp() throws Exception {
		dao = UserDAO.getInstance();
		Guest legalInfo = new Guest("Juan", "García", "12345678A", 0, null);
		user = new User("jgarcia", "password", legalInfo, false);
	}

	@After
	public void tearDown() throws Exception {
		dao.delete(user);
		dao = null;
		user = null;
	}

	@Test
	public void testSave() {
		dao.save(user);
		User retrievedUser = dao.find("jgarcia");
		assertNotNull(retrievedUser);
		assertEquals(user.getNick(), retrievedUser.getNick());
		assertEquals(user.getPassword(), retrievedUser.getPassword());
		assertEquals(user.isHotelOwner(), retrievedUser.isHotelOwner());
		assertEquals(user.getLegalInfo().getDni(), retrievedUser.getLegalInfo().getDni());
	}

	@Test
	public void testDelete() {
		dao.save(user);
		dao.delete(user);
		User retrievedUser = dao.find("jgarcia");
		assertNull(retrievedUser);
	}

	@Test
	public void testGetAll() {
		dao.save(user);
		List<User> userList = dao.getAll();
		assertTrue(userList.contains(user));
	}

	@Test
	public void testFindString() {
		dao.save(user);
		User retrievedUser = dao.find("jgarcia");
		assertNotNull(retrievedUser);
		assertEquals(user.getNick(), retrievedUser.getNick());
		assertEquals(user.getPassword(), retrievedUser.getPassword());
		assertEquals(user.isHotelOwner(), retrievedUser.isHotelOwner());
		assertEquals(user.getLegalInfo().getDni(), retrievedUser.getLegalInfo().getDni());
	}

	@Test
	public void testFindStringString() {
		dao.save(user);
		User retrievedUser = dao.find("jgarcia", "password");
		assertNotNull(retrievedUser);
		assertEquals(user.getNick(), retrievedUser.getNick());
		assertEquals(user.getPassword(), retrievedUser.getPassword());
		assertEquals(user.isHotelOwner(), retrievedUser.isHotelOwner());
		assertEquals(user.getLegalInfo().getDni(), retrievedUser.getLegalInfo().getDni());
	}

	@Test
	public void testExists() {
		dao.save(user);
		assertTrue(dao.exists("jgarcia"));
		assertFalse(dao.exists("pedro"));
	}
}

