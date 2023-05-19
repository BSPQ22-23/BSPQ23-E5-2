package api.resources;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import database.ImageDAO;
import domain.Image;
import domain.Image.ImageType;

public class ImageDownloadHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Downloading image" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		try {
			String resource = exchange.getRequestHeaders().getOrDefault("ctx", List.of("null")).get(0);
			l.info("Query's ctx: " + resource);
			int offset = 0;
			try {
				offset = Integer.parseInt(exchange.getRequestHeaders().getOrDefault("offset", List.of("0")).get(0));
			} catch(NumberFormatException e) {
				APIUtils.respondError(exchange, "Offset must be a number");
				return;
			}
			l.info("Offset set to "+offset);
			if(resource == "null") {
				l.info("No context submitted, responding error");
				APIUtils.respondError(exchange, "No context defined, please, make sure you defined the 'ctx' header");
				return;
			}
			String location = resource.substring(0, resource.lastIndexOf('/'));
			List<Image> result = null;
			try {
				result = ImageDAO.getInstance().find(resource.substring(resource.lastIndexOf('/') + 1), ImageType.valueOf(location.toUpperCase().replace('/', '_')));
			} catch(IllegalArgumentException e) {
				APIUtils.respondError(exchange, e.getMessage());
				return;
			}
			if(result.size() == 0) {
				APIUtils.rawResponse(404, exchange, "Not found");
				return;
			}
			if(result.size() < offset + 1) {
				APIUtils.respondError(exchange, "Offset too big");
				return;
			}
			JSONObject output = new JSONObject();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(result.get(offset).getImage(), result.get(offset).getFormat(), baos);
			byte[] body = Base64.getEncoder().encode(baos.toByteArray());
			output.put("image", new String(body, StandardCharsets.UTF_8));
			output.put("format", result.get(offset).getFormat());
			exchange.sendResponseHeaders(200, output.toString().length());
			OutputStream os = exchange.getResponseBody();
	 		os.write(output.toString().getBytes());
	 		os.close();
	 		return;
		}catch (Exception e) {
			l.error("Failed to download image: " +e.getMessage());
			e.printStackTrace();
		}
	}

}
