package Lab_1_3;

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

		Text lab1 = new Text("Laboratorium 1 ");
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
		url.setMinWidth(500);
		grid.add(url, 0, 3, 3, 1);

		Button bhtml = new Button("Parsuj do wersji .html");
		bhtml.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(bhtml, 0, 5);

		Button btxt = new Button("Wyczyœæ i parsuj do wersji .txt");
		btxt.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(btxt, 1, 5);

		bhtml.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {
					Lab1.SaveHtml(url.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Label logresults = new Label("Zapisano-html");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 1);

			}

		});

		btxt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {
					Lab1.SaveTxt();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Label logresults = new Label("Zapisano-txt");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 3);

			}

		});

		///////////////////////////////////////////////////////////// Laboratorium
		///////////////////////////////////////////////////////////// 2//////////////////////////////////////////////////////

		Text lab2 = new Text("Laboratorium 2 ");
		lab2.setTextAlignment(TextAlignment.CENTER);
		lab2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(lab2, 0, 9);

		Label labelk = new Label("Podaj k:");
		labelk.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		labelk.setTextAlignment(TextAlignment.CENTER);
		grid.add(labelk, 0, 10);

		Label labelTS = new Label("Podaj THRESH:");
		labelTS.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		labelTS.setTextAlignment(TextAlignment.CENTER);
		grid.add(labelTS, 1, 10);

		Label labelmult = new Label("Z³o¿onoœæ obliczeniowa algorytmu:	 O(n)");
		labelmult.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
		labelmult.setTextAlignment(TextAlignment.CENTER);

		TextField readk = new TextField();
		readk.setAlignment(Pos.BOTTOM_CENTER);
		readk.setMaxWidth(200);
		grid.add(readk, 0, 11);

		TextField readTS = new TextField();
		readTS.setAlignment(Pos.BOTTOM_CENTER);
		readTS.setMaxWidth(200);
		grid.add(readTS, 1, 11);

		Button dohashmap = new Button("Generuj HashMape");
		dohashmap.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(dohashmap, 0, 12);

		Button showstat = new Button("Pokaz statystyki");
		showstat.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(showstat, 0, 13);

		dohashmap.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {

					Lab2.ConvertToMap(Integer.parseInt((readk.getText())), Integer.parseInt(readTS.getText()));

				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Label logresults = new Label("Wygenerowa³o HashMape");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 10);

			}

		});

		showstat.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				grid.add(labelmult, 1, 12);
				Label labeltime = new Label("Czas wykonywania algorytmu:	" + Lab2.duration + " milisekund");
				labeltime.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
				labeltime.setTextAlignment(TextAlignment.CENTER);
				grid.add(labeltime, 1, 13);
				Label logresults = new Label("Pokazalo statystyki");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 11);

			}

		});
		
		///////////////////////////////////////////////////////////// Laboratorium
		///////////////////////////////////////////////////////////// 3//////////////////////////////////////////////////////

		Text lab3 = new Text("Laboratorium 3 ");
		lab3.setTextAlignment(TextAlignment.CENTER);
		lab3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(lab3, 0, 15);
		
		
		Button download = new Button("Poka¿ wyniki");
		download.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(download, 0, 16);

		download.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Lab3.CreateVector();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Label logresults = new Label("Pokaza³o wyniki");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				grid.add(logresults, 8, 16);
				
				Label Win = new Label("Zwyciezcy:\n"+Lab3.zwyciezcy);
				Win.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
				Win.setTextAlignment(TextAlignment.CENTER);
				grid.add(Win, 1, 16);
				
				Label Lose = new Label("Przegrani:\n"+Lab3.przegrani);
				Lose.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
				Lose.setTextAlignment(TextAlignment.CENTER);
				grid.add(Lose, 2, 16);
				
				Label Name = new Label("Nazwy stron: \n"+Lab3.nazwy);
				Name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
				Name.setTextAlignment(TextAlignment.CENTER);
				grid.add(Name, 3, 16);

			}

		});
		
	

		Scene scene = new Scene(grid, 400, 375);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}