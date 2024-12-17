package HW5;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadMatrix {


    public static int[][] parseSquareMatrix(String filename, String regexFilter) {
        ArrayList<Integer> values = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(!line.isEmpty()){

                    line = line.replaceAll(regexFilter, " ");

                    String[] raw = line.split("\\s");
                    for(String s : raw){
                        if(s.isEmpty()) continue;
                        values.add(Integer.parseInt(s));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("File not found");
        }

       
        int size = (int) Math.sqrt(values.size());
        int[][] matrix = new int[size][size];
        for (int i = 0; i < values.size(); i++) {
            int row = i / size;
            int col = i % size;
            matrix[row][col] = values.get(i);
        }

        return matrix;
    }
}