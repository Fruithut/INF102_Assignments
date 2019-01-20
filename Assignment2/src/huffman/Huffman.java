package huffman;

import edu.princeton.cs.algs4.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("Please provide filename of file to be encoded");
        String inputFile = args[0];
        if (inputFile.endsWith(".txt")) {
            String content = new In(inputFile).readAll();
            encode(content, inputFile);
            System.out.println("Compression done");
        } else if (inputFile.endsWith(".cmp")) {
            BinaryIn x = new BinaryIn(inputFile);
            String content = x.readString();
            System.out.println("decoded text:");
            System.out.println(decode(content));
        } else {
            throw new IllegalArgumentException("Please provide a .txt file or a compressed .cmp file");
        }
    }

    /**
     * Given text content and a filename the method will counstruct a 
     * symbol-table with frequencies on characters and build the necessary
     * structures using other methods (see desc @buildCode @buildHuffTrie)
     * to build a huffman representation for each character
     * and print this to a file for decoding later on.
     * @param content the text to be compressed
     * @param originalFileName the filename associated with the content
     */
    public static void encode(String content, String originalFileName) {
        //count frequencies while reading text
        int[] freqMap = new int[257];
        for (int i = 0; i < content.length(); i++)
            freqMap[content.charAt(i)]++;
        freqMap[256] = 1;
        
        MinPQ<Node> nodeQueue = new MinPQ<>();
        BinaryOut bWrite = new BinaryOut(originalFileName + ".cmp");
        for (int i = 0; i < freqMap.length; i++) {
            if (freqMap[i] != 0) {
                nodeQueue.insert(new Node((char) i, freqMap[i], null, null));
                bWrite.write(i + " " + freqMap[i] + "\n");
            }
        }
        Node huffTrie = buildHuffTrie(nodeQueue);

        //make space for EOF (end of compressed data marker at char "257")
        String[] charCodes = new String[257];
        buildCode(charCodes, huffTrie, "");

        bWrite.write("****\n");
        for (int i = 0; i < content.length(); i++) {
            String compressed = charCodes[content.charAt(i)];
            for (int j = 0; j < compressed.length(); j++) {
                if (compressed.charAt(j) == '0') {
                    bWrite.write(false);
                } else if (compressed.charAt(j) == '1') {
                    bWrite.write(true);
                }
            }
        }

        //Add EOF
        String compressedEnd = charCodes[256];
        for (int i = 0; i < compressedEnd.length(); i++) {
            if (compressedEnd.charAt(i) == '0') {
                bWrite.write(false);
            } else if (compressedEnd.charAt(i) == '1') {
                bWrite.write(true);
            }
        }
        bWrite.close();
    }

    /**
     * Consumes a symbol-table and the compressed data
     * and decodes this using the huffmantrie construced from
     * the given symbol-table.
     * @param content to be decoded
     * @return the decoded content
     */
    public static String decode(String content) {
        String[] lines = content.split("\n");
        MinPQ<Node> nodeQueue = new MinPQ<>();
        for (String str : lines) {
            if (str.equals("****")) break;
            else {
                int index = str.indexOf(" ");
                char character = (char) Integer.parseInt(str.substring(0, index));
                int frequency = Integer.parseInt(str.substring(index + 1, str.length()));
                nodeQueue.insert(new Node(character, frequency, null, null));
            }
        }

        Node root = buildHuffTrie(nodeQueue);
        Node x = root;

        int delimiter = content.indexOf("****") + 5;
        String binCode = content.substring(delimiter, content.length());

        //decode compressed input
        StringBuilder builder = new StringBuilder();
        InputStream stream = new ByteArrayInputStream(binCode.getBytes(Charset.forName("ISO-8859-1")));
        BinaryIn binaryIn = new BinaryIn(stream);
        while (!binaryIn.isEmpty()) {
            if (binaryIn.readBoolean()) x = x.right;
            else x = x.left;
            if (x.checkLeaf()) {
                if (x.character == (char) 256) break;
                builder.append(x.character);
                x = root;
            }
        }
        return builder.toString();
    }

    /**
     * Recursively build code for every character based on the given node (huffTrie)
     * @param st Array for keeping the codes
     * @param x  Node to build structure from
     * @param y  Accumulates the code for a given character
     */
    private static void buildCode(String[] st, Node x, String y) {
        if (!x.checkLeaf()) {
            buildCode(st, x.left, y + '0');
            buildCode(st, x.right, y + '1');
        } else {
            st[x.character] = y;
        }
    }

    /**
     * Pop off the least expected and connect to "top",
     * a parent node that holds the total frequency
     * @param freqNodes
     * @return A node with the most frequent nodes closest to the top
     */
    private static Node buildHuffTrie(MinPQ<Node> freqNodes) {
        while (freqNodes.size() > 1) {
            Node right = freqNodes.delMin();
            Node left = freqNodes.delMin();
            Node top = new Node('\0', right.frequency + left.frequency, right, left);
            freqNodes.insert(top);
        }
        return freqNodes.delMin();
    }

    /**
     * A privat class that represents nodes with an included frequency
     * variable.
     * Simliar code can be found in the included algs4 - princeton library
     */
    private static class Node implements Comparable<Node> {
        private final char character;
        private int frequency;
        private final Node right, left;

        private Node(char character, int frequency, Node right, Node left) {
            this.character = character;
            this.frequency = frequency;
            this.right = right;
            this.left = left;
        }

        private boolean checkLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }
    }
}
