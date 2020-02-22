import java.util.*;
public class Board 
{
	//State of the board at the beginning of the game.
	//Abbreviations for Triple Word, Double Word, Triple Letter and Double Letter
	//@@ = Middle Square
	
	private int numOfWordsOnBoard = 0;
	private int numOfTurns = 0;
	
	private String[][] board = 
		{
				{"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"},
				{"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "},
				{"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "},
				{"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"},
				{"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "},
				{"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "},
				{"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "},
				{"TW", "  ", "  ", "DL", "  ", "  ", "  ", "@@", "  ", "  ", "  ", "DL", "  ", "  ", "TW"},
				{"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "},
				{"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "},
				{"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "},
				{"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"},
				{"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "},
				{"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "},
				{"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"}
		};
	
	//This could be used to reset the board the same way we reset the pool
	private final String[][] boardAtBeginning = 
		{
				{"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"},
				{"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "},
				{"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "},
				{"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"},
				{"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "},
				{"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "},
				{"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "},
				{"TW", "  ", "  ", "DL", "  ", "  ", "  ", "@@", "  ", "  ", "  ", "DL", "  ", "  ", "TW"},
				{"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "},
				{"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "},
				{"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "},
				{"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"},
				{"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "},
				{"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "},
				{"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"}
		};
	
	public void resetBoard() 
	{
		for(int i = 0; i < 15; i++) 
		{
			for(int j = 0; j < 15; j++) 
			{
				board[i][j] = boardAtBeginning[i][j];
			}
		}
	}
	
	//Some checks to be made before placing the word
	//wordCheck isn't linked to placeWord in any way yet
	//x1, y1 is the square on the board which will contain the first letter of the word
	//x2, y2 is the square on the board which will contain the last letter of the word
	public void wordCheck(Frame f, String wordToBePlaced, int x1, int y1, int x2, int y2) 
	{
		if((x1 > 14 || x1 < 0) || (x2 > 14 || x2 < 0) || (y1 > 14 || y1 < 0) || (y2 > 14 || y2 < 0)) 
		{
			System.out.println("Word is outside of bounds");
		}
		
		else if(numOfWordsOnBoard == 0) 
		{
			for(int i = 0; i < 15; i++) 
			{
				
			}
		}
		
		else
		{
			//wordToBePlaced is converted from a string to a char array
			for(int i = 0; i < wordToBePlaced.toCharArray().length; i++) 
			{		
				//If a letter from wordToBePlaced is not on the frame
				if(!f.isLetterInFrame(wordToBePlaced.toCharArray()[i])) 
				{
					System.out.println(wordToBePlaced.toCharArray()[i] + " is not in the frame");
					System.out.println("Can't place " + wordToBePlaced + " on the board");
					break;
				}
				
				else 
				{
					System.out.println(wordToBePlaced.toCharArray()[i] + " is on the frame");
				}
			}
		}
	}
	
	//Places a word on the board
	//Frame f is not used but might be used later
	public void placeWord(Frame f, String wordToBePlaced, int x1, int y1, int x2, int y2) 
	{
		int index = 0;
		if(x1 == x2) //Word is vertical, x = columns 
		{
			for(int i = y1; i <= y2; i++) 
			{
				//A letter is placed on the board
				board[i][x1] = (wordToBePlaced.toCharArray()[index] + " ").toUpperCase(); 
				index++;
			}
			
			numOfTurns++;
			numOfWordsOnBoard++;
		}
		
		else if(y1 == y2) //Word is horizontal, y = rows
		{
			for(int j = x1; j <= x2; j++) 
			{
				//A letter is placed on the board
				board[y1][j] = (wordToBePlaced.toCharArray()[index] + " ").toUpperCase();
				index++;
			}
			
			numOfTurns++;
			numOfWordsOnBoard++;
		}
		
		else
		{
			System.out.println("Word has to be vertical or horizontal");
		}
	}
	
	public void printLine() 
	{
		
		System.out.println("  ----------------------------------------------------------------------------");
	}
	
	public void displayBoard()
	{
		System.out.println("    A    B    C    D    E    F    G    H    I    J    K    L    M    N    O   ");
		printLine();
		for(int i = 0; i < 15; i++) 
		{
			if(i >= 10)
			System.out.print(i + "|");
			
			else
			System.out.print(i + " |");
			
			for(int j = 0; j < 15; j++) 
			{
				System.out.print(" " + board[i][j] + " |");//Taran-just made a small change here to make the board look better
			}
			
			System.out.println();
			printLine();
		}
		
	}
}
