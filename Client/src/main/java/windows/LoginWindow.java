package windows;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import language.InternLanguage;
import remote.ClientController;
import remote.ClientController.Response;
import remote.ServiceLocator;

public class LoginWindow extends JFrame {
	private static final long serialVersionUID = 9081802356867145951L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;

    public LoginWindow() {
        super(InternLanguage.translateTxt("login"));
        JLabel usernameLabel = new JLabel(InternLanguage.translateTxt("username"));
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel(InternLanguage.translateTxt("password"));
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');

        showPasswordCheckbox = new JCheckBox(InternLanguage.translateTxt("sh_Password"));
        showPasswordCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });

        JButton loginButton = new JButton(InternLanguage.translateTxt("login"));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                Response resp = new Response(10,"");
                try {
                	if(!username.equals("") && !password.equals("")) {
                		
                		resp = ClientController.login(username, password);
                	} else {
                		System.out.println(InternLanguage.translateTxt("no_Info"));
                	}
                	if(resp.status == Response.SUCCESS) {
                		openMenu(ClientController.isHotelOwner());
                	} else {
                		messageWrong();
                	}
                	
                }  catch (Exception e1) {
					e1.printStackTrace();
                }
            }
        });
        
        JButton registerButton = new JButton(InternLanguage.translateTxt("register"));
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new RegistrationWindow();
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
    
    private void messageWrong() {
        JOptionPane.showMessageDialog(this, "Your username or password is incorrect");
    }
    
    private void closeW() {
    	this.dispose();
    }

    public static void main(String[] args) {
    	try {
			ClientController.setServerHandler(new ServiceLocator("localhost", 8000));
			new LoginWindow();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
