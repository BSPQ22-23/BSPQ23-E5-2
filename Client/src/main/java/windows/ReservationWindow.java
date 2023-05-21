package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import domain.Booking;
import domain.Guest;
import domain.Hotel;
import domain.Room;
import language.InternLanguage;
import remote.ClientController;
import windows.TranslatableObject.TranslatableJLabel;



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
	Hotel h = new Hotel(1, "Test Hotel", "Test city", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
	h.addRoom(new Room(100, "Single", 1, 15, 50, h));
	ReservationWindow window = new ReservationWindow(h);
	window.setVisible(true);
}

	private UpperMenu upperMenu;
	/**
	 * Create the panel.
	 */
	public ReservationWindow(Hotel h) {
		setTitle(InternLanguage.translateTxt("book_cr"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel upperPanel = new JPanel();
		upperPanel.setBorder(new TitledBorder(null, InternLanguage.translateTxt("hotel"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		
		JLabel lblNewLabel = new JLabel(h.getName());
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(h.getCity());
		panel.add(lblNewLabel_1);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(null, InternLanguage.translateTxt("book_inf"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		leftPanel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		TranslatableJLabel checkinLabel = new TranslatableJLabel("chin_dt");
		panel_1.add(checkinLabel);
		
		SimpleDateFormat model = new SimpleDateFormat("dd/MM/yyyy");
		JSpinner checkinSpinner = new JSpinner();
		checkinSpinner.setModel(new SpinnerDateModel(new Date(System.currentTimeMillis()), null, null, Calendar.DAY_OF_YEAR));
		checkinSpinner.setEditor(new JSpinner.DateEditor(checkinSpinner, model.toPattern()));
		panel_1.add(checkinSpinner);
		
		JPanel panel_1_1 = new JPanel();
		leftPanel.add(panel_1_1);
		panel_1_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		TranslatableJLabel checkoutLabel = new TranslatableJLabel("chot_dt");
		panel_1_1.add(checkoutLabel);
		upperMenu = new UpperMenu( v-> {
       	 	new Thread(() -> new HotelDescriptionWindow(h)).start();
       	 	dispose();
		}, checkoutLabel, checkinLabel);
		setJMenuBar(upperMenu);
		JSpinner checkoutSpinner = new JSpinner();
		checkoutSpinner.setModel(new SpinnerDateModel(new Date(System.currentTimeMillis()), null, null, Calendar.DAY_OF_YEAR));
		checkoutSpinner.setEditor(new JSpinner.DateEditor(checkoutSpinner, model.toPattern()));
		panel_1_1.add(checkoutSpinner);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, InternLanguage.translateTxt("room"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		DefaultComboBoxModel<Room> dcbm = new DefaultComboBoxModel<>();
		dcbm.addAll(h.getRooms());
		DefaultListModel<Guest> guestListModel = new DefaultListModel<Guest>();
		JComboBox<Room> comboBox = new JComboBox<Room>(dcbm);
		comboBox.addActionListener(v -> {
			if(((Room)comboBox.getSelectedItem()).getNumMaxGuests() < guestListModel.getSize()) {
				JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("tooManyGuests"));
				guestListModel.removeAllElements();
			}
		});
		panel_2.add(comboBox);
		
		JButton addGuest = new JButton(InternLanguage.translateTxt("add_guest"));
		leftPanel.add(addGuest);
		
		JButton btnCreate = new JButton(InternLanguage.translateTxt("book_cre"));
		btnCreate.addActionListener(v -> {
			if(
					((Date)checkinSpinner.getValue()).compareTo((Date)checkoutSpinner.getValue()) < 0 &&
					comboBox.getSelectedIndex() != -1) {
				List<Guest> guests = new LinkedList<>();
				for(int i = 0; i<guestListModel.getSize(); i++)
					guests.add(guestListModel.get(i));
				ClientController.createReservation(new Booking(0, (Date)checkinSpinner.getValue(), (Date)checkoutSpinner.getValue(), (Room)comboBox.getSelectedItem(), guests));
			} else {
				JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("wrong_val"));
			}
		});
		leftPanel.add(btnCreate);
		
		JList<Guest> guestList = new JList<>(guestListModel);
		addGuest.addActionListener(v ->{
			if(comboBox.getSelectedIndex() == -1 || ((Room)comboBox.getSelectedItem()).getNumMaxGuests() > guestListModel.getSize())
				new GuestCreatorWindow(guestListModel);
			else
				JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("tooManyGuests"));
		});
		JScrollPane scrollPane = new JScrollPane(guestList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportBorder(new TitledBorder(null, InternLanguage.translateTxt("guests"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrollPane, BorderLayout.EAST);
	}
}
