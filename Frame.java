import java.util.Arrays;

public class Frame //extends Pool
{   
	private char[] frame = {' ', ' ', ' ', ' ', ' ', ' ', ' '};

	public Frame()
	{
		;
	}
	
	public char removeLetter(char letter) 
	{
		char removedLetter = ' ';
		for(int i = 0; i < 7; i++) 
		{
			if(frame[i] == letter) 
			{
				removedLetter = frame[i];
				frame[i] = ' ';
				System.out.println(removedLetter + " tile was removed from frame.");
				break;
			}
		}
		
		return removedLetter;
	}
	
	public char[] getFrame() 
	{
		return frame;
	}
	
	public void addLetter(char c) 
	{
		for(int i = 0; i < 7; i++)
		{
			if(frame[i] == ' ') 
			{
				frame[i] = c;
				break;
			}
		}
	}
	
	public boolean isFrameEmpty()
	{	
		for(int i = 0; i < 7; i++) {
			if(frame[i] != ' ') 
			{
				//System.out.println("Frame is not empty");
				return false;
			}
		}
		//System.out.println("Frame is empty");
		return true;
	}
	
	public boolean isLetterInFrame(char c)
	{	
		for(int i = 0; i < 7; i++) {
			if(frame[i] == c) 
			{
				System.out.println(c + " is on the frame.");
				return true;
			}
		}
		System.out.println(c + " is not on the frame.");
		return false;
	}
	
	public void refillFrame(char[] c)
	{
		if(isFrameEmpty()) 
		{
			int i=0;
			while(i < 7) 
			{
				addLetter(c[i]);
				i++;
			}
		}
	}
	
	public void resetFrame() 
	{
		for(int i = 0; i < 7; i++) 
		{
			frame[i] = ' ';
		}
	}
	
	public void displayFrame() 
	{
		System.out.println(Arrays.toString(getFrame()));
	}	
}
