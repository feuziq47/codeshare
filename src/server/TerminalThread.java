package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TerminalThread implements Runnable{
	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
	
	public TerminalThread() {
		if(connect(Server.IP,Server.PORT)) {
			
		}
		
	}
	@Override
	public void run() {
		
		
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
}
