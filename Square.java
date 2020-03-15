package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Square extends Rectangle
{

    private int letterMuliplier;
    private int wordMultiplier;
    private boolean isOccupied;
    StackPane stack = new StackPane();
    private Tile tile;

    Square(int letterMultiplier, int wordMultiplier, int x, int y) 
    {
    	setWidth(UI.square_size);
    	setHeight(UI.square_size);
    	
        isOccupied = false;
        this.letterMuliplier = letterMultiplier;
        this.wordMultiplier = wordMultiplier;
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
