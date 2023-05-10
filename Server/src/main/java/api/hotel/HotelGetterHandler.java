package api.hotel;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import api.APIUtils;
import main.ServerAppService;

public class HotelGetterHandler implements HttpHandler{
	
	public void handle(HttpExchange exchange) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Getting hotel(s)" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		String name = APIUtils.getStringHeader(exchange, "query", "");
    	try {
    		String body = null;
    		if(name == "") {
    			l.info("No query provided, retrieving all hotels");
    			body = APIUtils.listToJSONArray(ServerAppService.getHotels()).toString();
    			l.info("Hotels retrieved");
    		}else {
    			l.info("Retrieving all hotels containing '"+name+"' in their name");
    			body = APIUtils.listToJSONArray(ServerAppService.getHotels(name)).toString();
    		}
			exchange.sendResponseHeaders(200, body.length());
    		OutputStream os = exchange.getResponseBody();
	 		os.write(body.getBytes());
	 		os.close();
    	} catch(IllegalArgumentException e) {
    		l.info("Illegal use of API: " + e.getMessage());
    		APIUtils.respondError(exchange, e.getMessage());
		}catch(IOException e) {
			l.error("Error getting hotels " + e.toString());
			APIUtils.respondInternalError(exchange, e.getMessage());
		}
	}

}
