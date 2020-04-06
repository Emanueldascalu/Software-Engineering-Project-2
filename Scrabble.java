package application;

//By Alphabet Inc. :	

	//Emanuel Dascalu, 18729365	

	//Pranchal Narang, 18339361 	
	//The github account is lakesh narang	

	//Taranpreet Singh, 18203372

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Scrabble {

	private static int NUM_PLAYERS = 2;
	private static int ZERO_SCORE_PLAY_LIMIT = 6;

	private Board board;
	private Pool pool;
	private ArrayList<Player> players;
	private int currentPlayerId;
	private int numZeroScorePlays;
	
	/*Used to store the dictionary in a trie for faster lookup 
	 * than a regular string comparison search algorithm*/
	private Trie trie;
	

	Scrabble() {
		board = new Board();
		pool = new Pool();
		players = new ArrayList<>();
		for (int i = 0; i < NUM_PLAYERS; i++) {
			players.add(new Player(i));
		}
		for (Player player : players) {
			player.getFrame().refill(pool);
		}
		currentPlayerId = 0;
		numZeroScorePlays = 0;
		trie = new Trie();
		//createTrie(trie);
	}
	
	public Trie getTrie() {return trie;}
	
	//Inserts every word from the dictionary into the trie
	public void createTrie(Trie trie) throws FileNotFoundException
	{
		//Trie trie = new Trie();

		File f = new File("C:\\Users\\Emanuel\\Desktop\\Collins Scrabble Words (2019).txt");
		Scanner sc = new Scanner(f);
		
		while(sc.hasNext()) 
		{
			trie.insert(sc.next());
		}
		
		sc.close();
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentPlayerId);
	}

	public void turnOver() {
		if (currentPlayerId == NUM_PLAYERS - 1) {
			currentPlayerId = 0;
		} else {
			currentPlayerId++;
		}
	}

	public Board getBoard() {
		return board;
	}

	public Pool getPool() {
		return pool;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void zeroScorePlay() {
		numZeroScorePlays++;
	}

	public void scorePlay() {
		numZeroScorePlays = 0;
	}

	public boolean isZeroScorePlaysOverLimit() {
		return numZeroScorePlays >= ZERO_SCORE_PLAY_LIMIT;
	}

	public void adjustScores() {
		for (Player player : players) {
			player.adjustScore();
		}
	}
	
	public int getCurrentPlayerId() { 
		return currentPlayerId; } 

}
