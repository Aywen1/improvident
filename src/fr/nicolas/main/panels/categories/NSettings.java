package fr.nicolas.main.panels.categories;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.components.NLabel;

public class NSettings extends NCategoryPanel {

	private NButtonImg buttonSavePath;
	private NLabel labelInfoPath = new NLabel("(Aucune map enregistrée)", 16);
	private JFileChooser fileChooser = new JFileChooser();
	private NPoints points;

	public NSettings() {
		fileChooser.setDialogTitle("Sauvegarder le chemin de la map");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		buttonSavePath = new NButtonImg("savePath", new Rectangle(85, 92, 158, 37));
		buttonSavePath.setBounds(40, 6, 158, 37);

		labelInfoPath.setBounds(35, 48, 400, 20);

		add(buttonSavePath);
		add(labelInfoPath);
	}

	public void mouseMove(Point mouseLocation) {
		buttonSavePath.mouseMove(mouseLocation);
	}

	public void mouseClick(Point mouseLocation, boolean middleClick) {
		if (buttonSavePath.mouseClick(mouseLocation, middleClick)) {
			int fileChooserValue = fileChooser.showOpenDialog(this);
			if (fileChooserValue == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedWriter bufferedWriter = new BufferedWriter(
							new FileWriter(new File("Improvident/Categories/Paramètres/mapPath")));

					points.setMapPath(fileChooser.getSelectedFile().getPath());

					bufferedWriter.write(fileChooser.getSelectedFile().getPath() + "\n");
					bufferedWriter.write(fileChooser.getSelectedFile().getName());

					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				labelInfoPath.setText("(Map enregistrée: " + fileChooser.getSelectedFile().getName() + ")");
				JOptionPane.showMessageDialog(null, "Le chemin de la map a bien été sauvegardée !",
						"Chemin de la map sauvegardée", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void setPoints(NPoints points) {
		this.points = points;
		if (new File("Improvident/Categories/Paramètres/mapPath").exists()) {
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader("Improvident/Categories/Paramètres/mapPath"));
				points.setMapPath(bufferedReader.readLine());

				labelInfoPath.setText("(Map enregistrée: " + bufferedReader.readLine() + ")");

				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
