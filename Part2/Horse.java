
/**
 * Horse class represents horses for race. Each horse has a name, symbol, confidence value.
 * 
 * @author Anonto Amin
 * @version 1
 */
public class Horse
{
    //Fields of class Horse
		private char horseSymbol;
		private String horseName;
		private double horseConfidence;
		private boolean fallen;
		private int distanceTravelled;

		private static char FALLEN_SYMBOL = 'X';
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
			this.horseSymbol = horseSymbol;
			this.horseName = horseName;
			setConfidence(horseConfidence);
			this.fallen = false;
			this.distanceTravelled = 0;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
			fallen = true;
    }
    
    public double getConfidence()
    {
       return horseConfidence; 
    }
    
    public int getDistanceTravelled()
    {
       return distanceTravelled; 
    }
    
    public String getName()
    {
       return horseName;
    }
    
    public char getSymbol()
    {
       return !fallen ? horseSymbol : FALLEN_SYMBOL; // Preserve symbol for when the horse is reset.
    }
    
    public void goBackToStart()
    {
       distanceTravelled = 0; 
			 fallen = false;
    }
    
    public boolean hasFallen()
    {
       return fallen; 
    }

    public void moveForward()
    {
			if(fallen) return; // Fallen horses should not move.
			distanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
			this.horseConfidence = Math.max(Math.min(newConfidence, 1), 0); // Math.clamp in Java 21+
    }
    
    public void setSymbol(char newSymbol)
    {
			this.horseSymbol = newSymbol;     
    }
}
