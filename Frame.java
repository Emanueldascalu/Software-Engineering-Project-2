//By Alphabet Inc. : 
//Emanuel Dascalu, 18729365 
//Pranchal Narang, 18339361  
//Taranpreet Singh, 18203372

import java.util.Arrays;

public class Frame
{   
	private char[] frame = {' ', ' ', ' ', ' ', ' ', ' ', ' '}; //empty frame

	public char removeLetter(char letter) //removes given letter from frame
	{
		char removedLetter = ' ';
		for(int i = 0; i < 7; i++) 
		{
			if(frame[i] == Character.toUpperCase(letter)) // all letters stored in the frames are uppercase
			{
				removedLetter = frame[i];
				frame[i] = ' ';
				System.out.println(removedLetter + " tile was removed from frame.");
				break; //ending loop execution once letter has been removed
			}
		}
		
		return removedLetter;
	}
	
	public char getLetter(int index) //gets the letter at given index in frame
	{
		return frame[index];
	}

	public boolean isFrameEmpty()
	{	
		for(int i = 0; i < 7; i++) { //loops through frame and if a letter is found in any position, returns false
			if(frame[i] != ' ')
			{
				//System.out.println("Frame is not empty");
				return false;
			}
		}
		//System.out.println("Frame is empty");
		return true;
	}
	
	public boolean isLetterInFrame(char c)//checks if a given letter is available in the frame
	{	
		for(int i = 0; i < 7; i++) {
			if(frame[i] == c) 
			{
				//System.out.println(c + " is on the frame.");
				return true;
			}
		}
		//System.out.println(c + " is not on the frame.");
		return false;
	}
	
	public void refillFrame(/*char[] c*/)
	{

		for(int i = 0; i < 7; i++) //loops through frame and places random letters in empty spaces
		{
			if(frame[i] == ' ') 
			{
				frame[i] = Pool.removeTileRandomly();
			}
		}
	}
	
	public void resetFrame() //resets the entire frame to blank frame
	{
		for(int i = 0; i < 7; i++) 
		{
			frame[i] = ' ';
		}
	}
	
	public void displayFrame() 
	{
		System.out.println(Arrays.toString(frame));//displays the frame character array as a string
	}	
}
