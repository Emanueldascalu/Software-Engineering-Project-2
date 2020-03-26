package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Square extends Rectangle
{
	private static final int square_size = 30;
    private int letterMuliplier;
    private int wordMultiplier;
    private boolean isOccupied;
    
    private StackPane squareUi = new StackPane();
    
    public StackPane getSquareUi() {return squareUi;}
    
    public void setSquareUi() 
    {
    	squareUi = new StackPane();
    	Text text = new Text("");
    	
    	if(isDoubleLetter() && isDoubleWord()) 
		{
			text = new Text("@@");
			this.setFill(Color.GREEN);
		}
		
        else if(isDoubleLetter()) 
        {
        	text = new Text("DL");
        	this.setFill(Color.BLUE);
        }
        
        else if(isTripleLetter())
        {
        	text = new Text("TL");
        	this.setFill(Color.RED);
        }
        
        else if(isDoubleWord()) 
        {
        	text = new Text("DW");
        	this.setFill(Color.BLUEVIOLET);
        }
        
        else if(isTripleWord()) 
        {
        	text = new Text("TW");
        	this.setFill(Color.CRIMSON);
        }
        
        else 
        {
        	this.setFill(Color.WHITE);
        }
    	
    	squareUi.getChildren().addAll(this, text);
    }
    
    private Tile tile;

    Square(int letterMultiplier, int wordMultiplier) 
    {
    	setWidth(square_size);
    	setHeight(square_size);
    	
        isOccupied = false;
        this.letterMuliplier = letterMultiplier;
        this.wordMultiplier = wordMultiplier;
        
        setSquareUi();
    }
    
    
    public int getLetterMuliplier() 
    {
        return letterMuliplier;
    }

    public int getWordMultiplier() 
    {
        return wordMultiplier;
    }

    public boolean isDoubleLetter() 
    {
        return letterMuliplier == 2;
    }

    public boolean isTripleLetter() 
    {
        return letterMuliplier == 3;
    }

    public boolean isDoubleWord() 
    {
        return wordMultiplier == 2;
    }

    public boolean isTripleWord() 
    {
        return wordMultiplier == 3;
    }

    public void add(Tile tile) 
    {
        isOccupied = true;
        this.tile = tile;
    }

    public boolean isOccupied() 
    {
        return isOccupied;
    }

    // getTile pre-condition: isOccupied must be true
    public Tile getTile() 
    {
        return tile;
    }

}
