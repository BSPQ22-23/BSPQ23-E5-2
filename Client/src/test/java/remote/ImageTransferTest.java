package remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import org.junit.BeforeClass;
import org.junit.Test;

import domain.Guest;
import domain.Hotel;
import domain.Room;
import domain.User;
import remote.ClientController.DownloadedImage;
import remote.ClientController.Response;

public class ImageTransferTest {
	static Hotel h;
	@BeforeClass
	public static void setup() throws IOException, InterruptedException, ExecutionException {
		ClientController.setServerHandler(new ServiceLocator("localhost", 8000));
		User hm = new User(
				"hotelManager", 
				"AnotherPassword", 
				new Guest("Benjamin", "Dover", "305MWW", 29, "Miami, Florida"), 
				true
			);
			if(ClientController.register(hm).status != Response.SUCCESS)
				ClientController.login(hm.getNick(), hm.getPassword());
		h = new Hotel(
				0, 
				"Hotel Gasteiz", 
				"Vitoria",
				"Muy buen servicio"
		
		);
		h.addRoom(new Room(100, "Single", 1, 10, 50, null));
		ClientController.createHotel(h);
	}

	@Test
	public void testUploadJPG() throws IOException {
		assertTrue(new File("src/test/resources/icon_test_jpg.jpg").exists());
		BufferedImage bi = ImageIO.read(new File("src/test/resources/icon_test_jpg.jpg"));
		assertEquals(Response.SUCCESS, ClientController.uploadImage(bi, "jpg", "hotel/icon/"+h.getId()).status);
		DownloadedImage retrieved = ClientController.downloadImage("hotel/icon/"+h.getId(), 0);
		assertEquals("jpg", retrieved.format);
		assertEquals(bi.getHeight(), retrieved.image.getHeight());
		assertEquals(bi.getWidth(), retrieved.image.getWidth());
		File f = new File("test_client_" + retrieved.format + "." + retrieved.format);
		ImageIO.write(retrieved.image, retrieved.format, f);
		
	}
}
