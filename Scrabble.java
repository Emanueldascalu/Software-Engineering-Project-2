package application;

import java.util.*;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Scrabble extends Application
{	
	private Pool pool = new Pool();
	private Board board = new Board();
	private Scanner scanner = new Scanner(System.in);
	
	private Frame frameP1 = new Frame();
	private Frame frameP2 = new Frame();
	private Player p1 = new Player();
	private Player p2 = new Player();
	private UI ui = new UI();
	
	private Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{	
		
		p1.setFrame(frameP1);
		p1.setScore(0);
		p2.setFrame(frameP2);
		p2.setScore(0);
		
		p1.getFrame().refill(pool);
		p2.getFrame().refill(pool);
		
		
		Button startGame = new Button();
		
		startGame.setText("Start Game");
		
		VBox initialPopup = ui.showBoard();
		initialPopup.getChildren().add(startGame);
		
		Scene scene = new Scene(initialPopup);
		mainStage = primaryStage;
		mainStage.setTitle("Scrabble");
		mainStage.setScene(scene);
		mainStage.show(); 
		
		startGame.setOnMouseClicked(e -> 
		{
			ui.showBoard().getChildren().remove(startGame);
			ui.setPlayerNames(p1, p2, mainStage);
		});
    }
}

