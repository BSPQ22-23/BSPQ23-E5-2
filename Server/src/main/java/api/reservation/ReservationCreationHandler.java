package api.reservation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import database.HotelDAO;
import domain.Booking;
import domain.Guest;
import domain.Hotel;
import domain.Room;
import domain.User;
import main.Server;
import main.ServerAppService;

/**
 * 
 * Connection between the client and the ServerAppService to create reservations
 *
 */
public class ReservationCreationHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange t) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Creating reservation" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		try {
	    	JSONObject obj = new JSONObject(APIUtils.readBody(t));
	    	String token = APIUtils.getStringHeader(t, "token", "");
	    	if(token == "") {
	    		l.info("Illegal use of API: No token provided");
	    		String resp = "No token provided";
	    		t.sendResponseHeaders(401, resp.length());
	    		OutputStream os = t.getResponseBody();
		 		os.write(resp.getBytes());
		 		os.close();
		 		return;
	    	}
	    	User author = Server.getUser(token);
	    	if(author == null) {
	    		l.info("Unauthorized use of API: Invalid token");
	    		String resp = "Invalid token";
	    		t.sendResponseHeaders(401, resp.length());
	    		OutputStream os = t.getResponseBody();
		 		os.write(resp.getBytes());
		 		os.close();
		 		return;
	    	}
	    	try {
	    		List<Guest> guests= new ArrayList<>();
	    		for (Object _o : obj.getJSONArray("guests")) {
	    			JSONObject o = (JSONObject)_o;
	    			guests.add(new Guest(o.getString("name"), o.getString("surname"), o.getString("dni"), o.getInt("age"), o.getString("cityOfProvenance")));
	    		}
	    		Booking b = Booking.fromJSON(obj);
	    		l.info("Searching hotel");
	    		Hotel h = HotelDAO.getInstance().find(""+b.getRoom().getHotel().getId());
	    		if(h == null) {
	    			l.info("Hotel not found, responding error");
	    			APIUtils.respondError(t, "Hotel doesn't exist");
	    			return;
	    		}
	    		l.info("Hotel found, searching room");
	    		getRoom: {
	    			for(Room r : h.getRooms()) {
	    				if(r.getRoomNumber() == b.getRoom().getRoomNumber()) {
	    					b.setRoom(r);
	    					break getRoom;
	    				}
	    			}
	    			l.info("Room not found, responding error");
	    			APIUtils.respondError(t, "Room doesn't exist");
	    			return;
	    		}
	    		l.info("Room found, creating reservation");
	    		b.setAuthor(author.getLegalInfo());
	    		if(ServerAppService.reservationCreate(b)) {
	    			l.info("Reservation done correctly");
	    			APIUtils.respondACK(t);
	    		}else {
	    			l.info("Reservation rejected");
	    			APIUtils.respondError(t, "The reservation couldn't be done");
	    		}
	      	}catch(IllegalArgumentException | JSONException e) {
	      		l.info("Illegal use of API: " + e.toString());
	      		APIUtils.respondError(t, e.getMessage());
	    	}
		}catch(IOException e) {
			l.error("Error creating reservation: " + e.toString());
			APIUtils.respondInternalError(t, e.getMessage());
		}
    	
    }

}
