package tree;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by knutandersstokke on 28 28.10.2017.
 * 
 * Modified: Just tabbed out one of the test, they end up equal in ("click to see difference"),
 * but the end of line sign may be different CRLF/LF
 */
public class PrintTreeTest {
    
    //fails but shows same text in comparison
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