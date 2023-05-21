package language;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
/**
 * 
 * Manager for project's i18n
 *
 */
public class InternLanguage {
	
	public static String language = "en";
	private static ResourceBundle bundle = ResourceBundle.getBundle("ApplicationMessages", Locale.forLanguageTag(InternLanguage.language));

	/**
	 * Set the UI's languaje
	 * @param lan
	 */
	public static void changeLanguage(String lan) {
		language = lan;
		bundle 	= ResourceBundle.getBundle("ApplicationMessages", Locale.forLanguageTag(InternLanguage.language));
	}
	/**
	 * Turn the key into a readable string in the current language
	 * @param txt key in the i18n properties
	 * @return the string value
	 */
	public static String translateTxt(String txt) {
		try {
			return bundle.getString(txt);
		} catch(MissingResourceException e) {
			return "{"+txt+"}";
		}
	}
    
}