package horserace;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class HorseCreator extends JFrame {
	public interface HorseCreatorCallback {
		public void execute(Character symbol, String name, double confidence, String path, String saddlePath);
	}

	private ArrayList<String> getFileNames(String folder) {
		ArrayList<String> fileNames = new ArrayList<String>();
		URL dirURL = getClass().getClassLoader().getResource(folder);
		if(dirURL!=null && dirURL.getProtocol().equals("file")) {
			try {
				File dir = new File(dirURL.toURI());
				for(File file : dir.listFiles()) {
					fileNames.add(file.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileNames;
	}

	public HorseCreator(HorseCreatorCallback callback) {
		super("Create horse!");
		final String breedsFolder = "images/horses/";
		final String saddlesFolder = "images/saddles/";

		final Character DEFAULT_SYMBOL = 'H';
		final String DEFAULT_NAME = "Horse";
		final double DEFAULT_CONFIDENCE = 0.5;
		setSize(400, 400);
		setResizable(false);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter symbol: "));
		JTextField symbolField = new JTextField(DEFAULT_SYMBOL.toString());
		symbolField.setPreferredSize(new Dimension(20, 20));
		panel.add(symbolField);
		panel.add(new JLabel("Enter name: "));
		JTextField nameField = new JTextField(DEFAULT_NAME);
		nameField.setPreferredSize(new Dimension(50, 20));
		panel.add(nameField);
		panel.add(new JLabel("Enter confidence: "));
		JTextField confidenceField = new JTextField(DEFAULT_CONFIDENCE+"");
		confidenceField.setPreferredSize(new Dimension(50, 20));
		panel.add(confidenceField);
		JComboBox<String> breedBox = new JComboBox<String>(getFileNames(breedsFolder).toArray(new String[0]));
		panel.add(breedBox);
		JComboBox<String> saddleBox = new JComboBox<String>(getFileNames(saddlesFolder).toArray(new String[0]));
		panel.add(saddleBox);
		JButton confirmButton = new JButton("Confirm");
		panel.add(confirmButton);
		add(panel);
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Character symbol = DEFAULT_SYMBOL;
				try { symbolField.getText().charAt(0); } catch(Exception ex) {};
				String name = nameField.getText();
				if(name.length()==0) name = DEFAULT_NAME;
				double confidence = DEFAULT_CONFIDENCE;
				try { confidence = Double.parseDouble(confidenceField.getText()); } catch(Exception ex) {};
				String breed = (String) breedBox.getSelectedItem();
				String saddle = (String) saddleBox.getSelectedItem();
				callback.execute(symbol, name, confidence, breedsFolder+breed, saddlesFolder+saddle);
				dispose();
			}
		});
	}
}
