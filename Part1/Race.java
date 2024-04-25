import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
* A three-horse race, each horse running in its own lane
* for a given distance
* 
* @author McFarewell
* @version 1.0
*/
public class Race
{
	private int raceLength;
	private Lanes lanes;

	private static Integer DEFAULT_LANES = 3;
	
	/**
	* Constructor for objects of class Race
	* Initially there are no horses in the lanes
	* 
	* @param distance the length of the racetrack (in metres/yards...)
	*/
	public Race(int distance)
	{
		// initialise instance variables
		raceLength = distance;
		lanes = new Lanes(DEFAULT_LANES, raceLength);
	}
	
	/**
	* Call this before adding horses! Resets all lanes.
	* @param count number of lanes.
	*/
	public void setLanes(int count) {
		lanes = new Lanes(count, raceLength, this.lanes);
	}
	
	/**
	* Adds a horse to the race in a given lane
	* 
	* @param theHorse the horse to be added to the race
	* @param laneNumber the lane that the horse will be added to
	*/
	public void addHorse(Horse theHorse, int laneNumber)
	{
		laneNumber--; // convert 1-indexed number to 0-indexed.
		lanes.setHorse(theHorse, laneNumber);
	}
	
	/**
	* Start the race
	* The horse are brought to the start and
	* then repeatedly moved forward until the 
	* race is finished
	*/
	public void startRace()
	{
		//declare a local variable to tell us when the race is finished
		boolean finished = false;
		int horseCount = 0;
		//reset all the lanes (all horses not fallen and back to 0) and count how many horses.
		Iterator<Lane> used_lanes = lanes.used_lanes();
		while(used_lanes.hasNext()) {
			Lane lane = used_lanes.next();
			lane.getHorse().goBackToStart();
			horseCount++;
		}
		
		ArrayList<Horse> finishedHorses = new ArrayList<Horse>();
		
		int fallenCount = 0;
		while (!finished)
		{
			fallenCount = 0;
			//move each horse
			used_lanes = lanes.used_lanes();
			while(used_lanes.hasNext()) {
				Lane lane = used_lanes.next();
				Horse horse = lane.getHorse();
				moveHorse(horse);
				if(raceWonBy(horse)) {
					finishedHorses.add(horse);
					finished = true;
				}
				if(horse.hasFallen()) fallenCount++;
			}
			if(fallenCount==horseCount) finished=true;
			//print the race positions
			printRace();
			
			//wait for 100 milliseconds
			try{ 
				TimeUnit.MILLISECONDS.sleep(100);
			}catch(Exception e){}
		}
		if(finishedHorses.size()>0) {
			ArrayList<String> names = new ArrayList<String>();
			for(Horse horse : finishedHorses)
				names.add(horse.getName());
			System.out.println("Horses won: "+String.join(", ", names));
		} else if(fallenCount==horseCount) {
			System.out.println("All horses fell!");
		}
		
	}
	
	/**
	* Randomly make a horse move forward or fall depending
	* on its confidence rating
	* A fallen horse cannot move
	* 
	* @param theHorse the horse to be moved
	*/
	private void moveHorse(Horse theHorse)
	{
		//if the horse has fallen it cannot move, 
		//so only run if it has not fallen
		
		if  (!theHorse.hasFallen())
		{
			//the probability that the horse will move forward depends on the confidence;
			if (Math.random() < theHorse.getConfidence())
			{
				theHorse.moveForward();
			}
			
			//the probability that the horse will fall is very small (max is 0.1)
			//but will also will depends exponentially on confidence 
			//so if you double the confidence, the probability that it will fall is *2
			if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
			{
				theHorse.fall();
			}
		}
	}
	
	/** 
	* Determines if a horse has won the race
	*
	* @param theHorse The horse we are testing
	* @return true if the horse has won, false otherwise.
	*/
	private boolean raceWonBy(Horse theHorse)
	{
		if (theHorse.getDistanceTravelled() == raceLength)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/***
	* Print the race on the terminal
	*/
	private void printRace()
	{
		System.out.print('\u000C');  //clear the terminal window
		
		multiplePrint('=',raceLength+3); //top edge of track
		System.out.println();
		
		for(Lane lane : lanes)
			printLane(lane);
		
		multiplePrint('=',raceLength+3); //bottom edge of track
		System.out.println();    
	}
	
	/**
	* printLane with specific horse. Function is deprecated and superseded by printLane(Lane) however still works for backward functionality.
	* print a horse's lane during the race
	* for example
	* |           X                      |
	* to show how far the horse has run
	*/
	private void printLane(Horse theHorse)
	{
		//calculate how many spaces are needed before
		//and after the horse
		int spacesBefore = theHorse.getDistanceTravelled();
		int spacesAfter = raceLength - theHorse.getDistanceTravelled();
		
		//print a | for the beginning of the lane
		System.out.print('|');
		
		//print the spaces before the horse
		multiplePrint(' ',spacesBefore);
		
		//if the horse has fallen then print dead
		//else print the horse's symbol
		if(theHorse.hasFallen())
		{
			System.out.print('\u2322');
		}
		else
		{
			System.out.print(theHorse.getSymbol());
		}
		
		//print the spaces after the horse
		multiplePrint(' ',spacesAfter);
		
		//print the | for the end of the track
		System.out.print('|');
	}
	
	private void printLane(Lane lane) {
		System.out.println(lane);
	}

	
	
	/***
	* print a character a given number of times.
	* e.g. printmany('x',5) will print: xxxxx
	* 
	* @param aChar the character to Print
	*/
	private void multiplePrint(char aChar, int times)
	{
		int i = 0;
		while (i < times)
		{
			System.out.print(aChar);
			i = i + 1;
		}
	}
}
