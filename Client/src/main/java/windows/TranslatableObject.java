package windows;

import javax.swing.JButton;
import javax.swing.JLabel;

import language.InternLanguage;

public interface TranslatableObject{
	public void refreshText();
	
	static class TranslatableJLabel extends JLabel implements TranslatableObject{
		private static final long serialVersionUID = -5533706707148258239L;
		private String id;
		
		public TranslatableJLabel(String id) {
			super(InternLanguage.translateTxt(id));
			this.id = id;
		}
		public void refreshText() {
			setText(InternLanguage.translateTxt(id));
		}
	}
	static class TranslatableJButton extends JButton implements TranslatableObject{
		private static final long serialVersionUID = 1464689730598597451L;
		private String id;
		
		public TranslatableJButton(String id) {
			super(InternLanguage.translateTxt(id));
			this.id = id;
		}
		public void refreshText() {
			setText(InternLanguage.translateTxt(id));
		}
		
	}

}
