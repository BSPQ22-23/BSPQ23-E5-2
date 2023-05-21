package api.reservation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import domain.Booking;
import domain.User;
import main.Server;
import main.ServerAppService;

/**
 * 
 * Connection between the client and the ServerAppService to edit reservations
 *
 */
public class ReservationEditHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + (exchange.getRequestMethod().equals("DELETE")?"Deleting":"Editing")+" reservation" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
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
	    	if(author == null) {
	    		l.info("Unauthorized use of API: Invalid token");
	    		String resp = "Invalid token";
	    		exchange.sendResponseHeaders(401, resp.length());
	    		OutputStream os = exchange.getResponseBody();
		 		os.write(resp.getBytes());
		 		os.close();
		 		return;
	    	}
			switch (exchange.getRequestMethod()) {
				case "DELETE":
					int id = Integer.parseInt(exchange.getRequestHeaders().getOrDefault("id", List.of("-1")).get(0));
					l.info("Deleting reservation with id: " + id);
					if(id < 0) {
						l.info("Deletion rejected: Invalid ID");
						String response = "Invalid / Missing id";
			    		exchange.sendResponseHeaders(400, response.length());
			    		OutputStream os = exchange.getResponseBody();
			    		os.write(response.getBytes());
			    		os.close();
						return;
					}
					l.info("Deletion aproved");
					if(ServerAppService.deleteReservation(author, id))
						APIUtils.respondACK(exchange);
					else
						APIUtils.respondError(exchange, "Deletion denied");
					return;
				case "POST":
					JSONObject o = new JSONObject(APIUtils.readBody(exchange));
					if(ServerAppService.editReservation(author, Booking.fromJSON(o)))
						APIUtils.respondACK(exchange);
					else
						APIUtils.respondError(exchange, "Unable to edit the reservation");
					
					return;
				default:
					l.info("Illegal use of API: Unknown method");
					String response = "Method "+exchange.getRequestMethod()+" not valid for this function";
		    		exchange.sendResponseHeaders(405, response.length());
		    		OutputStream os = exchange.getResponseBody();
		    		os.write(response.getBytes());
		    		os.close();
					return;
			}
		}catch(IOException e) {
			l.error("Error editing reservation: " + e.toString());
			APIUtils.respondInternalError(exchange, e.getMessage());
		}
	}

}
