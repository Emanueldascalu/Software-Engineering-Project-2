public class Player
{
	private String name;
	private int score = 0;
	private Frame frame = new Frame();

	public Player(String n, Frame f)//creates a player
	{
		setName(n);
		setFrame(f);
	}

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

	//allows all player data to be removed for new start
	public void dataReset() 
	{
		setName("");
		setScore(0);	
		frame.resetFrame();
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
