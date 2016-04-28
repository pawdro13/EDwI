package Lab_4;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class View extends Application {

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Semantic Web");
		primaryStage.setWidth(1000);
		primaryStage.setHeight(750);
		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setStyle("-fx-background-color: #DAE6F3");
		

		//////////////////////////////////////////// Laboratorium 1
		//////////////////////////////////////////// ///////////////////////////////////////////////////////

		Text lab1 = new Text("Laboratorium 4 ");
		lab1.setTextAlignment(TextAlignment.CENTER);
		lab1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(lab1, 0, 0);

		Label labelurl = new Label("Podaj adres strony:");
		labelurl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(labelurl, 0, 1);

		
		Label lablog = new Label("Logi:");
		lablog.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(lablog, 8, 0);
		
		TextField url = new TextField();
		url.setMinWidth(200);
		grid.add(url, 0, 3);

		Button bhtml = new Button("Wykonaj drzewo");
		bhtml.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(bhtml, 0, 5);
		
		bhtml.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				int n= Integer.parseInt(url.getText());
				Lab4.replayScan(n);
				
				Label logresults = new Label("Stworzono drzewo");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 1);

			}

		});

		Scene scene = new Scene(grid, 400, 375);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
