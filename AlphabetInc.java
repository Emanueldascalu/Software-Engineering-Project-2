import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AlphabetInc implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;
    private int turnCount = 0;

    AlphabetInc(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
    }

    public String getCommand() {
        // Add your code here to input your commands
        // Your code must give the command NAME <botname> at the start of the game
    	String command = "";
    	char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
    			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    	Trie dict = createDictionary();
    	
		String frameAsString = me.getFrameAsString();
		StringBuilder lettersOnFrame = new StringBuilder();

		Frame frame = new Frame();
		ArrayList<Tile> listOfTiles = new ArrayList();
		StringBuilder blankDesignations = new StringBuilder();
		
		for(int i = 1; i < frameAsString.length(); i = i + 3) 
		{
			Tile tile = new Tile(frameAsString.toCharArray()[i]);
			if(frameAsString.toCharArray()[i] == '_') 
			{
				Random rand = new Random();
				int index = rand.nextInt(26);
				char designation = alphabet[index];
				blankDesignations.append(designation);
				//lettersOnFrame.append(designation);
			}
			
			//else
			lettersOnFrame.append(frameAsString.toCharArray()[i]);
			
			listOfTiles.add(tile);
		}
		
		frame.addTiles(listOfTiles);

		
    	if(turnCount == 0) 
    	{
    		command = "NAME AlphabetInc";
    	}
    	
    	if(turnCount == 1) 
    	{
    		command = "PASS";
    	}
    	
    	if(turnCount >= 2) 
    	{
    		ArrayList<Word> words = new ArrayList();
        	
    		ArrayList<Character> lettersToBePermuted = new ArrayList();
    		for(int i = 0; i < lettersOnFrame.length(); i++) 
    		{
    			lettersToBePermuted.add(lettersOnFrame.charAt(i));
    		}
    		
			ArrayList<String> allPossiblePermutations = new ArrayList();
			
	    	if(board.isFirstPlay())
	    	{
				int k_Of_N = 7;
				int offset = 1;
				permK_of_N(lettersToBePermuted, 0, k_Of_N, allPossiblePermutations);
				
				findWords1stTurn(offset, dict, allPossiblePermutations, blankDesignations.toString(), words, frame);
				
				while(words.isEmpty() && offset < 7 && k_Of_N > 1) 
				{
					permK_of_N(lettersToBePermuted, 0, k_Of_N - 1, allPossiblePermutations);
					
					findWords1stTurn(offset + 1, dict, allPossiblePermutations, blankDesignations.toString(), words, frame);
						
					k_Of_N--;
					offset++;
				}
	    	}
	    	
	    	else 
	    	{
	    		ArrayList<Coordinates> tileLocations = new ArrayList();
	        	
	        	Square[][] b = new Square[15][15];
	        	for(int row = 0; row < 15; row++) 
	        	{
	        		for(int c = 0; c < 15; c++) 
	        		{
	        			b[row][c] = board.getSquareCopy(row, c);
	        			if(b[row][c].isOccupied()) 
	        			{
	        				tileLocations.add(new Coordinates(row, c));
	        			}
	        		}
	        	}
	        	
				int k_Of_N = 7;
				int offset = 6;
				permK_of_N(lettersToBePermuted, 0, k_Of_N, allPossiblePermutations);
	        	
				findWordsNthTurn(offset, dict, allPossiblePermutations, tileLocations, blankDesignations.toString(), words, frame, b);
				
	        	while(words.isEmpty() && offset > 0 && k_Of_N > 1) 
				{
					permK_of_N(lettersToBePermuted, 0, k_Of_N - 1, allPossiblePermutations);
					
					findWordsNthTurn(offset - 1, dict, allPossiblePermutations, tileLocations, blankDesignations.toString(), words, frame, b);	
					
					k_Of_N--;
					offset--;
				}
	    	}
	    	
	    	if(words.isEmpty()) 
			{
	    		if(lettersOnFrame.length() < 7) 
	    		{
	    			command = "PASS";
	    		}
	    		
	    		else 
	    		{
		    		Random rand1 = new Random();
		    		
		    		ArrayList<Character> frameAsCharList = new ArrayList<>();
		    		for(int i = 0; i < lettersOnFrame.length(); i++) 
		    		{
		    			frameAsCharList.add(lettersOnFrame.charAt(i));
		    		}
		    		
		    		StringBuilder str = new StringBuilder();
		    		str.append("EXCHANGE ");
		    		
		    		int numToBeExchanged = rand1.nextInt(lettersOnFrame.length());
		    		ArrayList<Character> charsToBeExchanged = new ArrayList();
		    		Random rand2 = new Random();
		    		
		    		for(int i = 0; i < numToBeExchanged; i++) 
		    		{
		    			int frameIndex = rand2.nextInt(frameAsCharList.size());
		    			charsToBeExchanged.add(frameAsCharList.get(frameIndex));
		    			frameAsCharList.remove(frameIndex);
		    			
		    		}
		    		
		    		for(int i = 0; i < charsToBeExchanged.size(); i++) 
		    		{
		    			str.append(charsToBeExchanged.get(i).toString());
		    			//charsToBeExchanged.remove(i);
		    		}
		    		
					command = str.toString();
				}
	    		
			}
			
			else 
			{	
				command = parseWord(words, blankDesignations.toString());
			}
    	}
        
        turnCount++;
        return command;
    }
    
    public String parseWord(ArrayList<Word> words, String blankDesignations) 
    {
    	Word mostPointsWord = new Word(0, 0, true, " ");
    	int mostPoints = 0;
    	for(int i = 0; i < words.size(); i++) 
		{
			if(wordScore(words.get(i)) > mostPoints) 
			{
				mostPointsWord = words.get(i);
				mostPoints = wordScore(words.get(i));
			}
		}
		
		StringBuilder str = new StringBuilder();
		char c = (char) ((int) 'A' + mostPointsWord.getFirstColumn());
		int r = mostPointsWord.getFirstRow() + 1;
		char direction;
		
		if(mostPointsWord.isHorizontal())
			direction = 'A';
		
		else
			direction = 'D';
		
		String letters = mostPointsWord.getLetters();
		
		str.append(c);
		str.append(r);
		str.append(" " + direction + " " + letters);
		
		if(blankDesignations.length() > 0) 
		{
			str.append(" " + blankDesignations);
		}
		
		return str.toString();
    }
    
    public void findWords1stTurn(int offset, Trie dict, ArrayList<String> allPossiblePermutations, 
    		String blankDesignations, ArrayList<Word> words, Frame frame) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possibleWord = allPossiblePermutations.get(i);
			StringBuilder possibleLetters = new StringBuilder();
			
			int index = 0;
			for(int j = 0; j < possibleWord.length(); j++) 
			{
				if(possibleWord.charAt(j) == '_') 
				{
					possibleLetters.append(blankDesignations.charAt(index));
					index++;
				}
				
				else
					possibleLetters.append(possibleWord.charAt(j));
			}
			
			if(dict.search(possibleLetters.toString())) 
			{		
				Random rand = new Random();
				int acrossOrDown = rand.nextInt(2);
				if(acrossOrDown == 1) 
				{
					addWords1stTurn(offset, true, possibleWord, blankDesignations.toString(), words, frame);
				}
				
				else if(acrossOrDown == 0)
				{
					addWords1stTurn(offset, false, possibleWord, blankDesignations.toString(), words, frame);
				}
			}
		}	
    }
    
    public void findWordsNthTurn(int offset, Trie dict, ArrayList<String> allPossiblePermutations, 
    		ArrayList<Coordinates> tileLocations, String blankDesignations, ArrayList<Word> words, Frame frame, Square[][] squares) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possibleWord = allPossiblePermutations.get(i);
			StringBuilder possibleLetters = new StringBuilder();
			
			int index = 0;
			for(int j = 0; j < possibleWord.length(); j++) 
			{
				if(possibleWord.charAt(j) == '_') 
				{
					possibleLetters.append(blankDesignations.charAt(index));
					index++;
				}
				
				else
					possibleLetters.append(possibleWord.charAt(j));
			}
			
			if(dict.search(possibleLetters.toString()))
			{					
				for(int j = 0; j < tileLocations.size(); j++) 
				{
					Coordinates coordinate = tileLocations.get(j);
					int row = coordinate.getRow();
					int col = coordinate.getCol();
					
					addWordsNthTurn(row, col, offset, true, possibleWord, blankDesignations, words, frame, squares);
					addWordsNthTurn(row, col, offset, false, possibleWord, blankDesignations, words, frame, squares);
					addWordsNthTurn(row - 1, col, offset, true, possibleWord, blankDesignations, words, frame, squares);
					addWordsNthTurn(row, col - 1, offset, false, possibleWord, blankDesignations, words, frame, squares);
					addWordsNthTurn(row + 1, col, offset, true, possibleWord, blankDesignations, words, frame, squares);
					addWordsNthTurn(row, col + 1, offset, false, possibleWord, blankDesignations, words, frame, squares);
				}
			}
		}
    }
    
    public void addWords1stTurn(int offset, boolean isHorizontal, String possibleWord, String blankDesignations, 
    		ArrayList<Word> words, Frame frame) 
    {
    	
    	if(isHorizontal) 
    	{
    		for(int c = offset; c <= 7; c++) 
    		{
    			if(possibleWord.contains("_")) 
    			{
    				if(board.isLegalPlay(frame, new Word(7, c, true, possibleWord, blankDesignations)))
    				words.add(new Word(7, c, true, possibleWord, blankDesignations));
    			}
    			
    			else
    			{
    				if(board.isLegalPlay(frame, new Word(7, c, true, possibleWord)))
    				words.add(new Word(7, c, true, possibleWord));
    			}
    		}
    	}
    	
    	else 
    	{
			for(int r = offset; r <= 7; r++) 
			{	
				if(possibleWord.contains("_")) 
				{
					if(board.isLegalPlay(frame, new Word(r, 7, false, possibleWord, blankDesignations)))
					words.add(new Word(r, 7, false, possibleWord, blankDesignations));
				}
				
				else
				{
					if(board.isLegalPlay(frame, new Word(r, 7, false, possibleWord)))
					words.add(new Word(r, 7, false, possibleWord));
				}
			}
    	}
	}
    
    public void addWordsNthTurn(int row, int column, int offset, boolean isHorizontal, String possibleWord, 
    		String blankDesignations, ArrayList<Word> words, Frame frame, Square[][] squares) 
    {
    	if(isHorizontal) 
    	{
    		for(int c = column - offset; c <= column; c++) 
    		{	
    			if(c >= 0 && c <= 14 && row >= 0 && row <= 14)
    			{	
    				Word word;
    				if(possibleWord.contains("_")) 
    				{
    					word = new Word(row, c, isHorizontal, possibleWord, blankDesignations);
    				}
    				
    				else 
    				{
    					word = new Word(row, c, isHorizontal, possibleWord);
    				}
    				
    				//ArrayList<Word> allAdditionalWords = getAllWords(word, 15, squares);
					if(board.isLegalPlay(frame, word) /*&& dictionary.areWords(allAdditionalWords)*/)
					words.add(word);
    			}
    		}
    	}
    	
    	else 
    	{ 
	    	for(int r = row - offset; r <= row; r++) 
			{	
				if(r >= 0 && r <= 14 && column >= 0 && column <= 14)
				{	
					Word word;
					if(possibleWord.contains("_")) 
					{
						word = new Word(r, column, isHorizontal, possibleWord, blankDesignations);
					}
					
					else 
					{
						word = new Word(r, column, isHorizontal, possibleWord);
					}
					
					//ArrayList<Word> allAdditionalWords = getAllWords(word, 15, squares);
					if(board.isLegalPlay(frame, word) /*&& dictionary.areWords(allAdditionalWords)*/)	
					words.add(word);
				}
			}
    	}
    }
    
    public int wordScore(Word w) 
    {
    	int wordValue = 0;
		int wordMultiplier = 1;
		int letterValue;
		int r = w.getFirstRow();
		int c = w.getFirstColumn();
		for (int i = 0; i < w.length(); i++) 
		{
			Square square = board.getSquareCopy(r, c);
			Tile t = new Tile(w.getLetters().charAt(i));
			letterValue = t.getValue();
			wordMultiplier *= square.getWordMultiplier();
			wordValue += (letterValue * square.getLetterMuliplier());
			
			if (w.isHorizontal()) 
			{
				c++;
			}
			
			else 
			{
				r++;
			}
		}
		
		return wordValue * wordMultiplier;
    }
    
    public Trie createDictionary()
    {
    	Trie trie = new Trie();
    	
    	try 
    	{
			File f = new File("C:\\Users\\Emanuel\\Desktop\\csw.txt");
			Scanner sc = new Scanner(f);
			
			while(sc.hasNext()) 
			{
				trie.insert(sc.next());
			}
				
		} 
    	
    	catch (FileNotFoundException e) 
    	{
			e.printStackTrace();
		}
    	
    	return trie;
    }

	public void permK_of_N(ArrayList<Character> n, int i, int k, ArrayList<String> storePermutations)
	{
		if(i == k)
		{
			List<Character> c = n.subList(0, k);
			StringBuilder str = new StringBuilder();
			for(int l = 0; l < c.size(); l++) 
			{
				str.append(c.get(l));
			}
			
			storePermutations.add(str.toString());
			return;
		}
			
		for(int j = i; j < n.size(); j++)
		{
			Collections.swap(n, i, j);
			permK_of_N(n, i + 1, k, storePermutations);    
			Collections.swap(n, i, j);
		}
	}
	
	/*public boolean isAdditionalWord(int r, int c, boolean isHorizontal, 
			int BOARD_SIZE, Square[][] squares) 
	{
		if ((r >= 0 && r <= 14 && c >= 0 && c <= 14) && (isHorizontal &&
				((r>0 && squares[r-1][c].isOccupied()) || (r<BOARD_SIZE-1 && squares[r+1][c].isOccupied()))) ||
				(!isHorizontal &&
				((c>0 && squares[r][c-1].isOccupied()) || (c<BOARD_SIZE-1 && squares[r][c+1].isOccupied()))) ) {
			return true;
		}
		return false;
	}

	public Word getAdditionalWord(int mainWordRow, int mainWordCol, 
			boolean mainWordIsHorizontal, int BOARD_SIZE, Square[][] squares) {
		int firstRow = mainWordRow;
		int firstCol = mainWordCol;
		// search up or left for the first letter
		while (firstRow >= 0 && firstCol >= 0 && squares[firstRow][firstCol].isOccupied()) {
			if (mainWordIsHorizontal) {
				firstRow--;
			} else {
				firstCol--;
			}
		}
		// went too far
		if (mainWordIsHorizontal) {
			firstRow++;
		} else {
			firstCol++;
		}
		// collect the letters by moving down or right
		String letters = "";
		int r = firstRow;
		int c = firstCol;
		while (r<BOARD_SIZE && c<BOARD_SIZE && squares[r][c].isOccupied()) {
			letters = letters + squares[r][c].getTile().getLetter();
			if (mainWordIsHorizontal) {
				r++;
			} else {
				c++;
			}
		}
		return new Word (firstRow, firstCol, !mainWordIsHorizontal, letters);
	}

	public ArrayList<Word> getAllWords(Word mainWord, int BOARD_SIZE, Square[][] squares) {
		ArrayList<Word> words = new ArrayList<>();
		words.add(mainWord);
		int r = mainWord.getFirstRow();
		int c = mainWord.getFirstColumn();
		for (int i=0; i<mainWord.length(); i++) {
			//if (newLetterCoords.contains(new Coordinates(r,c))) {
				if (isAdditionalWord(r, c, mainWord.isHorizontal(), BOARD_SIZE, squares)) {
					words.add(getAdditionalWord(r, c, mainWord.isHorizontal(), BOARD_SIZE, squares));
				//}
			}
			if (mainWord.isHorizontal()) {
				c++;
			} else {
				r++;
			}
		}
		return words;
	}*/
}

