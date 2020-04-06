package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.Scanner;

public class Trie
{	
	private class TrieNode 
	{
		TrieNode[] children = new TrieNode[ALPHABET_SIZE];
		
		boolean isEndOfWord;
		
		TrieNode()
		{
			isEndOfWord = false;
			for (int i = 0; i < ALPHABET_SIZE; i++) 
			{
				children[i] = null;
			}
		}
	}
	
	private TrieNode root = new TrieNode();
	
	public TrieNode getRoot() 
	{
		return root;
	}
	
	private final int ALPHABET_SIZE = 26;
	
	public boolean search(String word) 
	{
		int j;
		TrieNode curr = root;
		for(int i = 0; i < word.length(); i++) 
		{
			j = word.charAt(i) - 'A';
			if(curr.children[j] == null) 
			{
				return false;
			}
			
			curr = curr.children[j];
		}
		
		return (curr != null && curr.isEndOfWord == true);
	}
	
	public void insert(String word) 
	{
		int j;
		TrieNode curr = root;
		for(int i = 0; i < word.length(); i++) 
		{
			j = word.charAt(i) - 'A';
			if(curr.children[j] == null) 
			{
				curr.children[j] = new TrieNode();
			}
			
			curr = curr.children[j];
		}
		curr.isEndOfWord = true;
	}
	
	/*public static void main(String args[]) throws FileNotFoundException 
	{
		Trie trie = new Trie();

		File f = new File("C:\\Users\\Emanuel\\Desktop\\Collins Scrabble Words (2019).txt");
		Scanner sc = new Scanner(f);
		
		while(sc.hasNext()) 
		{
			trie.insert(sc.next().toLowerCase());
		}
		
		sc.close();
		
		String output[] = {"not present in trie", "present in trie"};
		
		long startTime = System.nanoTime();
		
		if(trie.search("aa")) 
			System.out.println("aa is " + output[1]);
		
	    else 
	    	System.out.println("aa is " + output[0]); 
	      
	    if(trie.search("writhe")) 
	        System.out.println("writhe is " + output[1]); 
	    
	    else 
	    	System.out.println("writhe is " + output[0]); 
	      
	    if(trie.search("asdf")) 
	        System.out.println("asdf is " + output[1]);
	    
	    else 
	    	System.out.println("asdf is " + output[0]); 
	      
	    if(trie.search("lllll")) 
	        System.out.println("lllll is " + output[1]); 
	    
	    else 
	    	System.out.println("lllll is " + output[0]);
	    
	    if(trie.search("zzzs"))
	    	System.out.println("zzzs is " + output[1]);
	    
	    else
	    	System.out.println("zzzs is " + output[0]);
	    
	    long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		System.out.println(elapsedTime);
	}*/
}