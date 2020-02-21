//By Alphabet Inc. : 
//Emanuel Dascalu, 18729365 
//Pranchal Narang, 18339361  
//Taranpreet Singh, 18203372

import java.util.*;

public class Pool 
{
	private static int numTilesInPool;
	private final int[] letterValues = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0};

	//amountOfTilesAtStart stores initial amounts of each tile and allows for reset
	private final int[] amountOfTilesAtStart = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};

	private static int[] amountOfTilesInPool = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};	
	//The tile '*' in letters is a blank tile
	private static final char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '*'};
	
	
	public Pool()
	{
		setNumTilesInPool(amountOfTilesAtStart); //initialises the number of tiles in pool
	}
	
	public void setNumTilesInPool(int[] amountOfTiles) //adds quantity of each letter tile together to get total num of tiles present
	{
		for(int i = 0; i < amountOfTiles.length; i++) 
		{
			numTilesInPool += (amountOfTiles[i]);
		}
	}
	
	public int getNumTilesInPool() 
	{
		return numTilesInPool;
	}
	
	public boolean isPoolEmpty() 
	{
		if(getNumTilesInPool() == 0) 
		{
			//System.out.println("Pool is empty");
			return true;
		}
		//System.out.println("Pool is not empty");
		return false;
	}
	
	public void displayNumTilesInPool() 
	{
		System.out.println("Number of tiles in Pool: " + getNumTilesInPool());
	}
	
	public void resetPool(/*int[] tilesCurrentlyInPool, int[] tilesInPoolAtBeginning*/)
	{
		for(int i = 0; i < 27; i++) //resets the pool by changing amountOfTilesInPool to initial values
		{
			amountOfTilesInPool[i] = amountOfTilesAtStart[i];
		}
	}

	public static char removeTileRandomly() //removes a tile from the pool
	{
		Random rand = new Random();
		int count = rand.nextInt(27); //generates random num between 0 and 26
		
		if(amountOfTilesInPool[count] == 0)
		{ 	//keeps generating new number until the amount of letter tiles present of that letter are not 0
			while(amountOfTilesInPool[count] == 0)
			{
				count = rand.nextInt(27);
			}	
		}
		
		amountOfTilesInPool[count]--;//amount of tiles of that letter decremented
		numTilesInPool--;//amount of total tiles decremented
		System.out.println(letters[count] + " tile was removed from the pool.");
		return letters[count];
	}
	
	public int tileValue(char letter)
	{
		if((letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z') || letter == '*') //allows input to be any case
		{
			for(int i = 0; i < 27; i++) 
			{
				if(Character.toUpperCase(letter) == letters[i]) //all tiles stored in pool are uppercase
				return letterValues[i];//if letter is found, the corresponding letter value is returned
			}
		}
		
		System.out.println("Invalid letter.");
		return -1;
	}
		
}