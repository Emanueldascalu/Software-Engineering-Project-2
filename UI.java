package application;

//By Alphabet Inc. :	

	//Emanuel Dascalu, 18729365	

	//Pranchal Narang, 18339361 	
	//The github account is lakesh narang	

	//Taranpreet Singh, 18203372

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UI {

    BorderPane mainPane;
    GridPane boardPane;
    TextArea infoArea;
    TextField commandField;
    Button[][] displaySquares = new Button[Board.BOARD_SIZE][Board.BOARD_SIZE];
    Scrabble scrabble;
    boolean gameOver;
    String[] PlayerNames = {"Player 1", "Player 2"};

    UI() {
        scrabble = new Scrabble();
        gameOver = false;
    }

    public void displayStage(Stage primaryStage) {
        primaryStage.setTitle("Scrabble");

        mainPane = new BorderPane();
        boardPane = new GridPane();
        infoArea = new TextArea();
        commandField = new TextField();

        infoArea.setPrefRowCount(10);
        infoArea.setPrefColumnCount(15);
        infoArea.setWrapText(true);

        commandField.setPromptText("Enter command...");
        commandField.setPrefColumnCount(15);
        commandField.setOnAction(e -> {
            if (!gameOver) {
                String input = commandField.getText();
                infoArea.appendText("> " + input + "\n");
                commandField.clear();
                processInput(input);
            }
            if (!gameOver) {
                printPrompt();
            }
        });

        // rows are numbered from zero at the top
        // columns are numbers from zero at the left
        boardPane.setGridLinesVisible(true);
        int squareSize = 40;
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            boardPane.getColumnConstraints().add(new ColumnConstraints(squareSize));
            boardPane.getRowConstraints().add(new RowConstraints(squareSize));
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                Button button = new Button();
                boardPane.add(button, c, r);
                displaySquares[r][c] = button;
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setFillHeight(button, true);
                GridPane.setFillWidth(button, true);
            }
        }
        refreshBoard();

        mainPane.setCenter(boardPane);
        mainPane.setBottom(commandField);
        mainPane.setRight(infoArea);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();

        printGameStart();
        printPoolSize();
        printScores();
        printPrompt();
        
    }

    void refreshBoard() {
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                displaySquare(r, c);
            }
        }
    }

    void displaySquare(int r, int c) {
        Square square = scrabble.getBoard().getSquare(r, c);
        Button button = displaySquares[r][c];
        var style = new StringBuilder();
        style.append("-fx-background-radius: 0;");
        String color;
        if (square.isDoubleLetter()) {
            color = "8080ff";
        } else if (square.isTripleLetter()) {
            color = "0000ff";
        } else if (square.isDoubleWord()) {
            color = "ff8080";
        } else if (square.isTripleWord()) {
            color = "ff0000";
        } else {
            color = "ffffff";
        }
        style.append("-fx-background-color: #").append(color).append(';');
        if (square.isOccupied()) {
            style.append("-fx-font-size: 14pt;");
            style.append("-fx-font-weight: bold;");
            button.setStyle(style.toString());
            button.setText(square.getTile() + "");  // placed letter
        } else {
            style.append("-fx-font-size: 8pt;");
            style.append("-fx-font-weight: lighter;");
            button.setStyle(style.toString());
            if (r==0) {
                button.setText(((char) (((int) 'A') + c)) + "");  // column letters
            } else if (c==0) {
                button.setText((r + 1) + "");  // row numbers
            } else {
                button.setText("");  // empty
            }
        }
    }

    // Input methods

    private void processInput(String input) {
    	printPoolSize();
    	printScores();
        String command = input.trim().toUpperCase();
        Player currentPlayer = scrabble.getCurrentPlayer();
        if (command.equals("QUIT") || command.equals("Q")) {
            System.exit(0);
        } else if (!gameOver && (command.equals("PASS") || command.equals("P"))) {
            scrabble.zeroScorePlay();
            if (scrabble.isZeroScorePlaysOverLimit()) {
                printZeroScorePlaysOverLimit();
                gameOver = true;
            } else {
                scrabble.turnOver();
            }
        } else if (!gameOver && (command.equals("HELP") || command.equals("H"))) {
            printHelp();
        } else if (!gameOver && (command.equals("SCORE") || command.equals("S"))) {
            printScores();
        } else if (!gameOver && (command.equals("POOL") || command.equals("O"))) {
            printPoolSize(); 
        }  else if(!gameOver && (command.matches("NAME( )+([A-Z_]){1,15}"))) { 
        	changePlayerName(command); 
        } else if(!gameOver && (command.equals("CHALLENGE") || command.equals("C"))) {
        	if(scrabble.getBoard().getNumPlays() == 0) 
        	{
        		//Can't challenge on the first turn.
        		printLine("Can't challenge now.");
        	}
        	
        	else 
        	{
        		//Throws exception if the file is not found when trie is being created
	        	try {
					scrabble.createTrie(scrabble.getTrie());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	        	
	        	/*If the challenged word is not in the dictionary/trie, 
	        	 * any tiles the player who was challenged drew from the pool are returned to the pool.
	        	 * The tiles on the board that make up the challenged word are returned to the player's frame.
	        	 * The player loses all points gained from the play before the challenge
	        	 * The challenged player also loses their turn since the turnOver method isn't called*/
	        	if(!scrabble.getTrie().search(scrabble.getBoard().getLastWordPlaced().getLetters())) 
	        	{
	        		scrabble.turnOver();
	        		currentPlayer = scrabble.getCurrentPlayer();
	    			currentPlayer.getFrame().moveTilesToGoInPool(scrabble.getPool());
	    			currentPlayer.getFrame().getTiles().addAll(scrabble.getBoard().nullifyPlay());
	    			currentPlayer.removePoints(scrabble.getBoard().getPoints());
	    			scrabble.turnOver();
	    			currentPlayer = scrabble.getCurrentPlayer();
	    			if(scrabble.getBoard().getNumPlays() == 1) 
	    			{
	    				scrabble.getBoard().setNumPlays(0);
	    			}
	    			refreshBoard();
	        		print("Challenge successful. Previous play nullified.\n");
	        		//printScores();
	        	}
	        	
	        	else 
	        	{
	        		print("Unsuccessful challenge.\n");
	        		//Since the challenge was unsuccessful the current player loses their turn
	        		scrabble.turnOver();
	        	}
	        	
	        	scrabble.getBoard().resetPositionsList();
        	}
        } else if (!gameOver && (command.matches("[A-O](\\d){1,2}( )+[A,D]( )+([A-Z_]){1,15}"))) {
            Word word = parsePlay(command);
            if (!scrabble.getBoard().isLegalPlay(currentPlayer.getFrame(),word)) {
                printPlayError(scrabble.getBoard().getErrorCode());
            } else {
                scrabble.getBoard().place(currentPlayer.getFrame(),word);
                scrabble.getBoard().setLastWordPlaced(word);
                refreshBoard();
                int points = scrabble.getBoard().getPoints();
                printPoints(points);
                currentPlayer.addPoints(points);
                currentPlayer.getFrame().refill(scrabble.getPool());
                //currentPlayer.getFrame().setTilesToGoInPool();
                scrabble.scorePlay();
                scrabble.turnOver();
                if (currentPlayer.getFrame().isEmpty() && currentPlayer.getFrame().isEmpty()) {
                    gameOver = true;
                }
            }
        } else if (!gameOver && (command.matches("EXCHANGE( )+([A-Z_]){1,7}") || command.matches("X( )+([A-Z_]){1,7}"))) {
            String[] parts = command.split("( )+");
            String letters = parts[1];
            if (!currentPlayer.getFrame().isLegalExchange(scrabble.getPool(),letters)) {
                printExchangeError(currentPlayer.getFrame().getErrorCode());
            } else {
                currentPlayer.getFrame().exchange(scrabble.getPool(),letters);
                currentPlayer.getFrame().resetDraw();
                printTiles();
                scrabble.zeroScorePlay();
                if (scrabble.isZeroScorePlaysOverLimit()) {
                    printZeroScorePlaysOverLimit();
                    gameOver = true;
                } else {
                    scrabble.turnOver();
                }
            }
        } else {
            printLine("Error: command syntax incorrect. See help.");
        }
        if (gameOver) {
            scrabble.adjustScores();
            printScores();
            printWinner();
            printGameOver();
        }
    }

    private Word parsePlay(String command) {
        String[] parts = command.split("( )+");
        String gridText = parts[0];
        int column = ((int) gridText.charAt(0)) - ((int) 'A');
        String rowText = parts[0].substring(1);
        int row = Integer.parseInt(rowText)-1;
        String directionText = parts[1];
        boolean isHorizontal = directionText.equals("A");
        String letters = parts[2];
        return new Word(row, column, isHorizontal, letters);
    }

    // Print methods

    private void print(String text) {
        infoArea.appendText(text);
    }

    private void printLine(String text) {
        infoArea.appendText(text + "\n");
    }

    private void printGameStart() {
        printLine("WELCOME TO SCRABBLE");
    }
    
    //Replaces current player's name (Player 1 or Player 2) with a string
    private void changePlayerName(String command)
    { 
    	String parts[]=command.split("( )+"); 
    	PlayerNames[scrabble.getCurrentPlayerId()]=parts[1]; 
    	printLine(scrabble.getCurrentPlayer()+ ": " + PlayerNames[scrabble.getCurrentPlayerId()]); 
    	//scrabble.turnOver();
    }

    private void printTiles() 
    { 
    	printLine(PlayerNames[scrabble.getCurrentPlayerId()] + " has the following tiles:"); 
    	for (Tile tile : scrabble.getCurrentPlayer().getFrame().getTiles()) { 
    		print(tile + " "); 
    	} 
    	printLine(""); 
    } 

    private void printPrompt() { 
    	printLine(PlayerNames[scrabble.getCurrentPlayerId()] + "'s turn:"); printTiles(); 
    } 
    
    private void printPoints(int points) { 
    	printLine(PlayerNames[scrabble.getCurrentPlayerId()] + " scored " + points + " points."); 
    } 

    private void printPoolSize() {
        printLine("Pool has " + scrabble.getPool().size() + " tiles");
    }
    
    private void printHelp() { 
    	printLine("Command options: Q (quit), P (pass), X (exchange), S (scores), O (pool), C(challenge), NAME(name) or play"); 
    	printLine("For an exchange, enter the letters that you wish to exchange. E.g. X ABC"); 
    	printLine("For a play, enter the grid reference of the first letter, and A (across) or D (down) and the word optionally including any letters already on the board. E.g. A1 D HELLO"); 
    	printLine("For challenge enter word being challenged. E.g. C TORN"); 
    	printLine("For blank use underscore"); 
    } 

    private void printPlayError(int errCode) {
        String message = "";
        switch (errCode) {
            case Board.WORD_OUT_OF_BOUNDS:
                message = "Error: Word does not fit on the board.";
                break;
            case Board.WORD_LETTER_NOT_IN_FRAME:
                message = "Error: You do not have the necessary letters.";
                break;
            case Board.WORD_LETTER_CLASH:
                message = "Error: The word entered does not fit with the letters on the board.";
                break;
            case Board.WORD_NO_LETTER_PLACED:
                message = "Error: The word does not use any of your letters.";
                break;
            case Board.WORD_NO_CONNECTION:
                message = "Error: The word is not connected with the words on the board. ";
                break;
            case Board.WORD_INCORRECT_FIRST_PLAY:
                message = "Error: The first word must be in the centre of the board.";
                break;
            case Board.WORD_EXCLUDES_LETTERS:
                message = "Error: The word places excludes letters already on the board";
                break;
            case Board.WORD_ONLY_ONE_LETTER:
            	message = "Error: The word can't be one letter long";
            	break;
        }
        printLine(message);
    }

    public void printExchangeError (int errCode) {
        String message = "";
        switch (errCode) {
            case Frame.EXCHANGE_NOT_AVAILABLE:
                message = "Error: Letter not available in the frame.";
                break;
            case Frame.EXCHANGE_NOT_ENOUGH_IN_POOL:
                message = "Error: No enough tiles in the pool.";
                break;
        }
        printLine(message);
    }

    private void printZeroScorePlaysOverLimit() {
        printLine("The number of zero score plays is over the limit.");
    }
    
    private void printScores() { 
    	int i=0; 
    	for (Player player : scrabble.getPlayers()) { 
    		printLine(PlayerNames[i] + " has " + player.getScore() + " points."); 
    		i++; 
    	} 
    } 


    private void printWinner() {
        int maxScore = -1000;
        ArrayList<Player> winners = new ArrayList<>();
        boolean draw = false;
        for (Player player : scrabble.getPlayers()) {
            if (player.getScore() > maxScore) {
                draw = false;
                winners.clear();
                winners.add(player);
                maxScore = player.getScore();
            } else if (player.getScore() == maxScore) {
                draw = true;
                winners.add(player);
            }
        }
        if (!draw) {
            printLine(winners.get(0) + " wins!");
        } else {
            printLine("The following players draw!");
            for (Player winner : winners) {
                printLine(winner + "");
            }
        }
    }

    private void printGameOver() {
        printLine("GAME OVER");
    }
}


