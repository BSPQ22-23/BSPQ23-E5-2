package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import domain.Hotel;
import domain.Room;
import domain.Service;
import language.InternLanguage;
import remote.ClientController;

public class HotelEditorWindow extends JFrame implements ActionListener {
	 	private JLabel roomNum, roomType, roomMax, roomSpace, roomPrice, roomHotel, serviceName, serviceDesc, servicePrice;
	    private JTextField roomNumF, roomTypeF, roomMaxF, roomSpaceF, roomPriceF, roomHotelF, serviceNameF, serviceDescF, servicePriceF;
	    private JButton submitRoomButton, submitServiceButton, clearButton, exitButton;

	    public HotelEditorWindow() {
	        super(InternLanguage.translateTxt("title_Ed"));
	        
	        roomNum = new JLabel(InternLanguage.translateTxt("roomNum"));
	        roomType = new JLabel(InternLanguage.translateTxt("roomType"));
	        roomMax = new JLabel(InternLanguage.translateTxt("roomMax"));
	        roomSpace = new JLabel(InternLanguage.translateTxt("roomSpace"));
	        roomPrice = new JLabel(InternLanguage.translateTxt("roomPrice"));
	        
	        serviceName = new JLabel(InternLanguage.translateTxt("serviceName"));
	        serviceDesc = new JLabel(InternLanguage.translateTxt("serviceDesc"));
	        servicePrice = new JLabel(InternLanguage.translateTxt("servicePrice"));
	        
	        roomHotel = new JLabel(InternLanguage.translateTxt("hotelEd"));
	        
	        roomNumF = new JTextField(20);
	        roomTypeF = new JTextField(20);
	        roomMaxF = new JTextField(20);
	        roomSpaceF = new JTextField(20);
	        roomPriceF = new JTextField(20);
	        
	        serviceNameF = new JTextField(20);
	        serviceDescF = new JTextField(60);
	        servicePriceF = new JTextField(20);
	        
	        roomHotelF = new JTextField(20);

	        submitRoomButton = new JButton(InternLanguage.translateTxt("addRoom"));
	        submitServiceButton = new JButton(InternLanguage.translateTxt("addService"));
	        clearButton = new JButton(InternLanguage.translateTxt("clear"));
	        exitButton = new JButton(InternLanguage.translateTxt("exit"));

	        submitRoomButton.addActionListener(this);
	        submitServiceButton.addActionListener(this);
	        clearButton.addActionListener(this);
	        exitButton.addActionListener(this);
	        
	        JPanel panelBorder = new JPanel(new BorderLayout());
	        JPanel formPanel = new JPanel(new GridLayout(3, 4, 4, 4));
	        formPanel.setBackground(new Color(255, 228, 181));
	        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        formPanel.add(roomNum);
	        formPanel.add(roomNumF);
	        formPanel.add(roomType);
	        formPanel.add(roomTypeF);
	        formPanel.add(roomMax);
	        formPanel.add(roomMaxF);
	        formPanel.add(roomSpace);
	        formPanel.add(roomSpaceF);
	        formPanel.add(roomPrice);
	        formPanel.add(roomPriceF);
	        panelBorder.add(formPanel, BorderLayout.NORTH);
	        
	        JPanel servicePanel = new JPanel(new GridLayout(3, 2, 4, 4));
	        servicePanel.setBackground(new Color(255, 228, 181));
	        servicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        servicePanel.add(serviceName);
	        servicePanel.add(serviceNameF);
	        servicePanel.add(serviceDesc);
	        servicePanel.add(serviceDescF);
	        servicePanel.add(servicePrice);
	        servicePanel.add(servicePriceF);
	        
	        JPanel hotelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        hotelPanel.setBackground(new Color(255, 228, 181));
	        hotelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        hotelPanel.add(roomHotel);
	        hotelPanel.add(roomHotelF);
	        
	        panelBorder.add(formPanel, BorderLayout.NORTH);
	        panelBorder.add(servicePanel, BorderLayout.CENTER);
	        panelBorder.add(hotelPanel, BorderLayout.SOUTH);

	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        buttonPanel.setBackground(new Color(135, 206, 250));
	        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); 
	        buttonPanel.add(submitRoomButton);
	        buttonPanel.add(submitServiceButton);
	        buttonPanel.add(clearButton);
	        buttonPanel.add(exitButton);

	        getContentPane().setLayout(new BorderLayout());
	        getContentPane().add(panelBorder, BorderLayout.CENTER);
	        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	   
	        setSize(600, 400);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setVisible(true);
	    }

	    
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == submitRoomButton) {
	        	int num = Integer.parseInt(roomNumF.getText());
	            String type = roomTypeF.getText();
	            int max = Integer.parseInt(roomMaxF.getText());
	            int space = Integer.parseInt(roomSpaceF.getText());
	            int price = Integer.parseInt(roomPriceF.getText());
	            String hotel = roomHotelF.getText();
	            Hotel hotelT = ClientController.getHotels(hotel).get(1);
	            Room room = new Room(num, type, max, space, price, hotelT);
	            
	        
	        } else if (e.getSource() == submitServiceButton) {
	        	String name = serviceNameF.getText();
	        	String description = serviceDescF.getText();
	        	int price = Integer.parseInt(servicePriceF.getText());
	            String hotel = roomHotelF.getText();
	            Hotel hotelT = ClientController.getHotels(hotel).get(1);
	            Service service = new Service(name, description, price);
	        	
	        } else if (e.getSource() == clearButton) {
	        	roomNumF.setText("");
	        	roomTypeF.setText("");
	            roomMaxF.setText("");
	            roomSpaceF.setText("");
	            roomPriceF.setText("");
	            serviceNameF.setText("");
	            serviceDescF.setText("");
	            servicePriceF.setText("");
	            roomHotelF.setText("");
	        
			} else if (e.getSource() == exitButton) {
				this.dispose();
			}  
	     }

	     public static void main(String[] args) {
	         HotelEditorWindow hotelEditorWindow = new HotelEditorWindow();
	     }        
}
