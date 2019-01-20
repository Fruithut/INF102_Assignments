package minimiseDisks;
import helpers.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MinimiseDisks {

    /**
     * Private class to represent a disk that can hold a certain amount of data
     */
    private static class Disk {
        private int size;
        private ArrayList<Integer> files = new ArrayList<>();

        /**
         * Initialize disk with a given size
         */
        private Disk() {
            size = 1000;
        }

        /**
         * Returns false if disk cannot hold the given file
         * @param file input file
         * @return false/true if the file can be added
         */
        private boolean add(int file){
            if (size - file >= 0) {
                files.add(file);
                size -= file;
                return true;
            }
            return false;
        }

        /**
         * @return files contained on disk
         */
        @Override
        public String toString() {
            String total = "[";
            for (int i = 0; i < files.size(); i++) {
                if (i == files.size() - 1) {
                    total += files.get(i) + "MB";
                    break;
                }
                total += files.get(i) + "MB, ";
            }
            total += "]";
            return total;
        }
    }

    public static void main(String[] args) {
        //allocateInOrder(readFile("inputfiles1.txt"));
        //allocateDescending(readFile("inputfiles1.txt"));
        Integer[] myFiles = generateFiles(200);
        allocateInOrder(myFiles);
        allocateDescending(myFiles);
    }

    /**
     * Adds files to disk as they are presented
     * @param input files to allocate to a drive/drives (numbers)
     */
    private static void allocateInOrder(Integer[] input){
        ArrayList<Disk> diskArray = new ArrayList<>();
        Disk dataDisk = new Disk();
        for (Integer file : input) {
            if(!dataDisk.add(file)){
                diskArray.add(dataDisk);
                dataDisk = new Disk();
                //consume the one that did not fit in last disk (@current file)
                dataDisk.add(file);
            }
        }
        //print out contents in a structured manner
        System.out.println(diskArray.size());
        for (int i = 0; i < diskArray.size(); i++) {
            System.out.println("Disk " + (i+1) + " : " + diskArray.get(i));
        }
    }

    /**
     * Sorts the input in descending order first
     * and the adds them to disks
     * @param input an array of 'files' (numbers)
     */
    private static void allocateDescending(Integer[] input){
        Arrays.sort(input, Collections.reverseOrder());
        allocateInOrder(input);
    }

    /**
     * Generate an array to represent an input sequence of
     * files with a number as file size
     * @param numberOfFiles number of 'files' to generate
     * @return an integer array of numbers ranging from 1-999
     */
    private static Integer[] generateFiles(int numberOfFiles) {
        Random x = new Random();
        Integer[] files = new Integer[numberOfFiles];
        for (int i = 0; i < files.length; i++) {
            files[i] = x.nextInt(998) + 1;
        }
        return files;
    }

    /**
     * Read numbers from file input
     * @param filename file to read from
     * @return an integer array with the numbers from a given file 
     */
    private static Integer[] readFile(String filename){
        FileReader x = new FileReader();
        String[] files = x.getFile(filename).split("\n");
        Integer[] fileSizes = new Integer[files.length];
        for (int i = 0; i < files.length; i++) {
            fileSizes[i] = Integer.parseInt(files[i].trim());
        }
        return fileSizes;
    }
}
