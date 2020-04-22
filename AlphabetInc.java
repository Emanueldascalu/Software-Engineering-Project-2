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
				Random rand = new Random(26);
				int index = rand.nextInt();
				char designation = alphabet[index];
				blankDesignations.append(designation);
				lettersOnFrame.append(designation);
			}
			
			else
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
        	Word mostPointsWord = new Word(0, 0, true, " ");
        	int mostPoints = 0;
        	
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
				permK(lettersToBePermuted, 0, k_Of_N, allPossiblePermutations);
				
				if(blankDesignations.length() > 0)
				findFirstWord(dict, allPossiblePermutations, words, frame, offset, blankDesignations.toString());
				
				else
				findFirstWord(dict, allPossiblePermutations, words, frame, offset);
				
				while(words.isEmpty() && offset < 7 && k_Of_N > 1) 
				{
					permK(lettersToBePermuted, 0, k_Of_N - 1, allPossiblePermutations);
					
					if(blankDesignations.length() > 0)
					findFirstWord(dict, allPossiblePermutations, words, frame, offset + 1, blankDesignations.toString());
					
					else
					findFirstWord(dict, allPossiblePermutations, words, frame, offset + 1);
						
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
				permK(lettersToBePermuted, 0, k_Of_N, allPossiblePermutations);
	        	
				if(blankDesignations.length() > 0)
				findWords(dict, allPossiblePermutations, words, frame, offset, tileLocations, blankDesignations.toString());
					
				else
				findWords(dict, allPossiblePermutations, words, frame, offset, tileLocations);
				
	        	while(words.isEmpty() && offset > 0 && k_Of_N > 1) 
				{
					permK(lettersToBePermuted, 0, k_Of_N - 1, allPossiblePermutations);
					
					if(blankDesignations.length() > 0)
					findWords(dict, allPossiblePermutations, words, frame, offset - 1, tileLocations, blankDesignations.toString());
						
					else
					findWords(dict, allPossiblePermutations, words, frame, offset - 1, tileLocations);
					
					k_Of_N--;
					offset--;
				}
	    	}
	    	
	    	if(words.isEmpty()) 
			{
	    		Random numToBeExchanged = new Random(7);
	    		StringBuilder str = new StringBuilder();
	    		str.append("EXCHANGE ");
	    		
	    		ArrayList<Integer> al = new ArrayList();
	    		al.add(0);
	    		al.add(1);
	    		al.add(2);
	    		al.add(3);
	    		al.add(4);
	    		al.add(5);
	    		al.add(6);
	    		
	    		for(int i = 0; i < numToBeExchanged.nextInt(); i++) 
	    		{
	    			Random index = new Random(al.size());
	    			
	    			str.append(frameAsString.charAt(index.nextInt()));
	    			al.remove(index.nextInt());
	    		}
	    		
				command = str.toString();
			}
			
			else 
			{
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
				char across;
				
				if(mostPointsWord.isHorizontal())
					across = 'A';
				
				else
					across = 'D';
				
				String letters = mostPointsWord.getLetters();
				
				str.append(c);
				str.append(r);
				str.append(" " + across + " " + letters);
				
				if(blankDesignations.length() > 0) 
				{
					str.append(" " + blankDesignations.toString());
				}
				
				command = str.toString();
			}
    	}
        
        turnCount++;
        return command;
    }
    
    public void findFirstWord(Trie dict, ArrayList<String> allPossiblePermutations, ArrayList<Word> words, Frame frame, int offset) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possibleWord = allPossiblePermutations.get(i);
			if(dict.search(possibleWord)) 
			{	
				Random acrossOrDown = new Random(2);
				if(acrossOrDown.nextInt() == 1)
				{
					for(int r = offset; r <= 7; r++) 
					{	
						if(board.isLegalPlay(frame, new Word(r, 7, false, possibleWord)))
						words.add(new Word(r, 7, false, possibleWord));
					}
				}
				
				else 
				{
					for(int c = offset; c <= 7; c++) 
					{
						if(board.isLegalPlay(frame, new Word(7, c, true, possibleWord)))
						words.add(new Word(7, c, true, possibleWord));
					}
				}
			}
		}
    }
    
    public void findWords(Trie dict, ArrayList<String> allPossiblePermutations, ArrayList<Word> words, Frame frame, int offset, ArrayList<Coordinates> tileLocations) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possibleWord = allPossiblePermutations.get(i);
			if(dict.search(possibleWord)) 
			{					
				for(int j = 0; j < tileLocations.size(); j++) 
				{
					Coordinates coordinate = tileLocations.get(j);
					int row = coordinate.getRow();
					int col = coordinate.getCol();
					
					for(int r = row - offset; r <= row; r++) 
					{	
						if(r >= 0 && r <= 14)
						{	
							if(board.isLegalPlay(frame, new Word(r, col, false, possibleWord)))	
							words.add(new Word(r, col, false, possibleWord));
						}
					}
					
					for(int c = col - offset; c <= col; c++) 
					{
						if(c >= 0 && c <= 14)
						{	
							if(board.isLegalPlay(frame, new Word(row, c, true, possibleWord)))
							words.add(new Word(row, c, true, possibleWord));
						}
					}
					
					for(int r = row - offset; r <= row; r++) 
					{	
						if(r >= 0 && r <= 14)
						{	if(board.isLegalPlay(frame, new Word(r, col - 1, false, possibleWord)))
							words.add(new Word(r, col - 1, false, possibleWord));
						}
					}
					
					for(int c = col - offset; c <= col; c++) 
					{
						if(c >= 0 && c <= 14) 
						{
							if(board.isLegalPlay(frame, new Word(row - 1, c, true, possibleWord)))
							words.add(new Word(row - 1, c, true, possibleWord));
						}
					}
					
					for(int r = row - offset; r <= row; r++) 
					{	
						if(r >= 0 && r <= 14)
						{
							if(board.isLegalPlay(frame, new Word(r, col + 1, false, possibleWord)))
							words.add(new Word(r, col + 1, false, possibleWord));
						}
					}
					
					for(int c = col - offset; c <= col; c++) 
					{
						if(c >= 0 && c <= 14) 
						{
							if(board.isLegalPlay(frame, new Word(row + 1, c, true, possibleWord)))
							words.add(new Word(row + 1, c, true, possibleWord));
						}
					}
				}
			}
		}
    }
    
    public void findFirstWord(Trie dict, ArrayList<String> allPossiblePermutations, ArrayList<Word> words, Frame frame, int offset, String blankDesignations) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possibleWord = allPossiblePermutations.get(i);
			if(dict.search(possibleWord)) 
			{		
				Random acrossOrDown = new Random(2);
				if(acrossOrDown.nextInt() == 1)
				{
					for(int r = offset; r <= 7; r++) 
					{	
						if(board.isLegalPlay(frame, new Word(r, 7, false, possibleWord, blankDesignations)))
						words.add(new Word(r, 7, false, possibleWord, blankDesignations));
					}
				}
				
				else 
				{
					for(int c = offset; c <= 7; c++) 
					{
						if(board.isLegalPlay(frame, new Word(7, c, true, possibleWord, blankDesignations)))
						words.add(new Word(7, c, true, possibleWord, blankDesignations));
					}
				}
			}
		}
    }
    
    public void findWords(Trie dict, ArrayList<String> allPossiblePermutations, ArrayList<Word> words, Frame frame, int offset, ArrayList<Coordinates> tileLocations, String blankDesignations) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possibleWord = allPossiblePermutations.get(i);
			if(dict.search(possibleWord)) 
			{					
				for(int j = 0; j < tileLocations.size(); j++) 
				{
					Coordinates coordinate = tileLocations.get(j);
					int row = coordinate.getRow();
					int col = coordinate.getCol();
					
					for(int r = row - offset; r <= row; r++) 
					{	
						if(r >= 0 && r <= 14)
						{	
							if(board.isLegalPlay(frame, new Word(r, col, false, possibleWord, blankDesignations)))	
							words.add(new Word(r, col, false, possibleWord, blankDesignations));
						}
					}
					
					for(int c = col - offset; c <= col; c++) 
					{
						if(c >= 0 && c <= 14)
						{	
							if(board.isLegalPlay(frame, new Word(row, c, true, possibleWord, blankDesignations)))
							words.add(new Word(row, c, true, possibleWord, blankDesignations));
						}
					}
					
					for(int r = row - offset; r <= row; r++) 
					{	
						if(r >= 0 && r <= 14)
						{	if(board.isLegalPlay(frame, new Word(r, col - 1, false, possibleWord, blankDesignations)))
							words.add(new Word(r, col - 1, false, possibleWord, blankDesignations));
						}
					}
					
					for(int c = col - offset; c <= col; c++) 
					{
						if(c >= 0 && c <= 14) 
						{
							if(board.isLegalPlay(frame, new Word(row - 1, c, true, possibleWord, blankDesignations)))
							words.add(new Word(row - 1, c, true, possibleWord, blankDesignations));
						}
					}
					
					for(int r = row - offset; r <= row; r++) 
					{	
						if(r >= 0 && r <= 14)
						{
							if(board.isLegalPlay(frame, new Word(r, col + 1, false, possibleWord, blankDesignations)))
							words.add(new Word(r, col + 1, false, possibleWord, blankDesignations));
						}
					}
					
					for(int c = col - offset; c <= col; c++) 
					{
						if(c >= 0 && c <= 14) 
						{
							if(board.isLegalPlay(frame, new Word(row + 1, c, true, possibleWord, blankDesignations)))
							words.add(new Word(row + 1, c, true, possibleWord, blankDesignations));
						}
					}
				}
			}
		}
    }
    
    public int wordScore(Word w) 
    {
    	int wordValue = 0;
		int wordMultipler = 1;
		int letterValue;
		int r = w.getFirstRow();
		int c = w.getFirstColumn();
		for (int i = 0; i < w.length(); i++) 
		{
			Tile t = new Tile(w.getLetters().charAt(i));
			letterValue = t.getValue();
			
			if(board.getSquareCopy(r, c).isDoubleLetter())
			{
				wordValue += (letterValue * 2);
			}
			
			else if(board.getSquareCopy(r, c).isDoubleWord()) 
			{
				wordMultipler *= 2;  
			}
			
			else if(board.getSquareCopy(r, c).isTripleLetter())
			{
				wordValue += (letterValue * 3);
			}
			
			else if(board.getSquareCopy(r, c).isTripleWord())
			{
				wordMultipler *= 3;
			}
			
			else 
			{
				wordValue = wordValue + letterValue;
			}
			
			if (w.isHorizontal()) 
			{
				c++;
			}
			
			else 
			{
				r++;
			}
		}
		
		return wordValue * wordMultipler;
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

	public void permK(ArrayList<Character> p, int i, int k, ArrayList<String> storePermutations)
	{
		if(i == k)
		{
			List<Character> c = p.subList(0, k);
			StringBuilder str = new StringBuilder();
			for(int l = 0; l < c.size(); l++) 
			{
				str.append(c.get(l));
			}
			
			storePermutations.add(str.toString());
			return;
		}
			
		for(int j=i; j<p.size(); j++)
		{
			Collections.swap(p, i, j);
			permK(p, i + 1, k, storePermutations);    
			Collections.swap(p, i, j);
		}
	}
}

