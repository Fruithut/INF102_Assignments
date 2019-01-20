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
     * to "bounds"
     */
    private static int[] listGen(int size, int bounds){
        int[] numbers = new int[size];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = numGen.nextInt(bounds);
        }
        return numbers;
    }

    /**
     * Unsorted linear-search
     * @param trials number of times to search
     * @param numArray Array to search in
     * @param keyArray Values to search for
     * @return time used for all searches
     */
    private static double linearSearch(int trials, int[] numArray, int[] keyArray){
        Stopwatch timeCounter = new Stopwatch();
        for (int i = 0; i < trials; i++) {
            for (int value : numArray) {
                if (value == keyArray[i]) {
                    System.out.println("LinearPos: " + value);
                    break;
                }
            }
        }
        return timeCounter.elapsedTime();
    }
    
    /**
     * Mergesort of an array followed på N trials of binary-search
     * @param trials number of times to search
     * @param numArray Array to search in
     * @param keyArray Values to search for
     * @return time used for all searches
     */
    private static double binarySearch(int trials , int[] numArray, int[] keyArray){
        Stopwatch timeCounter = new Stopwatch();
        MergeX.sort(numArray);
        for (int i = 0; i < trials; i++) {
            System.out.println("BinaryPos: " + BinarySearch.indexOf(numArray, keyArray[i]));
        }
        return timeCounter.elapsedTime();
    }
    
    public static void main(String[] args) {
        int trials = 259;
        int[] numList = listGen(N, (N));
        int[] keyList = listGen(trials, (int) (N*1.5));
        //do linear search first so the list doest get sorted before linear search takes place
        double lTime = linearSearch(trials, numList, keyList);
        double bTime = binarySearch(trials, numList, keyList);
        System.out.println("LinearSearch-time: " + lTime + "\n" + "BinarySearch-time: " + bTime);
    }
}
