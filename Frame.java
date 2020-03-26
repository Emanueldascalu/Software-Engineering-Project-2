package application;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

//By Alphabet Inc. :

	//Emanuel Dascalu, 18729365

	//Pranchal Narang, 18339361 
	//The github account is lakesh narang

	//Taranpreet Singh, 18203372

/*An example of a word command: A1.A.HELLO instead of A1 A HELLO*/

public class Frame 
{

	private static final int MAX_TILES = 7;
	private ArrayList<Tile> tiles;
	private GridPane frameUi;
	
	public GridPane getFrameUi() {return frameUi;}
	
	public void setFrameUi() 
	{
		frameUi = new GridPane();
		for(int i = 0; i < size(); i++) 
		{
			frameUi.add(returnTile(i).getTileUi(), i, 0);
		}
		
		frameUi.setAlignment(Pos.CENTER);
		frameUi.setGridLinesVisible(true);
	}

	Frame() 
	{
		tiles = new ArrayList<Tile>();
	}

	public int size() 
	{
		return tiles.size();
	}

	public boolean isEmpty() 
	{
		return tiles.isEmpty();
	}

	public boolean isFull() 
	{
		return tiles.size() == MAX_TILES;
	}

	public boolean isAvailable(String letters) 
	{
		boolean found = true;
		if (letters.length() > tiles.size()) 
		{
			found = false;
		}
		
		else 
		{
			ArrayList<Tile> copyTiles = new ArrayList<Tile>(tiles);
			for (int i=0; i<letters.length() && found; i++) 
			{
				Tile tileSought = new Tile(letters.charAt(i));
				if (copyTiles.contains(tileSought)) 
				{
					copyTiles.remove(tileSought);
				}
				
				else 
				{
					found = false;
				}
			}
		}
		return found;
	}

	public ArrayList<Tile> getTiles() 
	{
		return tiles;
	}

	// remove precondition: isAvailable(letters) is true
	public void remove(String letters) 
	{
		for (int i=0; i<letters.length(); i++) 
		{
			tiles.remove(new Tile(letters.charAt(i)));
		}
	}

	// getTile precondition: isAvailable(letters) is true
	public Tile getTile(Character letter) 
	{
		int index = tiles.indexOf(new Tile(letter));
		return tiles.get(index);
	}
	
	public Tile returnTile(int i) 
	{
		return tiles.get(i);
	}

	// removeTile precondition: isAvailable(letters) is true
	public void remove(Tile tile) 
	{
		tiles.remove(tile);
	}

	public void refill(Pool pool) 
	{
		int numTilesToDraw = MAX_TILES - tiles.size();
		ArrayList<Tile> draw = pool.drawTiles(numTilesToDraw);
		tiles.addAll(draw);
	}
	
	public boolean isLetterInFrame(char c)
	{
		for(int i = 0; i < size(); i++) 
		{
			if(c == returnTile(i).getLetter()) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void exchange(Pool pool, int numRequested, Scanner scanner) 
	{
		
		int numGiven;
		if (numRequested > pool.size()) 
		{
			numGiven = pool.size();
		} 
		
		else 
		{
			numGiven = numRequested;
		}
		
		for (int i = 0; i < numGiven; i++) 
		{
			System.out.println("Choose a tile you want to remove");
			String toBeExchanged = scanner.next();

			if(toBeExchanged.length() != 1) 
			{
				System.out.println("Input must be a character\nTry again");
				i--;
			}
			
			else 
			{
				char removedLetter = toBeExchanged.toCharArray()[0];
				if(isLetterInFrame(removedLetter)) 
				{
					pool.getPool().add(getTile(removedLetter));
					remove(getTile(removedLetter));
					System.out.println(removedLetter + " was removed from your frame and placed in the pool");
				}
				
				else
				{
					System.out.println("Tile not on your frame\nTry again");
					i--;
				}
			}
		}
		
		refill(pool);
		setFrameUi();
	}
	// test setter
	public void setTiles(String letters) 
	{
		for (int i=0; i<letters.length(); i++) 
		{
			tiles.add(new Tile(letters.charAt(i)));
		}
	}

	@Override
	public String toString() 
	{
		return tiles.toString();
	}
}