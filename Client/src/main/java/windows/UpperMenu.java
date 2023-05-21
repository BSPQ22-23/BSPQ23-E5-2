package windows;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import language.InternLanguage;

public class UpperMenu extends JMenuBar{
	private static final long serialVersionUID = 1L;
    private JMenu menu, menuH, menuL;
    public final JMenuItem logOutItem, returnHomeItem, exitItem, mItemES, mItemEN;
	public UpperMenu(ActionListener returnHome) {
		menu = new JMenu(InternLanguage.translateTxt("account"));
        menuH = new JMenu(InternLanguage.translateTxt("home"));
        menuL = new JMenu(InternLanguage.translateTxt("language"));
        logOutItem = new JMenuItem(InternLanguage.translateTxt("logOut"));
        returnHomeItem = new JMenuItem(InternLanguage.translateTxt("returnHome"));
        exitItem = new JMenuItem(InternLanguage.translateTxt("exit"));
        mItemES  = new JMenuItem("Español");
        mItemEN = new JMenuItem("English");
        
        menuH.add(returnHomeItem);
        menuH.add(exitItem);
        menuL.add(mItemES);
        menuL.add(mItemEN);
        menu.add(logOutItem);
        add(menuH);
        add(menu);
        add(menuL);
        
        returnHomeItem.addActionListener(returnHome);
        mItemES.addActionListener(v-> InternLanguage.changeLanguage("es"));
        mItemEN.addActionListener(v-> InternLanguage.changeLanguage("en"));
        exitItem.addActionListener(v-> System.exit(0));
        returnHomeItem.setEnabled(false);
	}

}
