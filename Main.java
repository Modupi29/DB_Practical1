// Team Name: DataSquad
// Student Numbers: 3711511, 3805949, 3800363
// File Name: Main.java
// DB_Practical 1

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFile = "file.txt";      // Input file with data
        String outputFile = "file2.txt";    // Output file to save results
        ArrayList<String[]> data = new ArrayList<String[]>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            boolean firstLine = true;

            // Read and store data (skip header)
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length < 15) continue;
                data.add(parts);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

            //  Question a: Countries ending with 'a' 
            int countEndsWithA = 0;
            boolean duplicate = true;
            String temp = "";
            for (String[] entry : data) {
               
                if( temp.equals(entry[2].trim())) {
                    duplicate = true; // If the country is the same as the last one, skip it
                } else {
                    duplicate = false; // Reset duplicate flag if a new country is found
                }

                if (duplicate) {
                    duplicate = false;
                    continue;
                }

                String country = entry[2].trim();
                if (country.charAt(country.length() - 1) == 'a') {
                    countEndsWithA++;
                }
                temp = country;
                duplicate = true; // Reset for the next iteration
            }
            bw.write("Question a:\n");
            bw.write("Number of countries ending with 'a': " + countEndsWithA + "\n\n");


            //  Question b: Top 5 cities by population 
            ArrayList<String[]> cityList = new ArrayList<>();
            int temp1 = 0; // Temporary variable to track duplicates
            boolean duplicatePop = true; // Flag to track duplicates
            for (String[] entry : data) {
                try {
                    if (temp1 == Integer.parseInt(entry[1])) {
                        duplicatePop = true; // If the population is the same as the last one, skip it
                    } else {
                        duplicatePop = false; // Reset duplicate flag if a new population is found
                    }

                    if (duplicatePop) {
                        duplicatePop = false;
                        continue;
                    }

                    String city = entry[0];
                    int pop = Integer.parseInt(entry[1]);
                    cityList.add(new String[]{city, String.valueOf(pop)});
                    temp1 = pop; // Update temp1 to the current population
                    duplicatePop = true; // Reset for the next iteration
                } catch (Exception ignored) {}
            }
            cityList.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));

            bw.write("Question b:\nTop 5 cities by population:\n");
            for (int i = 0; i < 5; i++) {
                bw.write(cityList.get(i)[0] + " - " + cityList.get(i)[1] + "\n");
            }
            bw.write("\n");


            //  Question h: Unique country names ending with 'a' 
            bw.write("Question h:\n Unique country names ending with 'a':\n");
            String temp2 ="";
            boolean unique = true; // Flag to track duplicates
            for (String[] entry : data) {

                if( temp2.equals(entry[2].trim())) {
                    unique = true; // If the country is the same as the last one, skip it
                } else {
                    unique = false; // Reset duplicate flag if a new country is found
                }

                if (unique) {
                    unique = false;
                    continue;
                }

                String country = entry[2].trim();
                if (country.charAt(country.length() - 1) == 'a') {
                bw.write(country + "\n");
                }
                temp2 = country;
                unique = true; // Reset for the next iteration
            }
            bw.write("\n");
            bw.close();

            
            System.out.println("All questions completed. Results saved to file2.txt.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
