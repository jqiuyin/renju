package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GamingPane extends Pane{

	Label flag=new Label();
	Label isBlackOrWhite=new Label();
	Button giveUp=new Button("认输");
	DisplayChesses displayChesses=new DisplayChesses();
	Thread thread;
	ConnectToServer connectToServer;
	public GamingPane(ConnectToServer connectToServer) {
		// TODO 自动生成的构造函数存根
		this.connectToServer=connectToServer;
		BorderPane pane=new BorderPane();
		
		chessboard chessboard=new chessboard(displayChesses);
		pane.setCenter(chessboard);
		
		VBox rightPane=new VBox();
		rightPane.setPrefWidth(100);
		rightPane.setAlignment(Pos.CENTER);
		rightPane.getChildren().add(isBlackOrWhite);
		Label label=new Label("轮到：");
		rightPane.getChildren().add(label);
		judgeFlag();
		rightPane.getChildren().add(flag);
		
		
		rightPane.getChildren().add(giveUp);
		pane.setRight(rightPane);
		this.getChildren().add(pane);
	}
	
	
	 void judgeFlag() {
		if(displayChesses.flag==0)
			flag.setText("黑方");
		else {
			flag.setText("白方");
		}
	}
}
