package com.crt.trie;
import java.io.*;
import java.util.*;
/*
 * Write a menu driven program with following operations:
 * 1. Insert a word
 * 2. Insert words form a file
 * 3. Search a word
 * 4. Print all words
 * 5. Print all words with given prefix
 * 6. exit
 */

class TNode{
	boolean isEndOfWord;
	TNode[] children;
	
	public TNode() {
		this.isEndOfWord = false;
		children = new TNode[26];
	}
}

class Trie{
	TNode root;
	public Trie() {
		root = new TNode();
	}
	//print all words
	public List<String> printAllWords() {
		TNode temp = root;
		List<String> ans = new ArrayList<>();
		StringBuilder path = new StringBuilder();
		h(root,path,ans);
		return ans;
	}
	 void h(TNode root, StringBuilder path, List<String> ans){
	        if(root.isEndOfWord == true){
	            ans.add(path.toString());
	        }
	        for (int i = 0; i < 26; i++) {
	            if(root.children[i] != null){
	                path.append((char)(i + 'a')); // choose
	                h(root.children[i], path, ans); // explore
	                path.setLength(path.length()-1); // un-choose
	            }
	        }
	    }
	//insert word
	public void insertWord(String word) {
		TNode temp = root;
		for(char ch:word.toCharArray()) {
			if(ch<'a' && ch>'z')continue;
			int idx = ch-'a';
			if(temp.children[idx]==null) {
				TNode nn = new TNode();
				temp.children[idx] = nn;
			}
			temp = temp.children[idx];
		}
		temp.isEndOfWord = true; 
	}
	//search word
	public boolean hasWord(String word) {
		TNode temp = root;
		for(char ch:word.toCharArray()) {
			int idx = ch-'a';
			if(temp.children[idx]==null) {
				return false;
			}
			temp = temp.children[idx];
		}
		return temp.isEndOfWord;
	}
	
	//print all word with given prefix
	public List<String> autoSuggest(String prefix){
		List<String> ans = new ArrayList<>();
		TNode temp = root;
		for(char ch:prefix.toCharArray()) {
			int idx = ch-'a';
			if(temp.children[idx]==null) {
				return ans;
			}
			temp = temp.children[idx];
		}
		h(temp,new StringBuilder(prefix),ans);
		return ans;
	}
}
public class Main {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Tries");
		System.out.println("--------------------------------------------");
		System.out.println("Press 1 to view the menu");
		boolean run = true;
		Trie t = new Trie();
		do {
			int choice = sc.nextInt();
			switch(choice){
				case 1:
					System.out.println("Press 2 to insert a word");
					System.out.println("Press 3 to insert words from a file");
					System.out.println("Press 4 to search a word in dictionary");
					System.out.println("Press 5 to Print all words");
					System.out.println("Press 6 to print words for a prefix");
					System.out.println("Press 7 to Exit");
					break;
				case 2:
					System.out.println("Enter Word: ");
					String word = sc.next();
					t.insertWord(word);
					System.out.println("Word Inserted Sucessfully");
					break;
				case 3:
					String filePath = "D:\\abhi BTECH-VI\\CRT-III\\trie dict.txt";
					File file = new File(filePath);

				    if (!file.exists()) {
				        System.out.println("File not found.");
				        System.out.println("File path: " + filePath);
				        break;
				    }

				    try (Scanner fileScanner = new Scanner(file)) {
				        while (fileScanner.hasNextLine()) {
				            String wrd = fileScanner.nextLine();
				            t.insertWord(wrd);
				        }
				        System.out.println("Words inserted from file successfully.");
				    } catch (FileNotFoundException e) {
				        System.err.println("Error reading file: " + e.getMessage());
				    }
				    break;
				case 4:
					String search = sc.next();
					if(t.hasWord(search)) {
						System.out.println("Word found in the dictionary");
					}else {
						System.out.println("Word not found");
					}
					break;
				case 5:
					List<String> words = t.printAllWords();
					for(String w:words) {
						System.out.print(w+" ");
					}
					System.out.println();
					break;
				case 6:
					String prefix = sc.next();
					List<String> suggwrds = t.autoSuggest(prefix);
					for(String w:suggwrds) {
						System.out.print(w+" ");
					}
					System.out.println();
					break;
				case 7:
					System.out.println("Quitting from Program");
					run = false;
					break;
				default:
					System.out.println("Enter correct option");
					break;
			}

		}while(run);		
	}
}
