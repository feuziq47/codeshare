package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import view.Alert;
import view.GUI;


public class Client_receiver implements Runnable {
	
	private DataOutputStream out;
	private DataInputStream in;
	private GUI gui;
	private BufferedReader br;
	private String clntname;
	private Document code_docu;
	private Socket sock;
	public Client_receiver(Socket sock, DataInputStream in,GUI gui,String clntname) {
		this.sock=sock;
		this.in=in;
		this.gui=gui;
		this.clntname=clntname;
		br=new BufferedReader(new InputStreamReader(System.in));
		this.code_docu=gui.getCode().getDocument();
	}

	@Override
	public void run() {
		
		while(true) {
			if(!sock.isClosed()) {
				recv();
			}
			
		}
		
	}
	
	public void recv() {
		String msg;
		try {
			msg = in.readUTF();
			if(msg.contains(Protocol.loginSig)){
				String content = msg.split(Protocol.loginSig)[1];
				if(!content.equals("success")) {
					Alert error = new Alert(content);
					System.exit(0);
				}
			}else if(msg.contains(Protocol.addUser)) {
				String newuser = msg.split("/")[1];
				gui.getListmodel().addElement(newuser);
			}else if(msg.contains(Protocol.RM_USER)) {
				String rmuser = msg.split("/")[1];
				gui.getListmodel().removeElement(rmuser);
			}else if(msg.contains(Protocol.sendChat)) {
				//Recv chat
				String name = msg.split(":")[0];
				String chat = msg.split(":")[1];
				chat = chat.split("/")[1];
				gui.getChat().append(name.concat(": ").concat(chat).concat("\n"));	
			}else if(msg.contains(Protocol.sendCode)) {
				// Recv code
				if(!"@01/".equals(msg)) {
					if(!msg.split("01/")[1].equals(gui.getCode().getText())){
						gui.getCode().setText(msg.split("01/")[1]);
						gui.getCode().setCaretPosition(gui.getCode().getText().length());
						System.out.println(msg.split("01/")[1]);
					}else{
						
					}
				}
			}else if(msg.contains(Protocol.saveError)) {
				String errmsg = msg.split(Protocol.saveError)[1];
				Alert saverr = new Alert(errmsg);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	

}
