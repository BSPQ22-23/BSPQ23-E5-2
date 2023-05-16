package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainMenuOwner extends JFrame implements ActionListener {
	 	private JLabel welcome, label, warningL, infoSettings, hotelNameL, hotelChainL, hotelRoomsL, hotelCityL;
	 	private JTextField search, hotelName, hotelChain, hotelRooms, hotelCity;
	    private JButton hotelEditButton, addHotelButton, searchButton, accountButton, searchHotelButton, deleteAccButton, logOutButton, changeDataButton, submitHotelButton, clearHotelButton;
	    private JMenuBar menuBar;
	    private JMenu menu, menuH;
	    private JMenuItem mItem, mItem3, mItem2;
	    private JPanel buttonPanel, welcomePanel, searchPanel, browserPanel, accountPanel, pCenter, addPanel;
	    
	    public MainMenuOwner() {
	        super("Menu for Owner");
	        
	        welcome = new JLabel("Welcome to the Hotel Management App for Owners");
	        label = new  JLabel("My hotel list: ");
	        infoSettings = new JLabel("Welcome to settings. Yo may edit your account information.");
	        warningL = new JLabel("Warning! Your account and your data will be deleted forever.");
	        hotelNameL = new JLabel("Hotel Name: ");
	        hotelChainL = new JLabel("Hotel Chain: ");
	        hotelRoomsL = new JLabel("Room quantity: ");
	        hotelCityL = new JLabel("Location: ");
	        
	        search = new JTextField(20);
	        hotelName = new JTextField(15);
	        hotelChain = new JTextField(15);
	        hotelRooms = new JTextField(5);
	        hotelCity = new JTextField(15);
	        
	        ImageIcon icon = new ImageIcon("../photos/pencil.png");
	        ImageIcon icon2 = new ImageIcon("../photos/search.png");
	        ImageIcon icon3 = new ImageIcon("../photos/account.png");
	        ImageIcon icon4 = new ImageIcon("../photos/addIcon.png");

	        
	        hotelEditButton = new JButton(resizeIcon(icon, 60, 60));
	        addHotelButton = new JButton(resizeIcon(icon4, 60, 60));
	        searchButton = new JButton(resizeIcon(icon2, 60, 60));
	        accountButton = new JButton(resizeIcon(icon3,60, 60));
	        searchHotelButton = new JButton(resizeIcon(icon2, 10, 10));
	        changeDataButton = new JButton("Change account data");
	        deleteAccButton = new JButton("Delete this account");
	        logOutButton = new JButton("Log out");
	        submitHotelButton = new JButton("Submit Hotel");
	        clearHotelButton = new JButton("Clear Info");
	        
	        menuBar = new JMenuBar();
	        menu = new JMenu("Account");
	        menuH = new JMenu("Home");
	        mItem = new JMenuItem("Log Out");
	        mItem2 = new JMenuItem("Return Home");
	        mItem3 = new JMenuItem("Exit");
	        
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
	        
	        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        searchPanel.setBackground(new Color(135, 206, 250));
	        searchPanel.add(label);
	        searchPanel.add(search);
	        searchPanel.add(searchHotelButton);
	        
	        GridLayout gridLayout4 = new GridLayout(0, 2);
	        gridLayout4.setVgap(20);
	        gridLayout4.setHgap(20);
	        addPanel = new JPanel(gridLayout4);
	        addPanel.setBackground(new Color(255, 228, 181));
	        addPanel.add(hotelNameL);
	        addPanel.add(hotelName);
	        addPanel.add(hotelChainL);
	        addPanel.add(hotelChain);
	        addPanel.add(hotelRoomsL);
	        addPanel.add(hotelRooms);
	        addPanel.add(hotelCityL);
	        addPanel.add(hotelCity);
	        addPanel.add(submitHotelButton);
	        addPanel.add(clearHotelButton);
	        
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
	        pCenter.add(addPanel);
	        
	        getContentPane().setLayout(new BorderLayout());
	        getContentPane().add(pCenter, BorderLayout.CENTER);
		    accountPanel.setVisible(false);
			browserPanel.setVisible(false);
			addPanel.setVisible(false);
			getContentPane().add(searchPanel, BorderLayout.NORTH);
		    searchPanel.setVisible(false);
	        getContentPane().add(welcomePanel, BorderLayout.SOUTH);
	        
	        menuH.add(mItem2);
	        menuH.add(mItem3);

	        menu.add(mItem);
	        menuBar.add(menuH);
	        menuBar.add(menu);
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
				searchPanel.setVisible(true);
				browserPanel.setVisible(true);
				browserPanel.revalidate();
				browserPanel.repaint();
				mItem2.setEnabled(true);

				
			 } else if (e.getSource() == accountButton) {
				buttonPanel.setVisible(false);
			    welcomePanel.setVisible(false);
			    accountPanel.setVisible(true);
				mItem2.setEnabled(true);
			    
			 } else if (e.getSource() == addHotelButton) {
				 HotelCreatorWindow hotelWindow = new HotelCreatorWindow();
				
			 } else if (e.getSource() == submitHotelButton) {

				 
			 } else if (e.getSource() == clearHotelButton) {
				 hotelName.setText("");
				 hotelChain.setText("");
				 hotelRooms.setText("");
				 hotelCity.setText("");
				 
		     } else if (e.getSource() == mItem2) {
		    	 searchPanel.setVisible(false);
				 browserPanel.setVisible(false);
				 addPanel.setVisible(false);
				 accountPanel.setVisible(false);
		    	 welcomePanel.setVisible(true);
		    	 buttonPanel.setVisible(true);
				 mItem2.setEnabled(false);
				
				
		     } else if (e.getSource() == mItem3) {
		    	dispose(); 
		     }
		}
		
		     public static void main(String[] args) {
		         MainMenuOwner menuOw = new MainMenuOwner();
		     }

}
