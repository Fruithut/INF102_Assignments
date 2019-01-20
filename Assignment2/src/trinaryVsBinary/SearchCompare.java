package trinaryVsBinary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchCompare {
    private static UBST<String, Integer> binaryTree = new UBST<>();
    private static UTST<String> ternaryTree = new UTST<>();
    
    public static void main(String[] args) {
        List<String> inList = new ArrayList<>();
        List<String> inListTwo = new ArrayList<>();
        try {
            inList = Files.readAllLines(Paths.get("1000words.txt"));
            inListTwo = Files.readAllLines(Paths.get("1000words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Collections.shuffle(inList);
        fillTrees(inList);
        
        Collections.shuffle(inListTwo);
        getWords(inListTwo);
    }

    /**
     * Fills the trees with the contents of a list
     * @param words
     */
    private static void fillTrees(List<String> words){
        for (int i = 0; i < words.size(); i++) {
            binaryTree.put(words.get(i), i);
            ternaryTree.put(words.get(i));
        }
        System.out.println("PutCompares-Binary: " + binaryTree.getGlobalCompare() + "\n" +
                           "PutCompares-Ternary: " + ternaryTree.getGlobalCompare());
    }

    /**
     * Runs get operation on a given tree with words from a list
     * @param words
     */
    private static void getWords(List<String> words){
        for (String word: words) {
            binaryTree.get(word);
            //ternaryTree.get(word);
        }
        System.out.println("Put&Get-Binary: " + binaryTree.getGlobalCompare());
    }
}
