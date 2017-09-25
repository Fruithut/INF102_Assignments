import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.Random;

/**
 * Simple class for comparing linear searches against mergesort followed by binary searches
 * Made by Olav Gjerde (ogj005)
 */
public class profitSorting {
    private static Random numGen = new Random();
    private static final int N = 10 *(int) Math.pow(10.0,6.0);

    /**
     * Generate a array with random integers
     * @return a list of size N with random integers from 0
     * to "Integer.MAX_VALUE"
     */
    private static int[] listGen(){
        int[] numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = numGen.nextInt(Integer.MAX_VALUE);
        }
        return numbers;
    }

    /**
     * Unsorted linear-search
     * @param trials how many times to search an array
     * @return time used for all trials
     */
    private static double linearSearch(int trials){
        int[] numList = listGen();
        Stopwatch timeCounter = new Stopwatch();
        for (int i = 0; i < trials; i++) {
            for (Integer value : numList) {
                if (value == numGen.nextInt(Integer.MAX_VALUE)) {
                    System.out.println("LinearPos: " + value);
                    break;
                }
            }
        }
        return timeCounter.elapsedTime();
    }

    /**
     * Mergesort of array followed på N trials of binarysearch
     * @param trials how many times to search an array
     * @return time used for all trials
     */
    private static double binarySearch(int trials){
        int[] numList = listGen();
        Stopwatch timeCounter = new Stopwatch();
        MergeX.sort(numList);
        
        for (int i = 0; i < trials; i++) {
            System.out.println("BinaryPos: " + BinarySearch.indexOf(numList,numGen.nextInt(Integer.MAX_VALUE)));
            //BinarySearch.indexOf(numList,numGen.nextInt(Integer.MAX_VALUE));
        }
        
        return timeCounter.elapsedTime();
    }
    
    public static void main(String[] args) {
        System.out.println("LinearSearch-time: " + linearSearch(11));
        System.out.println("BinarySearch-time: " + binarySearch(11));
    }
}
