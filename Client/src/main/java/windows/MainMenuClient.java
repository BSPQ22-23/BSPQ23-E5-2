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
import javax.swing.JLabel;
import javax.swing.JPanel;

import language.InternLanguage;

public class MainMenuClient extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8376278722958811219L;
	private JLabel welcome, info, info2, info3, info4, info5, warningL, infoSettings;
    private JButton hotelButton, searchButton, accountButton, contactButton, advSettingsButton, infoButton, deleteAccButton, logOutButton, changeDataButton;
    private JPanel buttonPanel, welcomePanel, infoPanel, pCenter, settingsPanel;
    private UpperMenu upperMenu;
    
    public MainMenuClient() throws IOException {
        super("Menu");    
        
        welcome = new JLabel(InternLanguage.translateTxt("welcome"));
        info = new JLabel(InternLanguage.translateTxt("info"));
        info2 = new JLabel(InternLanguage.translateTxt("info2"));
        info3 = new JLabel(InternLanguage.translateTxt("info3"));
        info4 = new JLabel(InternLanguage.translateTxt("info4"));
        info5 = new JLabel(InternLanguage.translateTxt("info5"));
        infoSettings = new JLabel(InternLanguage.translateTxt("settingInf"));
        warningL = new JLabel(InternLanguage.translateTxt("warning"));

        ImageIcon icon = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/home.png")));
        ImageIcon icon2 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/search.png")));
        ImageIcon icon3 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/account.png")));
        ImageIcon icon4 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/contact.png")));
        ImageIcon icon5 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/settings.png")));
        ImageIcon icon6 = new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/info.png")));
        
        hotelButton = new JButton(resizeIcon(icon, 60, 60));
        searchButton = new JButton(resizeIcon(icon2, 60, 60));
        accountButton = new JButton(resizeIcon(icon3,60, 60));
        contactButton = new JButton(resizeIcon(icon4, 60, 60));
        advSettingsButton = new JButton(resizeIcon(icon5, 60, 60));
        infoButton = new JButton(resizeIcon(icon6, 60, 60));

        changeDataButton = new JButton("Change account data");
        deleteAccButton = new JButton("Delete this account");
        logOutButton = new JButton("Log out");
        
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
        });
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

			buttonPanel.setVisible(false);
			welcomePanel.setVisible(false);
			pCenter.setVisible(false);
			setSize(700, 600);
			upperMenu.returnHomeItem.setEnabled(true);
			
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
		
     public static void main(String[] args) throws IOException {
         new MainMenuClient();
     }

}
