package application;

import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class UI extends Application
{
	public static final int square_size = 30;
	private Square[][] squares = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
	public Button startGame;
	public Button endTurn;
	
	private Parent initialPopup() 
	{
		VBox root = new VBox(8);
		GridPane grid = new GridPane();
		
		//root.setPrefSize(Board.BOARD_SIZE * square_size, Board.BOARD_SIZE * square_size);
		root.getChildren().addAll(grid);
		
		
		for (int r = 0; r < Board.BOARD_SIZE; r++) 
		{
			for (int c = 0; c < Board.BOARD_SIZE; c++) 
			{
				StackPane stack = new StackPane();
				squares[r][c] = new Square(Board.LETTER_MULTIPLIER[r][c],Board.WORD_MULTIPLIER[r][c], c, r);
				
				if (squares[r][c].isOccupied()) 
				{
					
					Text text = new Text(Character.toString(squares[r][c].getTile().getLetter()));
					stack.getChildren().addAll(squares[r][c], text);
				}
		        
				else if(squares[r][c].isDoubleLetter() && squares[r][c].isDoubleWord()) 
				{
					Text text = new Text("@@");
					stack.getChildren().addAll(squares[r][c], text);
					squares[r][c].setFill(Color.GREEN);
				}
				
		        else if(squares[r][c].isDoubleLetter()) 
		        {
		        	Text text = new Text("DL");
					stack.getChildren().addAll(squares[r][c], text);
					squares[r][c].setFill(Color.BLUE);
		        }
		        
		        else if(squares[r][c].isTripleLetter())
		        {
		        	Text text = new Text("TL");
					stack.getChildren().addAll(squares[r][c], text);
					squares[r][c].setFill(Color.RED);
		        }
		        
		        else if(squares[r][c].isDoubleWord()) 
		        {
		        	Text text = new Text("DW");
					stack.getChildren().addAll(squares[r][c], text);
					squares[r][c].setFill(Color.BLUEVIOLET);
		        }
		        
		        else if(squares[r][c].isTripleWord()) 
		        {
		        	Text text = new Text("TW");
					stack.getChildren().addAll(squares[r][c], text);
					squares[r][c].setFill(Color.CRIMSON);
		        }
		        
		        else 
		        {
		        	squares[r][c].setFill(Color.WHITE);
		        }
				
				grid.add(stack, r, c);
			}
		}
		
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);
		
		startGame = new Button();
		endTurn = new Button();
		
		startGame.setText("Start Game");
		endTurn.setText("End Turn");
		endTurn.setDisable(true);
		
		setPlayerNames();
		
		root.getChildren().addAll(startGame, endTurn);
		return root;
	}
	
	public void setPlayerNames() 
	{
		startGame.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			
			@Override
			public void handle(MouseEvent arg0) 
			{
				Stage enterName = new Stage();
		        enterName.initModality(Modality.APPLICATION_MODAL);
				
		        VBox nameOfPlayersVbox = new VBox(8);
		        
		        TextField getName1 = new TextField();
		        getName1.setPromptText("Enter the name of player 1");
		        getName1.setPrefWidth(200);
		        getName1.setMaxWidth(200);
		        
		        TextField getName2 = new TextField();
		        getName2.setPromptText("Enter the name of player 2");
		        getName2.setPrefWidth(200);
		        getName2.setMaxWidth(200);
		        
		        nameOfPlayersVbox.getChildren().add(getName1);	            
		        nameOfPlayersVbox.getChildren().add(getName2);
		        
		        Scene nameOfPlayersScene = new Scene(nameOfPlayersVbox, 300, 200);
		        enterName.setScene(nameOfPlayersScene);
		        enterName.show();
		        
		        String player1Name = getName1.getText();
		        String player2Name = getName2.getText();

        	}
		});
	}
	
	@Override
	public void start(Stage primaryStage) 
	{
		Scene scene = new Scene(initialPopup());
		primaryStage.setTitle("Scrabble");
		primaryStage.setScene(scene);
		primaryStage.show();        
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
