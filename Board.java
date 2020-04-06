package application;

//By Alphabet Inc. :	

	//Emanuel Dascalu, 18729365	

	//Pranchal Narang, 18339361 	
	//The github account is lakesh narang	

	//Taranpreet Singh, 18203372

import java.util.ArrayList;

public class Board {

	public static final int BOARD_SIZE = 15;
	public static final int BOARD_CENTRE = 7;
	private static int BONUS = 50;

	public static final int WORD_INCORRECT_FIRST_PLAY = 0;
	public static final int WORD_OUT_OF_BOUNDS = 1;
	public static final int WORD_LETTER_NOT_IN_FRAME = 2;
	public static final int WORD_LETTER_CLASH = 3;
	public static final int WORD_NO_LETTER_PLACED = 4;
	public static final int WORD_NO_CONNECTION = 5;
	public static final int WORD_EXCLUDES_LETTERS = 6;
	public static final int WORD_ONLY_ONE_LETTER = 7;

	private static final int[][] LETTER_MULTIPLIER =
			{ 	{1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
				{2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
				{1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
				{1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
				{1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
				{1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
				{1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1} };
	private static final int[][] WORD_MULTIPLIER =
			{   {3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3},
				{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
				{1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
				{1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
				{1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{3, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 3},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
				{1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
				{1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
				{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
				{3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3} };

	private Square[][] squares;
	private int errorCode;
	private int numPlays;
	private int points;
	
	//Following three variables are used in the Challenge command
	
	//We assumed that challenges can only be made on words played one turn before 
	/*Positions on the board of the tiles of the previously placed word are placed here 
	 * to be used if a challenge is successful and all tiles from the previous play 
	 * have to be removed.
	 * It takes column values if the challenged word is horizontal.
	 * It takes row values if the challenged word is vertical.*/
	private ArrayList<Integer> positionsOfPlayedTiles;
	
	/*If challenged word is horizontal it takes the row value 
	 * of the first letter's position. If vertical, it takes the column value*/
	private int positionOfFirstLetter;
	
	/*It stores the last placed word to be accessed for challenges*/
	private Word lastWordPlaced;
	
	public Word getLastWordPlaced() {return lastWordPlaced;}
	
	public void setLastWordPlaced(Word w) {lastWordPlaced = w;}
	
	
	public int getNumPlays() {return numPlays;}  
	
	public void setNumPlays(int i) {numPlays = i;} 
	Board() {
		squares = new Square[BOARD_SIZE][BOARD_SIZE];
		for (int r=0; r<BOARD_SIZE; r++)  {
			for (int c=0; c<BOARD_SIZE; c++)   {
				squares[r][c] = new Square(LETTER_MULTIPLIER[r][c],WORD_MULTIPLIER[r][c]);
			}
		}
		numPlays = 0;
		points = 0;
		positionsOfPlayedTiles = new ArrayList<Integer>();
		positionOfFirstLetter = 0;
	}

	public boolean isLegalPlay(Frame frame, Word word) {
		boolean isLegal = true;
		
		// check if word is only one letter long
		if(word.getLetters().length() == 1) 
		{
			isLegal = false;
			errorCode = WORD_ONLY_ONE_LETTER;
		}
		
		//check for invalid first play
		if (numPlays == 0 &&
				((word.isHorizontal() && (word.getRow()!=BOARD_CENTRE || word.getFirstColumn()>BOARD_CENTRE ||
						word.getLastColumn()<BOARD_CENTRE)) ||
						(word.isVertical() && (word.getColumn()!=BOARD_CENTRE || word.getFirstRow()>BOARD_CENTRE ||
								word.getLastRow()<BOARD_CENTRE)))) {
			isLegal = false;
			errorCode = WORD_INCORRECT_FIRST_PLAY;
		}
		// check for word out of bounds
		if (isLegal && ((word.isHorizontal() && word.getLastColumn()>= BOARD_SIZE) ||
				(word.isVertical() && word.getLastRow()>= BOARD_SIZE))) {
			isLegal = false;
			errorCode = WORD_OUT_OF_BOUNDS;
		}
		// check that letters in the word do not clash with those on the board
		String lettersPlaced = "";
		if (isLegal) {
			int r = word.getFirstRow();
			int c = word.getFirstColumn();
			for (int i = 0; i < word.getLength() && isLegal; i++) {
				if (squares[r][c].isOccupied() && squares[r][c].getTile().getLetter() != word.getLetter(i)) {
					isLegal = false;
					errorCode = WORD_LETTER_CLASH;
				} else if (!squares[r][c].isOccupied()) {
					lettersPlaced = lettersPlaced + word.getLetter(i);
				}
				if (word.isHorizontal()) {
					c++;
				} else {
					r++;
				}
			}
		}
		// check that more than one letter is placed
		if (isLegal && lettersPlaced.length() == 0) {
			isLegal = false;
			errorCode = WORD_NO_LETTER_PLACED;
		}
		// check that the letters placed are in the frame
		if (isLegal && !frame.isAvailable(lettersPlaced)) {
			isLegal = false;
			errorCode = WORD_LETTER_NOT_IN_FRAME;
		}
		// check that the letters placed connect with the letters on the board
		if (isLegal && numPlays>0) {
			int boxTop = Math.max(word.getFirstRow()-1,0);
			int boxBottom = Math.min(word.getLastRow()+1, BOARD_SIZE-1);
			int boxLeft = Math.max(word.getFirstColumn()-1,0);
			int boxRight = Math.min(word.getLastColumn()+1, BOARD_SIZE-1);
			boolean foundConnection = false;
			for (int r=boxTop; r<=boxBottom && !foundConnection; r++) {
				for (int c=boxLeft; c<=boxRight && !foundConnection; c++) {
					if (squares[r][c].isOccupied()) {
						foundConnection = true;
					}
				}
			}
			if (!foundConnection) {
				isLegal = false;
				errorCode = WORD_NO_CONNECTION;
			}
		}
		// check there are no tiles before the word
		if (isLegal && numPlays>0) {
			if ( (word.isHorizontal() && word.getFirstColumn()>0 &&
					squares[word.getRow()][word.getFirstColumn()-1].isOccupied()) ||
					(word.isHorizontal() && word.getLastColumn()<BOARD_SIZE-1 &&
					squares[word.getRow()][word.getLastColumn()+1].isOccupied()) ||
					(word.isVertical() && word.getFirstRow()>0 &&
					squares[word.getFirstRow()-1][word.getColumn()].isOccupied()) ||
					(word.isVertical() && word.getLastRow()<BOARD_SIZE-1 &&
					squares[word.getLastRow()+1][word.getColumn()].isOccupied())) {
				isLegal = false;
				errorCode = WORD_EXCLUDES_LETTERS;
			}
		}
		return isLegal;
	}

	// getCheckCode precondition: isLegal is false
	public int getErrorCode() {
		return errorCode;
	}

	// place precondition: isLegal is true
	public void place(Frame frame, Word word) {
		points = 0;
		boolean frameWasFull = frame.isFull();
		int wordMultipler = 1;
		int r = word.getFirstRow();
		int c = word.getFirstColumn();
		
		if (word.isHorizontal()) {
			positionOfFirstLetter = r;
		} else {
			positionOfFirstLetter = c;
		}
		
		for (int i=0; i<word.getLength(); i++) {
			if (!squares[r][c].isOccupied()) {
				char letter = word.getLetter(i);
				Tile tile = frame.getTile(letter);
				squares[r][c].add(tile);
				frame.removeTile(tile);
				points = points + tile.getValue() * squares[r][c].getLetterMuliplier();
				wordMultipler = wordMultipler * squares[r][c].getWordMultiplier();
				
				if (word.isHorizontal() ) {
					positionsOfPlayedTiles.add(c);
				} else {
					positionsOfPlayedTiles.add(r);
				}
			}
			if (word.isHorizontal() ) {
				c++;
			} else {
				r++;
			}
		}
		points = points * wordMultipler;
		if (frameWasFull && frame.isEmpty()) {
			points = points + BONUS;
		}
		numPlays++;
	}

	public int getPoints() {
		return points;
	}

	public Square getSquare(int row, int col) {
		return squares[row][col];
	}
	
	public ArrayList<Integer> getPositionsList()
	{
		return positionsOfPlayedTiles;
	}
	
	public int getPositionOfFirstLetter()
	{
		return positionOfFirstLetter;
	}
	
	/*Called once a challenge is over and
	 * removes all the positions of the tiles of the challenged word*/  
	public void resetPositionsList()
	{
		int initialSize = positionsOfPlayedTiles.size();
		
		for(int i = 0; i < initialSize; i++) 
		{
			positionsOfPlayedTiles.remove(0);
		}
	}
	
	/*Called on a successful challenge and removes all tiles
	 * from the board that were played on the previous play.
	 * Also returns an ArrayList of the Tiles to be removed from the board
	 * and placed back on a player's frame*/
	public ArrayList<Tile> nullifyPlay() 
	{
		ArrayList<Tile> moveFromBoardToFrame = new ArrayList<Tile>();
		for(int i = 0; i < getPositionsList().size(); i++) 
		{
			if(lastWordPlaced.isHorizontal()) 
			{
				Tile t = getSquare(getPositionOfFirstLetter(), getPositionsList().get(i)).removeTile();
				moveFromBoardToFrame.add(t);
			}
			
			else 
			{
				Tile t = getSquare(getPositionsList().get(i), getPositionOfFirstLetter()).removeTile();
				moveFromBoardToFrame.add(t);
			}
		}
		
		return moveFromBoardToFrame;
	}
}