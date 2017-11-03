package connectingToilets;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.PrimMST;
import graphics.Svg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Olav Gjerde on 03.11.2017
 * A class for solving a minimum spanning tree problem where
 * we want to find all edges which is able to connect all toilet
 * objects presented from input using the least amount of "pipes" (min. length)
 */
public class ConnectingToilets {

    private final static String TOILET_FILE = "connectingToilets/bergen_toilet_map.txt";

    public static void main(String[] args) {
        Set<Toilet> toilets = readToiletsFromFile(TOILET_FILE);
        ToiletMap mapOfToilets = new ToiletMap(toilets, connectToilets(toilets));
        Svg.runSVG(Svg.buildSvgFromScienceEmployees(mapOfToilets));
    }

    static Set<Toilet> readToiletsFromFile(String toiletFile) {
        List<String> lines = readLines(TOILET_FILE);
        if (lines == null) {
            System.out.print("An error ocurred trying to read " + TOILET_FILE + ". Check that the file exist.");
        }
        return lines.stream().map(ConnectingToilets::lineToToilet).collect(Collectors.toSet());
    }

    /**
     * Gets a set of toilets that contain a name and a (x,y)-coordinate
     * Method fins all possible edges from each toilet and constructs
     * an edge weighted graph with this information.
     * Prim's algorithm is used to find a minimum spanning tree on the
     * graph constructed earlier, this gives us all the edges that will
     * connect the toilet objects with the minimum amount of "length".
     *
     * @param toilets a set of all toilets which one wants to connect
     * @return edges to connect "toilet" objects
     */
    static Set<Edge> connectToilets(Set<Toilet> toilets) {
        HashMap<Toilet, Integer> toiletToInteger = new HashMap<>();
        HashMap<Integer, Toilet> integerToToilet = new HashMap<>();

        //constructs a bidirectional "hashmap"-structure
        int intRepresentation = 0;
        for (Toilet x : toilets) {
            toiletToInteger.put(x, intRepresentation);
            integerToToilet.put(intRepresentation, x);
            intRepresentation++;
        }
        //construct all possible edges
        ArrayList<Edge> allEdges = new ArrayList<>();
        for (Toilet i : toilets) {
            for (Toilet j : toilets) {
                if (i.equals(j)) break;
                allEdges.add(new Edge(i, j, euclideanDistance(i, j)));
            }
        }
        //add all toilet-edges with corresponding "length/weight" to a edge weighted graph
        EdgeWeightedGraph wGraph = new EdgeWeightedGraph(toilets.size());
        for (Edge toiletEdge : allEdges) {
            int aVertex = toiletToInteger.get(toiletEdge.getToiletA());
            int bVertex = toiletToInteger.get(toiletEdge.getToiletB());
            double weight = toiletEdge.getLength();
            wGraph.addEdge(new edu.princeton.cs.algs4.Edge(aVertex, bVertex, weight));
        }
        //find the edges that connect all toilets with the minimum amount of "length/weight"
        PrimMST minSpanningTree = new PrimMST(wGraph);

        //constructs a set of edges that contain (toiletA, toiletB, length)
        Set<Edge> MST = new HashSet<>();
        for (edu.princeton.cs.algs4.Edge orgEdge : minSpanningTree.edges()) {
            Toilet toiletA = integerToToilet.get(orgEdge.either());
            Toilet toiletB = integerToToilet.get(orgEdge.other(orgEdge.either()));
            double length = orgEdge.weight();
            MST.add(new Edge(toiletA, toiletB, length));
        }
        return MST;
    }

    /**
     * Calculates the distance between two (x,y) coordinates given two Toilet-objects
     *
     * @param a toilet a
     * @param b toilet b
     * @return the distance two (x,y) in format; double
     */
    private static double euclideanDistance(Toilet a, Toilet b) {
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    private static Toilet lineToToilet(String line) {
        String[] fields = line.split(";");
        String name = fields[0];
        double x = Double.parseDouble(fields[1]);
        double y = Double.parseDouble(fields[2]);
        return new Toilet(name, x, y);
    }

    private static List<String> readLines(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream resource = classloader.getResourceAsStream(fileName)) {
            if (resource == null) {
                System.out.println("File is missing!");
                return null;
            }
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
