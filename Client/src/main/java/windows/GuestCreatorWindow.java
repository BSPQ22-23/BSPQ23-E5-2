package windows;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domain.Guest;
import language.InternLanguage;

/**
 * Window to add guests to a booking from {@link windows.RegistrationWindow RegistrationWindow}
 *
 */
public class GuestCreatorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField idField;
	private JTextField ageField;
	private JTextField cityField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DefaultListModel<Guest> g = new DefaultListModel<>();
					GuestCreatorWindow frame = new GuestCreatorWindow(g);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuestCreatorWindow(DefaultListModel<Guest> guestList) {
		setTitle(InternLanguage.translateTxt("add_guest"));
		setBounds(100, 100, 480, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelLeft = new JPanel();
		contentPane.add(panelLeft);
		panelLeft.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panelLeft.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblNewLabel = new JLabel(InternLanguage.translateTxt("name"));
		panel.add(lblNewLabel);
		
		nameField = new JTextField();
		panel.add(nameField);
		nameField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panelLeft.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblId = new JLabel(InternLanguage.translateTxt("idnum"));
		panel_2.add(lblId);
		
		idField = new JTextField();
		idField.setColumns(10);
		panel_2.add(idField);
		
		JPanel panel_4 = new JPanel();
		panelLeft.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblCityOfProvenance = new JLabel(InternLanguage.translateTxt("cityLbl"));
		panel_4.add(lblCityOfProvenance);
		
		cityField = new JTextField();
		cityField.setColumns(10);
		panel_4.add(cityField);
		
		JPanel panelRight = new JPanel();
		contentPane.add(panelRight);
		panelRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panelRight.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblSurname = new JLabel(InternLanguage.translateTxt("lst_Name"));
		panel_1.add(lblSurname);
		
		surnameField = new JTextField();
		surnameField.setColumns(10);
		panel_1.add(surnameField);
		
		JPanel panel_3 = new JPanel();
		panelRight.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblAge = new JLabel(InternLanguage.translateTxt("age"));
		panel_3.add(lblAge);
		
		ageField = new JTextField();
		ageField.setColumns(10);
		ageField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!(c >= '0') || !(c <= '9'))
					e.consume();
			}
		});
		panel_3.add(ageField);
		
		JPanel panel_5 = new JPanel();
		panelRight.add(panel_5);
		panel_5.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton createButton = new JButton(InternLanguage.translateTxt("register"));
		createButton.addActionListener(v -> {
			if(
				nameField   .getText().strip().equals("" ) || 
				idField     .getText().strip().equals("" ) || 
				surnameField.getText().strip().equals("" ) ||
				ageField    .getText()        .equals("" ) || 
				ageField    .getText()        .equals("0") ||
				cityField   .getText().strip().equals("" )
			) {
				JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("wrong_val"));
				return;
			}
			guestList.addElement(new Guest(nameField.getText(), surnameField.getText(), idField.getText(), Integer.parseInt(ageField.getText()), cityField.getText()));
			dispose();
		});
		panel_5.add(createButton);
		
		JButton backButton = new JButton(InternLanguage.translateTxt("back"));
		backButton.addActionListener(v ->{
			dispose();
		});
		panel_5.add(backButton);
		setResizable(false);
		setVisible(true);
	}

}
