package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

//By Alphabet Inc. :

	//Emanuel Dascalu, 18729365

	//Pranchal Narang, 18339361 
	//The github account is lakesh narang

	//Taranpreet Singh, 18203372

public class Tile extends Rectangle{

	private static final int tile_size = 30;
    private static final int BLANK_VALUE = 0;
    private static final int[] TILE_VALUE = 
    {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    private boolean blank;
    private char letter;
    private int value;
    
    private StackPane tileUi;
    
    public StackPane getTileUi() {return tileUi;}
    
    public void setUiTile() 
    {
    	tileUi = new StackPane();
    	Text t = new Text(Character.toString(getLetter()));
    	this.setFill(Color.YELLOW);
    	tileUi.getChildren().addAll(this, t);
    }
    // Tile precondition: must be uppercase letter
    Tile(char letter) 
    {
    	setWidth(tile_size);
    	setHeight(tile_size);
    	
        if (letter == '_') 
        {
            this.blank = true;
            this.value = BLANK_VALUE;
        } 
        
        else 
        {
            this.blank = false;
            this.letter = letter;
            this.value = TILE_VALUE[(int) letter - (int) 'A'];
        }
        
        setUiTile();
    }

    public boolean isBlank() 
    {
        return blank;
    }

    // getLetter precondition isBlank() = false;
    public char getLetter() 
    {
        return letter;
    }

    // getValue precondition isBlank() = false;
    public int getValue() 
    {
        return value;
    }

    // equals is used by the contains method to find matching objects in an ArrayList
    @Override
    public boolean equals(Object object) 
    {
        if (!(object instanceof Tile)) 
        {
            return false;
        }
        
        else 
        {
            return this.letter == ((Tile) object).letter;
        }
    }

    @Override
    public String toString() 
    {
        if (blank) 
        {
            return "_";
        } 
        
        else 
        {
            return Character.toString(letter);
        }
    }

}