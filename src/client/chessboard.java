package client;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class chessboard extends Pane{
	public chessboard(DisplayChesses displayChesses ) {
		// TODO 自动生成的构造函数存根
		setStyle("-fx-background-color:blue;");
		setPrefSize(500, 500);
		
		//画棋盘
		for (int i = 40; i <=460; i+=30) {
			Line line=new Line(i,40,i,460);
			Line line2=new Line(40,i,460,i);
			this.getChildren().add(line);
			this.getChildren().add(line2);
			
			Text text=new Text(i-5,25,""+((i-40)/30+1));
			this.getChildren().add(text);
			Text text1=new Text(13,i+5,String.valueOf((char) ((i-40)/30+65)));
			this.getChildren().add(text1);
		}
		
		//画天元
		Circle circle1=new Circle(250,250,3);
		this.getChildren().add(circle1);
		//画四个星
		Circle circle2=new Circle(130,130,3);
		this.getChildren().add(circle2);
		Circle circle3=new Circle(130,370,3);
		this.getChildren().add(circle3);
		Circle circle4=new Circle(370,370,3);
		this.getChildren().add(circle4);
		Circle circle5=new Circle(370,130,3);
		this.getChildren().add(circle5);
		//将棋子添加到棋盘上
		this.getChildren().add(displayChesses);
		
	}
}
