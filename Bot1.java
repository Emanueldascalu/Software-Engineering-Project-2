import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Bot1 implements BotAPI {

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

    Bot1(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
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

    	Trie dict = createDictionary();
    	ArrayList<Word> words = new ArrayList();
    	Word mostPointsWord = new Word(0, 0, true, " ");
    	int mostPoints = 0;
    	
    	
		String frameAsString = me.getFrameAsString();
		StringBuilder lettersOnFrame = new StringBuilder();

		Frame frame = new Frame();
		ArrayList<Tile> listOfTiles = new ArrayList();
		for(int i = 1; i < frameAsString.length(); i = i + 3) 
		{
			Tile tile = new Tile(frameAsString.toCharArray()[i]);
			if(frameAsString.toCharArray()[i] == '_') 
			{
				Random rand = new Random(15);
				lettersOnFrame.append((char) ((int) 'A' + rand.nextInt()));
			}
			
			else
			lettersOnFrame.append(frameAsString.toCharArray()[i]);
			
			listOfTiles.add(tile);
		}
		
		frame.addTiles(listOfTiles);

		
    	if(turnCount == 0) 
    	{
    		command = "NAME Bot1";
    	}
    	
    	if(turnCount == 1) 
    	{
    		command = "PASS";
    	}
    	
    	if(turnCount >= 2) 
    	{
	    	if(board.isFirstPlay())
	    	{
	    		ArrayList<Character> lettersToBePermuted = new ArrayList();
	    		for(int i = 0; i < lettersOnFrame.length(); i++) 
	    		{
	    			lettersToBePermuted.add(lettersOnFrame.charAt(i));
	    		}
	    		
				ArrayList<String> allPossiblePermutations = new ArrayList();
				int k_Of_N = 7;
				int offset = 1;
				permK(lettersToBePermuted, 0, k_Of_N, allPossiblePermutations);
				
				findWords(dict, allPossiblePermutations, words, frame, offset);
				while(words.isEmpty() && offset < 7) 
				{
					k_Of_N--;
					permK(lettersToBePermuted, 0, k_Of_N, allPossiblePermutations);
					
					findWords(dict, allPossiblePermutations, words, frame, offset + 1);
					offset++;
				}
				
				if(words.isEmpty()) 
				{
					System.out.println("Can't start game. No words can be formed.");
					command = "PASS";
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
					
					char c = (char) ('A' + mostPointsWord.getFirstColumn());
					char r = (char) (mostPointsWord.getFirstRow() + 1);
					char across;
					
					if(mostPointsWord.isHorizontal())
						across = 'A';
					
					else
						across = 'D';
					
					String letters = mostPointsWord.getLetters();
					command = c + r + " " + across + " " + letters;
				}
	    	}
	    	
	    	else 
	    	{
	    		System.out.println("Need code for all plays after the first one.");
	    		command = "PASS";
	    	}
    	}
        
        turnCount++;
        return command;
    }
    
    public void findWords(Trie dict, ArrayList<String> allPossiblePermutations, ArrayList<Word> words, Frame frame, int offset) 
    {
    	for(int i = 0; i < allPossiblePermutations.size(); i++) 
		{
			String possible = allPossiblePermutations.get(i);
			if(dict.search(possible)) 
			{					
				for(int r = offset; r <= 7; r++) 
				{	
					if(board.isLegalPlay(frame, new Word(r, 7, false, possible)))
					words.add(new Word(r, 7, false, possible));
				}
				
				for(int c = offset; c <= 7; c++) 
				{
					if(board.isLegalPlay(frame, new Word(7, c, true, possible)))
					words.add(new Word(7, c, true, possible));
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
	    permK(p, i+1, k, storePermutations);    
	    Collections.swap(p, i, j);
	  }
	}
}
