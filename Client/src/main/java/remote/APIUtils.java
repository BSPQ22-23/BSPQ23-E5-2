package remote;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIUtils {
	
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
				}else if(o instanceof Collection)
					output.put(f.getName(), listToJSONArray((Collection<?>)o).toString());
				else if(f.get(o) != null)
					output.put(f.getName(), objectToJSON(f.get(o)).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return output;
	}
	public static String decode(String value) {
		return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
	}
	public static JSONArray listToJSONArray(Collection<?> o) {
		JSONArray array = new JSONArray();
		for(Object obj : o)
			if(obj instanceof Collection)
				listToJSONArray((Collection<?>)obj);
			else if(obj.getClass().equals(String.class))
				array.put(new String(Base64.getEncoder().encode(obj.toString().getBytes()), StandardCharsets.UTF_8));
			else if(obj.getClass().equals(Date.class)) {
				Calendar c = Calendar.getInstance();
				c.setTime((Date)obj);
				array.put(c.get(Calendar.DAY_OF_YEAR)+c.get(Calendar.YEAR)*365);
			} else if(obj.getClass().isPrimitive())
				array.put(obj);
			else
				array.put(objectToJSON(obj));
		return array;
	}
	public static JSONObject objectToJSON(Object o) {
		JSONObject output = new JSONObject();
		try {
			for(Field f : o.getClass().getDeclaredFields()){
				f.setAccessible(true);
				if(Modifier.isTransient(f.getModifiers()) || Modifier.isStatic(f.getModifiers()))
					continue;
				if(f.get(o) instanceof Collection)
					output.put(f.getName(), listToJSONArray((Collection<?>)f.get(o)));
				else if(f.getType().isPrimitive())
					output.put(f.getName(), f.get(o));
				else if(f.getType().equals(Date.class)) {
					Calendar c = Calendar.getInstance();
					c.setTime((Date)f.get(o));
					output.put(f.getName(), c.get(Calendar.DAY_OF_YEAR) + c.get(Calendar.YEAR) * 365);
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
}
