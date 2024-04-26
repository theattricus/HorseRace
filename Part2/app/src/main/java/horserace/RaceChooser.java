package horserace;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class RaceChooser extends JPanel {
	private HorseManager hm;
	private RaceManager rm;
	private class Buttons extends JPanel { 
		public Buttons() {
			setLayout(new GridLayout(0, 1));
			JButton horseButton = new JButton("Create horse");
			horseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					HorseCreator horseCreator = new HorseCreator((Character symbol, String name, double confidence, String path, String saddlePath) -> {
						hm.addHorse(symbol, name, confidence, path, saddlePath);
					}); // Same as new HorseCreator(hm::addHorse)
					horseCreator.setVisible(true);
				}
			});
			JButton startButton = new JButton("Start race");
			startButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							rm.startRace();
							return null;
						}
					}.execute();
				}
			});
			JButton statsButton = new JButton("Stats");
			statsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new StatsUI(rm.getResults(), hm).setVisible(true);
				}
			});
			add(horseButton);
			add(startButton);
			add(statsButton);
		}
	}
	public RaceChooser(String horses, String races) {
		setLayout(new BorderLayout());
		hm = new HorseManager("horses.txt"); // these must always be set before othe code as buttons uses it.
		rm = new RaceManager("races.txt");
		rm.setHorseManager(hm);
		rm.setLanes(hm.getHorses().size()+2);
		hm.getHorses().forEach((k,v) -> {
			rm.addHorse(v, k);
		});
		LanesUI lanesUI = rm.getRace().getLanesUI();
		add(lanesUI, BorderLayout.CENTER);
		add(new Buttons(), BorderLayout.SOUTH);
	}
}