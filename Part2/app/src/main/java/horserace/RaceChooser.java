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
	public RaceChooser(String horses, String races) {
		setLayout(new BorderLayout());
		HorseManager hm = new HorseManager("horses.txt");
		RaceManager rm = new RaceManager("races.txt");
		rm.setHorseManager(hm);
		rm.setLanes(hm.getHorses().size()+2);
		hm.getHorses().forEach((k,v) -> {
			rm.addHorse(v, k);
		});
		LanesUI lanesUI = rm.getRace().getLanesUI();
		add(lanesUI, BorderLayout.CENTER);
		JButton horseButton = new JButton("Create horse");
		JButton startButton = new JButton("Start race");
		horseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HorseCreator horseCreator = new HorseCreator((Character symbol, String name, double confidence, String path, String saddlePath) -> {
					hm.addHorse(symbol, name, confidence, path, saddlePath);
				});
				horseCreator.setVisible(true);
			}
		});
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
		add(horseButton, BorderLayout.NORTH);
		add(startButton, BorderLayout.SOUTH);
	}
}