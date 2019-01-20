package tree;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by knutandersstokke on 28 28.10.2017.
 * 
 * Running both test after eachother in one run fails, but executing them individually works fine.
 */
public class PrintTreeTest {
    
    /*@Test
    public void testExampleTree() {
        testTree("smallTree");
    }*/

    @Test
    public void testTrivialTree() {
        testTree("trivialTree");
    }

    private void testTree(String fileToTest) {
        String inputContent = new In("tree/" + fileToTest + ".txt").readAll();
        String actual = PrintTree.formatStringToTree(inputContent);
        
        String expectedOutput = new In("tree/" + fileToTest + ".out").readAll().trim(); // Be careful to not overwrite this file!
        assertEquals("Program should return expected output", expectedOutput, actual);
    }

}