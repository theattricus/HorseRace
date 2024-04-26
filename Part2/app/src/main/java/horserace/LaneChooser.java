package horserace;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.awt.Color;

public class LaneChooser extends JFrame {
	public LaneChooser(HashMap<String, Color> colors, HashMap<String, Horse> horses, LaneChooserCallback callback) {
		super("Choose color and horse!");
		setSize(400, 400);
		setResizable(false);
		JPanel panel = new JPanel();
		JComboBox<String> colorBox = new JComboBox<String>(colors.keySet().toArray(new String[0]));
		JComboBox<String> horseBox = new JComboBox<String>(horses.keySet().toArray(new String[0]));
		JButton confirmButton = new JButton("Confirm");
		panel.add(colorBox);
		panel.add(horseBox);
		panel.add(confirmButton);
		add(panel);

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String colorString = (String) colorBox.getSelectedItem();
				Color color = colors.get(colorString);
				String horseString = (String) horseBox.getSelectedItem();
				Horse horse = horses.get(horseString);
				callback.execute(color, horse);
				dispose();
			}
		});
	}
}
