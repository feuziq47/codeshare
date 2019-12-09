package server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server {
	public static final String IP="192.168.56.1";
	public static final int PORT=8080;
	private static final int max_client_count=20;
	public static int clnt_cnt=0;
	private static User user;
	
	public static void main(String[] args) {
		ServerSocket serv_sock=null;
		
		
		Thread th[]=new Thread[max_client_count];
		try {
			// 1. 서버 소켓 생성
			serv_sock= new ServerSocket(PORT);
			
			// 2. 바인딩
			String hostAddress=InetAddress.getLocalHost().getHostAddress();
			//serv_sock.bind(new InetSocketAddress(hostAddress,PORT));
			System.out.println(hostAddress+":"+ PORT);
			
			// 3. 요청 대기
			while(true) {
				Socket sock=serv_sock.accept();
				if(sock != null) {
					synchronized(sock) {
						HandleClient hc=new HandleClient(sock);
						th[clnt_cnt]= new Thread(hc);
						th[clnt_cnt].start();
						clnt_cnt++;
						System.out.println(clnt_cnt);
					}
					
				}
				
			}
		}catch(Exception e){
			System.out.println(clnt_cnt);
			e.printStackTrace();
		}finally {
			if(serv_sock !=null && !serv_sock.isClosed()) {
				try {
					serv_sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}


