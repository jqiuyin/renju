package client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartPane extends Pane{

	Button button=new Button("开始游戏");
	Label label=new Label("");
	ConnectToServer connectToServer;
	String ip="127.0.0.1";
	int port=8000;
	Label ipLabel=new Label("ip:");
	TextField ipTextField=new TextField(ip);
	Label portLabel=new Label("端口号:");
	TextField portTextField=new TextField(""+port);
	public StartPane() {
		// TODO 自动生成的构造函数存根
        BorderPane startPane=new BorderPane();
		Label title=new Label("五子棋");
		title.setFont(new Font("微软雅黑",60));
		startPane.setPrefSize(600, 500);
		startPane.setTop(title);
		VBox box=new VBox();
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(ipLabel);
		hBox.getChildren().add(ipTextField);
		hBox.getChildren().add(portLabel);
		hBox.getChildren().add(portTextField);
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(hBox);
		box.getChildren().add(label);
		box.getChildren().add(button);
		startPane.setCenter(box);
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setAlignment(box, Pos.CENTER);
		this.getChildren().add(startPane);
	}
	
	
	public void start() {
		ip=ipTextField.getText();
		port=Integer.valueOf(portTextField.getText());
		label.setText("连接中");
		connectToServer=new ConnectToServer(ip, port);
		//System.out.println("1");
		label.setText("匹配中");
		new Thread(()->{
			try {
				if(connectToServer.fromSever.readChar()=='s') {
					
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					        //更新JavaFX的主线程的代码放在此处
					    	label.setText("开始游戏");
					    }
					});
					
				}
					
					
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		}).start();
		
		
		
		
		
		
	}
	
}
