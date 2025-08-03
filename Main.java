// Team Name: Team no sleep 
// Student Numbers: 4281148, 4154846
// File Name: Main.java
// DB_Practical 1

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        String inputFile = "file.txt";    // Input file with data
        String absPathInput = Paths.get(inputFile).toAbsolutePath().toString();


        String outputFile = "file2.txt";    // Output file to save results
        String absPathOutput = Paths.get(outputFile).toAbsolutePath().toString();


        ArrayList<String[]> data = new ArrayList<String[]>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(absPathInput));
            String line;
            boolean firstLine = true;

            // Read and store data (skip header)
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                data.add(parts);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(absPathOutput));

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
            ArrayList<String> seenCities = new ArrayList<>();
            for (String[] entry : data) {
                try {
                    
                    if (seenCities.contains(entry[0].trim())) {
                        continue; // Skip if the city has already been processed
                    }

                    String city = entry[0];
                    int pop = Integer.parseInt(entry[1]);
                    cityList.add(new String[]{city, String.valueOf(pop)});
                    seenCities.add(city); // Mark this city as seen
                    
                } catch (Exception ignored) {}
            }
            cityList.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));

            bw.write("Question b:\nTop 5 cities by population:\n");
            for (int i = 0; i < 5; i++) {
                bw.write(cityList.get(i)[0] + " - " + cityList.get(i)[1] + "\n");
            }
            bw.write("\n");

           //Question c: Listing five countries by the largest land mass
            bw.write("Question c:\nTop 5 countries by the largest land mass:\n");
            int landMass = 0;
            String country1 = "";
            ArrayList<String[]> countryList = new ArrayList<>();
            ArrayList<String> seenCountries = new ArrayList<>();
            for (String[] entry : data) {

                 if (seenCountries.contains(entry[2])) {
                    continue; // Skip if the country has already been processed
                } 
                
                country1 = entry[2];

                try {
                    landMass = Integer.parseInt(entry[5]);
                } catch (NumberFormatException e) {
                    landMass = 0; // Default to 0 if parsing fails
                }


                countryList.add(new String[]{country1, String.valueOf(landMass)});

                seenCountries.add(country1); // Mark this country as seen

               
            }
            countryList.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
            for (int i = 0; i < 5; i++) {
                bw.write(countryList.get(i)[0] + " - " + countryList.get(i)[1] + "\n");
            } 
            bw.write("\n");

            
            //Question d: How many countries gained independence between the years 1960 and 1980 (inclusive)
            bw.write("Question d) How many countries gained independence between the years 1960 and 1980 (inclusive):\n");
            
            ArrayList<String> independentCountries = new ArrayList<>();
            for (String[] entry : data){
                 try{
                    String country = entry[2].trim();
                    int year = Integer.parseInt(entry[6].trim());//Independece year
                    
                    //Check if year is in range and not already in the list
                    if(year >= 1960 && year <= 1980){
                        if(!independentCountries.contains(country)){
                            independentCountries.add(country);
                        }
                    }
                }catch (NumberFormatException e) {
                    // Skip entries with invalid year
                }
            }

            bw.write("Number of countries: " + independentCountries.size() + "\n\n");
            
            //Question e: Which countries gained independence between the years 1830 and 1850 (inclusive)
            bw.write("Question e) Which countries gained independence between the years 1830 and 1850 (inclusive):\n");
            ArrayList<String> independentCountries2 = new ArrayList<>();
            for (String[] entry : data){
                 try{
                    String country = entry[2].trim();
                    int year = Integer.parseInt(entry[6].trim());

                    //Check if year is in range and not already in the list
                    if(year >= 1830 && year <= 1850){
                        if(!independentCountries2.contains(country)){
                            independentCountries2.add(country);
                        }
                    }
                }catch (NumberFormatException e) {
                    // Skip entries with invalid year
                }
            }
            //Write the countries
            for (String country : independentCountries2){
                bw.write(country + "\n");
            }

            bw.write("\n");
            
            //Question f: Top 5 American countries with the highest life expectancy 
            bw.write("Question f:\nTop 5 American countries with the highest life expectancy:\n");
            ArrayList<String[]> americanCountries = new ArrayList<String[]>();
            ArrayList<String> seenAmericanCountries = new ArrayList<>();
            String continent = "";
            String country2 = "";
            int lifeExpectancy = 0;
            for (String[] entry : data) {
                country2 = entry[2].trim();
                continent = entry[3].trim();

                if (seenAmericanCountries.contains(country2)) {
                    continue; // Skip if the country has already been processed
                }

                if (continent.equals("North America") || continent.equals("South America")) {
                    try {
                        lifeExpectancy = Integer.parseInt(entry[8]);
                    } catch (NumberFormatException e) {
                        // Skip if life expectancy is not a valid number
                        lifeExpectancy = 0; // Default to 0 if parsing fails
                    }
                    americanCountries.add(new String[]{country2, String.valueOf(lifeExpectancy)});

                }
                seenAmericanCountries.add(country2); // Mark this country as seen
            }
            americanCountries.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
            for (int i = 0; i < 5; i++) {
                bw.write(americanCountries.get(i)[0] + " - " + americanCountries.get(i)[1] + "\n");
            } 
            bw.write("\n");

            //  Question h: Unique country names ending with 'a' 
            bw.write("Question h:\nUnique country names ending with 'a':\n");
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

            // Print confirmation message to the console
            System.out.println("All questions completed. Results saved to file2.txt.");

            // Print absolute paths of input and output files for testing purposes 
            System.out.println("Absolute path of input file (file.txt): " + absPathInput);
            System.out.println("Absolute path of output file (file2.txt): " + absPathOutput);


        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
