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
		// TODO �Զ����ɵĹ��캯�����
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
}
