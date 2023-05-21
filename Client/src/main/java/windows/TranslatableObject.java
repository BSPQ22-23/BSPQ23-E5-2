package windows;

import javax.swing.JButton;
import javax.swing.JLabel;

import language.InternLanguage;

/**
 * An interface to allow object translation from {@link windows.UpperMenu UpperMenu}'s languaje options in real time
 *
 */
public interface TranslatableObject{
	/**
	 * Function to translate the text in the object when the languaje is changed
	 */
	public void refreshText();
	
	/**
	 * {@link javax.swing.JLabel JLabel} that automatically translates when {@link windows.UpperMenu UpperMenu}'s languaje buttons are used 
	 * @author a-rasines
	 *
	 */
	static class TranslatableJLabel extends JLabel implements TranslatableObject{
		private static final long serialVersionUID = -5533706707148258239L;
		private String id;
		
		/**
		 * {@link javax.swing.JLabel JLabel} that automatically translates when {@link windows.UpperMenu UpperMenu}'s languaje buttons are used
		 * @param id key in the properties of i18n
		 */
		public TranslatableJLabel(String id) {
			super(InternLanguage.translateTxt(id));
			this.id = id;
		}
		/**
		 * Function to translate the text in the object when the languaje is changed
		 */
		public void refreshText() {
			setText(InternLanguage.translateTxt(id));
		}
	}
	/**
	 * {@link javax.swing.JButton JButton} that automatically translates when {@link windows.UpperMenu UpperMenu}'s languaje buttons are used
	 */
	static class TranslatableJButton extends JButton implements TranslatableObject{
		private static final long serialVersionUID = 1464689730598597451L;
		private String id;
		
		/**
		 * {@link javax.swing.JButton JButton} that automatically translates when {@link windows.UpperMenu UpperMenu}'s languaje buttons are used
		 * @param id key in the properties of i18n
		 */
		public TranslatableJButton(String id) {
			super(InternLanguage.translateTxt(id));
			this.id = id;
		}
		/**
		 * Function to translate the text in the object when the languaje is changed
		 */
		public void refreshText() {
			setText(InternLanguage.translateTxt(id));
		}
		
	}

}
