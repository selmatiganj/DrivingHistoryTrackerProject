package test;

import main.Trip;
import org.junit.Assert;
import org.junit.Test;

public class TripTest {

    @Test
    public void testAddTrip(){
        String Name = "Kenny";
        String StartTime = "06:22";
        String EndTime = "06:58";
        String Distance = "20";
        Trip testTrip = new Trip(Name);
        testTrip.addTrip(StartTime, EndTime, Distance);

        Assert.assertEquals(Name, testTrip.getName());
        Assert.assertEquals(20, testTrip.getMiles());
        Assert.assertEquals(33, testTrip.getAvgSpeed());
    }

    @Test
    public void testAddMultipleTrips(){
        String Name = "Kenny";
        String StartTime1 = "06:22";
        String EndTime1 = "06:58";
        String Distance1 = "20";
        String StartTime2 = "12:00";
        String EndTime2 = "14:08";
        String Distance2 = "50";
        Trip testTrip = new Trip(Name);
        testTrip.addTrip(StartTime1, EndTime1, Distance1);
        testTrip.addTrip(StartTime2, EndTime2, Distance2);

        Assert.assertEquals(Name, testTrip.getName());
        Assert.assertEquals(70, testTrip.getMiles());
        Assert.assertEquals(26, testTrip.getAvgSpeed());
    }

    @Test
    public void testAddTripsSpeedLeesThan5MoreThan100(){
        String Name = "Kenny";
        String StartTime1 = "06:22";
        String EndTime1 = "06:58";
        String Distance1 = "20";
        String StartTime2 = "12:00";
        String EndTime2 = "14:08";
        String Distance2 = "8";
        String StartTime3 = "14:00";
        String EndTime3 = "14:08";
        String Distance3 = "20";
        Trip testTrip = new Trip(Name);
        testTrip.addTrip(StartTime1, EndTime1, Distance1);
        testTrip.addTrip(StartTime2, EndTime2, Distance2);
        testTrip.addTrip(StartTime3, EndTime3, Distance3);

        Assert.assertEquals(Name, testTrip.getName());
        Assert.assertEquals(20, testTrip.getMiles());
        Assert.assertEquals(33, testTrip.getAvgSpeed());
    }

    @Test
    public void testAddTripInvalidTimeValue(){
        String Name = "Kenny";
        String StartTime1 = "06:22";
        String EndTime1 = "invalid";
        String Distance1 ="20";

        Trip testTrip = new Trip(Name);
        testTrip.addTrip(StartTime1, EndTime1, Distance1);

        Assert.assertEquals(Name, testTrip.getName());
        Assert.assertEquals(0, testTrip.getMiles());
        Assert.assertEquals(0, testTrip.getAvgSpeed());
    }
}
