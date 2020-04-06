package application;

//By Alphabet Inc. :	

	//Emanuel Dascalu, 18729365	

	//Pranchal Narang, 18339361 	
	//The github account is lakesh narang	

	//Taranpreet Singh, 18203372

import java.util.ArrayList;

public class Frame {

	private static final int MAX_TILES = 7;
	public static final int EXCHANGE_NOT_AVAILABLE = 0;
	public static final int EXCHANGE_NOT_ENOUGH_IN_POOL = 1;

	private ArrayList<Tile> tiles;
	
	/*Tiles drawn from the pool when the refill command is called are stored here. 
	 * If a player places a word, draws some tiles to get back to 7 and is then successfully challenged
	 * this variable is needed to return those tiles drawn from the pool back to the pool.*/
	private ArrayList<Tile> draw;
	
	int errorCode;

	Frame() {
		tiles = new ArrayList<Tile>();
		draw = new ArrayList<Tile>();
	}

	public int size() {
		return(tiles.size());
	}

	public boolean isEmpty() {
		return tiles.isEmpty();
	}

	public boolean isFull() {
		return tiles.size() == MAX_TILES;
	}

	public boolean isAvailable(String letters) {
		boolean found = true;
		if (letters.length() > tiles.size()) {
			found = false;
		}
		else {
			ArrayList<Tile> copyTiles = new ArrayList<>(tiles);
			for (int i=0; i<letters.length() && found; i++) {
				Tile tileSought = new Tile(letters.charAt(i));
				if (copyTiles.contains(tileSought)) {
					copyTiles.remove(tileSought);
				}
				else {
					found = false;
				}
			}
		}
		return found;
	}

	// remove precondition: isAvailable(letters) is true
	public void removeTile(Tile tile) {
		tiles.remove(tile);
	}

	// remove precondition: isAvailable(letters) is true
	public void removeTiles(ArrayList<Tile> tiles) {
		for (Tile tile : tiles) {
			this.tiles.remove(tile);
		}
	}

	// getTile precondition: isAvailable(letters) is true
	public Tile getTile(Character letter) {
		int index = tiles.indexOf(new Tile(letter));
		return tiles.get(index);
	}

	// remove precondition: isAvailable(letters) is true
	public ArrayList<Tile> getTiles(String letters) {
		ArrayList<Tile> tiles = new ArrayList<>();
		for (int i=0; i<letters.length(); i++) {
			tiles.add(getTile(letters.charAt(i)));
		}
		return tiles;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	/*Removes all tiles stored in draw*/
	public void resetDraw() 
	{
		int initialSize = draw.size();
		for(int i = 0; i < initialSize; i++) 
		{
			draw.remove(0);
		}
	}
	
	/*Checks if a tile in a player's frame is also in the 
	 * ArrayList of Tiles "draw". */
	public boolean isInDraw(Tile t) 
	{
		for(int i = 0; i < draw.size(); i++)
		{
			if(t == draw.get(i)) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	//Moves tiles that are both in the players frame and in draw to the pool
	public void moveTilesToGoInPool(Pool pool) 
	{
		
		for(int i = 0; i < tiles.size(); i++) 
		{
			if(isInDraw(tiles.get(i))) 
			{
				tiles.remove(i);
				i--;
			}
		}
		
		pool.addTiles(draw);
		resetDraw();
	}

	public void refill(Pool pool) {
		int numTilesToDraw = MAX_TILES - tiles.size();
		draw = pool.drawTiles(numTilesToDraw);
		tiles.addAll(draw);
	}

	public boolean isLegalExchange(Pool pool, String letters) {
		boolean isLegal;
		if (!isAvailable(letters)) {
			errorCode = EXCHANGE_NOT_AVAILABLE;
			isLegal = false;
		} else if (pool.size() < letters.length()) {
			errorCode = EXCHANGE_NOT_ENOUGH_IN_POOL;
			isLegal = false;
		} else {
			isLegal = true;
		}
		return isLegal;
	}

	public int getErrorCode() {
		return errorCode;
	}

	// exchange precondition: isLegalExchange(pool, letters) is true
	public void exchange(Pool pool, String letters) {
		ArrayList<Tile> tilesToExchange = getTiles(letters);
		pool.addTiles(tilesToExchange);
		removeTiles(tilesToExchange);
		refill(pool);
	}

	@Override
	public String toString() {
		return tiles.toString();
	}
}
