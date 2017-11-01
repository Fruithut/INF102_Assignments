package tree;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Queue;

import java.util.*;
import java.util.Stack;

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

    public static String formatStringToTree(String inputContent) {
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

        marked = new boolean[graph.V()];
        written = new boolean[graph.V()];

        return dfs(graph, 0);
    }

    private static String dfs(Graph G, int v) {
        marked[v] = true;

        if (!written[v]) result.append("'-").append(itemArray[v]).append("\n");
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                depth++;
                
                written[w] = true;
                int indentationSize = 2*depth;
                for (int i = 0; i < indentationSize; i++) {
                    result.append(" ");
                }
                result.append("'-").append(itemArray[w]).append("\n");

                dfs(G, w);
                depth--;
            }
        }
        return result.toString();
    }
}
