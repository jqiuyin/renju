package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectToServer {

	DataInputStream fromSever;
	DataOutputStream toSever;
	Socket socket;
	
	
	public ConnectToServer(String ip,int port ) {
		// TODO 自动生成的构造函数存根
		try {
			socket=new Socket(ip, port);
			fromSever=new DataInputStream(socket.getInputStream());
			toSever=new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
