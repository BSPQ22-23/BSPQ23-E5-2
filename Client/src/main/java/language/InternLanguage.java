package language;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternLanguage {
	
	public static String language = "EN";
	private static ResourceBundle bundle = ResourceBundle.getBundle("ApplicationMessages", Locale.forLanguageTag(InternLanguage.language));

	
	public static void changeLanguage(String lan) {
		language = lan;
		bundle 	= ResourceBundle.getBundle("ApplicationMessages", Locale.forLanguageTag(InternLanguage.language));
	}
	
	public static String translateTxt(String txt) {
		return bundle.getString(txt);
	}
    
}