package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import language.TranslatableObject.TranslatableJButton;
import language.TranslatableObject.TranslatableJLabel;

/**
 * 
 * Main menu for not hotel owners
 *
 */
public class MainMenuClient extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8376278722958811219L;
	private TranslatableJLabel welcome, info, info2, info3, info4, info5, warningL, infoSettings;
    private JButton hotelButton, searchButton, accountButton, contactButton, advSettingsButton, infoButton;
    private TranslatableJButton deleteAccButton, logOutButton, changeDataButton;
    private JPanel buttonPanel, welcomePanel, infoPanel, pCenter, settingsPanel;
    private UpperMenu upperMenu;
    
    public MainMenuClient(){
        super("Menu");    
        
        welcome = new TranslatableJLabel("welcome");
        info = new TranslatableJLabel("info");
        info2 = new TranslatableJLabel("info2");
        info3 = new TranslatableJLabel("info3");
        info4 = new TranslatableJLabel("info4");
        info5 = new TranslatableJLabel("info5");
        infoSettings = new TranslatableJLabel("settingInf");
        warningL = new TranslatableJLabel("warning");

        ImageIcon icon= null;
		ImageIcon icon2= null;
		ImageIcon icon3= null;
		ImageIcon icon4= null;
		ImageIcon icon5= null;
		ImageIcon icon6= null;
		try {
			icon = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/home.png")));
			icon2 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/search.png")));
			icon3 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/account.png")));
			icon4 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/contact.png")));
			icon5 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/settings.png")));
			icon6 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/info.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        hotelButton = new JButton(resizeIcon(icon, 60, 60));
        searchButton = new JButton(resizeIcon(icon2, 60, 60));
        accountButton = new JButton(resizeIcon(icon3,60, 60));
        contactButton = new JButton(resizeIcon(icon4, 60, 60));
        advSettingsButton = new JButton(resizeIcon(icon5, 60, 60));
        infoButton = new JButton(resizeIcon(icon6, 60, 60));

        changeDataButton = new TranslatableJButton("ch_acc_dt");
        deleteAccButton = new TranslatableJButton("dlt_acc");
        logOutButton = new TranslatableJButton("logout");
        
        hotelButton.addActionListener(this);
        searchButton.addActionListener(this);
        accountButton.addActionListener(this);
        contactButton.addActionListener(this);
        advSettingsButton.addActionListener(this);
        infoButton.addActionListener(this);
        
        pCenter = new JPanel();
        pCenter.setBackground(new Color(255, 228, 181));

        
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(10);
        gridLayout.setHgap(50);
        buttonPanel = new JPanel(gridLayout);
        buttonPanel.setBackground(new Color(255, 228, 181));
        buttonPanel.add(hotelButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(advSettingsButton);
        buttonPanel.add(infoButton);
        
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel.setBackground(new Color(135, 206, 250));
        welcomePanel.add(welcome);
        
		GridLayout gridLayout2 = new GridLayout(0, 1);
		gridLayout2.setVgap(15);
        gridLayout2.setHgap(10);
        infoPanel = new JPanel(gridLayout2);
        infoPanel.setBackground(new Color(135, 206, 250));
        infoPanel.add(info);
        infoPanel.add(info2);
        infoPanel.add(info3);
        infoPanel.add(info4);
        infoPanel.add(info5);
        
        GridLayout gridLayout3 = new GridLayout(0, 1);
        gridLayout3.setVgap(15);
        gridLayout3.setHgap(10);
        settingsPanel = new JPanel(gridLayout3);
        infoPanel.setBackground(new Color(135, 206, 250));
        settingsPanel.add(infoSettings);
        settingsPanel.add(changeDataButton);
        settingsPanel.add(logOutButton);
        settingsPanel.add(warningL);
        settingsPanel.add(deleteAccButton);

        
        pCenter.add(buttonPanel);
        pCenter.add(infoPanel);

        pCenter.add(settingsPanel);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pCenter, BorderLayout.CENTER);
	    infoPanel.setVisible(false);

		settingsPanel.setVisible(false);
        getContentPane().add(welcomePanel, BorderLayout.SOUTH);
        
        upperMenu = new UpperMenu( v-> {
        	 infoPanel.setVisible(false);
			 settingsPanel.setVisible(false);
			 setSize(400, 300);
			 pCenter.setVisible(true);
	    	 welcomePanel.setVisible(true);
	    	 buttonPanel.setVisible(true);
			 upperMenu.returnHomeItem.setEnabled(false);
        }, welcome, info, info2, info3, info4, info5, infoSettings, warningL, changeDataButton, deleteAccButton, logOutButton);
        
        upperMenu.returnHomeItem.setVisible(false);
        setJMenuBar(upperMenu);
        
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
		
		if (e.getSource() == hotelButton) {
			
			//noting
			
		} else if (e.getSource() == searchButton) {
			
			HotelBrowserWindow.getInstance().setVisible(true);
			
		 } else if (e.getSource() == infoButton) {
			 buttonPanel.setVisible(false);
		     welcomePanel.setVisible(false);
		     infoPanel.setVisible(true);
		     upperMenu.returnHomeItem.setEnabled(true);
		    
		 } else if (e.getSource() == advSettingsButton) {
			 buttonPanel.setVisible(false);
			 welcomePanel.setVisible(false);
			 settingsPanel.setVisible(true);
			 upperMenu.returnHomeItem.setEnabled(true);
			    
	     }
	}
		
     public static void main(String[] args) {
         new MainMenuClient();
     }

}
