package trie;

import java.util.*;
import java.io.*;

public class TRIE {
    static class Node {
        public char leadChar;
        public Boolean IsWord;
        public Node firstChild;
        public Node rightSibling;
        
        public Node(char c) {
            this.leadChar = c;
            this.IsWord = false;
            this.firstChild = null;
            this.rightSibling = null;
        }
    }
    
    static int wordCount;
    static Node root;
    
    public TRIE() {
        root = new Node((char) 0);
    }
    
    void Query(Node p, String qPrefix, String forbidden) {
        Node q = search(p, qPrefix);
        preorderMatch(q, qPrefix, forbidden);
    }
        
    Node search(Node p, String qPrefix) {
        if (qPrefix.length() == 0) return p;
        Node q = findChild(p, qPrefix.charAt(0));
        if (q == null) return null;
        return search(q, qPrefix.substring(1, qPrefix.length()));
    }
    
    void preorderMatch(Node p, String prefix, String forbidden) {
        if (p == null) return;
        if (p.IsWord == true) wordCount++;
        Node q = p.firstChild;
        while (q != null)
        {
            if (forbidden.indexOf(q.leadChar) == -1) {
                    preorderMatch(q, prefix + p.leadChar, forbidden);
            }
            q = q.rightSibling;
        }
    }
        
    Node findChild(Node p, char c) {
        Node q = p.firstChild;
        while (q != null && q.leadChar != c) q = q.rightSibling;
        return q;
    }
    
    void insert(Node p, String s) {
        if (s.length() == 0) {
            p.IsWord = true;
            return;
        }
    
        char c = s.charAt(0);
        String ss = s.substring(1, s.length());
        Node q = findChild(p, c);
        if (q == null) {
            Node r = new Node(c);
            r.rightSibling = p.firstChild;
            p.firstChild = r;
            insert(r, ss);
        } else
            insert(q, ss);
    }


    public static void main(String[] args) throws FileNotFoundException {
        TRIE tree = new TRIE();
        Scanner scan = new Scanner(new File("WORD.LST"));
        Scanner scan2 = new Scanner(new File("inputFile.txt"));
        String prefix = scan2.next();
        String forbidden = scan2.next();
        while (scan.hasNext()) {
            String word = scan.next();
            if (word.length() == 5) {
                tree.insert(tree.root, word);
                //System.out.println(word);
            }
        }
        tree.Query(tree.root, prefix, forbidden);
        System.out.println(TRIE.wordCount);
   }
}
    

