package server;

import java.awt.RenderingHints.Key;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import server.Protocol;
import db.DBmanager;
import view.Alert;
import view.GUI;

public class HandleClient implements Runnable{
	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
	private String clntname;
	private DBmanager dao;
	private File file;
	public HandleClient(Socket sock) {
		this.sock=sock;
		
	}
	@Override
	public synchronized void run() {
		try {
			in=new DataInputStream(sock.getInputStream());
			out=new DataOutputStream(sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 클라이언트 정보받기
		String recv="fail";
		
		while(recv == "fail") {
			recv = recvClntinfo();
			
		}
		
		// userlist에 추가
		if(recv !="fail") {
			this.clntname=recv;
			this.addClient(clntname,sock);
			System.out.println("Userlist : "+User.getUserInstance().getClientmap().keySet());
		}

		
		try {
			while(true) {
				String msg=in.readUTF();
				if(msg.contains(Protocol.sendChat)) {
					broadcast(clntname, msg);
				}else if(msg.contains(Protocol.sendCode)) {
					sharecode(msg);
				}else if(msg.contains(Protocol.save)) {
					save(msg);
				}else if(msg.contains(Protocol.MK_TRM)) {
					Process p = Runtime.getRuntime().exec("cmd");
					OutputStream stdin = p.getOutputStream();
					InputStream stderr = p.getErrorStream();
					InputStream stdout = p.getInputStream();
					
					//TerminalThread tt = new TerminalThread();
					//Thread trmth= new Thread(tt);
					//trmth.start();
				}
			}
		} catch (IOException e) {
			
		}finally {
			if(clntname != null) {
				removeClient(clntname);
			}
			
		}
		
	}
	
	
	
	public String recvClntinfo() {
		dao = dao.getInstance();
		
		try {
			String idpw=in.readUTF();
			if(idpw.equals("empty")) {
				return "fail";
			}else if(idpw.contentEquals("exit")) {
				// Client exit
				return "exit";
			}
			String[] tempArr = idpw.split("/");
			//id
			String id=tempArr[0];
			//pw
			String pw=tempArr[1];
			
			if(User.getUserInstance().getClientmap().containsKey(id)) {
				// Occupied id
				out.writeUTF(Protocol.loginSig.concat("접속중인 아이디입니다."));
			}else {
				String result=dao.signIn(id,pw);
				if(result.equals("success")) {
					// login success
					String temp=Protocol.loginSig.concat("success");
					out.writeUTF(temp);
					return id;
				}else if(result.equals("fail")) {
					// login fail
					out.writeUTF(Protocol.loginSig.concat("fail"));
				}
			}
		} catch (IOException e) {
			try {
				sock.close();
				//Server.clnt_cnt--;
				Thread.currentThread().interrupt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		return "fail";
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void addClient(String clnt_name, Socket sock) {
		try {
			
			// Set New Client
			Iterator it = User.getUserInstance().getClientmap().keySet().iterator();
			while(it.hasNext()) {
				out.writeUTF(Protocol.addUser.concat((String) it.next()));
			}
			User.getUserInstance().getClientmap().put(clnt_name,out);
			// 기존 유저들에게 알림
			String temp=Protocol.addUser.concat(clnt_name); 
			for(DataOutputStream os : User.getUserInstance().getClientmap().values()) {
				synchronized(os) {
					os.writeUTF(temp);
				}
			}
			
			System.out.println(clnt_name+" 접속");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void removeClient(String clnt_name) {
		try {
			User.getUserInstance().getClientmap().remove(clnt_name);
			String temp=Protocol.RM_USER.concat(clnt_name); 
			for(DataOutputStream os : User.getUserInstance().getClientmap().values()) {
				synchronized(os) {
					os.writeUTF(temp);
				}
			}
			System.out.println(clnt_name+" 퇴장");
			System.out.println("Userlist : "+User.getUserInstance().getClientmap().keySet());
		}catch (Exception e) {
			
		}
	}
	public void broadcast(String name, String msg) {
		try {
			msg=name.concat(":").concat(msg);
			for(DataOutputStream os : User.getUserInstance().getClientmap().values()) {
				synchronized(os) {
					os.writeUTF(msg);
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sharecode(String content) {
		String sender = content.split("@")[0];
		String code = "@".concat(content.split("@")[1]);
		HashMap<String, DataOutputStream> receiver = (HashMap<String, DataOutputStream>) User.getUserInstance().getClientmap().clone();
		receiver.remove(sender);
		
		for(DataOutputStream os: receiver.values()) {
			synchronized(os) {
				try {
					os.writeUTF(code);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void save(String msg) throws IOException {
		String name = msg.split(Protocol.save)[0];
		String content = msg.split(Protocol.save)[1];
		name=name.concat(".java");
		try {
			BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(file,false));
			bs.write(content.getBytes());
			bs.flush();
			bs.close();
		} catch ( IllegalStateException | IOException e) {
			String error = Protocol.saveError.concat(e.getMessage());
			synchronized(out) {
				out.writeUTF(error);
			}
			
		}
	
	}
	
	
}
