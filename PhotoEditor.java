package photoeditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PhotoEditor extends Application{
	
	Scene sc;
	BorderPane bp;
	HBox hb;
	
	Label zoomLbl;
	Slider zoomSlider;
	Button rotateBtn;
	
	ImageView iv;
	Image img;
	
	double currRotation = 0;
	
	public void init() {
		bp = new BorderPane();
		hb = new HBox();
		
		img = new Image(getClass().getResourceAsStream("cat-image1.jpg"));
		iv = new ImageView(img);
		
		zoomLbl = new Label("Zoom");
		zoomSlider = new Slider(0.1, 3.0, 1.0);
		rotateBtn = new Button("Rotate");
		
		bp.setCenter(iv);
		
		iv.setFitWidth(img.getWidth());
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		
		hb.getChildren().addAll(zoomSlider,zoomLbl,rotateBtn);
		hb.setSpacing(8);
		
		bp.setTop(hb);
		sc = new Scene(bp, 800, 700);
	}
	
	public void setAction() {
		rotateBtn.setOnMouseClicked(e -> {
			currRotation += 90;
			if (currRotation >= 360) {
				currRotation -= 360;
			}
			iv.setRotate(currRotation);
		});
		
		zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
			double zoom = newVal.doubleValue();
			iv.setFitWidth(img.getWidth() * zoom);
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage ps) throws Exception {
		// TODO Auto-generated method stub
		init();
		setAction();
		ps.setScene(sc);
		ps.show();
	}

}
