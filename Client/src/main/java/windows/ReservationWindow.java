package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import language.InternLanguage;

import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;



public class ReservationWindow extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelArriba, panelCentro, panelCentroCentro, panelCentroAbajo, panelCC1, panelCC2, panelCC3,
	panelCC4, panelCA1, panelCA3, panelCA4;
	private JLabel  lblName,lblNumber,lblNumberGuest,lblService,lblSurname,lblDni,lblCity,lblRoom,lblPhone, lblFechaIni, lblFechaFin,lblEmail,labelC;


private JTextField txtname,txtsurname, txtdni,txtphone,textC,txtroom ,textField,txtEmail;
private JCheckBox CheckBox1,CheckBox2;
private JRadioButton rdbtn1,rdbtn2,rdbtn3;
private JPanel panelCA2;

private JButton btnAceptar, btnGuardar;
/**
 * Launch the application.
 */
public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				ReservationWindow window = new ReservationWindow();
				window.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}


	/**
	 * Create the panel.
	 */
	public ReservationWindow() {
		setLayout(new BorderLayout(0, 0));

		panelArriba = new JPanel();
		add(panelArriba, BorderLayout.NORTH);
		
		//frame.setBounds(40, 50, 1010, 790);
		setSize(1000, 800);
		Component lblAniadirReserva = new JLabel(" RESERVATION");
		panelArriba.add(lblAniadirReserva);

		panelCentro = new JPanel();
		add(panelCentro, BorderLayout.WEST);
		panelCentro.setLayout(new GridLayout(3, 1, 0, 0));
		panelCentroCentro = new JPanel();
		panelCentro.add(panelCentroCentro);
		panelCentroCentro.setLayout(new GridLayout(0, 4, 0, 0));
		  
		panelCC1 = new JPanel();
		panelCentroCentro.add(panelCC1);
		panelCC1.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblName = new JLabel(InternLanguage.translateTxt("name"));
		panelCC1.add(lblName);
		
		txtname = new JTextField();
		panelCC1.add(txtname);
		txtname.setColumns(10);
		
		lblSurname = new JLabel(InternLanguage.translateTxt("lst_Name"));
		panelCC1.add(lblSurname);
		
		txtsurname = new JTextField();
		txtsurname.setText("");
		panelCC1.add(txtsurname);
		txtsurname.setColumns(10);
		
		lblDni = new JLabel(InternLanguage.translateTxt("idnum"));
		panelCC1.add(lblDni);
		
		txtdni = new JTextField();
		panelCC1.add(txtdni);
		txtdni.setColumns(10);

		panelCC2 = new JPanel();
		panelCentroCentro.add(panelCC2);
		panelCC2.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblEmail = new JLabel("Email");
		panelCC2.add(lblEmail);
		txtEmail =   new JTextField();
		panelCC2.add(txtEmail);
		txtEmail.setColumns(10);
		
		lblPhone = new JLabel(InternLanguage.translateTxt("phone"));
		panelCC2.add(lblPhone);
		
		txtphone = new JTextField();
		panelCC2.add(txtphone);
		txtphone.setColumns(10);
		
		lblCity = new JLabel(InternLanguage.translateTxt("city"));
		panelCC2.add(lblCity);
		
		textC = new JTextField();
		textC.setText("");
		panelCC2.add(textC);
		textC.setColumns(10);


		panelCC3 = new JPanel();
		panelCentroCentro.add(panelCC3);

		lblFechaIni = new JLabel("Check-in");
		panelCC3.add(lblFechaIni);
		
	

		panelCC4 = new JPanel();
		panelCentroCentro.add(panelCC4);

		lblFechaFin = new JLabel("Check-out");
		panelCC4.add(lblFechaFin);
		
	
		

		panelCentroAbajo = new JPanel();
		panelCentro.add(panelCentroAbajo);
		panelCentroAbajo.setLayout(new GridLayout(0, 4, 0, 0));

		panelCA1 = new JPanel();
		panelCentroAbajo.add(panelCA1);
		panelCA1.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblRoom = new JLabel("Room");
		panelCA1.add(lblRoom);
		
		txtroom = new JTextField();
		panelCA1.add(txtroom);
		txtroom.setColumns(10);
		
		lblNumber = new JLabel(InternLanguage.translateTxt("room_Type"));
		panelCA1.add(lblNumber);
		
		rdbtn1 = new JRadioButton(InternLanguage.translateTxt("double"));
		panelCA1.add(rdbtn1);
		
		rdbtn2 = new JRadioButton(InternLanguage.translateTxt("single"));
		panelCA1.add(rdbtn2);
		
		rdbtn3 = new JRadioButton(InternLanguage.translateTxt("triple"));
		panelCA1.add(rdbtn3);
		
		panelCA2 = new JPanel();
		panelCentroAbajo.add(panelCA2);
		panelCA2.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNumberGuest = new JLabel(InternLanguage.translateTxt("gst_Num"));
		panelCA2.add(lblNumberGuest);
		
		textField = new JTextField();
		textField.setColumns(10);
		panelCA2.add(textField);
		
		lblService = new JLabel(InternLanguage.translateTxt("services"));
		panelCA2.add(lblService);
		
		CheckBox1 = new JCheckBox(InternLanguage.translateTxt("parking"));
		panelCA2.add(CheckBox1);
		
		CheckBox2 = new JCheckBox(InternLanguage.translateTxt("breakfast"));
		panelCA2.add(CheckBox2);

		panelCA3 = new JPanel();
		panelCentroAbajo.add(panelCA3);

		btnAceptar = new JButton(InternLanguage.translateTxt("submit"));
		panelCA3.add(btnAceptar);
		btnGuardar = new JButton(InternLanguage.translateTxt("clear"));
		panelCA3.add(btnGuardar);
		panelCA4 = new JPanel();
		panelCentroAbajo.add(panelCA4);

		
		/*con = BD.initBD("confortTravel.db");
		BD.crearTablas(con);
		BD.closeBD(con);
		*/
		
	
		
		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null,"PARA MÁS SEGURIDAD INTRODUZCA EL SU DNI ");
				
					
			
				


			}
		});
		
	
		
	


	}




	}
