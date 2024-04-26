package horserace;

import org.json.JSONObject;

/**
* Horse class represents horses for race. Each horse has a name, symbol, confidence value.
* 
* @author Anonto Amin
* @version 1
*/
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Horse
{
	//Fields of class Horse
	private int id;
	private char horseSymbol;
	private String horseName;
	private double horseConfidence;
	private boolean fallen = false;
	private int distanceTravelled = 0;
	private BufferedImage image;
	private BufferedImage saddle;
	private BufferedImage hat;
	private String imagePath;
	private String saddlePath;
	
	private static char FALLEN_SYMBOL = 'X';
	
	//Constructor of class Horse
	/**
	* Constructor for objects of class Horse
	*/
	public Horse(char horseSymbol, String horseName, double horseConfidence)
	{
		this.horseSymbol = horseSymbol;
		this.horseName = horseName;
		this.fallen = false;
		this.distanceTravelled = 0;
		setConfidence(horseConfidence);
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public BufferedImage getImage() { return image; }
	public void setImage() { this.image = null; }
	public void setImage(BufferedImage image) { this.image = image; }
	public void setImage(String path) { 
		try {
			this.image = ImageIO.read(getClass().getClassLoader().getResource(path));
			imagePath = path;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public BufferedImage getSaddle() { 
		try{
		if(hasFallen()) return ImageIO.read(getClass().getClassLoader().getResource("images/saddles/fallen.png"));
		}catch(Exception e) {};	
		return saddle;
	}
	public void setSaddle() { this.saddle = null; }
	public void setSaddle(BufferedImage image) { this.saddle = image; }
	public void setSaddle(String path) {
		if(path.equals("null")) { setSaddle(); return; }
		try {
			this.saddle = ImageIO.read(getClass().getClassLoader().getResource(path));
			saddlePath = path;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public BufferedImage getHat() { return hat; }
	public void setHat(BufferedImage image) { this.hat = image; }
	public void setHat(String path) {
		try {
			this.hat = ImageIO.read(getClass().getClassLoader().getResource(path));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static Horse deserializeHorse(String input) {
		//Example input is "id, symbol, name, confidence, breed, saddle" which are types "int, char, string, double, string, string"
		String[] parts = input.split(", ");
		int id = Integer.parseInt(parts[0]);
		char symbol = parts[1].charAt(0);
		String name = parts[2];
		double confidence = Double.parseDouble(parts[3]);
		Horse horse = new Horse(symbol, name, confidence);
		horse.setImage(parts[4]);
		horse.setSaddle(parts[5]);
		horse.setId(id);
		return horse;
	}

	public String serializeHorse() {
		return id + ", " + horseSymbol + ", " + horseName + ", " + horseConfidence + ", " + imagePath + ", " + saddlePath;
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
