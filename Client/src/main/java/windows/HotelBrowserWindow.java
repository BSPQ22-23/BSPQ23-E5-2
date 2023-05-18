package windows;
import javax.swing.*;

import domain.Hotel;
import remote.ClientController;
import language.InternLanguage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class HotelBrowserWindow extends JFrame  {
    private JTextField searchField;
    private JTextArea hotelListArea;
    private JButton viewHotelButton, homeButton,searchButton;
    public  ClientController controller;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new HotelBrowserWindow(ClientController controller);
            	
            }
        });
    }

    public HotelBrowserWindow(ClientController controller) {
        setTitle(InternLanguage.translateTxt("title_br"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Create components
        searchField = new JTextField();
        searchButton = new JButton(InternLanguage.translateTxt("Search"));
        hotelListArea = new JTextArea();
        viewHotelButton = new JButton(InternLanguage.translateTxt("view_Hotel"));
        homeButton = new JButton(InternLanguage.translateTxt("home"));

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                List<Hotel>hotels= ClientController.getHotels(query);
                hotelListArea.setText(hotels.toString());
            }
        });

        viewHotelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to enter the hotel description window
                JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("hotel_DescMen"));
            }
        });

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to return to the home page
                JOptionPane.showMessageDialog(null, InternLanguage.translateTxt("retur_HomePg"));
                dispose();
                MainMenuClient mmc = new MainMenuClient();
                mmc.setVisible(true);
            }
        });

        // Add components to the frame
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JScrollPane hotelListScrollPane = new JScrollPane(hotelListArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewHotelButton);
        buttonPanel.add(homeButton);

        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(hotelListScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



   
   
}