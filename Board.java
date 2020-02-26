//By Alphabet Inc. :

//Emanuel Dascalu, 18729365

//Pranchal Narang, 18339361 
//The github account is lakesh narang

//Taranpreet Singh, 18203372

public class Board {

//State of the board at the beginning of the game.

//Abbreviations for Triple Word, Double Word, Triple Letter and Double Letter

//@@ = Middle Square


	private int numOfWordsOnBoard = 0;

	private int numOfTurns = 0;

	private String[][] board = {

			{ "TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW" },

			{ "  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  " },

			{ "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  " },

			{ "DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL" },

			{ "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  " },

			{ "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  " },

			{ "  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  " },

			{ "TW", "  ", "  ", "DL", "  ", "  ", "  ", "@@", "  ", "  ", "  ", "DL", "  ", "  ", "TW" },

			{ "  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  " },

			{ "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  " },

			{ "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  " },

			{ "DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL" },

			{ "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  " },

			{ "  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  " },

			{ "TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW" } };

//This could be used to reset the board the same way we reset the pool

	private final String[][] boardAtBeginning = {

			{ "TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW" },

			{ "  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  " },

			{ "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  " },

			{ "DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL" },

			{ "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  " },

			{ "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  " },

			{ "  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  " },

			{ "TW", "  ", "  ", "DL", "  ", "  ", "  ", "@@", "  ", "  ", "  ", "DL", "  ", "  ", "TW" },

			{ "  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  " },

			{ "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  " },

			{ "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  " },

			{ "DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL" },

			{ "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  " },

			{ "  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  " },

			{ "TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW" } };

	public void resetBoard() {

		for (int i = 0; i < 15; i++) {

			for (int j = 0; j < 15; j++) {

				board[i][j] = boardAtBeginning[i][j];

			}

		}

	}

	public int getNumOfWordsOnBoard() {

		return numOfWordsOnBoard;

	}

	public int getNumOfTurns() {

		return numOfTurns;

	}

	public String[][] getBoard() {

		return board;

	}

	public String[][] getBoardAtBeginning() {

		return boardAtBeginning;

	}

//x1, y1 is the square on the board which will contain the first letter of the word(The starting point)

//y indicates rows and x indicates columns

	// The main checking method which calls sub-methods to check different aspects

	// of placing the word

	public void wordCheck(Frame f, String wordToBePlaced, int x1, int y1, char direction) {

		boolean isValidCentre = true;

		// function call to check if word is in bounds of the scrabble board

		boolean isValidBounds = isInBounds(wordToBePlaced, x1, y1, direction);

		// function call for checking if the letters chosen to make word are in the

		// frame.

		boolean isValidFrame = isInFrame(f, wordToBePlaced, x1, y1, direction);

		boolean isUnconflicting = isConnectedAndUnconflicitng(wordToBePlaced, x1, y1, direction);

		if (numOfWordsOnBoard == 0) {

			isValidCentre = isCentreWord(wordToBePlaced, x1, y1, direction);

		} else {

			System.out.println("There are already words on the board");

		}

		// Finally after checking everything, if the word is valid then it will be

		// placed on the boarding using placeWord()

		if (isValidBounds && isValidFrame && isValidCentre && isUnconflicting) {

			System.out.println(wordToBePlaced + " can be placed");

			placeWord(wordToBePlaced, x1, y1, direction);

		} else {

			System.out.println(wordToBePlaced + " cannot be placed" + "\n Try another word");

		}

	}

	public boolean isCentreWord(String wordToBeChecked, int x1, int y1, char direction) {

		boolean validity = true;

		if (direction == 'V') {

			if (x1 == 7 && (y1 <= 7 && (y1 + wordToBeChecked.length() - 1) >= 7)) {

				System.out.println("This is a valid placement for the first word");

			}

			else {

				System.out.println("This is an invalid placement for the first word or " + wordToBeChecked);

				validity = false;

			}

		} else if (direction == 'H') {

			if (y1 == 7 && (x1 <= 7 && (x1 + wordToBeChecked.length() - 1) >= 7)) {

				System.out.println("This is a valid placement for the first word");

			}

			else {

				System.out.println("This is an invalid placement for the first word or " + wordToBeChecked);

				validity = false;

			}

		}

		return validity;

	}

	public boolean isInFrame(Frame f, String wordToBePlaced, int x1, int y1, char direction) {

		boolean validity = true;

		char[] chosenWord = wordToBePlaced.toCharArray();

		// wordToBePlaced is converted from a string to a char array

		for (int i = 0; i < chosenWord.length; i++) {

			// If a letter from wordToBePlaced is not on the frame

			if (!f.isLetterInFrame(chosenWord[i])) {

				System.out.println("Letter " + chosenWord[i] + " is not in the frame");

				System.out.println(wordToBePlaced + " is not on the frame");

				validity = false;

				break;

			} else {

				System.out.println(chosenWord[i] + " is on the frame");

				if (i == chosenWord.length - 1) {

					System.out.println(wordToBePlaced + " is on the frame");

				}

			}

		}

		return validity;

	}

	public boolean isInBounds(String wordToBePlaced, int x1, int y1, char direction) {

		boolean validity = true;

		// Have to calculate if the word is in bounds or not. Can't do that without

		// knowing the starting and ending position

		if (direction == 'H') {

			if ((x1 + wordToBePlaced.length() - 1 > 14) || (y1 > 14)) {

				System.out.println(wordToBePlaced + " is outside of bounds");

				validity = false;

			}

		} else if (direction == 'V') {

			if ((x1 > 14) || (y1 + wordToBePlaced.length() - 1 > 14)) {

				System.out.println(wordToBePlaced + " is outside of bounds");

				validity = false;

			}

		} else if (direction != 'H' || direction != 'V') {

			System.out.println("Invalid input for direction. Please choose either H or V");

		} else {

			System.out.println("Word is inside bounds");

		}

		return validity;

	}

	public boolean isConnectedAndUnconflicitng(String wordToBePlaced, int x1, int y1, char direction) {

		boolean validity = true;

		char[] chosenWord = wordToBePlaced.toCharArray();

		if (direction == 'V') {

			int i = y1;

			int j = 0;

			while (i <= y1 + wordToBePlaced.length() - 1 && j < chosenWord.length) {

				if (board[i][x1].toCharArray()[0] >= 'A' && board[i][x1].toCharArray()[0] <= 'Z'
						&& board[i][x1].toCharArray()[1] == ' ')

				{

					System.out.println(wordToBePlaced + " connects to another word on the board");

					// if this square's letter is different to the current letter of the chosen word

					if (board[i][x1].toCharArray()[0] != chosenWord[j])

					{

						System.out.println("There is letter conflict with your placement");

						System.out.println("This is an invalid placement for " + wordToBePlaced);

						validity = false;

						break;

					}

				}

				i++;

				j++;

			}

		} else if (direction == 'H') {

			// if this is not the first word to go on the board

			System.out.println("There are already words on the board");

			int i = x1;

			int j = 0;

			while (i <= x1 + wordToBePlaced.length() && j < chosenWord.length)

			{

				// if this square already contains a letter (not DL DW TL or TW)

				if (board[y1][i].toCharArray()[0] >= 'A' && board[y1][i].toCharArray()[0] <= 'Z'
						&& board[y1][i].toCharArray()[1] == ' ')

				{

					System.out.println(wordToBePlaced + " connects to another word on the board");

					// System.out.println(board[y1][i].toCharArray()[0] + " " + chosenWord[j]);

					// if this square's letter is different to the current letter of the chosen word

					if (board[y1][i].toCharArray()[0] != chosenWord[j])

					{

						System.out.println("There is letter conflict with your placement");

						System.out.println("This is an invalid placement for " + wordToBePlaced);

						validity = false;

						break;

					}

				}

				i++;

				j++;

			}

		}

		else {

			System.out.println(wordToBePlaced + " is not vertical or horizontal");

			validity = false;

		}

		return validity;

	}

//Places a word on the board,asks the user for the word , starting position and direction (H/V for horizontal/verti)

	public void placeWord(String wordToBePlaced, int x1, int y1, char direction) {

		int index = 0;

		if (direction == 'V')// Word is vertical, x = columns

		{

			for (int i = y1; i <= y1 + wordToBePlaced.length() - 1; i++, index++) {

//A letter is placed on the board

				board[i][x1] = (wordToBePlaced.toCharArray()[index] + " ").toUpperCase();

			}

		}

		else if (direction == 'H') // Word is horizontal, y = rows

		{

			for (int j = x1; j <= x1 + wordToBePlaced.length() - 1; j++, index++) {

//A letter is placed on the board

				board[y1][j] = (wordToBePlaced.toCharArray()[index] + " ").toUpperCase();

			}

		}

		numOfTurns++;

		numOfWordsOnBoard++;

	}

	public void printLine() {

		System.out.println("  ----------------------------------------------------------------------------");

	}

	public void displayBoard() {

		System.out.println("    0    1    2    3    4    5    6    7    8    9    10   11   12   13   14  ");

		printLine();

		for (int i = 0; i < 15; i++) {

			if (i >= 10)

				System.out.print(i + "|");

			else

				System.out.print(i + " |");

			for (int j = 0; j < 15; j++) {

				System.out.print(" " + board[i][j] + " |");// Taran-just made a small change here to make the board look

				// better

			}

			System.out.println();

			printLine();

		}

	}

}