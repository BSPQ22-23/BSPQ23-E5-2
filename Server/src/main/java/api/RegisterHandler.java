package api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import domain.User;
import main.ServerAppService;

/**
 * Connection between client and ServerAppService for registering
 *
 */
public class RegisterHandler implements HttpHandler{
	
    @Override
    public void handle(HttpExchange t) throws IOException {
    	Logger l = LogManager.getLogger();
    	l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Registering user" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
    	JSONObject obj = new JSONObject(APIUtils.readBody(t));
    	try {
    		String token = ServerAppService.register(User.fromJSON(obj));
    		if(token != null) {
    			l.info("Authorized " + APIUtils.decode(obj.getString("nick")));
    			token =Base64.getEncoder().encodeToString(token.getBytes());
    			t.sendResponseHeaders(200, token.length());
    			OutputStream os = t.getResponseBody();
   		 		os.write(token.getBytes());
   		 		os.close();
   		 		return;
    		}else
    			throw new IllegalArgumentException("Unknown error occurred");
    	}catch(IllegalArgumentException | JSONException e) {
    		l.info("Unauthorized: " + e.getMessage());
    		String response = e.getMessage();
    		t.sendResponseHeaders(400, response.length());
    		OutputStream os = t.getResponseBody();
    		os.write(response.getBytes());
    		os.close();
    		return;
    	}
    }
}
