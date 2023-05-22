package language;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
	/**
	 * {@link javax.swing.border.TitledBorder TitledBorder} that automatically translates when {@link windows.UpperMenu UpperMenu}'s languaje buttons are used
	 */
	static class TranslatableTitledBorder extends TitledBorder implements TranslatableObject {

		private static final long serialVersionUID = 1L;
		private String key;
		private JComponent comp;
		public TranslatableTitledBorder(JComponent comp, Border border, String key) {
			super(border, InternLanguage.translateTxt(key));
			this.comp = comp;
			this.key = key;
		}

		public TranslatableTitledBorder(JComponent comp, Border object, String translateTxt, int leading, int top, Font object2,
				Color object3) {
			super(object, InternLanguage.translateTxt(translateTxt), leading, top, object2, object3);
			this.key = translateTxt;
			this.comp = comp;
		}

		@Override
		public void refreshText() {
			setTitle(InternLanguage.translateTxt(key));
			comp.getParent().repaint();
			
		}
		 
	 }

}
