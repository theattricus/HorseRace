package horserace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaceResult implements Serializable {
	private Map<Integer, Integer> winners; // Horse ID and steps taken to finish
	private List<Integer> didNotFinish; // Horse IDs that did not finish
	private List<Integer> fell; // Horse IDs that fell
	private Integer length;
	
	public RaceResult(Map<Integer, Integer> winners, List<Integer> didNotFinish, List<Integer> fell, Integer length) {
		this.winners = winners;
		this.didNotFinish = didNotFinish;
		this.fell = fell;
		this.length = length;
	}
	
	public Map<Integer, Integer> getWinners() {
		return winners;
	}
	
	public List<Integer> getDidNotFinish() {
		return didNotFinish;
	}
	
	public List<Integer> getFell() {
		return fell;
	}

	public Integer getLength() {
		return length;
	}
	
	@Override
	public String toString() {
		return "W: " + winners + ", D: " + didNotFinish + ", F: " + fell+ ", R: " + length;
	}
	
	//Method to extract HashMap from matched group
	private static HashMap<Integer, Integer> extractMap(String input, String regex) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			String matchedGroup = matcher.group(1);
			String[] entries = matchedGroup.split(", ");
			for (String entry : entries) {
				String[] keyValue = entry.split("=");
				int key = Integer.parseInt(keyValue[0].trim());
				int value = Integer.parseInt(keyValue[1].trim());
				map.put(key, value);
			}
		}
		return map;
	}
	
	private static ArrayList<Integer> extractList(String input, String regex) {
		ArrayList<Integer> list = new ArrayList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		if (matcher.find()) {
			String matchedGroup = matcher.group(1);
			String[] items = matchedGroup.split(", ");
			for (String item : items) {
				list.add(Integer.parseInt(item.trim()));
			}
		}
		
		return list;
	}
	
	private static Integer extractInteger(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		if (matcher.find()) {
			String matchedGroup = matcher.group(1);
			return Integer.parseInt(matchedGroup);
		}
		return 0;
	}
	public static RaceResult deserializeRaceResult(String serializedRaceResult) {
		HashMap<Integer, Integer> wonList = extractMap(serializedRaceResult, "W: \\{(.*?)\\}");
		ArrayList<Integer> lostList = extractList(serializedRaceResult, "D:\\[(.*?)\\]");
		ArrayList<Integer> fallenList = extractList(serializedRaceResult, "F: \\[(.*?)\\]");
		Integer length = extractInteger(serializedRaceResult, "R: (\\d+)");
		return new RaceResult(wonList, lostList, fallenList, length);
	}
}