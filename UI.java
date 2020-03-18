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

public class UI extends Application
{
	/*public static final CountDownLatch latch = new CountDownLatch(1);
    public static UI ui = null;

    public static UI waitForUI() 
    {
        try 
        {
            latch.await();
        } 
        
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        return ui;
    }

    public static void setUI(UI ui1) {
        ui = ui1;
        latch.countDown();
    }

    public UI() {
        setUI(this);
    }

    public void printSomething() {
        System.out.println("You called a method on the application");
    }*/
	
	
	private Square[][] squares = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
	private VBox root;
	private Button startGame;
	private Player p1 = new Player(); 
	private Player p2 = new Player();
	private boolean player1Turn = true;
	
	public void setPlayerNames() 
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
        
        
        startGame.setOnMouseClicked(e -> enterName.show());
        
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
   			System.out.println("Player1: " + p1.getName() + "\nPlayer2: " + p2.getName());
   			enterName.hide();
   			root.getChildren().remove(startGame);
   			promptMove();
    	});           
	}
	
	public void promptMove() 
	{
		Text makeAMoveP1 = new Text(p1.getName() + " make a move");
		Text makeAMoveP2 = new Text(p2.getName() + " make a move");
		Button endTurn = new Button();
		endTurn.setText("End Turn");
		
		if(player1Turn) 
		{
			root.getChildren().addAll(makeAMoveP1, endTurn);
			player1Turn = false;
		}
		
		else 
		{
			root.getChildren().addAll(makeAMoveP2, endTurn);
			player1Turn = true;
		}
		
	}
	
	@Override
	public void start(Stage primaryStage) 
	{	
		root = new VBox(8);
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
		
		startGame = new Button();
		
		startGame.setText("Start Game");

		root.getChildren().add(startGame);
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Scrabble");
		primaryStage.setScene(scene);
		primaryStage.show();  
		setPlayerNames();
    }
}
