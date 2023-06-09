package windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Guest;
import domain.User;
import language.InternLanguage;
import remote.ClientController;
import remote.ClientController.Response;

/**
 * 
 * Window to register a new user
 *
 */
public class RegistrationWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel nicknameLabel, passwordLabel, nameLabel, lastNameLabel, idLabel, ageLabel, cityLabel, ownerLabel;
    private JTextField nicknameTextField, passwordTextField, nameTextField, lastNameTextField, idTextField, ageTextField, cityTextField;
    private JButton submitButton, clearButton;
    private JCheckBox ownership;

    public RegistrationWindow() {
        super(InternLanguage.translateTxt("register"));

        nicknameLabel = new JLabel(InternLanguage.translateTxt("username"));
        passwordLabel = new JLabel(InternLanguage.translateTxt("password"));
        nameLabel = new JLabel(InternLanguage.translateTxt("name"));
        lastNameLabel = new JLabel(InternLanguage.translateTxt("lst_Name"));
        idLabel = new JLabel(InternLanguage.translateTxt("idnum"));
        ageLabel = new JLabel(InternLanguage.translateTxt("age"));
        cityLabel = new JLabel(InternLanguage.translateTxt("cityLbl"));
        ownerLabel = new JLabel(InternLanguage.translateTxt("ownership"));

        nicknameTextField = new JTextField(20);
        passwordTextField = new JTextField(20);
        nameTextField = new JTextField(20);
        lastNameTextField = new JTextField(20);
        idTextField = new JTextField(20);
        ageTextField = new JTextField(20);
        cityTextField = new JTextField(20);

        submitButton = new JButton(InternLanguage.translateTxt("submit"));
        clearButton = new JButton(InternLanguage.translateTxt("clear"));
        
        ownership = new JCheckBox();
        
        submitButton.addActionListener(this);
        clearButton.addActionListener(this);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.setBackground(new Color(255, 228, 181));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ageTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!(c >= '0') || !(c <= '9'))
					e.consume();
			}
		});
        
        formPanel.add(nicknameLabel);
        formPanel.add(nicknameTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordTextField);
        formPanel.add(nameLabel);
        formPanel.add(nameTextField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameTextField);
        formPanel.add(idLabel);
        formPanel.add(idTextField);
        formPanel.add(ageLabel);
        formPanel.add(ageTextField);
        formPanel.add(cityLabel);
        formPanel.add(cityTextField);
        formPanel.add(ownerLabel);
        formPanel.add(ownership);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(135, 206, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); 
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

   
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String nickname = nicknameTextField.getText();
            String password = passwordTextField.getText();
            String name = nameTextField.getText();
            String lastName = lastNameTextField.getText();
            String id = idTextField.getText();
            String age = ageTextField.getText();
            String city = cityTextField.getText();
            Boolean owner = ownership.isSelected();
            
            int ageI = Integer.parseInt(age);
            Guest guest = new Guest(name, lastName, id, ageI, city);
            User user = new User(nickname, password, guest, owner);
            
            try {
				Response res = ClientController.register(user);
				if(res.status == Response.SUCCESS) {
					JOptionPane.showMessageDialog(this, "Thank you for registering, " + name + "!\n" +
		                    "Your nickname is " + nickname + " and your password is " + password);
					openMenu(user.isHotelOwner());
				} else
					JOptionPane.showMessageDialog(null, "Registration failed: " + res.message);

			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
            
        } else if (e.getSource() == clearButton) {
        	 nicknameTextField.setText("");
             passwordTextField.setText("");
             nameTextField.setText("");
             lastNameTextField.setText("");
             idTextField.setText("");
             ageTextField.setText("");
             cityTextField.setText("");
         }
     }

    private void openMenu(boolean isOwner) {
    	if(isOwner) {
    		new MainMenuOwner();
    	} else {
	    	new MainMenuClient();
    	}
    	this.dispose();
    }
    
     public static void main(String[] args) {
         new RegistrationWindow();
     }
	        
}
