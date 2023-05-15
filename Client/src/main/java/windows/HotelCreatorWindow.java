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

public class HotelCreatorWindow extends JFrame implements ActionListener {
	 	private JLabel id, name, city, info, imageL;
	    private JTextField idF, nameF, cityF, infoF;
	    private JButton submitButton, clearButton, imageButton;
	    private JFileChooser fileChooser;
	    private FileNameExtensionFilter filter;

	    public HotelCreatorWindow() {
	        super("Hotel Registration Form");

	        id = new JLabel("Hotel identificator number:");
	        name = new JLabel("Hotel name:");
	        city = new JLabel("Hotel location:");
	        info = new JLabel("Hotel info:");
	        imageL = new JLabel();
	        
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

	        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
	        formPanel.setBackground(new Color(255, 228, 181));
	        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        formPanel.add(id);
	        formPanel.add(idF);
	        formPanel.add(name);
	        formPanel.add(nameF);
	        formPanel.add(city);
	        formPanel.add(cityF);
	        formPanel.add(info);
	        formPanel.add(infoF);
	        formPanel.add(imageButton);
	        formPanel.add(imageL);

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
	        	int id = Integer.parseInt(idF.getText());
	            String name = nameF.getText();
	            String city = cityF.getText();
	            String info = infoF.getText(); //ask canal about this
	            Hotel hotel = new Hotel(id, name, city);            
				ClientController.createHotel(hotel);

	            
	        } else if (e.getSource() == clearButton) {
	        	idF.setText("");
	        	nameF.setText("");
	            cityF.setText("");
	            infoF.setText("");
	            
	         } else if (e.getSource() == imageButton) {
	        	 
	        	 int image = fileChooser.showOpenDialog(HotelCreatorWindow.this);
	        	 if(image == JFileChooser.APPROVE_OPTION) {
	        		 File file = fileChooser.getSelectedFile();
		        	 try {
		        		 BufferedImage imageBuff = ImageIO.read(file);
		        		 ImageIcon icon = new ImageIcon(imageBuff);
		        		 imageL.setIcon(icon);
		        	 } catch (IOException ex) {
		        		 ex.printStackTrace();
		        	 }
	        	 }
	         }
	     }

	     public static void main(String[] args) {
	         HotelCreatorWindow hotelWindow = new HotelCreatorWindow();
	     }
	        
}
