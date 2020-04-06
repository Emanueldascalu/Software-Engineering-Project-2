package application;

//A trie object is created in Scrabble to store the Dictionary
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
}