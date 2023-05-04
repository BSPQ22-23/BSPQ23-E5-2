package remote;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import domain.Guest;
import domain.User;
import remote.ClientController.Response;

public class ClientControllerTest {
	
	@Before
	public void setup() throws IOException{
		ClientController.setServerHandler(new ServiceLocator("localhost", 8000));
	}
	
	@Test
	public void registerTest() throws InterruptedException, ExecutionException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Response r = ClientController.register(
			new User(
				"OriginalNick", 
				"ASecurePassword",
				new Guest(
					"This is a name", 
					"This is a surname",  
					"123456789J", 
					10, 
					"A city somewhere"
				),
				false
			)
		);
		assertEquals(Response.BAD_REQUEST, r.status);
		System.out.println(r.message);
		Field f =ClientController.class.getDeclaredField("token");
		f.setAccessible(true);
		System.out.println("Register token: " + f.get(null));
	}
	@Test
	public void loginTest() throws InterruptedException, ExecutionException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Response r = ClientController.login("name", "password");
		assertEquals(Response.BAD_REQUEST, r.status);
		Field f =ClientController.class.getDeclaredField("token");
		f.setAccessible(true);
		System.out.println("Login token on fail: " + f.get(null));
		r = ClientController.login("OriginalNick", "ASecurePassword");
		assertEquals(Response.SUCCESS, r.status);
		System.out.println("Login token on success: " + f.get(null));
	}
}
