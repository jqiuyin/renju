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
	
	
	//设置一个标志位，表明轮到黑子还是白子，0：黑子，1：白子
	private int flag=0;
	
	
	
	public HandleASession(Socket player1,Socket player2) {
		// TODO 自动生成的构造函数存根
		this.player1=player1;
		this.player2=player2;
	}
	
	
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		try {
			fromPlayer1=new DataInputStream(player1.getInputStream());
			toPlayer1=new DataOutputStream(player1.getOutputStream());
			fromPlayer2=new DataInputStream(player2.getInputStream());
			toPlayer2=new DataOutputStream(player2.getOutputStream());
			
			toPlayer1.writeInt(0);//向player1表明他是黑子；
			toPlayer2.writeInt(1);//向player2表明他是白子
			
			
			while(true) {
				//黑子动
				if(flag==0) {
					//将player1的数据发往player2
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
				//白子动
				else if(flag==1) {
					//将player2的数据发往player1
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}

	
	
	
}
