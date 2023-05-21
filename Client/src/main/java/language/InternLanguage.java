package language;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class InternLanguage {
	
	public static String language = "en";
	private static ResourceBundle bundle = ResourceBundle.getBundle("ApplicationMessages", Locale.forLanguageTag(InternLanguage.language));

	
	public static void changeLanguage(String lan) {
		language = lan;
		bundle 	= ResourceBundle.getBundle("ApplicationMessages", Locale.forLanguageTag(InternLanguage.language));
	}
	
	public static String translateTxt(String txt) {
		try {
			return bundle.getString(txt);
		} catch(MissingResourceException e) {
			return "{"+txt+"}";
		}
	}
    
}