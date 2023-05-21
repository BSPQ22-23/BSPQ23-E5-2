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

import domain.PasswordEncryption;
import main.ServerAppService;

public class LoginHandler implements HttpHandler{

	@Override
    public void handle(HttpExchange t) throws IOException {
		Logger l = LogManager.getLogger();
		l.debug("▓".repeat(78)+"\n" + " ".repeat(31) + "Logging user" + " ".repeat(31) + "\n" + "▓".repeat(78)+"\n");
		String body =APIUtils.readBody(t);
		JSONObject obj = new JSONObject(body);
		String token = null;
		try {
			token = ServerAppService.login(
				APIUtils.decode(obj.getString("user")),
				PasswordEncryption.encryptPassword(APIUtils.decode(obj.getString("password")))
			);
		}catch(IllegalArgumentException | JSONException e) {
			l.info("Illegal use of API: " + e.getMessage());
    		String response = e.getMessage();
    		t.sendResponseHeaders(400, response.length());
    		OutputStream os = t.getResponseBody();
    		os.write(response.getBytes());
    		os.close();
    	}
		if(token == null) {
			l.info("Authorization failed");
			String res = "User or password incorrect";
			t.sendResponseHeaders(400, res.length());
			OutputStream os = t.getResponseBody();
			os.write(res.getBytes());
			os.close();
			return;
		}
		l.info("Authorized " + APIUtils.decode(obj.getString("user")));
		t.sendResponseHeaders(200, token.length());
		OutputStream os = t.getResponseBody();
 		os.write(token.getBytes());
 		os.close();
    }

}
