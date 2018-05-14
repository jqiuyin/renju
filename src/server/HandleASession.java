package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleASession implements Runnable{

	private Socket player1;
	private Socket player2;
	
	
	private DataInputStream fromPlayer1;
	private DataOutputStream toPlayer1;
	private DataInputStream fromPlayer2;
	private DataOutputStream toPlayer2;
	
	
	//����һ����־λ�������ֵ����ӻ��ǰ��ӣ�0�����ӣ�1������
	private int flag=0;
	
	
	
	public HandleASession(Socket player1,Socket player2) {
		// TODO �Զ����ɵĹ��캯�����
		this.player1=player1;
		this.player2=player2;
	}
	
	
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		try {
			fromPlayer1=new DataInputStream(player1.getInputStream());
			toPlayer1=new DataOutputStream(player1.getOutputStream());
			fromPlayer2=new DataInputStream(player2.getInputStream());
			toPlayer2=new DataOutputStream(player2.getOutputStream());
			
			toPlayer1.writeInt(0);//��player1�������Ǻ��ӣ�
			toPlayer2.writeInt(1);//��player2�������ǰ���
			
			
			while(true) {
				//���Ӷ�
				if(flag==0) {
					//��player1�����ݷ���player2
//					byte[] temp=new byte[32];
//					int counts=fromPlayer1.read(temp);
//					toPlayer2.write(temp, 0,counts);
					char temp=fromPlayer1.readChar();
					toPlayer2.writeChar(temp);
					if(temp=='c') {
						int x =fromPlayer1.readInt();
						int y=fromPlayer1.readInt();
						int type =fromPlayer1.readInt();
						toPlayer2.writeInt(x);//x
						toPlayer2.writeInt(y);//y
						toPlayer2.writeInt(type);//1
						flag=1;
					}
					
				}
				//���Ӷ�
				else if(flag==1) {
					//��player2�����ݷ���player1
//					byte[] temp=new byte[32];
//					int counts=formPlayer2.read(temp);
//					toPlayer1.write(temp, 0,counts );
					
					char temp=fromPlayer2.readChar();
					toPlayer1.writeChar(temp);
					if(temp=='c') {
						toPlayer1.writeInt(fromPlayer2.readInt());//x
						toPlayer1.writeInt(fromPlayer2.readInt());//y
						toPlayer1.writeInt(fromPlayer2.readInt());//2
						flag=0;
					}
				}
			}
			
			
			
			
			
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		
	}

	
	
	
}
