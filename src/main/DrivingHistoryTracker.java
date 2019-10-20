package main;

import java.io.*;
import java.util.*;

public class DrivingHistoryTracker {

    private static final int DRIVER_LINE_LENGTH = 2;
    private static final int TRIP_LINE_LENGTH = 5;

    public static void generateDrivingHistory(String filePath) {
        // Example of file data
        /* Driver Dan
           Driver Lauren
           Driver Kumi
           Trip Dan 07:15 07:45 17.3
           Trip Dan 06:12 06:32 21.8
           Trip Lauren 12:01 13:16 42.0
         */
        File file = new File(filePath);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file);
            return;
        }
        // tripMap saves Trip object for each driver:
        // key - driver name
        // value - Trip object with driving history for this driver
        HashMap<String, Trip> tripMap = new HashMap<>();
        // TreeMap orderMap used to order drivers per miles traveled
        // Key is number of miles, and value is name of driver ( List is used in order to allow
        // multiple drivers having the same number of miles traveled
        TreeMap<Integer, List<String>> orderMap = new TreeMap<>();
        String line;

        while (true) {
            try {
                if((line = br.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                System.out.println("I/OError!!!");
                return;
            }
            // parse input line
            String[] fields = line.split(" ");

            // Validate and store input data
            // Each line should have 2 or 5 fields. Empty line is ok
            if (fields.length == DRIVER_LINE_LENGTH) {
                if ( fields[0].equals("Driver"))
                    tripMap.put(fields[1], new Trip(fields[1]));
                else
                    System.out.println("Invalid input data. Expected command Driver: " + line);
            }
            else if (fields.length == TRIP_LINE_LENGTH) {
                // Get Trip object for this driver and addTrip passing StartTime, EndTime and Miles
                if ( fields[0].equals("Trip")) {
                    Trip currentTrip = tripMap.get(fields[1]);
                    currentTrip.addTrip(fields[2], fields[3], fields[4]);
                }
                else
                    System.out.println("Invalid input data. Expected command Trip: " + line);
            }
            else if(!line.equals("") )
                System.out.println("Invalid input: " + line);
        }

        // Loop through the tripMap, and for each element insert pair (miles, List names) in orderMap
        for (Map.Entry<String, Trip> entry : tripMap.entrySet()) {
            int miles = entry.getValue().getMiles();
            List<String> value = orderMap.get(miles);
            if (value == null) {
                value = new ArrayList<>();
            }
            value.add(entry.getKey());
            orderMap.put(miles, value);
        }
        // Print output in decreasing order of miles driven
        ArrayList<Integer> keys = new ArrayList<>(orderMap.keySet());
        for (int i=keys.size()-1; i>=0;i--) {
            List<String> values = orderMap.get(keys.get(i));
            for( String name: values) {
                Trip trip = tripMap.get(name);
                StringBuilder resultStr = new StringBuilder(name);
                resultStr.append(" : ");
                resultStr.append(trip.getMiles());
                resultStr.append(" miles");
                if (trip.getMiles() > 0) {
                    resultStr.append(" @ ");
                    resultStr.append(trip.getAvgSpeed());
                    resultStr.append(" mph");
                }
                System.out.println(resultStr.toString());
            }
        }
    }
}
