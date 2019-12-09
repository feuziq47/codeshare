package view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Alert extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3530910062094128377L;

	public Alert(String str) {
		JFrame frame = new JFrame(str);
		JLabel text= new JLabel(str);
		frame.setBounds(700, 500, 400, 200);
		frame.setLayout(new GridLayout(1,1));
		//text.setBounds(100,50,100,50);
		text.setFont(new Font("Times",Font.BOLD,20));
		text.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(text);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}
