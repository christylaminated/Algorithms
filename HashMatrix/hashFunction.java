package HW5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class hashFunction {
    public static void main(String[] args) throws FileNotFoundException{
        int C = 123;
        int m = 1000;
        //System.out.println("Working Directory: " + System.getProperty("user.dir"));
        Scanner console = new Scanner(System.in);

        System.out.print("File name: ");
        String filePath = console.nextLine(); 
        File file = new File(filePath);
        //System.out.println("File exists: " + file.exists());
        //System.out.println("Absolute Path: " + file.getAbsolutePath());
        System.out.print("Enter the size of the hash table (m): ");
        m = console.nextInt();
        String[] hashtable = new String[m]; // Closed hash table with open addressing
        int drifted = 0;
        String driftword ="";
        int maxdrift = 0;
        int hashdriftstart = 0;
        try (Scanner input = new Scanner(file)) {
            input.useDelimiter("[^a-zA-Z'-]+");
            while (input.hasNext()){
                int drift=0;
                int hashValue = 0;
                String word = input.next();
                //System.out.print(word + " ");
                for (int i=0; i<word.length(); i++){
                    hashValue = (hashValue * C + (int)word.charAt(i)) % m;
                }
                boolean duplicate = false;
                if(hashtable[hashValue]==null){
                    drifted++;
                }
                while (hashtable[hashValue]!=null){
                    if (hashtable[hashValue].equals(word)) {
                        duplicate = true;
                        break; // Skip insertion for duplicates
                    }                
                    if (hashValue == m-1){
                        hashValue = 0;
                    }
                    else{
                        if (!hashtable[hashValue].equals(word)){
                            //if cell is not empty and its not the same word
                            hashValue ++;
                            drift++;
                        }
                    }
                }
                if(drift>maxdrift){
                    maxdrift = drift;
                    drift = 0;
                    driftword = word;
                    hashdriftstart = hashValue;
                }
                if (duplicate == false){
                    hashtable[hashValue] = word;
                }
            }
        }
        /*for (int i=0; i<hashtable.length; i++){
            if (hashtable[i] != null){
                System.out.println(i + " " + hashtable[i]);
            }else{
                System.out.println(i + "-1");
            }
        }*/
        int totalnonempty = 0;
        int maxempty = 1;
        int countempty = 0;
        ArrayList<Integer> maxindicies = new ArrayList<Integer>();

        int cluster = 0;
        int maxcluster = 0;
        ArrayList<Integer> clusterIndex = new ArrayList<Integer>();
        
        int maxHash=0;
        ArrayList<Integer> originalHash = new ArrayList<Integer>();
        for (int i = 0; i < hashtable.length; i++) {
            if (hashtable[i] != null) {
                totalnonempty++;
                cluster++;
                // Compute the original hash value for the word
                int originalHashValue = 0;
                String word = hashtable[i];
                for (int j = 0; j < word.length(); j++) {
                    originalHashValue = (originalHashValue * C + (int) word.charAt(j)) % m;
                }
                // Print the hash address, hashed word, and original hash value
                System.out.println(i + " " + word + " " + originalHashValue);
                originalHash.add(originalHashValue);
                if (countempty>maxempty){
                    maxempty = countempty;
                    maxindicies.clear();
                    maxindicies.add(i-1);
                }
                else if(countempty == maxempty){
                    maxindicies.add(i-1);
                }
                countempty = 0;
            } else {
                if(cluster>maxcluster){
                    maxcluster = cluster;
                    clusterIndex.clear();
                    clusterIndex.add(i-1);
                }
                else if(cluster == maxcluster){
                    clusterIndex.add(i-1);
                }
                cluster = 0;
                // Empty slots can be indicated if necessary
                System.out.println(" " + i + " -1");
                countempty ++;
            }
        }
        boolean checkWrapAround = false;
        int wraparound = 0;
        int wrapstart =0;
        int wrapend = 0;
        if(hashtable[m-1]!=null && hashtable[0]!=null){
            checkWrapAround = true;
            for (int i=m-1; i>0; i--){
                if(hashtable[i]!=null){
                    wraparound++;
                }
                else{
                    wrapend = i;
                    break;
                }
            }
            for (int i=0; i<m-1; i++){
                if(hashtable[i]!= null) {
                    wraparound++;
                } 
                else {
                    wrapstart = i-1;
                    break;
                }
            }
        }
        System.out.println("finished");
        System.out.println("done done done\n");
        double loadFactor = (double)totalnonempty/m;
        System.out.println("There are " + totalnonempty + " distrinct entries in the hash table.");
        System.out.println("Of these, " + drifted + " are in their appropriate spots, i.e without drifting.");
        System.out.println("There are " + (m-totalnonempty) + " open cells in the hash table.");
        System.out.println("The load factor (alpha) is thus " + loadFactor + " for our hash table.");
        //longest empty area in the table and where is it
        System.out.println("The longest run of open cells is " + maxempty + " entries long: ");
        for (int i=0; i< maxindicies.size(); i++){
            System.out.println("Position " + (maxindicies.get(i) - maxempty+1) + " to position " + maxindicies.get(i) + " (inclusive)");
        }
        System.out.println("The longest cluster is " + maxcluster + " entries long: ");
        for (int i=0; i<clusterIndex.size(); i++){
            System.out.println("Position " + (clusterIndex.get(i) - maxcluster+1) + " to position " + clusterIndex.get(i) +" (inclusive)");
        }
        if (checkWrapAround == true){
            System.out.println("Position " + (wrapend+1) + " wraps around to position " + wrapstart + " (inclusive) for a length of " +wraparound);
        }
        int result[] = findMostFrequentValue(originalHash);
        System.out.println("Hash value " + result[1] + " occurs " + result[0] + " times.");
        System.out.println("The word '" + driftword +"' drifts more than any other word, " + maxdrift + " places from the hash address " + (hashdriftstart - maxdrift) + " all the way to position " + hashdriftstart);

    }
    public static int[] findMostFrequentValue(ArrayList<Integer> list) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        // Count the frequency of each value
        for (int value : list) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        // Find the value with the highest frequency
        int maxFrequency = 0;
        int mostFrequentValue = -1; // Default if list is empty
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentValue = entry.getKey();
            }
        }

        return new int[]{maxFrequency,mostFrequentValue};


}
}
