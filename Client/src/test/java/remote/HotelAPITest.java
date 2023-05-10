package remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import domain.Guest;
import domain.Hotel;
import domain.Room;
import domain.User;
import remote.ClientController.Response;

public class HotelAPITest {
	
	@Before
	public void setup() throws IOException, InterruptedException, ExecutionException {
		ClientController.setServerHandler(new ServiceLocator("localhost", 8000));
		User hm = new User(
			"hotelManager", 
			"AnotherPassword", 
			new Guest("Benjamin", "Dover", "305MWW", 29, "Miami, Florida"), 
			true
		);
		if(ClientController.register(hm).status != Response.SUCCESS)
			ClientController.login(hm.getNick(), hm.getPassword());
	}
	
	@Test
	public void mainTest() throws InterruptedException, ExecutionException {
		Hotel h = new Hotel(
				0, 
				"Hotel Lakua", 
				"Vitoria"
		
		);
		Response creation = ClientController.createHotel(h);
		assertEquals("Can't make hotel without rooms", Response.BAD_REQUEST, creation.status);
		h.addRoom(new Room(100, "Single", 1, 1, 1, null));
		creation = ClientController.createHotel(h);
		assertEquals(Response.SUCCESS, creation.status);		
		
		if(ClientController.register(
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
			).status != Response.SUCCESS)
			ClientController.login("OriginalNick", "ASecurePassword");
		
		Response unauthCreation = ClientController.createHotel(h);
		assertEquals(unauthCreation.status, Response.UNATHORIZED);
		System.out.println(unauthCreation.message);
		
		List<Hotel> getAll = ClientController.getHotels();
		assertNotEquals(0, getAll.size());
		System.out.println(getAll);
		
		List<Hotel> getExisting = ClientController.getHotels("La");
		assertEquals(getExisting.size(), 1);
		
		List<Hotel> getNotExisting = ClientController.getHotels("▓");
		assertEquals(getNotExisting.size(), 0);
	}
}
