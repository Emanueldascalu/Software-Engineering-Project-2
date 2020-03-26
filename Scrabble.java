package application;

import java.util.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

//By Alphabet Inc. :

	//Emanuel Dascalu, 18729365

	//Pranchal Narang, 18339361 
	//The github account is lakesh narang

	//Taranpreet Singh, 18203372

/*An example of a word command: A1.A.HELLO instead of A1 A HELLO*/

public class Scrabble
{	
	private Pool pool = new Pool();
	private Board board = new Board();
	private Scanner scanner = new Scanner(System.in);
	
	private Frame frameP1 = new Frame();
	private Frame frameP2 = new Frame();
	private Player p1 = new Player();
	private Player p2 = new Player();
	private UI ui = new UI();
	
	private Word word;
	
	private BooleanProperty player1Turn = new SimpleBooleanProperty(true);
	
	private boolean endOfGame = false;
	
	public Player getP1() {return p1;}
	
	public Player getP2() {return p2;}
	
	public Board getBoard() {return board;}
	
	public boolean isEndOfGame() {return endOfGame;}
	
	public BooleanProperty isPlayer1Turn() {return player1Turn;}
	
	public void setEndOfGame() {endOfGame = true;}
	
	public void setPlayer1TurnFalse() {player1Turn.set(false);}
	
	public void setPlayer1TurnTrue() {player1Turn.set(true);}
	
	public Scanner getScanner() {return scanner;}
	
	public boolean areThereAnyTilesLeft() 
	{
		if(pool.size() == 0 && (p1.getFrame().size() == 0 || p2.getFrame().size() == 0)) 
		{
			return false;
		}
		
		return true;
	}
	
	public Scrabble(UI ui) 
	{
		this.ui = ui;
	}
	
	public void initializePlayers() 
	{
		p1.setFrame(frameP1);
		p1.setScore(0);
		p2.setFrame(frameP2);
		p2.setScore(0);
		
		p1.getFrame().refill(pool);
		p2.getFrame().refill(pool);
		p1.getFrame().setFrameUi();
		p2.getFrame().setFrameUi();
		
	}
	
	public void makeAMove(Player player) 
	{
		System.out.printf("Enter a command " + player.getName() + "\nThere are %d tiles in the pool\n", pool.size());
		String command = scanner.next();

		switch(command) 
		{
			case "QUIT":
				setEndOfGame();
				break;
				
			case "PASS":
				break;
			
			case "HELP":
				System.out.println("Enter PASS, QUIT or EXCHANGE.");
				System.out.println("Example of command placing a word: A1.1.HELLO");
				break;
			
			case "EXCHANGE":
				if(!areThereAnyTilesLeft()) 
				setEndOfGame();
				
				else 
				{
					System.out.println("How many tiles do you want to exchange?");
					int numGiven = scanner.nextInt();
					
					player.getFrame().exchange(pool, numGiven, scanner);
					System.out.println(pool.size());
				}
				
				break;
				
			default:
				String[] s = command.split("\\.");
				
				if(isLegalCommand(s, player) && word != null) 
				{
					board.place(player, word);
					player.getFrame().refill(pool);
					player.getFrame().setFrameUi();
				}
				
				
				break;
		}
		
		if(isPlayer1Turn().get()) 
		{
			setPlayer1TurnFalse();
			ui.promptMove(ui.showBoard(this), p2, this);
		}
		
		else
		{
			setPlayer1TurnTrue();
			ui.promptMove(ui.showBoard(this), p1, this);
		}
	}
	
	public boolean isLegalCommand(String[] s, Player player) 
	{
		boolean legal = true;
		
		if(s.length != 3) 
		{
			legal = false;
			System.out.println("Wrong number of strings");
			return legal;
		}
		
		else 
		{
			String firstLetterPosition = s[0];
			String acrossOrDown = s[1];
			String actualWord = s[2];
			
			if((firstLetterPosition.length() != 2 && firstLetterPosition.length() != 3) || acrossOrDown.length() != 1) 
			{
				legal = false;
				System.out.println("1st string is invalid or 2nd string is invalid");
				return legal;
			}
			
			if(firstLetterPosition.toCharArray()[0] < 'A' || firstLetterPosition.toCharArray()[0] > 'O' 
				|| firstLetterPosition.toCharArray()[1] < '1' || firstLetterPosition.toCharArray()[1] > '9') 
			{
				legal = false;
				System.out.println("1st string is invalid");
				return legal;
			}
			
			if(acrossOrDown.toCharArray()[0] != firstLetterPosition.toCharArray()[0] 
					&& acrossOrDown.toCharArray()[0] != firstLetterPosition.toCharArray()[1]) 
			{
				legal = false;
				System.out.println("2nd string is invalid");
				return legal;
			}
			
			int row = ((int) (firstLetterPosition.toCharArray()[0])) - 65;
			int column = (int) (firstLetterPosition.toCharArray()[1]) - 48;
			boolean across = (acrossOrDown.toCharArray()[0] == firstLetterPosition.toCharArray()[0]);
			
			
			word = new Word(row, column, across, actualWord);
			if(!board.isLegal(player.getFrame(), word)) 
			{
				legal = false;
				System.out.println("3rd string is invalid");
				return legal;
			}
		}
		
		return legal;
	}
}
