import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.In;
import huffman.Huffman;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This test class works with my implementation of huffman coding
 * (BinaryIn/In from algs4 used to get files contra the original)
 */
public class HuffmanAdaptedTest {
    private final String DELIMITER = "****";
    private final boolean DEBUG = false;

    @Test
    public void testOneLineFile() {
        testHuffman("oneLine.txt");
    }

    @Test
    public void testTwoLinesFile() {
        testHuffman("twoLines.txt");
    }

    @Test
    public void testSmallText() {
        testHuffman("lorem.txt");
    }

    @Test
    public void testEmptyLineBetweenTwoLines() {
        testHuffman("emptyLineBetweenTwoLines.txt");
    }

    //modified to work with the IO- of my program
    private void testHuffman(String originalFile) {
        String originalContent = new In(getDefaultAbsolutePath() + "/res/" + originalFile).readAll();
        Huffman.encode(originalContent, getDefaultAbsolutePath() + originalFile);

        BinaryIn bRead = new BinaryIn(getDefaultAbsolutePath() + originalFile + ".cmp");
        String compressedContent = bRead.readString();

        String[] linesOfCmp = compressedContent.split("\n");
        int delLine = findLineWithDelimiter(linesOfCmp);
        assertNotEquals(delLine, -1);

        String decoded = Huffman.decode(compressedContent);

        if(DEBUG) printOrigAndDecoded(originalContent, decoded);

        assertEquals(originalContent, decoded);
    }

    private void printOrigAndDecoded(String original, String decoded) {
        System.out.println("\n******************\n");
        System.out.println(original);
        System.out.println("----");
        System.out.println(decoded);

    }

    private int findLineWithDelimiter(String[] lines) {
        for (int i=0; i < lines.length; i++)
            if (lines[i].equals(DELIMITER))
                return i + 1;
        return -1;
    }

    private String getDefaultAbsolutePath() {
        String absPath = new File(".").getAbsolutePath(); // a little hack...
        absPath = absPath.substring(0, absPath.length() - 1);
        return absPath;
    }
}
