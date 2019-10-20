
DrivingHistoryTracker project is written in Java (jdk-12.0.2) and provides solution for tracking driving history,
and generating report with total miles driven and average speed for each driver.

Requirements:
	Each line in the input file will start with a command. There are two possible commands.
	The first command is Driver, which will register a new Driver in the app. Example:
	Driver Dan
	The second command is Trip, which will record a trip attributed to a driver. The line will be space delimited 
	with the following fields: the command (Trip), driver name, start time, stop time, miles driven. Times will be
 	given in the format of hours:minutes. We'll use a 24-hour clock and will assume that drivers never drive past 
	midnight (the start time will always be before the end time). Example:
	Trip Dan 07:15 07:45 17.3
	Discard any trips that average a speed of less than 5 mph or greater than 100 mph.
	Generate a report containing each driver with total miles driven and average speed. Sort the output by most miles
 	driven to least. Round miles and miles per hour to the nearest integer.
	Example input:
	Driver Dan
	Driver Lauren
	Driver Kumi
	Trip Dan 07:15 07:45 17.3
	Trip Dan 06:12 06:32 21.8
	Trip Lauren 12:01 13:16 42.0
	Expected output:
	Lauren: 42 miles @ 34 mph
	Dan: 39 miles @ 47 mph
	Kumi: 0 miles

Assumptions:
	Validation of file data is mostly limited to the information given in requirements.
        Input file is validated for the following errors:
		- In case file not found print error: "File not found: <file name>"
		- If number of space delimited fields is not 2 or 5 ( except for empty line) print error:
			"Invalid input: <line>"
		- In case when line has 2 space delimited fields but command (first filed) is not
			"Driver" print error: "Invalid input data. Expected command Driver: <line>"
		- In case when line has 5 space delimited fields but command (first filed) is not
			"Trip" print error: "Invalid input data. Expected command Trip: <line>"	
		- in case when time fields have unexpected value ( non numerical ) print error:
			"Error processing input data. Invalid time."	
	For cases when error was found as line of file is processed, line with error will be skipped, and
	processing will continue.
Input/Output: 
	To run program from command line:
	java -jar DrivingHistory.jar <dataFile.txt>
	Example:
	>java -jar DrivingHistory.jar DrivingHistoryData.txt
	Lauren : 42 miles @ 34 mph
	Dan : 39 miles @ 47 mph
	Kumi : 0 miles

Design:

Class DrivingHistoryTracker
	Provides methods for reading input file, processing data, generating report printed as stdout.
	Uses Trip class for driver's data processing.

	Methods:
	public static void generateDrivingHistory(String filePath) - 
		Parses data from input file, and stores driving information for each driver in instance of 
		Trip class.
		HasMap tripMap<String, Trip> is used to hold Trip objects per driver so it can be updated 
		each time new "Trip" command is parsed for driver. Using HashMap to be able to directly access
		each driver's Trip object ( HasMap access time is O(1) ).

		Sorts data per miles driven by looping trough tripMap and inserting pair (miles, List names).
		in TreeMap orderMap ( selected TreeMap as it guarantees that its elements will be sorted in 
		an ascending key order. Insertion speed for TreeMap O(nlog(n)). Value in orderMap is List
		so it can handle case when multiple drivers have the same total miles.
		
		Generates report by looping through orderMap in reversed order. Report is sorted from driver 
		with most miles driven to driver with least miles driven.
	

Class Trip
	Encapsulates data for each driver. Stores driver's name, total miles driven, and total time duration.
	Provides public methods to get driver's name, average speed, and total miles driven

	Members:
	private String Name;
    	private double TotalTime;
    	private double TotalMiles;

	Methods:
	public void addTrip (String startTime, String endTime, String miles) -
		- calculates tripDuration in minutes from input values startTime, endTime. Storing duration 
		in minutes intentionaly in order to not lose precision.
		- adds tripDuraton to the totalTime member
		- rounds input value miles and adds it to the totalMiles member
		- validates average speed for each trip, and discards trip if average speed is less than 5,
		or more than 100. 

	public String getName() - returns driver's name
	public int getMiles()  -  return total miles driven for one driver rounded to the nearst int
	public int getAvgSpeed() - returns average speed for all trips for one driver rounded to the nearest int

Unit tests:
DriverHistoryTest
	testGenerateDrivingHistory
	testGenerateDrivingHistorySameMilesForMultipleDrivers
	testGenerateDrivingHistoryNoFileError
	testGenerateDrivingHistoryIOError
	testGenerateDrivingHistoryDataError

TripTest
	testAddTrip
	testAddMultipleTrips
	testAddTripsSpeedLeesThan5MoreThan100
	testAddTripInvalidTimeValue




