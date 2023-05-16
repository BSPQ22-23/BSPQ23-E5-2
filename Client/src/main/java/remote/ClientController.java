package remote;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import domain.Booking;
import domain.Hotel;
import domain.User;

public class ClientController {
	public static class Response extends RuntimeException {
		private static final long serialVersionUID = 519375302527407530L;
		public final String message;
		public final int status;
		public Response(int status, String message) {
			super(message);
			this.message = message;
			this.status = status;
		}
		/**
		 * Responded when a query gets a response from the server
		 */
		public static final int SUCCESS = 200;
		/**
		 * One or many parameters are missing or have invalid values
		 */
		public static final int BAD_REQUEST = 400;
		/**
		 * The query requires a token and/or the token it's invalid
		 */
		public static final int UNATHORIZED = 401;
		/**
		 * The API doesn't have the method trying to reach or has been moved
		 */
		public static final int METHOD_NOT_ALLOWED = 405;
		/**
		 * The server has an unexpected exception
		 */
		public static final int INTERNAL_SERVER_ERROR = 500;
	}
	public static void setServerHandler(ServiceLocator sv) {
		handler = sv;
	}
	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
		 BufferedImage bi = ImageIO.read(new File("2022-12-15 (9).png"));
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 ImageIO.write(bi, "jpg", baos);
		 byte[] bytes = baos.toByteArray();
		 setServerHandler(new ServiceLocator("localhost", 8000));
		 handler.sendPOST("resources/upload", Map.of("format", "png"), Base64.getEncoder().encode(bytes));
	}
	private static String token = null;
	private static ServiceLocator handler;
	
	public static Response register(User g) throws InterruptedException, ExecutionException{
		try {
			HttpResponse<String> response = handler.sendPOST("register", APIUtils.objectToJSON(g));
			if(response.statusCode() != 200)
				return new Response(response.statusCode(), response.body());
			else {
				token = response.body();
				return new Response(200, "");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Response login(String user, String password) throws InterruptedException, ExecutionException {
		try {
			JSONObject body = new JSONObject();
			body.put("user", Base64.getEncoder().encodeToString(user.getBytes()).toString());
			body.put("password", Base64.getEncoder().encodeToString(password.getBytes()).toString());
			HttpResponse<String> response = handler.sendPOST("login", body);
			if(response.statusCode() != 200)
				return new Response(response.statusCode(), response.body());
			else {
				token = response.body();
				return new Response(200, "");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Response createReservation(Booking b) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("token", token);
		HttpResponse<String> response;
		try {
			response = handler.sendPOST("booking/create", headers, APIUtils.objectToJSON(b));
			return new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static Response editReservation(Booking b) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("token", token);
		HttpResponse<String> response;
		try {
			response = handler.sendPOST("booking/edit", headers, APIUtils.objectToJSON(b));
			return new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}	
	}
	public static Response deleteReservation(Booking b) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("token", token);
		headers.put("id", Integer.toString(b.getId()));
		HttpResponse<String> response;
		try {
			response = handler.sendDELETE("booking/edit", headers);
			return new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static Response createHotel(Hotel h) {
		HttpResponse<String> response;
		try {
			response = handler.sendPOST("hotel/create", Map.of("token", token), APIUtils.objectToJSON(h));
			if(response.statusCode() != 200)
				return new Response(response.statusCode(), response.body());
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 ImageIO.write(h.getIcon(), h.getIconFormat(), baos);
			response = handler.sendPOST("/resources/upload", Map.of("Content-Type", "image/"+h.getIconFormat(), "ctx", "hotel/create/"+response.body(), "token", token),Base64.getEncoder().encode(baos.toByteArray()));
			return new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static List<Booking> getReservations(){
		HttpResponse<String> response;
		try {
			response = handler.sendGET("booking/get", Map.of("token", token));
			if(response.statusCode() == 200) {
				JSONArray resp = new JSONArray(response.body());
				List<Booking> output = new LinkedList<>();
				resp.forEach(v -> output.add(Booking.fromJSON((JSONObject)v)));
				return output;
			}
			throw new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static List<Booking> getReservations(Hotel h){
		HttpResponse<String> response;
		try {
			response = handler.sendGET("booking/get", Map.of("token", token, "q", "hotel", "value", ""+h.getId()));
			if(response.statusCode() == 200) {
				JSONArray resp = new JSONArray(response.body());
				List<Booking> output = new LinkedList<>();
				resp.forEach(v -> output.add(Booking.fromJSON((JSONObject)v)));
				return output;
			}
			throw new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Booking getReservation(int id) {
		HttpResponse<String> response;
		try {
			response = handler.sendGET("booking/get", Map.of("token", token, "q", "single", "value", ""+id));
			if(response.statusCode() == 200) {
				JSONObject resp = new JSONObject(response.body());
				return Booking.fromJSON(resp);
			}
			throw new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static List<Hotel> getHotels(){
		HttpResponse<String> response;
		try {
			response = handler.sendGET("hotel/get", Map.of("token", token));
			if(response.statusCode() == 200) {
				JSONArray resp = new JSONArray(response.body());
				List<Hotel> output = new LinkedList<>();
				resp.forEach(v -> output.add(Hotel.fromJSON((JSONObject)v)));
				return output;
			}
			throw new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static List<Hotel> getHotels(String name){
		HttpResponse<String> response;
		try {
			response = handler.sendGET("hotel/get", Map.of("token", token, "query", new String(Base64.getEncoder().encode(name.getBytes()), StandardCharsets.UTF_8)));
			if(response.statusCode() == 200) {
				JSONArray resp = new JSONArray(response.body());
				List<Hotel> output = new LinkedList<>();
				resp.forEach(v -> output.add(Hotel.fromJSON((JSONObject)v)));
				return output;
			}
			throw new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
}
