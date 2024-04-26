package horserace;

import java.util.Scanner;

import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.HashMap;

public class HorseManager {
	private File horsesFile;
	private HashMap<Integer, Horse> horses = new HashMap<Integer, Horse>();
	HorseManager(String horsesFileName) {
		horsesFile = new File(horsesFileName);
		try{
			if(horsesFile.createNewFile()) { //Default. Be warey of addHorse dependencies, which are for now just horsesFile.
				addHorse('a', "Donner", 0.4, "images/horses/donner.png", "images/saddles/saddle.png");
				addHorse('b', "Bob", 0.5, "images/horses/tescomealdeal.png", "images/saddles/bluesaddle.png");
			}
			Scanner scanner = new Scanner(horsesFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Horse horse = Horse.deserializeHorse(line);
				horses.put(horse.getId(), horse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public HashMap<Integer, Horse> getHorses() { return horses; }

	public Horse addHorse(Character symbol, String name, double confidence, String breed, String saddle) {
		Horse horse = new Horse(symbol, name, confidence);
		int id = horses.size()+1;
		horse.setId(id);
		horse.setImage(breed);
		horse.setSaddle(saddle);
		horses.put(id, horse);
		try {
			FileWriter fw = new FileWriter(horsesFile, true);
			fw.write(horse.serializeHorse() + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return horse;
	}
}