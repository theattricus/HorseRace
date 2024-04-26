package horserace;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

public class LanesUI extends JPanel {
	public LanesUI(Lanes lanes, int length) {
		ArrayList<LaneUI> laneUIs = new ArrayList<LaneUI>();
		for(Lane lane : lanes) {
			laneUIs.add(new LaneUI(lane));
		}
		setLayout(new GridLayout(0, 1));
		for (int i=0; i<laneUIs.size(); i++) {
			LaneUI lane = laneUIs.get(i);
			Dimension trackDimension;
			int initialWidth = 100 * length;
			int height = 100;
			double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double scale = (initialWidth > screenWidth) ? (screenWidth / initialWidth) : 1.0;
			
			trackDimension = new Dimension((int)(initialWidth*scale), height);

			lane.setPreferredSize(trackDimension);
			add(laneUIs.get(i));
		}
	}
}
