import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * This is an alternative implementation of exactTriplicates
 * that works for lists that contain no duplicates.
 * Made by Olav Gjerde (ogj005)
 */
public class exactTriplicatesV2 {
    private static String findName(List<String> listA) {
        Collections.sort(listA);
        
        int nameCounter = 0;
        String lastName = "";
        
        for (int i = 0; i < listA.size(); i++) {
            if (lastName.equals(listA.get(i))){
                nameCounter++;
                
                //Edgecase count last one
                if (i == listA.size() - 2) {
                    if (lastName.equals(listA.get(listA.size()-1))) nameCounter++;
                    if (nameCounter == 3) return listA.get(i);
                }
                
                if(nameCounter == 3 && !listA.get(i+1).equals(lastName)){
                    return listA.get(i);
                }
            } else {
                lastName = listA.get(i);
                nameCounter = 1;
            }
        }
        
        return "None found";
    }

    public static void main(String[] args) {
        try {
            List<String> list1 = Files.readAllLines(Paths.get("listA.txt"));
            list1.addAll(Files.readAllLines(Paths.get("listB.txt")));
            list1.addAll(Files.readAllLines(Paths.get("listC.txt")));
            list1.addAll(Files.readAllLines(Paths.get("listD.txt")));
            String name = findName(list1);
            System.out.println("Triplicate: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
