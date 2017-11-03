package maze;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import graphics.Svg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Olav Gjerde on 02.11.2017
 */
public class MazeSolver {

    private final static String MAZE_FILE = "maze/maze.txt"; // or maze.txt

    /**
     * Solves the maze
     *
     * @return A list of points showing where the player needs to go to reach the end.
     * The points should be in the correct order, meaning the first point in the
     * list should be next to the player.
     */
    public static List<Point> solve(Maze m) {
        Set<Point> walls = m.getWalls();
        //finds all possible coordinates for movement; false = not wall
        boolean[][] paths = new boolean[m.getHeight()][m.getWidth()];
        for (Point x : walls) {
            paths[x.y][x.x] = true;
        }

        int totalPoints = 0;
        //hashmaps as a Bidirectional-map, maps coordinates to a integer and the opposite
        HashMap<Point, Integer> positionToNumber = new HashMap<>();
        HashMap<Integer, Point> numberToPosition = new HashMap<>();
        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths[i].length; j++) {
                if (!paths[i][j]) {
                    positionToNumber.put(new Point(j, i), totalPoints);
                    numberToPosition.put(totalPoints, new Point(j, i));
                    totalPoints++;
                }
            }
        }
        Graph graph = new Graph(totalPoints);

        //i = y-axis and j = x-axis
        //does a run through of the maze and connects every point to those around it
        //in the order: look ahead (connect), look above (connect)
        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths[i].length; j++) {
                Point thisPoint = new Point(j, i);
                Point forwardPoint = new Point(j + 1, i);
                Point abovePoint = new Point(j, i - 1);

                if (j + 1 < paths[i].length) {
                    if (!paths[i][j] && !paths[i][j + 1]) {
                        graph.addEdge(positionToNumber.get(thisPoint), positionToNumber.get(forwardPoint));
                    }
                }

                if (i - 1 >= 0) {
                    if (!paths[i][j] && !paths[i - 1][j]) {
                        graph.addEdge(positionToNumber.get(thisPoint), positionToNumber.get(abovePoint));
                    }
                }
            }
        }

        //use breadth first search to find the shortest path in the graph constructed earlier
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, positionToNumber.get(m.getPlayer()));
        Iterable<Integer> pointsToEnd = bfs.pathTo(positionToNumber.get(m.getGoal()));

        List<Point> pathToGoal = new ArrayList<>();
        for (Integer x : pointsToEnd) {
            pathToGoal.add(numberToPosition.get(x));
        }

        pathToGoal.remove(m.getPlayer());
        return pathToGoal;
    }

    public static void main(String[] args) {
        Maze maze = new Maze(MAZE_FILE);
        String finalHtmlContent = Svg.buildSvgFromMaze(maze);
        Svg.runSVG(finalHtmlContent);
    }
}