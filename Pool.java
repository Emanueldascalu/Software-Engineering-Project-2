import java.util.*;
//import java.lang.*;
public class Pool 
{
	private int numTilesInPool;
	private final int[] letterValues = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0}; 
	private final int[] amountOfTilesAtStart = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};
	private int[] amountOfEachLetter = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};	
	private final char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '*'};
	
	
	public Pool()
	{
		setNumTilesInPool(amountOfTilesAtStart);
	}
	
	public void setNumTilesInPool(int[] array) 
	{
		int total = 0;
		for(int i = 0; i < array.length; i++) 
		{
			total += (array[i]);
		}
		numTilesInPool = total;
	}
	
	public int getNumTilesInPool() 
	{
		return numTilesInPool;
	}
	
	public boolean isPoolEmpty() 
	{
		if(getNumTilesInPool() <= 0) 
		{
			System.out.println("Pool is empty");
			return true;
		}
		System.out.println("Pool is not empty");
		return false;
	}
	
	public void displayNumTilesInPool() 
	{
		System.out.println("Number of tiles in Pool: " + getNumTilesInPool());
	}
	
	public void resetPool(int[] array1, int[] array2) 
	{
		for(int i = 0; i < 27; i++) 
		{
			array1[i] = array2[i];
		}
	}
	
	/*public ArrayList<Character> removeMoreTilesRandomly(int numToBeRemoved)
	{
		ArrayList<Character> lettersRemoved;
		for(int i = 0; i < numToBeRemoved; i++)
		{
			lettersRemoved.add(removeTileRandomly());
		}
		return lettersRemoved;
	}*/
	
	public char removeTileRandomly()
	{
		Random rand = new Random();
		int count = rand.nextInt(27);
		
		if(amountOfEachLetter[count] == 0)
		{
			while(amountOfEachLetter[count] == 0) 
			{
				count = rand.nextInt(27);
			}	
		}
		
		amountOfEachLetter[count]--;
		numTilesInPool--;
		System.out.println(letters[count] + " tile was removed from the pool.");
		return letters[count];
	}
	
	public int tileValue(char letter) 
	{
		for(int i = 0; i < 27; i++) 
		{
			if(Character.toUpperCase(letter) == letters[i])
			return letterValues[i];
		}
		
		return -1;
	}
		
}