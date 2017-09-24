import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class exactTriplicates {
    private static String findName(List<String> listA, List<String> listB, List<String> listC, List<String> listD) {
        //Mergesort lists <= N log N
        Collections.sort(listA);
        Collections.sort(listB);
        Collections.sort(listC);
        Collections.sort(listD);
        
        String candidateOne = null, candidateTwo = null;
        boolean A, B, C, D;
        
        //each binarySearch <= log N accesses
        //combinations ABC(notD), ABD(notC), ACD(notB)
        for (String aValue : listA) {
            B = false; C = false; D = false;
            if(Collections.binarySearch(listB, aValue) > -1) B = true;
            if(Collections.binarySearch(listC, aValue) > -1) C = true;
            if(Collections.binarySearch(listD, aValue) > -1) D = true;
            
            if (B && C && !D) {
                candidateOne = aValue;
                break;
            } else if (B && D && !C) {
                candidateOne = aValue;
                break;
            } else if (C && D && !B) {
                candidateOne = aValue;
                break;
            }
        }
        //last combination BCD(notA)
        for (String bValue : listB) {
            A = false; C = false; D = false;
            if (Collections.binarySearch(listC, bValue) > -1) C = true;
            if (Collections.binarySearch(listD, bValue) > -1) D = true;
            if (Collections.binarySearch(listA, bValue) > -1) A = true;
            
            if (C && D && !A) {
                candidateTwo = bValue;
                break;
            }
        }
        
        if (candidateOne == null && candidateTwo == null) return "None found";
        else if (candidateOne == null) return candidateTwo;
        else if (candidateTwo == null) return candidateOne;
        
        if (candidateOne.compareTo(candidateTwo) > 0) return candidateTwo;
        return candidateOne;
    }

    public static void main(String[] args) {
        try {
            List<String> list1 = Files.readAllLines(Paths.get("listA.txt"));
            List<String> list2 = Files.readAllLines(Paths.get("listB.txt"));
            List<String> list3 = Files.readAllLines(Paths.get("listC.txt"));
            List<String> list4 = Files.readAllLines(Paths.get("listD.txt"));
            String name = findName(list1, list2, list3, list4);
            System.out.println("Triplicate: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
