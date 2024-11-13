package dsa;

import java.util.Scanner;

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.children[ch - 'a'] == null) current.children[ch - 'a'] = new TrieNode();
            current = current.children[ch - 'a'];
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.children[ch - 'a'] == null) return false;
            current = current.children[ch - 'a'];
        }
        return current.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (current.children[ch - 'a'] == null) return false;
            current = current.children[ch - 'a'];
        }
        return true;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(System.in);

        System.out.println("The Trie Program");

        System.out.println("Enter strings to insert into the Trie (type 'done' to finish):");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("done")) {
                break;
            }
            trie.insert(input);
        }

        System.out.println("Enter a string to search:");
        String searchInput = scanner.nextLine();
        System.out.println("Search result for '" + searchInput + "': " + trie.search(searchInput));

        System.out.println("Enter a prefix to check:");
        String prefixInput = scanner.nextLine();
        System.out.println("Prefix search result for '" + prefixInput + "': " + trie.startsWith(prefixInput));

        scanner.close();
    }
}