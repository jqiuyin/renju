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
	//结束界面
	EndPane blackWin=new EndPane(1);
	EndPane whiteWin=new EndPane(0);
	//游戏界面
	GamingPane pane;
	Scene scene;
	//开始界面
	StartPane startPane;
	//表示是否能动
	int canMove;
	int isBlackOrWhite;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		
		 
//		Timeline animation=new Timeline(
//				new KeyFrame(Duration.millis(50),e->{
//					displayChesses.draw();
//				}));
//		animation.setCycleCount(Timeline.INDEFINITE);
//		animation.play();
		startPane=new StartPane();
		pane=new GamingPane(startPane.connectToServer);
		scene=new Scene(startPane);
		
		//对startPane.label 的text进行监听
		startPane.label.textProperty().addListener(new startText());
		//开始按钮
		startPane.button.setOnAction(new startButton());
		
		//primaryStage.setResizable(false);
		primaryStage.setTitle("五子棋");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//认输按钮
		pane.giveUp.setOnAction(new giveUpHandler());
		
		//监听鼠标点击
		pane.displayChesses.setOnMousePressed(new displayChessesHandler());
		//再来一局的监听器
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
	
	//等待对手的回应
	private void waiteOtherPlayer()  {
		new Thread(()->{
			try {
				//接收认输标志
				int temp=pane.connectToServer.fromSever.readChar();
				//继续
				if(temp=='c') {
					//接收坐标
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
					        //更新JavaFX的主线程的代码放在此处
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
	
	
	//判断是否胜利
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
	
	//统计个数
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
			// TODO 自动生成的 catch 块
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
			// TODO 自动生成的 catch 块
		}
		return count;
	}

	//下子的监听器
	class displayChessesHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent e) {
			// TODO 自动生成的方法存根
			
			if (canMove==1) {
				int x=(int) e.getX();
				int y=(int) e.getY();
				int tempX= (x-30)/30;
				int tempY=(y-30)/30;
				if(x>=10&&x<=490&&y>=10&&y<=490) {
					if(pane.displayChesses.chesses[tempX][tempY]==0) {
							
							//pane.displayChesses.flag=1;
						try {
							//表白继续游戏
							startPane.connectToServer.toSever.writeChar('c');
							//发送坐标
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
							//进行输赢判断
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
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
						
					}
				
				
				
			}
			
			
		}
			//pane.displayChesses.draw();
			//pane.judgeFlag();
//			if(isWin(tempX, tempY)) {
//				//这里标志位是反的
//				if(pane.displayChesses.flag==1) {
//					//JOptionPane.showMessageDialog(null, "黑方获胜");
//					//scene.setRoot(new EndPane(1));
//					scene.setRoot(blackWin);
//				}
//				else {
//					//JOptionPane.showMessageDialog(null, "白方获胜");
//					//scene.setRoot(new EndPane(0));
//					scene.setRoot(whiteWin);
//				}
//			}
		}
		
		
		
		
		
		
	}
	//开始按钮的监听器
	class startButton implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// TODO 自动生成的方法存根
			startPane.button.setOnAction(null);
			startPane.start();
			pane.connectToServer=startPane.connectToServer;
		}
		
	}
	
	
	
	
	//认输按钮的监听器
	class giveUpHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// TODO 自动生成的方法存根
			//scene.setRoot(new EndPane(pane.displayChesses.flag));
			try {
				pane.connectToServer.toSever.writeChar('l');
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(isBlackOrWhite==1) {
				//JOptionPane.showMessageDialog(null, "黑方获胜");
				//scene.setRoot(new EndPane(1));
				scene.setRoot(blackWin);
			}
			else {
				//JOptionPane.showMessageDialog(null, "白方获胜");
				//scene.setRoot(new EndPane(0));
				scene.setRoot(whiteWin);
			}
		}
		
	}
	
	//开始界面文本的监听器
	class startText implements ChangeListener<Object> {

		@Override
		public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
			// TODO 自动生成的方法存根
			if(startPane.label.getText().equals("开始游戏")) {
				scene.setRoot(pane);
				try {
					int flag=startPane.connectToServer.fromSever.readInt();
					if(flag==0) {
						isBlackOrWhite=0;
						pane.isBlackOrWhite.setText("我是黑方");
						canMove=1;
					}
					else if(flag==1){
						isBlackOrWhite=1;
						pane.isBlackOrWhite.setText("我是白方");
						canMove=0;
						waiteOtherPlayer();
					}
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
}
