package client;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DisplayChesses extends Pane{

	//数组存储棋子的位置，0：无子，1：黑子，2：白子
		public  int[][] chesses=new int[15][15];
		//设置一个标志位，表明轮到黑子还是白子，0：黑子，1：白子
		public  int  flag=0;
	
	public DisplayChesses() {
		// TODO 自动生成的构造函数存根
		//setStyle("-fx-background-color:white;");
		setPrefSize(500, 500);
		draw();
		
	}
	//画棋子
	public void draw() {
		this.getChildren().clear();//将所有节点清空
		for(int i=0;i<15;i++) {
			//System.out.print("\n");
			for(int j=0;j<15;j++) {
				//System.out.print(chesses[i][j]);
				//画黑子
				//chesses[i][j]=1;
				if(chesses[i][j]==1) {
					Circle circle=new Circle(i*30+40,j*30+40,10);
					this.getChildren().add(circle);
				}
				//画白子
				if(chesses[i][j]==2) {
					Circle circle=new Circle(i*30+40,j*30+40,10);
					circle.setFill(Color.WHITE);
					this.getChildren().add(circle);
				}
			}
		}
		//System.out.print("\n---------------------------------------");
	}
	
	
	
	
	
}
