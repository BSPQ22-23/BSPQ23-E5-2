package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Hotel;
import domain.Room;
import domain.Service;
import language.InternLanguage;
import remote.ClientController;

/**
 * 
 * Window to edit a hotel owned by the user
 *
 */
public class HotelEditorWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -7362369183665923719L;
	private JLabel roomNum, roomType, roomMax, roomSpace, roomPrice, roomHotel, serviceName, serviceDesc, servicePrice;
	private JTextField roomNumF, roomTypeF, roomMaxF, roomSpaceF, roomPriceF, roomHotelF, serviceNameF, serviceDescF, servicePriceF;
	private JButton submitRoomButton, submitServiceButton, clearButton, exitButton;
	private DefaultComboBoxModel<Room> roomList;

	public HotelEditorWindow(DefaultComboBoxModel<Room> roomList) {
		super(InternLanguage.translateTxt("title_Ed"));
	    this.roomList = roomList;
        roomNum = new JLabel(InternLanguage.translateTxt("roomNum"));
        roomType = new JLabel(InternLanguage.translateTxt("roomType"));
        roomMax = new JLabel(InternLanguage.translateTxt("roomMax"));
        roomSpace = new JLabel(InternLanguage.translateTxt("roomSpace"));
        roomPrice = new JLabel(InternLanguage.translateTxt("roomPrice"));
        
        serviceName = new JLabel(InternLanguage.translateTxt("serviceName"));
        serviceDesc = new JLabel(InternLanguage.translateTxt("serviceDesc"));
        servicePrice = new JLabel(InternLanguage.translateTxt("servicePrice"));
        
        roomHotel = new JLabel(InternLanguage.translateTxt("hotelEd"));
        
        KeyListener numberField = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!(c >= '0') || !(c <= '9'))
					e.consume();
			}
		};
        roomNumF = new JTextField(20);
        roomNumF.addKeyListener(numberField);
        roomTypeF = new JTextField(20);
        roomMaxF = new JTextField(20);
        roomMaxF.addKeyListener(numberField);
        roomSpaceF = new JTextField(20);
        roomSpaceF.addKeyListener(numberField);
        roomPriceF = new JTextField(20);
        roomPriceF.addKeyListener(numberField);
        serviceNameF = new JTextField(20);
        serviceDescF = new JTextField(60);
        servicePriceF = new JTextField(20);
        servicePriceF.addKeyListener(numberField);
        
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
            //Hotel hotelT = ClientController.getHotels(hotel).get(1);
            Room room = new Room(num, type, max, space, price, null);
            
            int h = 0;
            for(int i = 0; i < roomList.getSize(); i++) {
            	if(roomList.getElementAt(i).getRoomNumber() == num) {
            		h = 1;
            	}
            }
            if(h == 0) {
            	roomList.addElement(room);
            } else {
            	messageWrong();
            }
        
        } else if (e.getSource() == submitServiceButton) {
        	String name = serviceNameF.getText();
        	String description = serviceDescF.getText();
        	int price = Integer.parseInt(servicePriceF.getText());
            String hotel = roomHotelF.getText();
            Hotel hotelT = ClientController.getHotels(hotel).get(0);
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
    
    private void messageWrong() {
        JOptionPane.showMessageDialog(this, "Room already exists");
    }

     public static void main(String[] args) {
         new HotelEditorWindow(null);
     }        
}
