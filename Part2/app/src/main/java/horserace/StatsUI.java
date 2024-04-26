package horserace;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
public class StatsUI extends JFrame {
	public StatsUI(ArrayList<RaceResult> results, HorseManager hm) {
		super("Horse stats!");
		setLayout(new GridLayout(0, 1));
		setSize(400, 400);
		for(Horse horse : hm.getHorses().values()) {
			int wins = 0;
			int losses = 0;
			int falls = 0;
			int total = 0;
			int totalLength = 0; // for wins
			int totalSteps = 0; // for wins
			for(RaceResult result : results) {
				if(result.getWinners().keySet().contains(horse.getId())) {
					wins++;
					totalLength += result.getLength();
					totalSteps += result.getWinners().get(horse.getId());
					total++;
				}
				if(result.getDidNotFinish().contains(horse.getId())) {
					losses++;
					total++;
				}
				if(result.getFell().contains(horse.getId())) {
					falls++;
					total++;
				}
			}
			String s = "Horse: " + horse.getName() + "\nWins: " + wins + "\nLosses: " + losses + "\nFalls: " + falls + "\nTotal: " + total + "\nAverage length/step per win: ";
			if(totalSteps==0) {
				s += "N/A";
			} else {
				s += (double)totalLength/(double)totalSteps;
				System.out.println("L"+(double)totalLength/(double)totalSteps);
			}
			s+="\nWin rate: ";
			if(total==0) {
				s += "N/A";
			} else {
				s += (double)wins/(double)total;
				System.out.println("W"+(double)wins/(double)total);
			}
			add(new JTextArea(s));
		}
	}
}