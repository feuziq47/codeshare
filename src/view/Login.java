package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.DataOutputStream;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;


public class Login extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8762849402942962079L;
	public JLabel idlabel;
	public JLabel pwlabel;
	public JTextField idfield;
	public JPasswordField pwfield;
	public JButton loginBtn;
	public JButton exitBtn;
	public DataOutputStream out;
	private Client client;

	public Login(Client client) {
		this.client=client;
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(700, 300, 400, 500);
		
		
		Container contentPane = this.getContentPane();
		this.getContentPane().setLayout(null);
		
		idlabel= new JLabel();
		idlabel.setText("ID");
		idlabel.setBounds(140,50,100,50);
		idlabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(idlabel);
		
		idfield = new JTextField("Enter ID");
		idfield.setBounds(90,100,200,30);
		idfield.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				idfield.setText(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				idfield.setText(null);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				idfield.setText(null);
			}
		});
		contentPane.add(idfield);
		
		pwlabel= new JLabel("Password");
		pwlabel.setBounds(140,150,100,50);
		pwlabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(pwlabel);
		
		pwfield = new JPasswordField();
		pwfield.setBounds(90,200,200,30);
		pwfield.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pwfield.setText(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {		
			}
			@Override
			public void mouseExited(MouseEvent e) {	
			}
			@Override
			public void mousePressed(MouseEvent e) {
				pwfield.setText(null);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pwfield.setText(null);
			}
		});
		contentPane.add(pwfield);
		
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(80,300,100,50);
		contentPane.add(loginBtn);
		
		
		exitBtn = new JButton("exit");
		exitBtn.setBounds(200,300,100,50);
		contentPane.add(exitBtn);
		exitBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        			System.exit(1);
        	}
        });
		
		this.setVisible(true);
	}
	
	
	
	public JButton getLoginBtn() {
		return this.loginBtn;
	}
	
	public JTextField getIdfield() {
		return this.idfield;
	}
	
	public JPasswordField getPwfield() {
		return pwfield;
	}
	
	
	public JButton getExitbtn() {
		return exitBtn;
	}
	
}
