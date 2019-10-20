package main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {

    private String Name;
    // Save total time of trips in minutes
    private double TotalTime;
    private double TotalMiles;

    public Trip(String name) {
        this.Name = name;
        this.TotalTime = 0;
        this.TotalMiles = 0;
    }

    public void addTrip (String startTime, String endTime, String miles) {

        // Calculate trip duration using startTime and endTime
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat( "HH:mm");
            Date parsedStartDate = dateFormat.parse(startTime);
            Date parsedEndDate = dateFormat.parse(endTime);
            // obtain elapsed time in milliseconds
            double tripDurationMs = parsedEndDate.getTime() - parsedStartDate.getTime();
            double tripDurationMin = tripDurationMs/60000;
            float milesF = Float.parseFloat(miles);
            double milesD = Math.round(milesF);
            float avgSpeed = 0;
            if ( tripDurationMin !=0 )
                avgSpeed = (float) (milesD*60/tripDurationMin);

            // Discard any trips that average a speed of less than 5 mph or greater than 100 mph.
            if ((avgSpeed < 5) || (avgSpeed > 100))
                return;

            this.TotalTime += tripDurationMin;
            this.TotalMiles += milesD;
        } catch(Exception e) {
            System.out.println(" Error processing input data. Invalid time.");
        }
    }

    public String getName() {
        return this.Name;
    }

    public int getMiles() {
        return (int)this.TotalMiles;
    }

    public int getAvgSpeed() {
        if (this.TotalTime != 0) {
            float result = (float) ((this.TotalMiles * 60) / this.TotalTime);
            return Math.round(result);
        }
        return 0;
    }
}
