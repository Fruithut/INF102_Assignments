package tree;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

/**
 * Created by Olav Gjerde 01.11.2017
 */
public class PrintTree {
    private static String[] itemArray;
    private static boolean[] marked, written;
    private static int depth = 0;
    private static StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
        String inputFile = args[0];
        String inputContent = new In(inputFile).readAll();
        System.out.println(formatStringToTree(inputContent));
    }

    /**
     * Reads input from string, and construct a graph to connect
     * the different items.
     *
     * @param inputContent string with hierarchy-description of items
     * @return a string with the hierarchy indented
     */
    static String formatStringToTree(String inputContent) {
        String[] lines = inputContent.split("\n");
        int itemNum = Integer.parseInt(lines[0].trim());
        itemArray = lines[1].split(" ");

        Graph graph = new Graph(itemNum);
        for (int i = 2; i < lines.length; i++) {
            String content = lines[i].replaceAll(" ", "");
            content = content.replaceAll(":", "").trim();
            int vertex = Integer.parseInt(String.valueOf(content.charAt(0)));

            for (int j = 1; j < content.length(); j++) {
                int connectTo = Integer.parseInt(String.valueOf(content.charAt(j)).trim());
                graph.addEdge(vertex, connectTo);
            }
        }

        //keep track of which items haven been visited 
        //and which have already been printed
        marked = new boolean[graph.V()];
        written = new boolean[graph.V()];

        return modDepthFirstSearch(graph, 0);
    }

    /**
     * A modified depth first search on a graph, uses a
     * counter to keep track of how deep the search is
     * and then indents the print of a vertex accordingly
     *
     * @param G A graph with "relations constructed"
     * @param v Vertex to start search from
     * @return a hierarchy of the elements in the graph
     */
    private static String modDepthFirstSearch(Graph G, int v) {
        marked[v] = true;

        //example: root contains folder1; go to folder1 print sub-elements, don't print "folder1" again
        //also handles 1 element input edge-case
        if (!written[v]) result.append("'-").append(itemArray[v]).append("\n");
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                depth++;

                written[w] = true;
                int indentationSize = 2 * depth;
                for (int i = 0; i < indentationSize; i++) {
                    result.append(" ");
                }
                result.append("'-").append(itemArray[w]).append("\n");

                modDepthFirstSearch(G, w);
                depth--;
            }
        }
        return result.toString().trim();
    }
}
