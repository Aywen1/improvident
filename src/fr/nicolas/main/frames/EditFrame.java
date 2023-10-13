package fr.nicolas.main.frames;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.nicolas.main.components.NBackground;
import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.components.NLabel;
import fr.nicolas.main.panels.categories.NDefault;

public class EditFrame extends JFrame implements MouseListener, MouseMotionListener {

	private NBackground bg;
	private NButtonImg buttonAFaire, buttonARevoir, buttonArchives, buttonSupprimer;
	private NLabel labelText;
	private String path, category, url, name = "";
	private NDefault panel;

	private Point mouseLocation = new Point(0, 0);

	public EditFrame(Point location, String path, String category, String url, NDefault panel) {
		this.path = path;
		this.category = category;
		this.url = url;
		this.panel = panel;

		this.setTitle("Improvident - Edition lien");
		this.setSize(520, 120);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon("res/icon.png").getImage());
		this.setLocation(location);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);

		init();

		this.setVisible(true);
	}

	private void init() {
		bg = new NBackground(0);
		this.setContentPane(bg);

		labelText = new NLabel("Déplacer le lien dans :", 22);
		labelText.setBounds(8, 10, 250, 20);

		buttonAFaire = new NButtonImg("aFaire", new Rectangle(7 + 3, 40 + 26, 121, 40), 4);
		buttonAFaire.setBounds(7, 40, 121, 40);

		buttonARevoir = new NButtonImg("aRevoir", new Rectangle(133 + 3, 40 + 26, 121, 40), 4);
		buttonARevoir.setBounds(133, 40, 121, 40);

		buttonArchives = new NButtonImg("archives", new Rectangle(259 + 3, 40 + 26, 121, 40), 4);
		buttonArchives.setBounds(259, 40, 121, 40);

		buttonSupprimer = new NButtonImg("supprimer", new Rectangle(385 + 3, 40 + 26, 121, 40), 4);
		buttonSupprimer.setBounds(385, 40, 121, 40);

		bg.add(labelText);
		bg.add(buttonAFaire);
		bg.add(buttonARevoir);
		bg.add(buttonArchives);
		bg.add(buttonSupprimer);
	}

	// MouseListener

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (buttonAFaire.mouseClick(mouseLocation, false)) {
				delete();
				change(path + "À faire");
			}
			if (buttonARevoir.mouseClick(mouseLocation, false)) {
				delete();
				change(path + "À revoir");
			}
			if (buttonArchives.mouseClick(mouseLocation, false)) {
				delete();
				change(path + "Archivés");
			}
			if (buttonSupprimer.mouseClick(mouseLocation, false)) {
				delete();
				panel.load();
				this.dispose();
			}
		}
	}

	private void delete() {
		String lineText = "", newFileText = "", lastLineText = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path + category));

			while (lineText != null) {
				lineText = bufferedReader.readLine();

				if (lineText != null) {
					if (!lineText.equals(url)) {
						if (lastLineText != "") {
							newFileText += lastLineText + "\n";
						}
						lastLineText = lineText;
					} else {
						name = lastLineText;
						lastLineText = "";
					}
				}
			}

			if (lastLineText != "") {
				newFileText += lastLineText + "\n";
			}

			bufferedReader.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path + category)));

			bufferedWriter.write(newFileText);

			bufferedWriter.close();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}

	private void change(String newPath) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(newPath), true));

			bufferedWriter.write(name + "\n");
			bufferedWriter.write(url + "\n");

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		panel.load();
		this.dispose();
	}

	// MouseMotionListener

	public void mouseDragged(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		buttonAFaire.mouseMove(mouseLocation);
		buttonARevoir.mouseMove(mouseLocation);
		buttonArchives.mouseMove(mouseLocation);
		buttonSupprimer.mouseMove(mouseLocation);

		bg.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		buttonAFaire.mouseMove(mouseLocation);
		buttonARevoir.mouseMove(mouseLocation);
		buttonArchives.mouseMove(mouseLocation);
		buttonSupprimer.mouseMove(mouseLocation);

		bg.repaint();
	}
}
