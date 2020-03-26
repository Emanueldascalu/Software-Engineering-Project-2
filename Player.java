package application;

//By Alphabet Inc. :

	//Emanuel Dascalu, 18729365

	//Pranchal Narang, 18339361 
	//The github account is lakesh narang

	//Taranpreet Singh, 18203372

public class Player
{
	private String name;
	private int score = 0;
	private Frame frame = new Frame();

	//setters and getters for player score, name & frame
	public void setScore(int s) 
	{
		score = s;
	}
	
	public void setName(String n) 
	{
		name = n;
	}
	
	public void setFrame(Frame f) 
	{
		frame = f;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public int getScore() 
	{
		return score;
	}
	
	public Frame getFrame() 
	{
		return frame;
	}
	
	public void increaseScore(int addedPoints) //adds points scored to current score
	{
		score += addedPoints;
	}
	
	public void displayName() //displays player name
	{
		System.out.println(getName());
	}
}