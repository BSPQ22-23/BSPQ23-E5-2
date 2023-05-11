package api.reservation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import domain.Booking;
import domain.User;
import main.Server;
import main.ServerAppService;

public class ReservationGetterHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Getting reservation(s)" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
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
	    	try {
		    	String queryType = exchange.getRequestHeaders().getOrDefault("q", List.of("user")).get(0);
		    	String parameter = APIUtils.getStringHeader(exchange, "value", "");
		    	switch (queryType) {
				case "user":
					l.info("Getting reservations by user");
					List<Booking> b = ServerAppService.getReservationsByUser(author);
					b.forEach(v-> {
						v.getRoom().setBookings(null);//Avoid stack overflow
						v.getRoom().getHotel().setRooms(null);//Avoid stack overflow x2
					});
					String body = APIUtils.listToJSONArray(b).toString();
					exchange.sendResponseHeaders(200, body.length());
		    		OutputStream os = exchange.getResponseBody();
			 		os.write(body.getBytes());
			 		os.close();
			 		return;
				case "hotel":
					l.info("Getting reservations by hotel");
					if(!author.isHotelOwner()) {
						l.info("Unauthorized use of API: Unauthorized");
			    		String resp = "Unauthorized";
			    		exchange.sendResponseHeaders(401, resp.length());
			    		os = exchange.getResponseBody();
				 		os.write(resp.getBytes());
				 		os.close();
					}else {
						body = APIUtils.listToJSONArray(ServerAppService.getReservationsByHotel(author, parameter)).toString();
						exchange.sendResponseHeaders(200, body.length());
		    			os = exchange.getResponseBody();
			 			os.write(body.getBytes());
			 			os.close();
					}
					return;
				case "single":
					l.info("Getting reservation by id");
					Booking res = ServerAppService.getReservationById(exchange.getRequestHeaders().getOrDefault("value", List.of("-1")).get(0));
					if(res == null) {
						l.info("No reservations found");
						String response = "Not Found";
			    		exchange.sendResponseHeaders(404, response.length());
			    		os = exchange.getResponseBody();
			    		os.write(response.getBytes());
			    		os.close();
					} else if(res.getAuthor().equals(author.getLegalInfo()) || author.isHotelOwner() && res.getRoom().getHotel().getOwner().equals(author.getLegalInfo())){
						l.info("Successfully retrieved reservation " + parameter);
						body = APIUtils.objectToJSON(res).toString();
						exchange.sendResponseHeaders(200, body.length());
			    		os = exchange.getResponseBody();
				 		os.write(body.getBytes());
				 		os.close();
					} else {
						l.info("Unauthorized: Not the legit owner of the reservation");
						String resp = "Invalid token";
			    		exchange.sendResponseHeaders(401, resp.length());
			    		os = exchange.getResponseBody();
				 		os.write(resp.getBytes());
				 		os.close();
				 		return;
					}
					return;
				default:
					l.info("Illegal use of API: Unknown method");
					String response = "No method called '"+queryType+'\'';
		    		exchange.sendResponseHeaders(400, response.length());
		    		os = exchange.getResponseBody();
		    		os.write(response.getBytes());
		    		os.close();
					return;
				}
	    	} catch(IllegalArgumentException | JSONException e) {
	    		l.info("Illegal use of API: " + e.getMessage());
	    		String response = e.getMessage();
	    		exchange.sendResponseHeaders(400, response.length());
	    		OutputStream os = exchange.getResponseBody();
	    		os.write(response.getBytes());
	    		os.close();
	    	}
		}catch(Exception e) {
			l.error("Error getting reservation(s): " + e.toString());
			APIUtils.respondInternalError(exchange, e.getMessage());
		}
	}

}
