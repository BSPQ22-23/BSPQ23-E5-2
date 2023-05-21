package windows;
import javax.swing.*;

import domain.Guest;
import domain.User;
import language.InternLanguage;
import remote.ClientController;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

public class RegistrationWindow extends JFrame implements ActionListener {
	 private JLabel nicknameLabel, passwordLabel, nameLabel, lastNameLabel, idLabel, ageLabel, cityLabel;
	    private JTextField nicknameTextField, passwordTextField, nameTextField, lastNameTextField, idTextField, ageTextField, cityTextField;
	    private JButton submitButton, clearButton;

	    public RegistrationWindow() {
	        super(InternLanguage.translateTxt("register"));

	        nicknameLabel = new JLabel(InternLanguage.translateTxt("username"));
	        passwordLabel = new JLabel(InternLanguage.translateTxt("password"));
	        nameLabel = new JLabel(InternLanguage.translateTxt("name"));
	        lastNameLabel = new JLabel(InternLanguage.translateTxt("lst_Name"));
	        idLabel = new JLabel(InternLanguage.translateTxt("idnum"));
	        ageLabel = new JLabel(InternLanguage.translateTxt("age"));
	        cityLabel = new JLabel(InternLanguage.translateTxt("cityLbl"));

	        nicknameTextField = new JTextField(20);
	        passwordTextField = new JTextField(20);
	        nameTextField = new JTextField(20);
	        lastNameTextField = new JTextField(20);
	        idTextField = new JTextField(20);
	        ageTextField = new JTextField(20);
	        cityTextField = new JTextField(20);

	        submitButton = new JButton(InternLanguage.translateTxt("submit"));
	        clearButton = new JButton(InternLanguage.translateTxt("clear"));

	        submitButton.addActionListener(this);
	        clearButton.addActionListener(this);

	        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
	        formPanel.setBackground(new Color(255, 228, 181));
	        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
	            JOptionPane.showMessageDialog(this, "Thank you for registering, " + name + "!\n" +
	                    "Your nickname is " + nickname + " and your password is " + password);
	            
	            int ageI = Integer.parseInt(age);
	            Guest guest = new Guest(name, lastName, id, ageI, city);
	            User user = new User(nickname, password, guest, false);
	            
	            try {
					ClientController.register(user);
					openMenu(user.isHotelOwner());

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
	    		MainMenuOwner menuOwner = new MainMenuOwner();
		    	menuOwner.setVisible(true);
	    	} else {
		    	MainMenuClient menuClient = new MainMenuClient();
		    	menuClient.setVisible(true);
	    	}
	    	this.dispose();
	    }
	    
	     public static void main(String[] args) {
	         RegistrationWindow registrationForm = new RegistrationWindow();
	     }
	        
}
