package application;

import java.util.*;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
public class Scrabble 
{
	public static void main(String[] args) 
	{
		Pool pool = new Pool();
		Scanner scanner = new Scanner(System.in);
		Frame frameP1 = new Frame();
		Frame frameP2 = new Frame();
		Board board = new Board();
		Application.launch(UI.class);
		/*new Thread() 
		{
		       @Override
		       public void run() 
		       {
		           Application.launch(UI.class);
		       }
		}.start();
		 
		UI ui1 = UI.waitForUI();
		ui1.printSomething();
		ui1.setPlayerNames();
		ui1.promptMove();*/
	}
		
}

