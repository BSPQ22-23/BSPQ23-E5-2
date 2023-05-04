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
		dao.save(user);
	}

	@After
	public void tearDown() throws Exception {
		user = dao.find("jgarcia");
		dao.delete(user);
		User retrievedUser = dao.find("jgarcia");
		assertNull(retrievedUser);
	}

	@Test
	public void testFind() {
		User retrievedUser = dao.find("jgarcia");
		assertNotNull(retrievedUser);
		assertEquals(user.getNick(), retrievedUser.getNick());
	}
	
	@Test
	public void testGetAll() {
		List<User> userList = dao.getAll();
		assertTrue(userList.contains(user));
	}
	
	@Test
	public void testFindByAuth() {
		User retrievedUser = dao.find("jgarcia", "password");
		assertNotNull(retrievedUser);
		assertEquals(user.getNick(), retrievedUser.getNick());
	}
	
	@Test
	public void testExists() {
		assertTrue(dao.exists("jgarcia"));
		assertFalse(dao.exists("pedro"));
	}
}

