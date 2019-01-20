package tree;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

/**
 * Created by Olav Gjerde 01.11.2017
 */
public class PrintTree {
    private static String[] itemNames;
    private static boolean[] marked, written;
    private static int searchDepth = 0;
    private static StringBuilder treeHierarchy = new StringBuilder();

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
        String[] lines = inputContent.split(System.lineSeparator());
        int itemNum = Integer.parseInt(lines[0].trim());
        itemNames = lines[1].split(" ");

        Graph graph = new Graph(itemNum);
        for (int i = 2; i < lines.length; i++) {
            String[] content = lines[i].split(" ");
            int vertex = Integer.parseInt(content[0]);
            
            for (int j = 2; j < content.length; j++) {
                graph.addEdge(vertex, Integer.parseInt(content[j]));
            }
        }

        //keep track of which items haven been visited, and which have already been written
        marked = new boolean[graph.V()];
        written = new boolean[graph.V()];
        
        return modDepthFirstSearch(graph, 0);
    }

    /**
     * A modified searchDepth first search on a graph, uses a
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
        if (!written[v]) treeHierarchy.append("'-").append(itemNames[v]).append(System.lineSeparator());
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                searchDepth++;
                
                for (int i = 0; i < 2 * searchDepth; i++) treeHierarchy.append(" ");
                treeHierarchy.append("'-").append(itemNames[w]).append(System.lineSeparator());
                written[w] = true;
                
                modDepthFirstSearch(G, w);
                searchDepth--;
            }
        }
        return treeHierarchy.toString().trim();
    }
    
}
