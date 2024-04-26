package horserace;

import javax.swing.JFrame;

public class RaceChooser extends JFrame {
	public RaceChooser(String horses, String races) {
				HorseManager hm = new HorseManager("horses.txt");
				// Horse horse = new Horse('a', "asdf", 0.1);
				// Horse horse = hm.addHorse('a', "asdf", 0.1);
				// Horse horse2 = hm.addHorse('b', "asd2", 1);
				// Horse horse2 = new Horse('b', "asd2", 1);
				// try{
				// 	horse.setImage("images/horses/donner.png");
				// 	horse.setSaddle("images/saddles/saddle.png");
				// 	horse2.setImage("images/horses/tescomealdeal.png");
				// 	horse2.setSaddle("images/saddles/greensaddle.png");
				// }
				// catch(Exception e) {
				// 	System.exit(1);
				// }
				RaceManager rm = new RaceManager("races.txt");
				hm.getHorses().forEach((k,v) -> {
					rm.addHorse(v, k);
				});
				// race.addHorse(horse, 1);
				// race.addHorse(horse2, 2);
	}
}
