package application;

import java.util.Scanner;

import javafx.application.Application;
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

public class UI
{	
	private Square[][] squares = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
	
	private boolean player1Turn = true;
	private boolean endOfGame = false;
	
	public boolean isEndOfGame() 
	{
		return endOfGame;
	}
	
	public void setPlayerNames(Player p1, Player p2, Stage mainStage) 
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
   			loops(p1, p2, mainStage);
    	});           
	}
	
	public void loops(Player p1, Player p2, Stage mainStage) 
	{
		//Scanner scanner = new Scanner(System.in);
		//while(!endOfGame)
		
		//loops() is meant to implement the above while loop
		//for loop is just a placeholder for now, needs to be implemented properly
		for(int i = 0; i < 8; i++) 
		{
			if(player1Turn) 
			{
				promptMove(mainStage, showBoard(), p1 /*scanner*/);
				player1Turn = false;
			}
			
			else 
			{
				promptMove(mainStage, showBoard(), p2 /*scanner*/);
				player1Turn = true; 
			}
		}
		//scanner.close();
	}
	
	public void promptMove(Stage mainStage, VBox vbox, Player player/*, Scanner scanner*/) 
	{
		Text makeAMove = new Text(player.getName() + " make a move");
		
		Button endTurn = new Button();
		endTurn.setText("End Turn");
		
		GridPane playerTiles = new GridPane();
			
		vbox.getChildren().addAll(makeAMove, endTurn, playerTiles);
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
		
		/*endTurn.setOnMouseClicked(e -> 
		{
			String command = scanner.next();
			switch(command) 
			{
				case "QUIT":
					endOfGame = true;
					break;
					
				case "PASS":
					break;
					
				case "HELP":
					break;
					
				case "EXCHANGE":
					break;	
					
				default:
					break;
			}
		});
		
		
		//mainStage.show();		*/
	}
	
	public VBox showBoard() 
	{
		VBox root = new VBox(8);
		root.setPadding(new Insets(20, 20, 20, 20));
		GridPane grid = new GridPane();
		
		root.getChildren().addAll(grid);
		
		
		for (int r = 0; r < Board.BOARD_SIZE; r++) 
		{
			for (int c = 0; c < Board.BOARD_SIZE; c++) 
			{
				StackPane stack = new StackPane();
				Text text = new Text("");
				squares[r][c] = new Square(Board.LETTER_MULTIPLIER[r][c],Board.WORD_MULTIPLIER[r][c], c, r);
				
				if (squares[r][c].isOccupied()) 
				{
					text = new Text(Character.toString(squares[r][c].getTile().getLetter()));
				}
		        
				else if(squares[r][c].isDoubleLetter() && squares[r][c].isDoubleWord()) 
				{
					text = new Text("@@");
					squares[r][c].setFill(Color.GREEN);
				}
				
		        else if(squares[r][c].isDoubleLetter()) 
		        {
		        	text = new Text("DL");
					squares[r][c].setFill(Color.BLUE);
		        }
		        
		        else if(squares[r][c].isTripleLetter())
		        {
		        	text = new Text("TL");
					squares[r][c].setFill(Color.RED);
		        }
		        
		        else if(squares[r][c].isDoubleWord()) 
		        {
		        	text = new Text("DW");
					squares[r][c].setFill(Color.BLUEVIOLET);
		        }
		        
		        else if(squares[r][c].isTripleWord()) 
		        {
		        	text = new Text("TW");
					squares[r][c].setFill(Color.CRIMSON);
		        }
		        
		        else 
		        {
		        	squares[r][c].setFill(Color.WHITE);
		        }
				
				stack.getChildren().addAll(squares[r][c], text);
				grid.add(stack, r, c);
			}
		}
		
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);
		
		return root;
	}
}
