package windows;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import remote.ClientController;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;

    public LoginWindow() {
        super("Login");
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');

        showPasswordCheckbox = new JCheckBox("Show password");
        showPasswordCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                try {
                	if(!username.equals("") && !password.equals("")) {
                		ClientController.login(username, password);
                		openMenu(true);
                		
                	} else {
                		System.out.println("No info");
                	}
                }  catch (Exception e1) {
					e1.printStackTrace();
                }
            }
        });
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	RegistrationWindow registrationWindow = new RegistrationWindow();
            	closeW();
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(new Color(255, 228, 181));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(showPasswordCheckbox);
        panel.add(loginButton);
        panel.add(registerButton);

        this.add(panel);

        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
    
    private void openMenu(boolean isOwner) {
    	if(isOwner) {
    		MainMenuOwner menuOwner = new MainMenuOwner();
	    	menuOwner.setVisible(true);
    	} else {
	    	MainMenuClient menuClient = new MainMenuClient();
	    	menuClient.setVisible(true);
    	}
    	closeW();
    }
    
    private void closeW() {
    	this.dispose();
    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
    }
}
