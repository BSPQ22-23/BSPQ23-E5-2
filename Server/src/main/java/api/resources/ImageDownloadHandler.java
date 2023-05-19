package api.resources;

import java.io.ByteArrayOutputStream;
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
import database.ImageDAO;
import domain.Image;
import domain.Image.ImageType;

public class ImageDownloadHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Uploading image" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		String resource = exchange.getRequestHeaders().getOrDefault("ctx", List.of("null")).get(0);
		int offset = 0;
		try {
			offset = Integer.parseInt(exchange.getRequestHeaders().getOrDefault("offset", List.of("0")).get(0));
		} catch(NumberFormatException e) {
			APIUtils.respondError(exchange, "Offset must be a number");
			return;
		}
		if(resource == "null") {
			APIUtils.respondError(exchange, "No context defined, please, make sure you defined the 'ctx' header");
			return;
		}
		String location = resource.substring(0, resource.lastIndexOf('/'));
		List<Image> result = ImageDAO.getInstance().find(resource.substring(resource.lastIndexOf('/') + 1), ImageType.valueOf(location.replace('/', '_')));
		if(result.size() == 0) {
			APIUtils.rawResponse(404, exchange, "Not found");
			return;
		}
		if(result.size() < offset + 1) {
			APIUtils.respondError(exchange, "Offset too big");
			return;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(result.get(offset).getImage(), result.get(offset).getFormat(), baos);
		byte[] body = Base64.getEncoder().encode(baos.toByteArray());
		exchange.sendResponseHeaders(200, body.length);
		OutputStream os = exchange.getResponseBody();
 		os.write(body);
 		os.close();
 		return;
	}

}
