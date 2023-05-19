package api.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import database.HotelDAO;
import database.ImageDAO;
import domain.Hotel;
import domain.Image;
import domain.Image.ImageType;
import domain.User;
import main.Server;

public class ImageUploadHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Uploading image" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		String resource = exchange.getRequestHeaders().getOrDefault("ctx", List.of("null")).get(0);
		String format = exchange.getRequestHeaders().getOrDefault("Content-Type", List.of("png")).get(0);
		if(!format.startsWith("image/")) {
			APIUtils.respondError(exchange, "Body isn't an image");
			return;
		}
		format = format.replace("image/", "");
		if(resource == "null") {
			APIUtils.respondError(exchange, "No context defined, please, make sure you defined the 'ctx' header");
			return;
		}
		String location = resource.substring(0, resource.lastIndexOf('/'));
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(APIUtils.readBody(exchange))));
		switch (location) {
		case "hotel/icon":
			String token = APIUtils.getStringHeader(exchange, "token", "");
			if(token == "") {
				l.info("Illegal use of API: No token provided");
	    		String resp = "No token provided";
	    		exchange.sendResponseHeaders(401, resp.length());
	    		OutputStream os = exchange.getResponseBody();
		 		os.write(resp.getBytes());
		 		os.close();
		 		return;
	    	}
	    	User author = Server.getUser(token);
	    	if(author == null || !author.isHotelOwner()) {
	    		l.info("Unauthorized: Invalid token");
	    		APIUtils.rawResponse(401, exchange, "Invalid token");
		 		return;
	    	}
			String id = resource.substring(resource.lastIndexOf('/') + 1);
			Hotel h = HotelDAO.getInstance().find(id);
			if(h == null) {
				l.info("Error: Not found hotel " + id);
				APIUtils.rawResponse(404, exchange, "No hotel with id "+id);
				return;
			}
			if(!h.getOwner().equals(author.getLegalInfo())) {
				l.info("Unauthorized: Not the owner");
				APIUtils.rawResponse(401, exchange, "Not the owner");
		 		return;
			}
			l.info("Searching for an existing icon");
			List<Image> img = ImageDAO.getInstance().find(id, ImageType.HOTEL_ICON);
			l.info("Query finished");
			if(img != null && img.size() != 0) {
				l.info("Icon found, replacing");
				img.get(0).setImage(image, format);
				ImageDAO.getInstance().save(img.get(0));
				APIUtils.respondACK(exchange);
				return;
			}
			l.info("Icon not found, adding to the database");
			Image i = new Image(image, format, ImageType.HOTEL_ICON, id);
			ImageDAO.getInstance().save(i);
			APIUtils.respondACK(exchange);
			break;

		default:
			break;
		}
		
		File f = new File("test_server_" + format + "." + format);
		ImageIO.write(image, format, f);
	}

}
