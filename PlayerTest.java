import java.io.*; 
import java.util.*;
public class PlayerTest 
{
	public static void main(String[] args) 
	{
		Pool p = new Pool();
		Frame f = new Frame();
		Player player = new Player("Emanuel", f);

		
		player.displayName();
		System.out.println(player.getScore());
	
		p.displayNumTilesInPool();
		player.getFrame().displayFrame();
		
		player.getFrame().refillFrame();
		player.getFrame().displayFrame();
		p.displayNumTilesInPool();
		System.out.println(player.getFrame().getLetter(6));
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose a tile to remove from the frame.");
		char c = player.getFrame().removeLetter(scanner.next().charAt(0)); //removes first occurence of your input character
		player.getFrame().displayFrame();
		player.increaseScore(p.tileValue(c));
		System.out.println(player.getScore());
		scanner.close();
	}
}
