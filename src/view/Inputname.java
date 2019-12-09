package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Inputname extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTextField textField;
	private JButton okButton;
	
	public Inputname() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Input file name");
		
		setBounds(700, 300, 330, 169);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(14, 35, 270, 24);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		
		JLabel lblInputFileName = new JLabel("Input file name");
		lblInputFileName.setBounds(14, 12, 160, 24);
		contentPanel.add(lblInputFileName);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	}
	public JButton getOkbtn() {
		return okButton;
	}
	public static JTextField getTextfield() {
		return textField;
	}
}
