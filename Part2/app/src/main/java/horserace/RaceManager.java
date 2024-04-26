package horserace;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;

import java.io.FileWriter;

public class RaceManager implements RaceObserver {
	private static final int DEFAULT_LENGTH = 20;
	private File historyFile;
	private Race race = new Race(DEFAULT_LENGTH);
	RaceManager(String historyFile) {
		this.historyFile = new File(historyFile);
		try {
			this.historyFile.createNewFile();// only creates new one if old one doesnt exist
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		race.registerObserver(this);
	}

	public ArrayList<RaceResult> getResults() {
		ArrayList<RaceResult> results = new ArrayList<RaceResult>();
		try{
			Scanner scanner = new Scanner(historyFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				results.add(RaceResult.deserializeRaceResult(line));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return results;
	}

	public void addResult(RaceResult result) {
		try {
			FileWriter fw = new FileWriter(historyFile, true);
			fw.write(result + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Race getRace() { return race; }

	public void startRace() { race.startRace(); }

	public void setLanes(int count) { race.setLanes(count); }

	public void addHorse(Horse horse, int lane) { race.addHorse(horse, lane); }

	public void setLength(int length) { race.setLength(length); }

	public void setColor(int lane, Color color) { race.getLanes().get(lane).setColor(color); }

	@Override
	public void raceObserverUpdate(RaceResult result) {
		addResult(result);
	}
}
