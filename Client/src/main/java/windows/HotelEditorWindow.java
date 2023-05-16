package windows;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.Guest;
import domain.Hotel;
import domain.User;
import remote.ClientController;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HotelEditorWindow extends JFrame implements ActionListener {
	 	private JLabel roomNum, roomType, roomMax, roomSpace, roomPrice, roomHotel;
	    private JTextField idF, nameF, cityF, infoF;
	    private JButton submitButton, clearButton, imageButton;
	    private JFileChooser fileChooser;
	    private FileNameExtensionFilter filter;

	    public HotelEditorWindow() {
	        super("Hotel Editor Form");
	        
	        idF = new JTextField(20);
	        nameF = new JTextField(20);
	        cityF = new JTextField(20);
	        infoF = new JTextField(60);

	        submitButton = new JButton("Submit");
	        clearButton = new JButton("Clear");
	        imageButton = new JButton("Submit image");
	        
	        fileChooser = new JFileChooser();
	        filter = new FileNameExtensionFilter("jpg", "png", "ico");
	        fileChooser.setFileFilter(filter);

	        submitButton.addActionListener(this);
	        clearButton.addActionListener(this);
	        imageButton.addActionListener(this);
	        
	        JPanel panelBorder = new JPanel(new BorderLayout());
	        JPanel formPanel = new JPanel(new GridLayout(5, 2, 4, 4));
	        JPanel imagePanel = new JPanel(new BorderLayout());
	        formPanel.setBackground(new Color(255, 228, 181));
	        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        formPanel.add(idF);

	        formPanel.add(nameF);

	        formPanel.add(cityF);

	        formPanel.add(infoF);
	        panelBorder.add(formPanel, BorderLayout.NORTH);
	        imagePanel.add(imageButton, BorderLayout.NORTH);
	        panelBorder.add(imagePanel);

	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        buttonPanel.setBackground(new Color(135, 206, 250));
	        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); 
	        buttonPanel.add(submitButton);
	        buttonPanel.add(clearButton);

	        getContentPane().setLayout(new BorderLayout());
	        getContentPane().add(panelBorder, BorderLayout.CENTER);
	        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	   
	        setSize(400, 400);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setVisible(true);
	    }

	    
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == submitButton) {
	        	int id = Integer.parseInt(idF.getText());
	            String name = nameF.getText();
	            String city = cityF.getText();
	            String info = infoF.getText();
	            Hotel hotel = new Hotel(id, name, city, info);            
				ClientController.createHotel(hotel);

	            
	        } else if (e.getSource() == clearButton) {
	        	idF.setText("");
	        	nameF.setText("");
	            cityF.setText("");
	            infoF.setText("");
	        }  
	     }

	     public static void main(String[] args) {
	         HotelEditorWindow hotelEditorWindow = new HotelEditorWindow();
	     }        
}
