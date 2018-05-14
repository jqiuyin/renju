package client;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DisplayChesses extends Pane{

	//����洢���ӵ�λ�ã�0�����ӣ�1�����ӣ�2������
		public  int[][] chesses=new int[15][15];
		//����һ����־λ�������ֵ����ӻ��ǰ��ӣ�0�����ӣ�1������
		public  int  flag=0;
	
	public DisplayChesses() {
		// TODO �Զ����ɵĹ��캯�����
		//setStyle("-fx-background-color:white;");
		setPrefSize(500, 500);
		draw();
		
	}
	//������
	public void draw() {
		this.getChildren().clear();//�����нڵ����
		for(int i=0;i<15;i++) {
			//System.out.print("\n");
			for(int j=0;j<15;j++) {
				//System.out.print(chesses[i][j]);
				//������
				//chesses[i][j]=1;
				if(chesses[i][j]==1) {
					Circle circle=new Circle(i*30+40,j*30+40,10);
					this.getChildren().add(circle);
				}
				//������
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
