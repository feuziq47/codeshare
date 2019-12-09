package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import client.*;


public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextArea code;
	private JTextArea chat;
	private JTextArea txtEnterChat;
	private JTextArea terminal;
	private JTextArea cmd;
	
	private JButton btnEnter;
	private JButton executeBtn;
	private JButton saveBtn;
	private JButton terminalBtn;
	private JButton rmsaveBtn;
	private JButton cmdenterBtn;

	private JList<String> userlist;
	private DefaultListModel<String> listmodel;
	
	private boolean tflag=false;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;

	
	public GUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("CodeShare");
		setBounds(5, 5, 1830, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodeView = new JLabel(" CODE VIEW");
		lblCodeView.setBounds(14, 22, 144, 18);
		contentPane.add(lblCodeView);
		
		JLabel lblUserView = new JLabel(" USER VIEW");
		lblUserView.setBounds(1485, 36, 144, 18);
		contentPane.add(lblUserView);
		
		
		listmodel = new DefaultListModel<String>();
		userlist = new JList<String>(listmodel);
		scrollPane_3 = new JScrollPane(userlist);
		scrollPane_3.setBounds(1485, 54, 315, 130);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane_3);
		
		code= new JTextArea();
		scrollPane = new JScrollPane(code);
		scrollPane.setBounds(24, 52, 1447, 791);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		code.setEditable(true);
		contentPane.add(scrollPane);
		
		
		
		chat = new JTextArea();
		scrollPane_1 = new JScrollPane(chat);
		scrollPane_1.setBounds(1485, 205, 315, 520);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane_1);
		
		// 채팅창
		scrollPane_1.setViewportView(chat);
		
		JLabel lblChatLog = new JLabel(" CHAT LOG");
		lblChatLog.setBounds(1482, 190, 144, 18);
		contentPane.add(lblChatLog);
		
		
		txtEnterChat = new JTextArea("Enter Chat",20,6);
		txtEnterChat.setBounds(1485, 737, 235, 114);
		contentPane.add(txtEnterChat);
		txtEnterChat.setColumns(10);
		txtEnterChat.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(e.isControlDown()) {
						txtEnterChat.append(System.lineSeparator());
					}else {
						btnEnter.doClick();
						e.consume();
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		// 실행버튼
		executeBtn = new JButton("Execute");
		executeBtn.setBounds(1370, 855, 105, 30);
		contentPane.add(executeBtn);
		
		// 터미널, 터미널 명령어
		terminal = new JTextArea();
		cmd = new JTextArea();
		
		terminal.setEditable(false);
		cmd.setBounds(25, 820, 1320, 25);
		cmd.setEditable(true);
		scrollPane_2 = new JScrollPane(terminal);
		scrollPane_2.setBounds(24, 550, 1447, 265);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		terminalBtn = new JButton("Terminal");
		terminalBtn.setBounds(1130, 855, 105, 30);
		contentPane.add(terminalBtn);
		
		
		// command line enter 버튼
		cmdenterBtn = new JButton("Enter");
		cmdenterBtn.setBounds(1370, 820, 105, 25);
		
		
		// local save 버튼
		saveBtn = new JButton("Save Local");
		saveBtn.setBounds(1250, 855, 105, 30);
		contentPane.add(saveBtn);
		
		// 채팅창 엔터 버튼
		btnEnter = new JButton("Enter");
		btnEnter.setBounds(1734, 737, 65, 114);
		contentPane.add(btnEnter);
		btnEnter.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		txtEnterChat.setText(null);
        	}
        });
		
		// remote save 버튼
		rmsaveBtn = new JButton("Save in server");
		rmsaveBtn.setBounds(910, 855, 200, 30);
		contentPane.add(rmsaveBtn);
		
		this.setVisible(true);
	}

	public JList<String> getUserlist() {
		return userlist;
	}
	
	public DefaultListModel<String> getListmodel() {
		return listmodel;
	}
	
	public JButton getBtnenter() {
		return btnEnter;
	}
	
	public JButton getExecutebtn() {
		return executeBtn;
	}
	
	public JTextArea getTxtenterchat() {
		return txtEnterChat;
	}
	
	public JTextArea getChat() {
		return chat;
	}
	
	public JTextArea getCode() {
		return code;
	}

	public JButton getSavebtn() {
		return saveBtn;
	}
	
	public JButton getTerminalbtn() {
		return terminalBtn;
	}
	
	public JPanel getContentPane() {
		return contentPane;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JScrollPane getScrollPane_1() {
		return scrollPane_1;
	}

	public JScrollPane getScrollPane_2() {
		return scrollPane_2;
	}

	public JTextArea getCmd() {
		return cmd;
	}

	public void setTflag(boolean tflag) {
		this.tflag = tflag;
	}
	
	public boolean isTflag() {
		return tflag;
	}

	public JButton getRmsaveBtn() {
		return rmsaveBtn;
	}
	
	public JButton getCmdenterbtn() {
		return cmdenterBtn;
	}
	public static void main(String args[]){
		GUI gui=new GUI();
	}
	
	
}
