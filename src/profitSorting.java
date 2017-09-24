import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Stopwatch;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class profitSorting {
    private static Random numGen = new Random();
    private static final int SIZE = (int) Math.pow(10.0,6.0);
    
    private static int[] listGen(){
        int[] numbers = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = numGen.nextInt(Integer.MAX_VALUE);
        }
        return numbers;
    }
    
    //unsorted linear-search
    private static double linearSearch(int trials){
        int[] numList = listGen();
        Stopwatch timeCounter = new Stopwatch();
        
        for (int i = 0; i < trials; i++) {
            for (Integer value : numList) {
                if (value == numGen.nextInt(300000)) {
                    //System.out.println("LinearPos: " + value);
                    break;
                }
            }
        }
        
        return timeCounter.elapsedTime();
    }
    
    //quick-sort then binary search
    private static double binarySearch(int trials){
        int[] numList = listGen();
        Stopwatch timeCounter = new Stopwatch();
        Arrays.sort(numList);
        
        for (int i = 0; i < trials; i++) {
            //System.out.println("BinaryPos: " + BinarySearch.indexOf(numList,numGen.nextInt(300000)));
            BinarySearch.indexOf(numList,numGen.nextInt(300000));
        }
        
        return timeCounter.elapsedTime();
    }
    
    public static void main(String[] args) {
        System.out.println("LinearSearch-time: " + linearSearch(11));
        System.out.println("BinarySearch-time: " + binarySearch(11));
    }
}
