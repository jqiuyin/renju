package client;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class EndPane extends Pane{

	public Button button=new Button("����һ��");
	public EndPane(int flag) {
		// TODO �Զ����ɵĹ��캯�����
		super();
		this.setPrefSize(600, 500);
		Text text1=new Text(250,200,"��ϲ��");
		this.getChildren().add(text1);
		if(flag==1) {
			Text text=new Text(250,250,"�ڷ���ʤ");
			this.getChildren().add(text);
		}
		else {
			Text text=new Text(250,250,"�׷���ʤ");
			this.getChildren().add(text);
		}
		button.setLayoutX(250);
		button.setLayoutY(300);
		this.getChildren().add(this.button);
		
	}
}
