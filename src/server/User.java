package server;

import java.io.DataOutputStream;
import java.util.HashMap;


public class User {
	private HashMap<String,DataOutputStream> clientmap=new HashMap<String,DataOutputStream>();
	
	
	private static class Userholder{
		public static final User INSTANCE = new User();
	}
	public static User getUserInstance() {
		return User.Userholder.INSTANCE;
	}
	public HashMap<String,DataOutputStream> getClientmap(){
		return Userholder.INSTANCE.clientmap;
	}
}
