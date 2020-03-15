//By Alphabet Inc. : 
//Emanuel Dascalu, 18729365 
//Pranchal Narang, 18339361  
//Taranpreet Singh, 18203372

import java.util.*;
public class PlayerTest 
{
	public static void main(String[] args) //driver method checks implementation
	{
		//new instances of the objects are created
		Pool p = new Pool();
		Frame f = new Frame();
		Player player = new Player("Emanuel", f);

		//the output from the methods is displayed and checked
		player.displayName();
		System.out.println(player.getScore());
	
		p.displayNumTilesInPool();
		player.getFrame().displayFrame();
		
		player.getFrame().refillFrame();
		player.getFrame().displayFrame();
		p.displayNumTilesInPool();
		System.out.println(player.getFrame().getLetter(5));
		
		Scanner scanner = new Scanner(System.in); //allowing user to enter tile to be removed
		System.out.println("Choose a tile to remove from the frame.");
		char c = player.getFrame().removeLetter(scanner.next().charAt(0)); //removes first occurence of your input character
		player.getFrame().displayFrame();
		player.increaseScore(p.tileValue(c));
		System.out.println(player.getScore());
		scanner.close();
	}
}
