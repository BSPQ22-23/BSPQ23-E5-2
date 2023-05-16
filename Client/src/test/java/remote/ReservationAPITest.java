package remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import domain.Booking;
import domain.Guest;
import domain.Hotel;
import domain.Room;
import domain.User;
import remote.ClientController.Response;

public class ReservationAPITest {
	
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
	public void testReservation() throws InterruptedException, ExecutionException {
		Hotel h = new Hotel(1, "Hotel Overlook", "Oregón", "Increible");
		h.addRoom(new Room(100, "Double", 2, 15, 300, null));
		ClientController.createHotel(h);
		h = ClientController.getHotels(h.getName()).get(0);
		Booking b = new Booking(
			2, 
			new Date(System.currentTimeMillis()), 
			new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3)), 
			new Room(100, "Double", 2, 10, 15.5f, h), 
			List.of(
				new Guest("Erik", "Torsten", "12314", 38, "Estocolmo"),
				new Guest("Tayane", "Alves", "987654321", 27, "Salvador")
			)
		);
		//Create reservation + getReservations
		assertEquals(Response.SUCCESS, ClientController.createReservation(b).status);
		List<Booking> result = ClientController.getReservations();
		assertEquals(1, result.size());
		b = result.get(0);
		//Modify reservation
		b = result.get(0);//Get the id assigned by the server
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date newDate = c.getTime();
		b.setCheckinDate(newDate);
		ClientController.editReservation(b);
		Date newDateOut = ClientController.getReservation(b.getId()).getCheckinDate();
		System.out.println(newDate);
		System.out.println(newDateOut);
		assertEquals(newDate.getTime(), newDateOut.getTime());
		
		//Authorized use of getReservationsByHotel
		assertEquals(1, ClientController.getReservations(b.getRoom().getHotel()).size());
		
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
		
		//Unauthorized use of getReservationsByHotel
		final Booking _b = b;
		Response r = assertThrows(Response.class, () -> ClientController.getReservations(_b.getRoom().getHotel()));
		assertEquals(Response.UNATHORIZED, r.status);
		
		//Delete of reservation
		
		ClientController.deleteReservation(b);
		assertEquals(0, ClientController.getReservations().size());
	}
}
