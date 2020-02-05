import java.io.*; 
import java.util.*;
public class PlayerTest 
{
	public static void main(String[] args) 
	{
		Pool p = new Pool();
		Frame f = new Frame();
		Player player = new Player(f);
		
		player.setName("Emanuel");
		player.displayName();
		player.getFrame().displayFrame();
		p.displayNumTilesInPool();
		char[] array = {' ', ' ', ' ', ' ', ' ', ' ', ' '};
		
		for(int i = 0; i < 7; i++) 
		{
			array[i] = p.removeTileRandomly();
		}
		
		player.getFrame().refillFrame(array);
		player.getFrame().displayFrame();
		p.displayNumTilesInPool();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose a tile to remove from the frame.");
		//player.getFrame().removeLetter(scanner.next());
		
	}
}
