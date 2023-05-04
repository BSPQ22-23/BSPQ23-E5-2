package api.reservation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import domain.Booking;
import domain.Guest;
import domain.Room;
import domain.User;
import main.Server;
import main.ServerAppService;

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
	    		Calendar c = Calendar.getInstance();
	    		long checkinDate = obj.getLong("checkinDate");
	    		c.set(Calendar.DAY_OF_YEAR, (int)(checkinDate%365));
	    		c.set(Calendar.YEAR, (int)(checkinDate/365));
	    		Date inDate = c.getTime();
	    		long checkoutDate = obj.getLong("checkoutDate");
	    		c.set(Calendar.DAY_OF_YEAR, (int)(checkoutDate%365));
	    		c.set(Calendar.YEAR, (int)(checkoutDate/365));
	    		Date outDate = c.getTime();
	    		Booking b = Booking.fromJSON(obj);
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
