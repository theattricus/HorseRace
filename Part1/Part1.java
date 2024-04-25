public class Part1 {
	public static void main(String[] args) {
		Race race = new Race(20);
		race.setLanes(6);
		Horse horse1 = new Horse('♘', "PIPPI LONGSTOCKING", 0.6);
		Horse horse2 = new Horse('♞', "KOKOMO", 0.6);
		Horse horse3 = new Horse('♘', "EL JEFE", 0.6);
		race.addHorse(horse1, 1);
		race.addHorse(horse2, 2);
		race.addHorse(horse3, 3);
		race.startRace();
	}	
}
