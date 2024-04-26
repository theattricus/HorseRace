package horserace;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.HashMap;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;

import java.util.ArrayList;
import horserace.LaneObserver;
public class LaneUI extends JPanel implements LaneObserver { // Could also use Swing timer for animation, however I like the observer pattern for this just in case movelane logic changes.
	private static final int UNIT_SIZE = 100;
	private static Color SADDLE_COLOR = new Color(255,0,255); // Alpha = opaque, red=255, green=0, blue=255
	private static Color GLASSES_COLOR = new Color(0,0,255); // Alpha = opaque, red=0, green=0, blue=255

	private static HashMap<String, Color> colors = new HashMap<String, Color>();

	private static BufferedImage finishline;
	
	private Lane lane;
	LaneUI(Lane lane, HorseManager hm) { 
		colors.put("Gray", Color.GRAY);
		colors.put("Red", Color.RED);
		colors.put("Blue", Color.BLUE);
		colors.put("Black", Color.BLACK);
		try {
			finishline = ImageIO.read(getClass().getClassLoader().getResource("images/finish.png"));
		} catch(Exception e) {}
		this.lane = lane;
		lane.registerObserver(this);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked!");
				HashMap<String, Horse> horseNameMap = new HashMap<String, Horse>();
				for(Horse horse : hm.getHorses().values()) {
					horseNameMap.put(horse.getName(), horse);
				}
				LaneChooser chooser = new LaneChooser(colors, horseNameMap, (Color color, Horse horse) -> {
					lane.setColor(color);
					lane.setHorse(horse);
				});
				chooser.setVisible(true);
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}	
	
	private static BufferedImage deepCopy(BufferedImage bi) { //https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	private static ArrayList<Integer> findPoint(BufferedImage image, Color color) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int height = image.getHeight(), width = image.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color pColor = new Color(image.getRGB(x, y));
				if(pColor.equals(color)) {
					result.add(x);
					result.add(y);
					return result;
				}
			}
		}
		return result;
	}
	
	private static ArrayList<Integer> accessoryLocation(int horseX, int horseY, BufferedImage horse, BufferedImage accessory, Color accessoryColor) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> horsePoint = findPoint(horse, accessoryColor);
		if(horsePoint.size()==0) return result;
		ArrayList<Integer> accessoryOffset = findPoint(accessory, accessoryColor);
		if(accessoryOffset.size()==0) return result;
		int x = horseX+horsePoint.get(0)-accessoryOffset.get(0);
		int y = horseY+horsePoint.get(1)-accessoryOffset.get(1);
		result.add(x);
		result.add(y);
		return result;
	}

	@Override
	public void laneObserverUpdate() { //TODO: Boolean argument "animate" to calculate with horseX+0.5 and horseY-=0.5
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage image;
		g.setColor(lane.getColor());
		g.fillRect(0, 0, getWidth(), getHeight());
		final int finishingLineX = getWidth()-finishline.getWidth();
		final int finishingLineY = 0;
		g.drawImage(finishline, finishingLineX, finishingLineY, this);
		Horse horse = lane.getHorse();
		if(horse!=null && (image=horse.getImage())!=null) {
			image = deepCopy(image);
			final int horseX = getWidth()*horse.getDistanceTravelled()/(lane.getLength()+1);///lane.getLength();
			final int horseY = 0;//getHeight()*1/4;
			g.drawImage(image, horseX, horseY, this);
			BufferedImage saddle;
			if((saddle = horse.getSaddle())!=null) {
				ArrayList<Integer> saddleLocation = accessoryLocation(horseX, horseY, image, saddle, SADDLE_COLOR);
				if(saddleLocation.size()==2) {
					g.drawImage(saddle, saddleLocation.get(0), saddleLocation.get(1), this);
				};
			}
		}
	}
}
