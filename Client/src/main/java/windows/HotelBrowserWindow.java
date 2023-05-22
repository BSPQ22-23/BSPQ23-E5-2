package windows;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
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
import windows.TranslatableObject.TranslatableJButton;

/**
 * Window to browse a hotel from a list
 *
 */
public class HotelBrowserWindow extends JFrame  {
	private static final long serialVersionUID = 1L;
	private JTextField searchField;
    private TranslatableJButton homeButton,searchButton;
    public  ClientController controller;
    private static JLabel notAvailableHotel;
    private static BufferedImage noIconHotel;
    private static Hotel errorHotel = new Hotel(-1, "Disconnected", "---", "The server connection is missing");
    private UpperMenu upperMenu;
    static {
		try {//Compiled jar
			BufferedImage nahBi = ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/hna.png"));
			noIconHotel = ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/dha.png"));
			notAvailableHotel = new JLabel(new ImageIcon(nahBi.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)));
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HotelBrowserWindow();
            	
            }
        });
    }
    private static HotelBrowserWindow instance;
    public static HotelBrowserWindow getInstance() {
    	if(instance == null)
    		instance = new HotelBrowserWindow();
    	return instance;
    }
    
    private HotelBrowserWindow() {
    	DefaultListModel<Hotel> hotelListModel = new DefaultListModel<>();
    	try {
    		ClientController.getHotels().forEach(v->hotelListModel.addElement(v));
    	} catch(Exception e) {
    		hotelListModel.addElement(errorHotel);
    	}
    	JList<Hotel> hotelList = new JList<>(hotelListModel);
    	hotelList.setCellRenderer(new ListCellRenderer<Hotel>() {
    		private HashMap<Hotel, JLabel> im = new HashMap<>();
			@Override
			public Component getListCellRendererComponent(JList<? extends Hotel> list, Hotel value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JPanel panel = new JPanel();
				if(value.getId() < 0) {
					panel.add(notAvailableHotel);
				}else {
					if(im.get(value) == null)
						try {
							if(value.getIcon() == null) {
								DownloadedImage i = ClientController.downloadImage("hotel/icon/"+value.getId(), 0);
								if(i.image != null)
									value.setIcon(i.image, i.format);
								else
									value.setIcon(noIconHotel, "png");
							}
						} catch(Response r) {
							value.setIcon(noIconHotel, "png");
						} finally {
							im.put(value, new JLabel(new ImageIcon(value.getIcon().getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH))));
						}
					panel.add(im.get(value));
				}
				JPanel panelInfo = new JPanel(new GridLayout(0, 1));
				panelInfo.add(new JLabel(value.getName()));
				panelInfo.add(new JLabel(value.getCity()));
				panel.add(panelInfo, BorderLayout.CENTER);
				
				return panel;
			}
    		
    	});
		hotelList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = hotelList.locationToIndex(e.getPoint());
			    Hotel item = (Hotel) hotelList.getModel().getElementAt(index);
			    new HotelDescriptionWindow(item).setVisible(true);
			    setVisible(false);
			    
			}
		});
        setTitle(InternLanguage.translateTxt("title_br"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Create components
        searchField = new JTextField();
        searchButton = new TranslatableJButton("search");
        homeButton = new TranslatableJButton("home");

        upperMenu = new UpperMenu( v-> {
          	 new Thread(() -> new MainMenuClient()).start();
          	 dispose();
          }, searchButton, homeButton);
          setJMenuBar(upperMenu);
        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                List<Hotel> hotels;
                try {
                	if(query.strip().equals(""))
                		hotels =  ClientController.getHotels();
                	else
                		hotels =  ClientController.getHotels(query);
                } catch(Exception ex) {
                	hotels = new LinkedList<>();
                	hotels.add(errorHotel);
                }
                hotelListModel.removeAllElements();
                hotels.forEach(v->hotelListModel.addElement(v));
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
        buttonPanel.add(homeButton);

        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(hotelListScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



   
   
}