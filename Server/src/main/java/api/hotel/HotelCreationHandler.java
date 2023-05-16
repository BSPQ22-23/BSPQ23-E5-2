package api.hotel;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import domain.Hotel;
import domain.User;
import main.Server;
import main.ServerAppService;

public class HotelCreationHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Creating hotel" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		try {
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
	    		String resp = "Invalid token";
	    		exchange.sendResponseHeaders(401, resp.length());
	    		OutputStream os = exchange.getResponseBody();
		 		os.write(resp.getBytes());
		 		os.close();
		 		return;
	    	}
	    	Hotel h = Hotel.fromJSON(new JSONObject(APIUtils.readBody(exchange)));
	    	h.getRooms().forEach(v->v.setHotel(h));
	    	h.setOwner(author.getLegalInfo());
	    	if(ServerAppService.createHotel(h)) {
	    		l.info("Hotel created " + h.getName());
	    		APIUtils.respondSuccess(exchange, ""+h.getId());
	    	}else {
	    		l.info("Hotel NOT created");
	    		APIUtils.respondError(exchange, "Something went wrong");
	    	}
		} catch(Exception e) {
			l.error("Error creating hotel: " + e.toString());
			e.printStackTrace();
			APIUtils.respondInternalError(exchange, e.getMessage());
		}
	}

}
