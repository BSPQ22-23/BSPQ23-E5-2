package language;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class InternLanguageTest {
	
	@Test 
	public void translation() {
		assertEquals("Hotel name:", InternLanguage.translateTxt("name_cr"));
	}
	
	@Test
	public void translationTwo() {
		assertEquals("App made by Alberto and Andoni", InternLanguage.translateTxt("info"));
	}
	
	@Test
	public void languageChange() {
		
		String txt = InternLanguage.translateTxt("info");
		InternLanguage.changeLanguage("es");
		String txt2 = InternLanguage.translateTxt("info");
		
		assertNotEquals(txt, txt2);
	}
}
