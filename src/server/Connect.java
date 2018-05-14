package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect {

	
	public static void main(String[] args) {
		//创建一个线程等待为两位玩家提供服务
		new Thread(()->{
			try {
				//创建一个server socket用于接收客户端的连接
				ServerSocket serverSocket=new ServerSocket(8000);
				System.out.println("服务在8000号端口启动");
				while(true) {
					System.out.println("等待第一位玩家接入");
					//第一个玩家接入
					Socket player1=serverSocket.accept();
					System.out.println("第一位玩家接入");
					System.out.println("等待第二位玩家接入");
					//第二位玩家接入
					Socket player2=serverSocket.accept();
					System.out.println("第二位玩家接入");
					System.out.println("游戏开始");
					//向两位玩家说明游戏开始
					new DataOutputStream(
							player1.getOutputStream()).writeChar('s');
					new DataOutputStream(
							player2.getOutputStream()).writeChar('s');
					new Thread(new HandleASession(player1, player2)).start();
					
				}
				
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}).start();
	}
}
