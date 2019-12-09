package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FileChooserUI;

import view.Alert;
import view.GUI;
import view.Inputname;
import view.Login;


public class Client{
	public static final String IP="192.168.56.1";
	public static final int PORT=8080;
	
	private Socket sock=null;
	public DataInputStream in =null;
	private DataOutputStream out=null;

	
	private Login logingui;
	private GUI gui;
	
	private String clntname;
	private String filename;
	
	
	private Client() {
		
		if(connect(IP,PORT)) {
			logingui=new Login(this);
			logingui.getLoginBtn().addActionListener(new LoginBtnClass());
			logingui.getExitbtn().addActionListener(new ExitBtnClass());
		}
		
		
		
	}
		
	
	public boolean connect(String ip,int portnum) {
		try {
			sock=new Socket(ip,portnum);
			in=new DataInputStream(sock.getInputStream());
			out=new DataOutputStream(sock.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void makeRecvthread(Socket sock, DataOutputStream out, GUI gui, String clntname) {
		Client_receiver cs = new Client_receiver(sock,in,gui,clntname);
		Thread send_thread= new Thread(cs);
		send_thread.start();
	}
	
	
	public void setClientname(String name) {
		this.clntname=name;
	}
	
	
	public DataOutputStream getOutputstream() {
		return out;
	}
		
	public void setFilename(String name) {
		filename=name;
	}
	
	public static void main(String args[]) {
		Client clnt = new Client();
	}
	
	
	public class LoginBtnClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if(logingui.getIdfield().getText().isEmpty() || logingui.getPwfield().getText().isEmpty()) {
					out.writeUTF("empty");
					Alert error = new Alert("Put id, pw");
				}else {
					out.writeUTF(logingui.getIdfield().getText().concat("/").concat(logingui.getPwfield().getText()));
					String result = in.readUTF();
					if(result.contains(Protocol.loginSig.concat("아이디"))){
						Alert error = new Alert(result.split(Protocol.loginSig)[1]);
					}else if(result.contains(Protocol.loginSig.concat("접속중인"))){
						Alert error = new Alert(result.split(Protocol.loginSig)[1]);
					}else if(result.equals(Protocol.loginSig.concat("success"))) {
						gui=new GUI();
						guiSet(gui);
						
						clntname=logingui.getIdfield().getText();
						makeRecvthread(sock, out, gui, clntname);
						logingui.dispose();
					}else if(result.equals(Protocol.loginSig.concat("fail"))) {
						Alert alert= new Alert("Check id, pw");
					}
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void guiSet(GUI gui) {
		gui.getExecutebtn().addActionListener(new ExecuteBtnClass());
		gui.getBtnenter().addActionListener(new ChatBtnClass());
		gui.getCode().getDocument().addDocumentListener(new MyDocumentListener());
		gui.getSavebtn().addActionListener(new SaveBtnClass());
		gui.getTerminalbtn().addActionListener(new TerminalBtnClass());
		gui.getRmsaveBtn().addActionListener(new RmsaveBtnClass());
	}
	
	private class ExitBtnClass implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("exit");
				out.writeUTF(Protocol.loginSig.concat("exit"));
				sock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				
			}
			
		}
		
	}
	private class ChatBtnClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if(gui.getTxtenterchat().getText().isEmpty()) {
					return;
				}else {
					synchronized(out) {
						out.writeUTF(Protocol.sendChat.concat(gui.getTxtenterchat().getText()));
					}
					
				}
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
	}

	private class SaveBtnClass implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
    		JFileChooser fileChooser = new JFileChooser();
    		// 저장 창 띄우기
    		int flag = fileChooser.showSaveDialog(null);
    		// 다중 선택 불가
    		fileChooser.setMultiSelectionEnabled(false);
    		if(flag == JFileChooser.APPROVE_OPTION) {
    			String all_code = gui.getCode().getText();
    			File savefile = fileChooser.getSelectedFile();
    			try {
					FileWriter fw = new FileWriter(savefile.getPath()+".java");
					fw.write(all_code);
					fw.flush();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    			
    		}
    		
    	
			
			
		}
		
	}
	
	private class ExecuteBtnClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				out.writeUTF(Protocol.execute);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class MyDocumentListener implements DocumentListener{
		
		@Override
		public synchronized void changedUpdate(DocumentEvent arg0) {
			
		}

		@Override
		public synchronized void insertUpdate(DocumentEvent arg0) {
			sendCode();
		}

		@Override
		public synchronized void removeUpdate(DocumentEvent arg0) {
			sendCode();
			
		}
		
		public synchronized void sendCode() {
			String sender = clntname;
			String code = gui.getCode().getText();
			String content = sender.concat(Protocol.sendCode).concat(code);
			try {
				out.writeUTF(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Click Terminal Button  
	private class TerminalBtnClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			cmdSet(gui);
			try {
				synchronized(out) {
					out.writeUTF(Protocol.MK_TRM);
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
		public void cmdSet(GUI gui) {
			if(gui.isTflag()==false) {
				gui.getScrollPane_2().setEnabled(true);
				gui.getScrollPane().setBounds(24, 52, 1447, 491);
				gui.getScrollPane().repaint();
				gui.getCmd().setEnabled(true);
				gui.getCmdenterbtn().setEnabled(true);
				gui.getContentPane().add(gui.getScrollPane_2());
				gui.getContentPane().add(gui.getCmd());
				gui.getContentPane().add(gui.getCmdenterbtn());
				gui.setTflag(true);
			}else if(gui.isTflag()==true) {
				gui.getScrollPane_2().setEnabled(false);
				gui.getCmd().setEnabled(false);
				gui.getCmdenterbtn().setEnabled(false);
				gui.getContentPane().remove(gui.getContentPane());
				gui.getContentPane().remove(gui.getCmd());
				gui.getContentPane().remove(gui.getCmdenterbtn());
				gui.getScrollPane().setBounds(24, 52, 1447, 791);
				gui.getScrollPane().repaint();
				gui.setTflag(false);
			}
		}
	}
	
	private class RmsaveBtnClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Inputname dialog = new Inputname();
			dialog.getOkbtn().addActionListener(new OkBtnClass());
		}
		
	}
	
	private class OkBtnClass implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			// Send filename + save protocol + code
			setFilename(Inputname.getTextfield().getText());
			try {
			if(filename==null || gui.getCode().getText()==null) {
				return;
			}else {
				String content = filename.concat(Protocol.save).concat(gui.getCode().getText());
				System.out.println(content);
				synchronized(out) {
					try {
						out.writeUTF(content);
						Alert alert = new Alert("Send Success");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			} catch (NullPointerException e) {
				Error error = new Error("Insert value");
			}
			
		}
	}
	
	
}
