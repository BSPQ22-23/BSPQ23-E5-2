package windows;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import domain.Hotel;
import windows.TranslatableObject.TranslatableJButton;

public class HotelDescriptionWindow extends JFrame{
	
	private JPanel contentPane;
	private UpperMenu upperMenu;
	
	private static final long serialVersionUID = 1L;
	public HotelDescriptionWindow(Hotel h) {
		super(h.getName());
		setSize(450, 320);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel icon;
		try {
			icon = new JLabel(h.getIcon() == null? new ImageIcon(ImageIO.read(HotelBrowserWindow.class.getClassLoader().getResourceAsStream("images/dha.png")).getScaledInstance(200, 200, BufferedImage.SCALE_SMOOTH)): new ImageIcon(h.getIcon().getScaledInstance(200, 200, BufferedImage.SCALE_SMOOTH)));
			contentPane.add(icon, BorderLayout.WEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JPanel panelDown = new JPanel(new GridLayout(1, 0));
		TranslatableJButton back = new TranslatableJButton("back");
		back.addActionListener(v -> {
			HotelBrowserWindow.getInstance().setVisible(true);
			dispose();
		});
		panelDown.add(back);
		TranslatableJButton makeBooking = new TranslatableJButton("book");
		makeBooking.addActionListener(v -> {
			new ReservationWindow(h);
			dispose();
		});
		panelDown.add(makeBooking);
		contentPane.add(panelDown, BorderLayout.SOUTH);
		upperMenu = new UpperMenu( v-> {
       	 new MainMenuClient();
       	 dispose();
       }, back, makeBooking);
       setJMenuBar(upperMenu);
		
		JLabel title = new JLabel(h.getName() + " - " + h.getCity());
		title.setFont(new Font("Calibri", Font.PLAIN, 30));
		contentPane.add(title, BorderLayout.NORTH);
		
		JScrollPane description = new JScrollPane(new JLabel("<html><p style=\"width:155px\">"+h.getInfo()+"</p></html>"));
		contentPane.add(description, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public static void main(String[] args) {
		new HotelDescriptionWindow(new Hotel(1, "Test Hotel", "Test city", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")).setVisible(true);;
	}

}
