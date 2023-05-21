package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import domain.Guest;
import domain.Hotel;
import domain.Room;



public class ReservationWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
/**
 * Launch the application.
 */
public static void main(String[] args) {
	ReservationWindow window = new ReservationWindow(new Hotel(1, "Test Hotel", "Test city", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
	window.setVisible(true);
}

	private UpperMenu upperMenu;
	/**
	 * Create the panel.
	 */
	public ReservationWindow(Hotel h) {
		upperMenu = new UpperMenu( v-> {
       	 	new Thread(() -> new HotelDescriptionWindow(h)).start();
       	 	dispose();
		});
		setJMenuBar(upperMenu);
		setTitle("Booking creation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel upperPanel = new JPanel();
		upperPanel.setBorder(new TitledBorder(null, "Hotel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(upperPanel, BorderLayout.NORTH);
		upperPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		try {
			upperPanel.add(new JLabel(h.getIcon() == null? new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/dha.png")).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)): new ImageIcon(h.getIcon().getScaledInstance(200, 200, BufferedImage.SCALE_SMOOTH))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		upperPanel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(null, "Booking information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		leftPanel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel checkinLabel = new JLabel("Checkin date:");
		panel_1.add(checkinLabel);
		
		JSpinner checkinSpinner = new JSpinner();
		checkinSpinner.setModel(new SpinnerDateModel(new Date(1684620000000L), null, null, Calendar.DAY_OF_YEAR));
		panel_1.add(checkinSpinner);
		
		JPanel panel_1_1 = new JPanel();
		leftPanel.add(panel_1_1);
		panel_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel checkoutLabel = new JLabel("Checkout date:");
		panel_1_1.add(checkoutLabel);
		
		JSpinner checkoutSpinner = new JSpinner();
		checkoutSpinner.setModel(new SpinnerDateModel(new Date(1684620000000L), null, null, Calendar.DAY_OF_YEAR));
		panel_1_1.add(checkoutSpinner);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Room", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JComboBox<Room> comboBox = new JComboBox<Room>();
		panel_2.add(comboBox);
		
		JButton addGuest = new JButton("Add guest");
		leftPanel.add(addGuest);
		
		JButton btnCreate = new JButton("Create booking");
		leftPanel.add(btnCreate);
		
		JList<Guest> guestList = new JList<>();
		guestList.setBorder(null);
		JScrollPane scrollPane = new JScrollPane(guestList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportBorder(new TitledBorder(null, "Guests", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrollPane, BorderLayout.EAST);
	}
}
