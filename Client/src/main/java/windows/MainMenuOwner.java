package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import language.InternLanguage;
/**
 * 
 * Home for hotel owners
 *
 */
public class MainMenuOwner extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7388889135006913436L;
		private JLabel welcome, warningL, infoSettings;
	 	private JTextField hotelName, hotelChain, hotelRooms, hotelCity;
	    private JButton hotelEditButton, addHotelButton, searchButton, accountButton, deleteAccButton, logOutButton, changeDataButton, submitHotelButton, clearHotelButton;
	    private JMenuBar menuBar;
	    private JMenu menu, menuH, menuL;
	    private JMenuItem mItem, mItem3, mItem2, mItemES, mItemEN;
	    private JPanel buttonPanel, welcomePanel, browserPanel, accountPanel, pCenter;
	    
	    public MainMenuOwner() {
	        super(InternLanguage.translateTxt("menuOwn"));
	        
	        welcome = new JLabel(InternLanguage.translateTxt("settingInf"));
	        infoSettings = new JLabel(InternLanguage.translateTxt("settingInf"));
	        warningL = new JLabel(InternLanguage.translateTxt("warning"));
	        

	        hotelName = new JTextField(15);
	        hotelChain = new JTextField(15);
	        hotelRooms = new JTextField(5);
	        hotelCity = new JTextField(15);
	        
	        ImageIcon icon = null;
			ImageIcon icon2 = null;
			ImageIcon icon3 = null;
			ImageIcon icon4 = null;
			try {
				icon = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/pencil.png")));
				icon2 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/search.png")));
				icon3 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/account.png")));
				icon4 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/addIcon.png")));
			} catch (Exception e) {
				e.printStackTrace();
			}

	        
	        hotelEditButton = new JButton(resizeIcon(icon, 60, 60));
	        addHotelButton = new JButton(resizeIcon(icon4, 60, 60));
	        searchButton = new JButton(resizeIcon(icon2, 60, 60));
	        accountButton = new JButton(resizeIcon(icon3,60, 60));
	        changeDataButton = new JButton("Change account data");
	        deleteAccButton = new JButton("Delete this account");
	        logOutButton = new JButton("Log out");
	        submitHotelButton = new JButton("Submit Hotel");
	        clearHotelButton = new JButton("Clear Info");
	        
	        menuBar = new JMenuBar();
	        menu = new JMenu(InternLanguage.translateTxt("account"));
	        menuH = new JMenu(InternLanguage.translateTxt("home"));
	        menuL = new JMenu(InternLanguage.translateTxt("language"));
	        mItem = new JMenuItem(InternLanguage.translateTxt("logOut"));
	        mItem2 = new JMenuItem(InternLanguage.translateTxt("returnHome"));
	        mItem3 = new JMenuItem(InternLanguage.translateTxt("exit"));
	        mItemES  = new JMenuItem("Español");
	        mItemEN = new JMenuItem("English");
	        
	        hotelEditButton.addActionListener(this);
	        addHotelButton.addActionListener(this);
	        searchButton.addActionListener(this);
	        accountButton.addActionListener(this);
	        submitHotelButton.addActionListener(this);
	        clearHotelButton.addActionListener(this);
	        
	        pCenter = new JPanel();
	        pCenter.setBackground(new Color(255, 228, 181));

	        
	        GridLayout gridLayout = new GridLayout(0, 2);
	        gridLayout.setVgap(10);
	        gridLayout.setHgap(50);
	        buttonPanel = new JPanel(gridLayout);
	        buttonPanel.setBackground(new Color(255, 228, 181));
	        buttonPanel.add(hotelEditButton);
	        buttonPanel.add(addHotelButton);
	        buttonPanel.add(searchButton);
	        buttonPanel.add(accountButton);
	        
	        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        welcomePanel.setBackground(new Color(135, 206, 250));
	        welcomePanel.add(welcome);
	        
	        GridLayout gridLayout2 = new GridLayout(0, 3);
	        gridLayout2.setVgap(25);
	        gridLayout2.setHgap(10);
	        browserPanel = new JPanel(gridLayout2);
	        browserPanel.setBackground(new Color(255, 228, 181));

	        GridLayout gridLayout3 = new GridLayout(0, 1);
	        gridLayout3.setVgap(15);
	        gridLayout3.setHgap(10);
	        accountPanel = new JPanel(gridLayout3);
	        accountPanel.setBackground(new Color(135, 206, 250));
	        accountPanel.add(infoSettings);
	        accountPanel.add(changeDataButton);
	        accountPanel.add(logOutButton);
	        accountPanel.add(warningL);
	        accountPanel.add(deleteAccButton);

	        
	        pCenter.add(buttonPanel);
	        pCenter.add(browserPanel);
	        pCenter.add(accountPanel);
	        
	        getContentPane().setLayout(new BorderLayout());
	        getContentPane().add(pCenter, BorderLayout.CENTER);
		    accountPanel.setVisible(false);
			browserPanel.setVisible(false);
	        getContentPane().add(welcomePanel, BorderLayout.SOUTH);
	        
	        menuH.add(mItem2);
	        menuH.add(mItem3);
	        menuL.add(mItemES);
	        menuL.add(mItemEN);
	        menu.add(mItem);
	        menuBar.add(menuH);
	        menuBar.add(menu);
	        menuBar.add(menuL);
	        setJMenuBar(menuBar);
	        
	        mItem2.addActionListener(this);
	        mItem3.addActionListener(this);
			mItem2.setEnabled(false);
	        
	        setSize(400, 300);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setVisible(true);
	    }
	    
	    private static Icon resizeIcon(ImageIcon icon, int w, int h) {
	        Image img = icon.getImage();  
	        Image resizedImage = img.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);  
	        return new ImageIcon(resizedImage);
	    }
	    
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == searchButton) {
				buttonPanel.setVisible(false);
				welcomePanel.setVisible(false);
				browserPanel.setVisible(true);
				browserPanel.revalidate();
				browserPanel.repaint();
				mItem2.setEnabled(true);
			
			 } else if (e.getSource() == hotelEditButton) {
				 new HotelEditorWindow();
				
			 } else if (e.getSource() == accountButton) {
				buttonPanel.setVisible(false);
			    welcomePanel.setVisible(false);
			    accountPanel.setVisible(true);
				mItem2.setEnabled(true);
			    
			 } else if (e.getSource() == addHotelButton) {
				 new HotelCreatorWindow();
				
			 } else if (e.getSource() == submitHotelButton) {

				 
			 } else if (e.getSource() == clearHotelButton) {
				 hotelName.setText("");
				 hotelChain.setText("");
				 hotelRooms.setText("");
				 hotelCity.setText("");
				 
		     } else if (e.getSource() == mItem2) {
				 browserPanel.setVisible(false);
				 accountPanel.setVisible(false);
		    	 welcomePanel.setVisible(true);
		    	 buttonPanel.setVisible(true);
				 mItem2.setEnabled(false);
				
				
		     } else if (e.getSource() == mItem3) {
		    	dispose(); 
		     }
		}
		
		     public static void main(String[] args) {
		         new MainMenuOwner();
		     }

}
