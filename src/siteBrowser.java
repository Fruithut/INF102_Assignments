import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class siteBrowser {
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
    
    public static void main(String[] args) {
        try {
            List<String> list = Files.readAllLines(Paths.get("browseInput.txt"));
            browse(Integer.parseInt(list.get(0)), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
