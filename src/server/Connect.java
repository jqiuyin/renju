package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect {

	
	public static void main(String[] args) {
		//����һ���̵߳ȴ�Ϊ��λ����ṩ����
		new Thread(()->{
			try {
				//����һ��server socket���ڽ��տͻ��˵�����
				ServerSocket serverSocket=new ServerSocket(8000);
				System.out.println("������8000�Ŷ˿�����");
				while(true) {
					System.out.println("�ȴ���һλ��ҽ���");
					//��һ����ҽ���
					Socket player1=serverSocket.accept();
					System.out.println("��һλ��ҽ���");
					System.out.println("�ȴ��ڶ�λ��ҽ���");
					//�ڶ�λ��ҽ���
					Socket player2=serverSocket.accept();
					System.out.println("�ڶ�λ��ҽ���");
					System.out.println("��Ϸ��ʼ");
					//����λ���˵����Ϸ��ʼ
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
