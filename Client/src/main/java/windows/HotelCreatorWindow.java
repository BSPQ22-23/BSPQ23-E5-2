package windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.Hotel;
import domain.Room;
import language.InternLanguage;
import language.TranslatableObject.TranslatableJButton;
import language.TranslatableObject.TranslatableJLabel;
import remote.ClientController;

/**
 * Window to create new hotels
 *
 */
public class HotelCreatorWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private TranslatableJLabel name, city, info;
	private JLabel imageL;
    private JTextField nameF, cityF, infoF;
    private TranslatableJButton submitButton, clearButton, imageButton, roomButton;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filter;
    private JComboBox<Room> roomBox;
    private BufferedImage icon = null;
    private String format = "";
    
    public HotelCreatorWindow() {
        super(InternLanguage.translateTxt("title_cr"));
        name = new TranslatableJLabel("name_cr");
        city = new TranslatableJLabel("city_cr");
        info = new TranslatableJLabel("info_cr");
        imageL = new JLabel();
        nameF = new JTextField(20);
        cityF = new JTextField(20);
        infoF = new JTextField(60);

        submitButton = new TranslatableJButton("submit");
        clearButton = new TranslatableJButton("clear");
        imageButton = new TranslatableJButton("submit_Image");
        roomButton = new TranslatableJButton("create_Room");
        
        UpperMenu upperMenu = new UpperMenu(v -> {
        	new MainMenuOwner();
        	dispose();
        }, name, city, info, submitButton, clearButton, imageButton);
        setJMenuBar(upperMenu);
        
        fileChooser = new JFileChooser();
        filter = new FileNameExtensionFilter("", "jpg", "png", "ico");
        fileChooser.setFileFilter(filter);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        imageButton.addActionListener(this);
        roomButton.addActionListener(this);
        
        roomBox = new JComboBox<Room>(new DefaultComboBoxModel<>());
        JPanel panelBorder = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 4, 4));
        JPanel imagePanel = new JPanel(new BorderLayout());
        formPanel.setBackground(new Color(255, 228, 181));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(name);
        formPanel.add(nameF);
        formPanel.add(city);
        formPanel.add(cityF);
        formPanel.add(info);
        formPanel.add(infoF);
        formPanel.add(roomButton);
        formPanel.add(roomBox);
        panelBorder.add(formPanel, BorderLayout.NORTH);
        imagePanel.add(imageButton, BorderLayout.NORTH);
        imagePanel.add(imageL, BorderLayout.CENTER);
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
            String name = nameF.getText();
            String city = cityF.getText();
            String info = infoF.getText();
            Hotel hotel = new Hotel(0, name, city, info);
            for(int i = 0; i < roomBox.getItemCount(); i++) {
            	hotel.addRoom(roomBox.getItemAt(i));
            }
            hotel.setIcon(icon, format);
			ClientController.createHotel(hotel);
            
        } else if (e.getSource() == roomButton) {
        	new HotelEditorWindow((DefaultComboBoxModel<Room>) roomBox.getModel());
			
        } else if (e.getSource() == clearButton) {
        	nameF.setText("");
            cityF.setText("");
            infoF.setText("");
            
         } else if (e.getSource() == imageButton) {
        	 int image = fileChooser.showOpenDialog(HotelCreatorWindow.this);
        	 if(image == JFileChooser.APPROVE_OPTION) {
        		 File file = fileChooser.getSelectedFile();
	        	 try {
	        		 BufferedImage imageBuff = ImageIO.read(file);
	        		 format = file.getName().substring(file.getName().indexOf(".") + 1);
	        		 ImageIcon icon = new ImageIcon(imageBuff);
	        		 this.icon = imageBuff;
	        		 imageL.setIcon(icon);
	        	 } catch (IOException ex) {
	        		 ex.printStackTrace();
	        	 }
        	 }
         }
     }

     public static void main(String[] args) {
         new HotelCreatorWindow();
     }        
}
