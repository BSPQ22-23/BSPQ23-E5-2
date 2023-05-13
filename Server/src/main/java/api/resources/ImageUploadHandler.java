package api.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;

public class ImageUploadHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Uploading image" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(APIUtils.readBody(exchange))));
		System.out.println("A");
		File f = new File("test." + exchange.getRequestHeaders().getOrDefault("format", List.of("png")).get(0));
		System.out.println(f.getAbsolutePath());
		ImageIO.write(image, exchange.getRequestHeaders().getOrDefault("format", List.of("png")).get(0), f);
	}

}
