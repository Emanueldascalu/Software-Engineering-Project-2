
public class Player 
{
	private String name;
	private int score = 0;
	private Frame frame = new Frame();
	
	public Player(Frame f)
	{
		setFrame(f);
	}
	
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
	
	public void dataReset() 
	{
		setName("");
		setScore(0);	
		frame.resetFrame();
	}
	
	public void increaseScore(int addedPoints) 
	{
		score += addedPoints;
	}
	
	public void displayName() 
	{
		System.out.println(getName());
	}
}
