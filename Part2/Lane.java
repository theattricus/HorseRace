public class Lane {
	//Fields
	private Horse horse;
	private Integer length;
	private static Character EMPTY_TRACK = ' ';

	/**
	 * Constructor for Lane object, taking horse and length.
	 * @param horse is the horse.
	 * @param length is the length of the lane/track.
	 */
	public Lane(Horse horse, int length) {
		this.horse = horse;
		this.length = length;
	};

	/**
	 * Gets horse field.
	 * @return the horse.
	 */
	public Horse getHorse() {
		return horse;
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
