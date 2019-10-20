package test;

import main.DrivingHistoryTracker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DriverHistoryTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void testGenerateDrivingHistory(){

        String testFile = "src/test/DrivingHistoryData.txt";

        DrivingHistoryTracker.generateDrivingHistory(testFile);

        String resultStr = "Lauren : 42 miles @ 34 mph\r\n" +
                "Dan : 39 miles @ 47 mph\r\n" +
                "Kumi : 0 miles\r\n";
        Assert.assertEquals(resultStr, outContent.toString());
    }

    @Test
    public void testGenerateDrivingHistorySameMilesForMultipleDrivers(){

        String testFile = "src/test/DrivingHistoryDataSameMiles.txt";

        DrivingHistoryTracker.generateDrivingHistory(testFile);

        String resultStr = "Lauren : 42 miles @ 34 mph\r\n" +
                "Jason : 42 miles @ 34 mph\r\n" +
                "Dan : 39 miles @ 47 mph\r\n" +
                "Ben : 39 miles @ 47 mph\r\n" +
                "Bob : 0 miles\r\n" +
                "Kumi : 0 miles\r\n";
        Assert.assertEquals(resultStr, outContent.toString());
    }

    @Test
    public void testGenerateDrivingHistoryNoFileError(){
        String testFile = "Error.txt";

        DrivingHistoryTracker.generateDrivingHistory(testFile);
        String resultStr = "File not found: Error.txt\r\n";

        Assert.assertEquals(resultStr, outContent.toString());
    }

    @Test
    public void testGenerateDrivingHistoryIOError() {
        String testFile = "src/test/ErrorEmptyFile.txt";

        DrivingHistoryTracker.generateDrivingHistory(testFile);
        String resultStr = "";
        Assert.assertEquals(resultStr, outContent.toString());
    }

    @Test
    public void testGenerateDrivingHistoryDataError() {

        String testFile = "src/test/ErrorFile.txt";

        DrivingHistoryTracker.generateDrivingHistory(testFile);
        String resultStr = "Invalid input: Test Error Driver\r\n" +
        "Invalid input: Driver Error Test\r\n" +
        "Invalid input data. Expected command Driver: Trip Error\r\n" +
        "Invalid input data. Expected command Trip: Error Trip 9:50 10:50 60\r\n" +
        "Lara : 90 miles @ 60 mph\r\n" +
        "Lauren : 42 miles @ 34 mph\r\n" +
        "Bob : 39 miles @ 47 mph\r\n" +
        "Ben : 0 miles\r\n";
        Assert.assertEquals(resultStr, outContent.toString());
    }
}
