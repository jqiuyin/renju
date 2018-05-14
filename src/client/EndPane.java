package client;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class EndPane extends Pane{

	public Button button=new Button("再来一局");
	public EndPane(int flag) {
		// TODO 自动生成的构造函数存根
		super();
		this.setPrefSize(600, 500);
		Text text1=new Text(250,200,"恭喜：");
		this.getChildren().add(text1);
		if(flag==1) {
			Text text=new Text(250,250,"黑方获胜");
			this.getChildren().add(text);
		}
		else {
			Text text=new Text(250,250,"白方获胜");
			this.getChildren().add(text);
		}
		button.setLayoutX(250);
		button.setLayoutY(300);
		this.getChildren().add(this.button);
		
	}
}
