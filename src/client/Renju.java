package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Renju extends Application{
	//��������
	EndPane blackWin=new EndPane(1);
	EndPane whiteWin=new EndPane(0);
	//��Ϸ����
	GamingPane pane;
	Scene scene;
	//��ʼ����
	StartPane startPane;
	//��ʾ�Ƿ��ܶ�
	int canMove;
	int isBlackOrWhite;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO �Զ����ɵķ������
		
		 
//		Timeline animation=new Timeline(
//				new KeyFrame(Duration.millis(50),e->{
//					displayChesses.draw();
//				}));
//		animation.setCycleCount(Timeline.INDEFINITE);
//		animation.play();
		startPane=new StartPane();
		pane=new GamingPane(startPane.connectToServer);
		scene=new Scene(startPane);
		
		//��startPane.label ��text���м���
		startPane.label.textProperty().addListener(new startText());
		//��ʼ��ť
		startPane.button.setOnAction(new startButton());
		
		//primaryStage.setResizable(false);
		primaryStage.setTitle("������");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//���䰴ť
		pane.giveUp.setOnAction(new giveUpHandler());
		
		//���������
		pane.displayChesses.setOnMousePressed(new displayChessesHandler());
		//����һ�ֵļ�����
		blackWin.button.setOnAction(e->{
			startPane.connectToServer.close();
			startPane=new StartPane();
			pane=new GamingPane(startPane.connectToServer);
			pane.giveUp.setOnAction(new giveUpHandler());
			startPane.label.textProperty().addListener(new startText());
			
			startPane.button.setOnAction(new startButton());
			scene.setRoot(startPane);
		});
		whiteWin.button.setOnAction(e->{
			startPane.connectToServer.close();
			startPane=new StartPane();
			pane=new GamingPane(startPane.connectToServer);
			pane.giveUp.setOnAction(new giveUpHandler());
			startPane.label.textProperty().addListener(new startText());
			startPane.button.setOnAction(new startButton());
			scene.setRoot(startPane);
		});
		
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	//�ȴ����ֵĻ�Ӧ
	private void waiteOtherPlayer()  {
		new Thread(()->{
			try {
				//���������־
				int temp=pane.connectToServer.fromSever.readChar();
				//����
				if(temp=='c') {
					//��������
					int x=pane.connectToServer.fromSever.readInt();
					int y=pane.connectToServer.fromSever.readInt();
					int type=pane.connectToServer.fromSever.readInt();
					//System.out.println(x+"+"+y+"+"+type);
					pane.displayChesses.chesses[x][y]=type;
					if(isBlackOrWhite==0) {
						pane.displayChesses.flag=0;
					}
					else {
						pane.displayChesses.flag=1;
					}
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					        //����JavaFX�����̵߳Ĵ�����ڴ˴�
					    	pane.displayChesses.draw();
					    	if(isWin(x, y)) {
					    		if(isBlackOrWhite==0) {
					    			scene.setRoot(whiteWin);
					    		}
					    		else {
									scene.setRoot(blackWin);
								}
					    	}
					    	pane.judgeFlag();
					    }
					});
					canMove=1;
				}
				else {
					if(isBlackOrWhite==1) {
		    			scene.setRoot(whiteWin);
		    		}
		    		else {
						scene.setRoot(blackWin);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}).start();
		
	}
	
	
	//�ж��Ƿ�ʤ��
	boolean isWin(int x,int y) {
		int color=pane.displayChesses.chesses[x][y];
		int count=checkCount(x, y, 1, 0, color);
		if(count>=5) {
			return true;
		}
		else {
			count=checkCount(x, y, 0, 1, color);
			if(count>=5) {
				return true;
			}
			else {
				count=checkCount(x, y, 1, -1, color);
				if(count>=5) {
					return true;
				}
				else {
					count=checkCount(x, y, 1, 1, color);
					if (count>=5) {
						return true;
						
					}
				}
			}
		}
		return false;
	}
	
	//ͳ�Ƹ���
	int checkCount(int x,int y,int xChange,int yChange,int color) {
		int count=1;
		int tempX=xChange;
		int tempY=yChange;
		try {
			while (color==pane.displayChesses.chesses[x+xChange][y+yChange]) {
				
				count++;
				if(xChange!=0)
					xChange++;
				if(yChange!=0) {
					if(yChange>0)
						yChange++;
					else
						yChange--;
				}
				
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
		}
		xChange=tempX;
		yChange=tempY;
		try {
			while (color==pane.displayChesses.chesses[x-xChange][y-yChange]) {
				count++;
				if(xChange!=0)
					xChange++;
				if(yChange!=0)
					if(yChange>0)
						yChange++;
					else
						yChange--;
				
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
		}
		return count;
	}

	//���ӵļ�����
	class displayChessesHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent e) {
			// TODO �Զ����ɵķ������
			
			if (canMove==1) {
				int x=(int) e.getX();
				int y=(int) e.getY();
				int tempX= (x-30)/30;
				int tempY=(y-30)/30;
				if(x>=10&&x<=490&&y>=10&&y<=490) {
					if(pane.displayChesses.chesses[tempX][tempY]==0) {
							
							//pane.displayChesses.flag=1;
						try {
							//��׼�����Ϸ
							startPane.connectToServer.toSever.writeChar('c');
							//��������
							startPane.connectToServer.toSever.writeInt(tempX);
							startPane.connectToServer.toSever.writeInt(tempY);
							if (isBlackOrWhite==0) {
								startPane.connectToServer.toSever.writeInt(1);
								pane.displayChesses.chesses[tempX][tempY]=1;
								pane.displayChesses.flag=1;
								
							}
							else {
								startPane.connectToServer.toSever.writeInt(2);
								pane.displayChesses.chesses[tempX][tempY]=2;
								pane.displayChesses.flag=0;
								
							}
							
							canMove=0;
							
							pane.judgeFlag();
							pane.displayChesses.draw();
							//������Ӯ�ж�
							if(isWin(tempX, tempY)) {
								if(isBlackOrWhite==0)
									scene.setRoot(blackWin);
								else
									scene.setRoot(whiteWin);
							}
							else {
								//waiteOtherPlayer
								waiteOtherPlayer();
							}
							
						} catch (IOException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
						
					}
				
				
				
			}
			
			
		}
			//pane.displayChesses.draw();
			//pane.judgeFlag();
//			if(isWin(tempX, tempY)) {
//				//�����־λ�Ƿ���
//				if(pane.displayChesses.flag==1) {
//					//JOptionPane.showMessageDialog(null, "�ڷ���ʤ");
//					//scene.setRoot(new EndPane(1));
//					scene.setRoot(blackWin);
//				}
//				else {
//					//JOptionPane.showMessageDialog(null, "�׷���ʤ");
//					//scene.setRoot(new EndPane(0));
//					scene.setRoot(whiteWin);
//				}
//			}
		}
		
		
		
		
		
		
	}
	//��ʼ��ť�ļ�����
	class startButton implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// TODO �Զ����ɵķ������
			startPane.button.setOnAction(null);
			startPane.start();
			pane.connectToServer=startPane.connectToServer;
		}
		
	}
	
	
	
	
	//���䰴ť�ļ�����
	class giveUpHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// TODO �Զ����ɵķ������
			//scene.setRoot(new EndPane(pane.displayChesses.flag));
			try {
				pane.connectToServer.toSever.writeChar('l');
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			if(isBlackOrWhite==1) {
				//JOptionPane.showMessageDialog(null, "�ڷ���ʤ");
				//scene.setRoot(new EndPane(1));
				scene.setRoot(blackWin);
			}
			else {
				//JOptionPane.showMessageDialog(null, "�׷���ʤ");
				//scene.setRoot(new EndPane(0));
				scene.setRoot(whiteWin);
			}
		}
		
	}
	
	//��ʼ�����ı��ļ�����
	class startText implements ChangeListener<Object> {

		@Override
		public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
			// TODO �Զ����ɵķ������
			if(startPane.label.getText().equals("��ʼ��Ϸ")) {
				scene.setRoot(pane);
				try {
					int flag=startPane.connectToServer.fromSever.readInt();
					if(flag==0) {
						isBlackOrWhite=0;
						pane.isBlackOrWhite.setText("���Ǻڷ�");
						canMove=1;
					}
					else if(flag==1){
						isBlackOrWhite=1;
						pane.isBlackOrWhite.setText("���ǰ׷�");
						canMove=0;
						waiteOtherPlayer();
					}
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
}
