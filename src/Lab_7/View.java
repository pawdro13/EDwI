package Lab_7;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import Lab_7.Lab_7;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class View  extends Application {
	
	int counter=10;
	int a=0;
	Label cleaner = new Label("");

	
	
	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Semantic Web");
		primaryStage.setWidth(800);
		primaryStage.setHeight(650);
		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setStyle("-fx-background-color: #DAE6F3");
		
		Lab_7 lab7 = new Lab_7();
		
		Text lab1 = new Text("Laboratorium 4 ");
		lab1.setTextAlignment(TextAlignment.CENTER);
		lab1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(lab1, 0, 0);
		
		Button indx = new Button("Uruchom indexowanie");
		indx.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(indx, 0, 2);
		
	
		Label labelurl = new Label("Podaj fraze do wyszukania:");
		labelurl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(labelurl, 0, 4);

		
		Label lablog = new Label("Logi:");
		lablog.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(lablog, 8, 0);
		
		TextArea url = new TextArea();
		url.setMinWidth(400);
		url.setMinHeight(50);
		grid.add(url, 0, 5, 3, 3);

		Button tit = new Button("Po tytule");
		tit.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(tit, 0, 9);
		
		
		Button con = new Button("W zawartosci");
		con.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(con, 2, 9);
		
		Label wyn = new Label("Wyniki wyszukiwania:");
		wyn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(wyn, 0, 10);
		
		TextArea pw = new TextArea();
		pw.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		pw.setMinWidth(400);
		pw.setMinHeight(170);
		grid.add(pw, 0, 11,3,3);
		
		indx.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				

							
				Label logresults = new Label("Indexowanie...");
				logresults.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
			
				grid.add(logresults, 8, counter);
				counter++;
				try {
					lab7.engine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
				Label logresult2 = new Label("Zaindexowano");
				logresult2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
				
				grid.add(logresult2, 8, counter);
				counter++;
			}

		});
	
		tit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				pw.setText("");
				try {
					
					pw.setText(lab7.engain(url.getText(),0));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}

		});
		
		con.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				pw.setText("");
				try {
					pw.setText(lab7.engain(url.getText(),1));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			

			}

		});


		Scene scene = new Scene(grid, 400, 375);
		primaryStage.setScene(scene);
		primaryStage.show();

	}


}
