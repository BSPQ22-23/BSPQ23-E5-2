package remote;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ImageTransferTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		ClientController.setServerHandler(new ServiceLocator("localhost", 8000));
	}
	
	@Test
	public void testUploadJPG() {
		
	}
}
