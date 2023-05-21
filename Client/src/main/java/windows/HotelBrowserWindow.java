package windows;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import domain.Hotel;
import language.InternLanguage;
import remote.ClientController;
import remote.ClientController.DownloadedImage;
import remote.ClientController.Response;


public class HotelBrowserWindow extends JFrame  {
	private static final long serialVersionUID = 1L;
	private JTextField searchField;
    private JButton viewHotelButton, homeButton,searchButton;
    public  ClientController controller;
    private static JLabel notAvailableHotel;
    private static JLabel noIconHotel;
    private static Hotel errorHotel = new Hotel(-1, "Disconnected", "---", "The server connection is missing");
    
    static {
    	try {//Java testing
			BufferedImage nahBi = ImageIO.read(new File("src/main/resources/images/hna.png"));
			BufferedImage dhaBi = ImageIO.read(new File("src/main/resources/images/dha.png"));
			notAvailableHotel = new JLabel(new ImageIcon(nahBi.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)));
			noIconHotel = new JLabel(new ImageIcon(dhaBi.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)));
		} catch (IOException e) {
			try {//Compiled jar
				BufferedImage nahBi = ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/hna.png"));
				BufferedImage dhaBi = ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/dha.png"));
				notAvailableHotel = new JLabel(new ImageIcon(nahBi.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)));
				noIconHotel = new JLabel(new ImageIcon(dhaBi.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)));
			} catch(IOException e1) {
				e.printStackTrace();
			}
		}
    }
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HotelBrowserWindow();
            	
            }
        });
    }

    public HotelBrowserWindow() {
    	DefaultListModel<Hotel> hotelListModel = new DefaultListModel<>();
    	try {
    		ClientController.getHotels().forEach(v->hotelListModel.addElement(v));
    	} catch(Exception e) {
    		hotelListModel.addElement(errorHotel);
    	}
    	JList<Hotel> hotelList = new JList<>(hotelListModel);
    	hotelList.setCellRenderer(new ListCellRenderer<Hotel>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Hotel> list, Hotel value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JPanel panel = new JPanel();
				if(value.getId() < 0) {
					panel.add(notAvailableHotel);
				}else {
					try {
						DownloadedImage i = ClientController.downloadImage("hotel/icon/"+value.getId(), 0);
						panel.add(new JLabel(new ImageIcon(i.image.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH))), BorderLayout.WEST);
					} catch(Response r) {
						panel.add(noIconHotel, BorderLayout.WEST);
					}
				}
				
				JPanel panelInfo = new JPanel(new GridLayout(0, 1));
				panelInfo.add(new JLabel(value.getName()));
				panelInfo.add(new JLabel(value.getCity()));
				panel.add(panelInfo, BorderLayout.CENTER);
				
				JButton buttonEnter = new JButton(InternLanguage.translateTxt("go"));
				buttonEnter.addActionListener(v -> {
					//TODO enter detail window
				});
				panel.add(buttonEnter, BorderLayout.EAST);
				
				return panel;
			}
    		
    	});
        setTitle(InternLanguage.translateTxt("title_br"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Create components
        searchField = new JTextField();
        searchButton = new JButton(InternLanguage.translateTxt("search"));
        viewHotelButton = new JButton(InternLanguage.translateTxt("view_Hotel"));
        homeButton = new JButton(InternLanguage.translateTxt("home"));

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                List<Hotel> hotels;
                try {
                	hotels =  ClientController.getHotels(query);
                } catch(Exception ex) {
                	hotels = new LinkedList<>();
                	hotels.add(errorHotel);
                }
                hotelListModel.removeAllElements();
                hotels.forEach(v->hotelListModel.addElement(v));
            }
        });

        viewHotelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to enter the hotel description window
                JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("hotel_DescMen"));
            }
        });

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to return to the home page
                JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("retur_HomePg"));
                dispose();
                MainMenuClient mmc = new MainMenuClient();
                mmc.setVisible(true);
            }
        });

        // Add components to the frame
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JScrollPane hotelListScrollPane = new JScrollPane(hotelList);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewHotelButton);
        buttonPanel.add(homeButton);

        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(hotelListScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



   
   
}