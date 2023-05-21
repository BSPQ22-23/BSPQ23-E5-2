package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
/**
 *  A class that contains common functions for API usage
 *
 */
public class APIUtils {
	/**
	 * Reads the body of an HTTP exchange
	 * @param t exchange to translate
	 * @return The body of the exchange in a string format
	 * @throws IOException if the body isn't readable
	 */
	public static String readBody(HttpExchange t) throws IOException{
		InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder();
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();
    	return buf.toString();
	}
	/**
	 * Macro to get a decoded header value
	 * @param t exchange that contains the header to decode
	 * @param name name of the header
	 * @param def default value of the header in case of not existing
	 * @return the decoded value of the header or def
	 */
	public static String getStringHeader(HttpExchange t, String name, String def) {
		String v = t.getRequestHeaders().getOrDefault(name, List.of(def)).get(0);
		if(v != def && v.strip().length() > 0)
			return new String(Base64.getDecoder().decode(v), StandardCharsets.UTF_8);
		else
			return v;
	}
	/**
	 * Turn a java object into http headers.
	 * This function uses fields to turn it into headers, transient or static fields are ignored
	 * @param o Object to turn into headers
	 * @return hashmap with the header names as keys and header values as value
	 */
	public static HashMap<String, String> objectToHeaders(Object o) {
		HashMap<String, String> output = new HashMap<>();
		for(Field f : o.getClass().getDeclaredFields())
			try {
				f.setAccessible(true);
				if(Modifier.isTransient(f.getModifiers()) || Modifier.isStatic(f.getModifiers()))
					continue;
				if(f.getType().isPrimitive())
					output.put(f.getName(), f.get(o).toString());
				else if(f.getType().equals(String.class))
					output.put(f.getName(), new String(Base64.getEncoder().encode(f.get(o).toString().getBytes()), StandardCharsets.UTF_8));
				else if(f.getType().equals(Date.class)) {
					Calendar c = Calendar.getInstance();
					c.setTime((Date)f.get(o));
					output.put(f.getName(), Long.toString(c.get(Calendar.DAY_OF_YEAR) + c.get(Calendar.YEAR) * 365));
				}else if(f.get(o) instanceof Collection)
					output.put(f.getName(), listToJSONArray((Collection<?>)o).toString());
				else if(f.get(o) != null)
					output.put(f.getName(), objectToJSON(f.get(o)).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		return output;
	}
	/**
	 * Takes a collection and turns it into a JSONArray of JSONObjects
	 * @param o collection to turn into JSONArray
	 * @return the JSONArray representation of the collection
	 */
	public static JSONArray listToJSONArray(Collection<?> o) {
		JSONArray array = new JSONArray();
		for(Object obj : o)
			if(obj instanceof Collection)
				listToJSONArray((Collection<?>)obj);
			else if(obj.getClass().equals(String.class))
				array.put(new String(Base64.getEncoder().encode(obj.toString().getBytes()), StandardCharsets.UTF_8));
			else if(obj instanceof Date) {
				Calendar c = Calendar.getInstance();
				c.setTime((Date)obj);
				array.put(Long.toString(c.get(Calendar.DAY_OF_YEAR) + c.get(Calendar.YEAR) * 365));
			}else if(obj.getClass().isPrimitive())
				array.put(obj);
			else if (o != null)
				array.put(objectToJSON(obj));
		return array;
	}
	/**
	 * Takes a java Object and turns it into a JSONObject, if it's a {@link java.util.Collection Collection} use better {@link APIUtils#listToJSONArray(Collection) listToJSONArray}
	 * as it won't take the un-needed fields.
	 * 
	 * This uses the declared fields of the class and parents to turn it into a {@link org.json.JSONObject JSONObject}, to avoid one set it's value to null or the field to transient or static.
	 * @param o object to turn into a json object
	 * @return JSONObject representation of the input object
	 */
	public static JSONObject objectToJSON(Object o) {
		JSONObject output = new JSONObject();
		try {
			for(Field f : o.getClass().getDeclaredFields()){
				f.setAccessible(true);
				if(Modifier.isTransient(f.getModifiers()) || Modifier.isStatic(f.getModifiers()) || f.get(o) == null)
					continue;
				 if(f.get(o) instanceof Collection)
					output.put(f.getName(), listToJSONArray((Collection<?>)f.get(o)));
				else if(f.getType().isPrimitive())
					output.put(f.getName(), f.get(o));
				else if(f.getType().equals(Date.class)) {
					Calendar c = Calendar.getInstance();
					c.setTime((Date)f.get(o));
					output.put(f.getName(), Long.toString(c.get(Calendar.DAY_OF_YEAR) + c.get(Calendar.YEAR) * 365));
				}else if(f.getType().equals(String.class))
					output.put(f.getName(), new String(Base64.getEncoder().encode(f.get(o).toString().getBytes()), StandardCharsets.UTF_8));
				else if(f.get(o) != null)
					output.put(f.getName(), objectToJSON(f.get(o)));
			}
		} catch (JSONException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return output;
	}
	/**
	 * Quick respond 200 with "ACK" as body
	 * @param t http exchange to respond
	 * @throws IOException
	 */
	public static void respondACK(HttpExchange t) throws IOException {
		respondSuccess(t, "ACK");
	}
	/**
	 * Quick respond 200 with body
	 * @param t http exchange to respond
	 * @throws IOException
	 */
	public static void respondSuccess(HttpExchange t, String message) throws IOException {
		rawResponse(200, t, message);
	}
	/**
	 * Quick respond 500 with body
	 * @param t http exchange to respond
	 * @throws IOException
	 */
	public static void respondInternalError(HttpExchange t, String response) throws IOException {
		rawResponse(500, t, response);
	}
	/**
	 * Quick respond with body
	 * @param t http exchange to respond
	 * @throws IOException
	 */
	public static void rawResponse(int code, HttpExchange t, String response) throws IOException {
		t.sendResponseHeaders(code, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	/**
	 * Quick respond 400 with body
	 * @param t http exchange to respond
	 * @throws IOException
	 */
	public static void respondError(HttpExchange t, String response) throws IOException {
		rawResponse(400, t, response);
	}
	/**
	 * Macro to decode string into a string
	 * @param value base64 string to decode
	 * @retyrb decoded string in utf-8
	 */
	public static String decode(String value) {
		return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
	}
}
