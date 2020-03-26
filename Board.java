package application;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

//By Alphabet Inc. :

	//Emanuel Dascalu, 18729365

	//Pranchal Narang, 18339361 
	//The github account is lakesh narang

	//Taranpreet Singh, 18203372

/*An example of a word command: A1.A.HELLO instead of A1 A HELLO*/

public class Board 
{

	public static final int BOARD_SIZE = 15;
	public static final int BOARD_CENTRE = 7;

	public static final int[][] LETTER_MULTIPLIER =
		{ {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
		  {1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1},
		  {1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
		  {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
		  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		  {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
		  {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
		  {1, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 1},
		  {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
		  {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
		  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	      {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
		  {1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
		  {1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1},
		  {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1} };
	
	public static final int[][] WORD_MULTIPLIER =
		  { {3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3},
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
			{1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
			{1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
			{1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{3, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 3},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
			{1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
			{1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
			{3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3} };

	public static final int WORD_INCORRECT_FIRST_PLAY = 0;
	public static final int WORD_OUT_OF_BOUNDS = 1;
	public static final int WORD_LETTER_NOT_IN_FRAME = 2;
	public static final int WORD_LETTER_CLASH = 3;
	public static final int WORD_NO_LETTER_PLACED = 4;
	public static final int WORD_NO_CONNECTION = 5;

	private Square[][] squares;
	private int checkCode;
	private int numPlays;
	private GridPane boardUi;
	
	public GridPane getBoardUi() {return boardUi;}
	
	public void setBoardUi(Square[][] squares) 
	{
		boardUi = new GridPane();
		for(int i = 1; i <= BOARD_SIZE; i++) 
		{
			Text across = new Text(Character.toString((char) (int) ('A' + i - 1)));
			boardUi.add(across, 0, i);
		}
		
		for(int i = 1; i <= BOARD_SIZE; i++) 
		{
			Text down = new Text(Integer.toString(i - 1));
			boardUi.add(down, i, 0);
		}
		
		for (int r = 0; r < BOARD_SIZE; r++) 
		{
			for (int c = 0; c < BOARD_SIZE; c++) 
			{
				if(squares[r][c].isOccupied()) 
				{
					boardUi.add(squares[r][c].getTile().getTileUi(), c + 1, r + 1);
				}
				
				else 
				{
					boardUi.add(squares[r][c].getSquareUi(), c + 1, r + 1);
				}
			}
		}
		
		boardUi.setAlignment(Pos.CENTER);
		boardUi.setGridLinesVisible(true);
	}
	
	Board() 
	{
		squares = new Square[BOARD_SIZE][BOARD_SIZE];
		for (int r=0; r<BOARD_SIZE; r++)  
		{
			for (int c=0; c<BOARD_SIZE; c++)   
			{
				squares[r][c] = new Square(LETTER_MULTIPLIER[r][c],WORD_MULTIPLIER[r][c]);
			}
		}
		
		setBoardUi(squares);
		numPlays = 0;
	}
	
	public boolean isLegal(Frame frame, Word word) 
	{
		boolean isLegal = true;
		//check for invalid first play
		if (numPlays == 0 &&
				((word.isHorizontal() && (word.getRow()!=BOARD_CENTRE || word.getFirstColumn()>BOARD_CENTRE ||
						word.getLastColumn()<BOARD_CENTRE)) ||
				(word.isVertical() && (word.getColumn()!=BOARD_CENTRE || word.getFirstRow()>BOARD_CENTRE ||
						word.getLastRow()<BOARD_CENTRE)))) 
		{
			isLegal = false;
			checkCode = WORD_INCORRECT_FIRST_PLAY;
			System.out.println(checkCode);
		}
		// check for word out of bounds
		if (isLegal && ((word.isHorizontal() && word.getLastColumn()>= BOARD_SIZE) ||
				        (word.isVertical() && word.getLastRow()>= BOARD_SIZE))) 
		{
			isLegal = false;
			checkCode = WORD_OUT_OF_BOUNDS;
			System.out.println(checkCode);
		}
		// check that letters in the word do not clash with those on the board
		String lettersPlaced = "";
		if (isLegal) {
			int r = word.getFirstRow();
			int c = word.getFirstColumn();
			for (int i = 0; i < word.getLength() && isLegal; i++) 
			{
				if (squares[r][c].isOccupied() && squares[r][c].getTile().getLetter() != word.getLetter(i)) 
				{
					isLegal = false;
					checkCode = WORD_LETTER_CLASH;
					System.out.println(checkCode);
				} 
				
				else if (!squares[r][c].isOccupied()) 
				{
					lettersPlaced = lettersPlaced + word.getLetter(i);
				}
				
				if (word.isHorizontal()) 
				{
					c++;
				} 
				
				else 
				{
					r++;
				}
			}
		}
		// check that more than one letter is placed
		if (isLegal && lettersPlaced.length() == 0) 
		{
			isLegal = false;
			checkCode = WORD_NO_LETTER_PLACED;
			System.out.println(checkCode);
		}
		// check that the letters placed are in the frame
		if (isLegal && !frame.isAvailable(lettersPlaced)) 
		{
			isLegal = false;
			checkCode = WORD_LETTER_NOT_IN_FRAME;
			System.out.println(checkCode);
		}
		// check that the letters placed connect with the letters on the board
		if (isLegal && numPlays!=0) 
		{
			int boxTop = Math.max(word.getFirstRow()-1,0);
			int boxBottom = Math.min(word.getLastRow()+1, BOARD_SIZE-1);
			int boxLeft = Math.max(word.getFirstColumn()-1,0);
			int boxRight = Math.min(word.getLastColumn()+1, BOARD_SIZE-1);
			boolean foundConnection = false;
			for (int r=boxTop; r<=boxBottom && !foundConnection; r++) 
			{
				for (int c=boxLeft; c<=boxRight && !foundConnection; c++) 
				{
					if (squares[r][c].isOccupied()) 
					{
						foundConnection = true;
					}
				}
			}
			
			if (!foundConnection) 
			{
				isLegal = false;
				checkCode = WORD_NO_CONNECTION;
				System.out.println(checkCode);
			}
		}
		return isLegal;
	}

	// getCheckCode precondition: isLegal is false
	public int getCheckCode() 
	{
		return checkCode;
	};

	// place precondition: isLegal is true
	public void place(Player player, Word word) 
	{
		
		int playerScore = 0;
		int wordM = 1;
		
		int r = word.getFirstRow();
		int c = word.getFirstColumn();
		for (int i=0; i<word.getLength(); i++) 
		{
			if(!squares[r][c].isOccupied()) 
			{
				char letter = word.getLetter(i);
				Tile tile = player.getFrame().getTile(letter);
				
				squares[r][c].add(tile);
				player.getFrame().remove(tile);
				
				if(squares[r][c].isDoubleLetter()) 
				{
					playerScore += 2 * tile.getValue();
				}
				
				else if(squares[r][c].isDoubleWord() || squares[r][c].isCenter()) 
				{
					wordM *= 2;
					playerScore += tile.getValue();
				}
				
				else if(squares[r][c].isTripleLetter()) 
				{
					 playerScore += 3 * tile.getValue();
				}
				
				else if(squares[r][c].isTripleWord()) 
				{
					wordM *= 3;
					playerScore += tile.getValue();
				}
				
				else 
				{
					playerScore += tile.getValue();
				}
			}
			
			if (word.isHorizontal()) 
			{
				c++;
			} 
			
			else 
			{
				r++;
			}
		}
		
		playerScore *= wordM;
		player.increaseScore(playerScore);
		
		setBoardUi(squares);
		numPlays++;
	}

}