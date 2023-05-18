package com.journaldev.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class JavaInternationalization {
	
	public static String language = "EN";
	private static ResourceBundle bundle = ResourceBundle.getBundle("SystemMessages", Locale.forLanguageTag(JavaInternationalization.language));

	
	public static void changeLanguage(String lan) {
		language = lan;
		bundle 	= ResourceBundle.getBundle("SystemMessages", Locale.forLanguageTag(JavaInternationalization.language));
	}
	
	public static String translateTxt(String txt) {
		return bundle.getString(txt);
	}
    
}