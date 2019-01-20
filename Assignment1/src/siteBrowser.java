import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class to represent the forwards and backwards function that is present
 * in most modern browsers
 */
public class siteBrowser {

    /**
     * Simulates browsing with the use of two stacks
     * @param N length of input (not used in this implementation)
     * @param input a list of commands to be handled in this method
     */
    private static void browse(int N, List<String> input) {
        //remove index number
        input.remove(0);
        Stack<String> backStack = new Stack<>(), forwardStack = new Stack<>();
        
        for (String value : input) {
            if (value.equals("*back*")) {
                if (backStack.size() < 2) {
                    System.out.println("[Warning: end of history]");
                    continue;
                }
                forwardStack.push(backStack.pop());
                //currently at this page
                System.out.println(backStack.peek());
            } else if (value.equals("*forward*")) {
                if (forwardStack.isEmpty()) {
                    System.out.println("[Warning: last website]");
                    continue;
                }
                System.out.println(backStack.push(forwardStack.pop()));
            } else {
                //visiting a new page removes history for forward-movement
                forwardStack.removeAllElements();
                System.out.println(backStack.push(value));
            }
        }
    }

    /**
     * Read commands from a txt file packaged with this class
     * @param args
     */
    public static void main(String[] args) {
        try {
            List<String> list = Files.readAllLines(Paths.get("res/browseInput.txt"));
            browse(Integer.parseInt(list.get(0)), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
