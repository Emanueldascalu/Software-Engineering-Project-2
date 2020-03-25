package application;

//import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.concurrent.CountDownLatch;
import javafx.scene.control.Label;

public class UI extends Application
{		
	private Stage mainStage;
	
	
	
	public void setPlayerNames(Player p1, Player p2, Scrabble scrabble) 
	{
		//New popup to enter two players' names
		Stage enterName = new Stage();
		
		//Can't exit out of initial popup without leaving this one first
        enterName.initModality(Modality.APPLICATION_MODAL); 
		
        VBox nameOfPlayersVbox = new VBox(8);
        nameOfPlayersVbox.setPadding(new Insets(20, 20, 20, 20));
        
        TextField getName1 = new TextField();
        getName1.setPromptText("Enter the name of player 1");
        getName1.setPrefWidth(200);
        getName1.setMaxWidth(200);
        
        TextField getName2 = new TextField();
        getName2.setPromptText("Enter the name of player 2");
        getName2.setPrefWidth(200);
        getName2.setMaxWidth(200);
      
        Button namesConfirmed = new Button();
        namesConfirmed.setText("Done");
        
        nameOfPlayersVbox.getChildren().add(getName1);	            
        nameOfPlayersVbox.getChildren().add(getName2);
        nameOfPlayersVbox.getChildren().add(namesConfirmed);
        
        Scene nameOfPlayersScene = new Scene(nameOfPlayersVbox, 300, 200);
        enterName.setScene(nameOfPlayersScene);
        enterName.show();
        
        //namesConfirmed Button can't be clicked until there is something in both textfields
        BooleanBinding textField1Valid = Bindings.createBooleanBinding(() -> {
        	if(getName1.getText().isEmpty()) 
        	return false;
        	
        	else
        	return true;
        }, getName1.textProperty());
        
        BooleanBinding textField2Valid = Bindings.createBooleanBinding(() -> {
        	if(getName2.getText().isEmpty()) 
        	return false;
        	
        	else
        	return true;
        }, getName2.textProperty());
        
        namesConfirmed.disableProperty().bind(textField1Valid.not().or(textField2Valid.not()));
        
    	
        namesConfirmed.setOnMouseClicked(e ->
    	{
    		p1.setName(getName1.getText());
   			p2.setName(getName2.getText());

   			enterName.hide();
   			//loops(p1, p2, scrabble);
   			promptMove(showBoard(scrabble), p1, scrabble);
    	});           
	}
	
	
	
	/*public void loops(Player p1, Player p2, Scrabble scrabble) 
	{
		for(int i = 0; i < 8; i++) 
		{
			if(scrabble.isPlayer1Turn().get()) 
			{
				promptMove(showBoard(scrabble), p1);
				scrabble.setPlayer1TurnFalse();
			}
			
			else 
			{
				promptMove(showBoard(scrabble), p2);
				scrabble.setPlayer1TurnTrue(); 
			}
		}
	}*/
	
	public void promptMove(VBox vbox, Player player, Scrabble scrabble) 
	{
		if(scrabble.isEndOfGame()) 
		{
			//System.out.println("There are no more tiles");
			Platform.exit();
		}
		
		else 
		{
			Text makeAMove = new Text(player.getName() + " make a move");
			
			Button makeMove = new Button();
			makeMove.setText("Make Move");
			
			GridPane playerTiles = new GridPane();
				
			vbox.getChildren().addAll(makeAMove, makeMove, playerTiles);
			for(int i = 0; i < 7; i++) 
			{
				StackPane stack = new StackPane();
				Text text = new Text("");
				Tile t = player.getFrame().getTiles().get(i);
				
				text = new Text(Character.toString(t.getLetter()));
				t.setFill(Color.YELLOW);
				stack.getChildren().addAll(t, text);
				playerTiles.add(stack, i, 0);
			}
			
			playerTiles.setAlignment(Pos.CENTER);
			playerTiles.setGridLinesVisible(true);
			
			mainStage.getScene().setRoot(vbox);
			
				
			makeMove.setOnMouseClicked(e -> scrabble.makeAMove(player));
		}
	}
	
	public VBox showBoard(Scrabble scrabble) 
	{
		VBox root = new VBox(8);
		root.setPrefSize(600, 600);
		root.setPadding(new Insets(20, 20, 20, 20));
		
		root.getChildren().add(scrabble.getBoard().getGrid());
		
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{	
		Scrabble scrabble = new Scrabble(this);
		scrabble.initializePlayers();
		
		Button startGame = new Button();		
		startGame.setText("Start Game");
		
		VBox initialPopup = showBoard(scrabble);
		initialPopup.getChildren().add(startGame);
		
		Scene scene = new Scene(initialPopup);
		mainStage = primaryStage;
		mainStage.setTitle("Scrabble");
		mainStage.setScene(scene);
		mainStage.show(); 
		
		startGame.setOnMouseClicked(e -> 
		{
			initialPopup.getChildren().remove(startGame);
			setPlayerNames(scrabble.getP1(), scrabble.getP2(), scrabble);
			//showBoard(scrabble).getChildren().remove(startGame);
			//setPlayerNames(p1, p2, mainStage);
		});
    }
}
