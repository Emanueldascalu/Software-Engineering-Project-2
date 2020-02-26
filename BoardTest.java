
//By Alphabet Inc. :

//Emanuel Dascalu, 18729365

//Pranchal Narang, 18339361 
//The github account name is lakesh narang

//Taranpreet Singh, 18203372

public class BoardTest

{

	public static void main(String[] args)

	{

		Board b = new Board();

		Frame f1 = new Frame();

		Frame f2 = new Frame();

		Frame f3 = new Frame();

		String start = "    A    B    C    D    E    F    G    H    I    J    K    L    M    N    O   \n" +

				"  ----------------------------------------------------------------------------\n" +

				"0 | TW |    |    | DL |    |    |    | TW |    |    |    | DL |    |    | TW |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"1 |    | DW |    |    |    | TL |    |    |    | TL |    |    |    | DW |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"2 |    |    | DW |    |    |    | DL |    | DL |    |    |    | DW |    |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"3 | DL |    |    | DW |    |    |    | DL |    |    |    | DW |    |    | DL |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"4 |    |    |    |    | DW |    |    |    |    |    | DW |    |    |    |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"5 |    | TL |    |    |    | TL |    |    |    | TL |    |    |    | TL |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"6 |    |    | DL |    |    |    | DL |    | DL |    |    |    | DL |    |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"7 | TW |    |    | DL |    |    |    | @@ |    |    |    | DL |    |    | TW |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"8 |    |    | DL |    |    |    | DL |    | DL |    |    |    | DL |    |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"9 |    | TL |    |    |    | TL |    |    |    | TL |    |    |    | TL |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"10|    |    |    |    | DW |    |    |    |    |    | DW |    |    |    |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"11| DL |    |    | DW |    |    |    | DL |    |    |    | DW |    |    | DL |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"12|    |    | DW |    |    |    | DL |    | DL |    |    |    | DW |    |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"13|    | DW |    |    |    | TL |    |    |    | TL |    |    |    | DW |    |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"14| TW |    |    | DL |    |    |    | TW |    |    |    | DL |    |    | TW |\n" +

				"  ----------------------------------------------------------------------------\n" +

				"";

		char[] frame1 = { 'A', 'B', 'D', 'O', 'R', 'W', 'Z' };

		f1.setFrame(frame1);

		System.out.println("\n\n\n");

		b.wordCheck(f1, "WORD", 6, 7, 'H');

		System.out.println("\n\n\n");

		b.displayBoard();

		// getter functions being tested

		System.out.println("Number of words on board: \n Expected result:" + 1);

		System.out.println(" Actual result:" + b.getNumOfWordsOnBoard() + "\n");

		System.out.println("Number of turns: \n Expected result:" + 1);

		System.out.println(" Actual result:" + b.getNumOfTurns() + "\n");

		// CHECKING METHODS BEING TESTED INDIVIDUALLY.

		System.out.println("Is a centre word(first word): \n Expected result:true ");

		System.out.println(" Actual result:" + b.isCentreWord("WORD", 6, 7, 'H') + "\n");

		System.out.println("Is in frame: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isInFrame(f1, "WORD", 6, 7, 'H') + "\n");

		System.out.println("Is in bounds: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isInBounds("WORD", 6, 7, 'H') + "\n");

		System.out.println("Is a connected and unconflicting: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isConnectedAndUnconflicitng("WORD", 6, 7, 'H') + "\n");

		System.out.println("\t \t *************************************************************************");

		char[] frame2 = { 'C', 'A', 'T', 'H', 'O', 'D', 'E' };

		f2.setFrame(frame2);

		System.out.println("\n\n\n");

		b.wordCheck(f2, "CATHODE", 7, 3, 'V');

		System.out.println("\n\n\n");

		b.displayBoard();

		System.out.println("Number of words on board: \n Expected result:" + 2);

		System.out.println(" Actual result:" + b.getNumOfWordsOnBoard() + "\n");

		System.out.println("Number of turns: \n Expected result:" + 2);

		System.out.println(" Actual result:" + b.getNumOfTurns() + "\n");

		System.out.println("Is a centre word(first word): \n Expected result:true ");

		System.out.println(" Actual result:" + b.isCentreWord("CATHODE", 7, 3, 'V') + "\n");

		System.out.println("Is in frame: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isInFrame(f2, "CATHODE", 7, 3, 'V') + "\n");

		System.out.println("Is in bounds: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isInBounds("CATHODE", 7, 3, 'V') + "\n");

		System.out.println("Is a connected and unconflicting: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isConnectedAndUnconflicitng("CATHODE", 7, 3, 'V') + "\n");

		System.out.println("\t \t *************************************************************************");

		char[] frame3 = { 'C', 'C', 'O', 'R', 'S', 'D', 'E' };

		f3.setFrame(frame3);

		System.out.println("\n\n\n");

		b.wordCheck(f3, "CORDS", 6, 7, 'H');

		System.out.println("\n\n\n");

		b.displayBoard();

		System.out.println("Number of words on board: \n Expected result:" + 2);

		System.out.println(" Actual result:" + b.getNumOfWordsOnBoard() + "\n");

		System.out.println("Number of turns: \n Expected result:" + 2);

		System.out.println(" Actual result:" + b.getNumOfTurns() + "\n");

		System.out.println("Is a centre word(first word): \n Expected result:true ");

		System.out.println(" Actual result:" + b.isCentreWord("CORDS", 6, 7, 'H') + "\n");

		System.out.println("Is in frame: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isInFrame(f3, "CORDS", 6, 7, 'H') + "\n");

		System.out.println("Is in bounds: \n Expected result:true ");

		System.out.println(" Actual result:" + b.isInBounds("CORDS", 6, 7, 'H') + "\n");

		System.out.println("Is a connected and unconflicting: \n Expected result:false ");

		System.out.println(" Actual result:" + b.isConnectedAndUnconflicitng("CORDS", 6, 7, 'H') + "\n");

		// reseting the board. Expecting the board at the start.

		System.out.println("\t***************RESETTING THE BOARD*************");

		b.resetBoard();

		System.out.println("Expected result: \n" + start);

		System.out.println("Actual result:");

		b.displayBoard();

	}

}