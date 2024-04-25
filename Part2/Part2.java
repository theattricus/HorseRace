public class Part2 {
	public static void main(String[] args) {
		Race race = new Race(50);
		race.setLanes(4);
		Horse horse1 = new Horse('♘', "PIPPI LONGSTOCKING", 0.3);
		Horse horse2 = new Horse('♞', "KOKOMO", 0.3);
		Horse horse3 = new Horse('♘', "EL JEFE", 0.3);
		race.addHorse(horse1, 1);
		race.addHorse(horse2, 2);
		race.addHorse(horse3, 4);
		race.startRace();
	}	
}
