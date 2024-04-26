package horserace;
import java.awt.Color;
import java.util.ArrayList;
public class Lane {
	//Default members/config.
	private static Character EMPTY_TRACK = ' ';
	//Fields
	private Horse horse;
	private Integer length;
	
	private Color color = Color.GRAY;

	private final ArrayList<LaneObserver> observers = new ArrayList<LaneObserver>();

	/**
	 * Constructor for Lane object, taking horse and length.
	 * @param horse is the horse.
	 * @param length is the length of the lane/track.
	 */
	public Lane(Horse horse, int length) {
		this.horse = horse;
		this.length = length;
	};

	public int getLength() { return length; }
	
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }

	/**
	 * Gets horse field.
	 * @return the horse.
	 */
	public Horse getHorse() {
		return horse;
	}

	public void registerObserver(LaneUI laneUI) {
		observers.add(laneUI);
	}

	public void removeObserver(LaneUI laneUI) {
		observers.remove(laneUI);
	}

	public void notifyObservers() {
		for(LaneObserver observer : observers)
			observer.laneObserverUpdate();
	}

	public boolean moveHorse() {
		if(horse==null) return false;
		boolean result = false;
		if  (!horse.hasFallen())
		{
			//the probability that the horse will move forward depends on the confidence;
			if (Math.random() < horse.getConfidence())
			{
				horse.moveForward();
				result = true;
			}
			
			//the probability that the horse will fall is very small (max is 0.1)
			//but will also will depends exponentially on confidence 
			//so if you double the confidence, the probability that it will fall is *2
			if (Math.random() < (0.1*horse.getConfidence()*horse.getConfidence()))
			{
				horse.fall();
				result = false;
			}
		}
		notifyObservers();
		return result;
	}

	/**
	 * Sets horse field.
	 * @param horse is the horse.
	 */
	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	/***
	* print a character a given number of times.
	* e.g. printmany('x',5) will print: xxxxx
	* 
	* @param aChar the character to Print
	*/
	private String multipleChar(char aChar, int times)
	{
		String res = "";
		for(int i = 0; i<times;i++) {
			res+=aChar;
		}
		return res;
	}

	/**
	 * For printing in text program.
	 */
	@Override
	public String toString() {
		int spacesBefore = (horse!=null) ? horse.getDistanceTravelled() : 0;
		int spacesAfter = (horse!=null) ? length - horse.getDistanceTravelled() : length;
		String res = "";
		
		//print a | for the beginning of the lane
		res+=('|');
		
		//print the spaces before the horse
		res+=multipleChar(EMPTY_TRACK,spacesBefore);
		
		if(horse==null) {
			res+=EMPTY_TRACK;
		}
		//if the horse has fallen then print dead
		//else print the horse's symbol
		else if(horse.hasFallen())
		{
			res+='\u2322';
		}
		else
		{
			res+=horse.getSymbol();
		}
		
		//print the spaces after the horse
		res+=multipleChar(EMPTY_TRACK,spacesAfter);
		
		//print the | for the end of the track
		res+='|';
		return res;
	}
}
