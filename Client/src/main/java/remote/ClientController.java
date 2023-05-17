package remote;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
	/**
	 * A simplified version of an http response and also way to assure you can get either your result or the error message if the server gives one
	 *
	 */
	public static class Response extends RuntimeException {
		private static final long serialVersionUID = 519375302527407530L;
		public final String message;
		public final int status;
		/**
		 * Represents a simplified version of what the server returns, can be thrown
		 * @param status Status of the response, some mapped ones are {@link Response#SUCCESS SUCCESS}, {@link Response#BAD_REQUEST BAD REQUEST}, {@link Response#UNAUTHORIZED UNAUTHORIZED}...
		 * @param message Body of the response or custom message
		 */
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
	/**
	 * Establishes the server connection data
	 * @param sv
	 */
	public static void setServerHandler(ServiceLocator sv) {
		handler = sv;
	}
	private static String token = null;
	private static ServiceLocator handler;
	
	/**
	 * Register a new user
	 * @param g user data to try to register
	 * @return A simplified response, giving in the status the result of the exchange and in the body the error in case of one
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
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
	/**
	 * Tries to log an existing user
	 * @param user Nick of the user
	 * @param password Password of the user not encrypted
	 * @return A simplified response, giving in the status the result of the exchange and in the body the error in case of one
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
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
	/**
	 * Tries to create a reservation
	 * @param b original data of the reservation
	 * @return A simplified version of the result from the server, giving the result type and a comment from the server
	 */
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
	/**
	 * Tries to edit an existing reservation
	 * @param b A reservation with a valid id (one given by the server, not generated locally)
	 * @return A simplified version of the result from the server, giving the result type and a comment from the server
	 */
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
	/**
	 * Deletes an existing booking
	 * @param b A booking with a valid id (one given by the server, not generated locally)
	 * @return A simplified version of the result from the server, giving the result type and a comment from the server
	 */
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
	/**
	 * Tries to register a hotel in the database
	 * @param h The hotel data to upload
	 * @return A simplified version of the result from the server, giving the result type and a comment from the server
	 */
	public static Response createHotel(Hotel h) {
		try {
			HttpResponse<String> response = handler.sendPOST("hotel/create", Map.of("token", token), APIUtils.objectToJSON(h));
			if(response.statusCode() != 200 || h.getIcon() == null)
				return new Response(response.statusCode(), response.body());
			return uploadImage(h.getIcon(), h.getIconFormat(), "hotel/icon/"+response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Tries to upload an image to the server
	 * @param image Image to upload
	 * @param format Format in which the image is encoded (jpg, png, webp...)
	 * @param ctx place where the image goes, determined by the server
	 * @return A simplified version of the result from the server, giving the result type and a comment from the server
	 * @throws IOException
	 */
	public static Response uploadImage(BufferedImage image, String format, String ctx) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format, baos);
		HttpResponse<String> response;
		try {
			response = handler.sendPOST("/resources/upload", Map.of("Content-Type", "image/"+format, "ctx", ctx, "token", token),Base64.getEncoder().encode(baos.toByteArray()));
			return new Response(response.statusCode(), response.body());
		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * Tries to return the list of reservations the user has made
	 * @return A list of reservations where the user is registered
	 */
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
	/**
	 * Get reservations by hotel. Only allowed to the owner of said hotel
	 * @param h A hotel the logged user owns
	 * @return A list of bookings registered to that hotel
	 */
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
	/**
	 * Gets an specific reservation if the user it's in the guest list
	 * @param id id of the booking
	 * @return the booking data
	 */
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
	/**
	 * Returns a random list of hotels to show when no query is set
	 * @return A list of hotels
	 */
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
	/**
	 * Gets a list of hotels according to user input
	 * @param name string the name must contain
	 * @return A list of hotels that comply with the query
	 */
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
