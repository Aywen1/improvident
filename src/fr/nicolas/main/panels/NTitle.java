package fr.nicolas.main.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.components.NChrono;
import fr.nicolas.main.components.NLabel;
import fr.nicolas.main.frames.MainFrame;

public class NTitle extends JPanel {

	private JLabel categoryName;
	private BufferedImage imgPoints;
	private NLabel labelPoints = new NLabel("0", 20);
	private NLabel labelChrono = new NLabel("0mn", 20);
	private int points = 0;
	private NButtonImg buttonConfirmItems, buttonChrono;
	private boolean isShowed = false;
	private MainFrame mainFrame;
	private boolean chronoOn = false;
	private NChrono chrono;

	public NTitle(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		try {
			imgPoints = ImageIO.read(new File("res/components/point.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		buttonConfirmItems = new NButtonImg("confirmItems", new Rectangle(405 + 45, 7 + 26, 158, 31));
		buttonConfirmItems.setBounds(405, 7, 158, 31);

		buttonChrono = new NButtonImg("chrono", new Rectangle(590 + 45, 11 + 26, 26, 26));
		buttonChrono.setBounds(590, 11, 26, 26);

		categoryName = new JLabel("Accueil");

		reloadPoints();

		this.setLayout(null);
		this.add(categoryName);
		this.add(labelPoints);
		this.add(labelChrono);
		this.add(buttonChrono);

		categoryName.setForeground(new Color(255, 255, 255));
		categoryName.setFont(new Font("Calibri Light", Font.PLAIN, 22));
		categoryName.setBounds(35, 14, 300, 30);

		labelPoints.setBounds(710, 11, 80, 30);
		labelChrono.setBounds(620, 11, 80, 30);

		if (new File("Improvident/Categories/Paramètres/mapPath").exists()) {
			String mapPath = "";
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader("Improvident/Categories/Paramètres/mapPath"));

				mapPath = bufferedReader.readLine();

				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(mapPath + "/data/functions/join/join1.mcfunction"));

				if (bufferedReader.readLine() != null) {
					this.add(buttonConfirmItems);
					isShowed = true;
				}

				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void showConfirmItemsButton() {
		if (!isShowed) {
			this.add(buttonConfirmItems);
			isShowed = true;
		}
	}

	public void reloadPoints() {
		String newPoints = "0";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("Improvident/Categories/Points/points"));

			newPoints = bufferedReader.readLine();

			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		labelPoints.setText(newPoints);
		points = Integer.parseInt(newPoints);

		mainFrame.repaint();
	}

	public void reloadChrono(int newChrono, NChrono chrono) {
		if (newChrono == 0) {
			chronoOn = false;
		} else {
			chronoOn = true;
		}
		
		this.chrono = chrono;
		
		labelChrono.setText(Integer.toString(newChrono) + "mn");

		mainFrame.repaint();
	}

	public void mouseClick(Point mouseLocation) {
		if (isShowed) {
			if (buttonConfirmItems.mouseClick(mouseLocation, false)) {

				String mapPath = "";
				try {
					BufferedReader bufferedReader = new BufferedReader(
							new FileReader("Improvident/Categories/Paramètres/mapPath"));

					mapPath = bufferedReader.readLine();

					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					BufferedWriter bufferedWriter = new BufferedWriter(
							new FileWriter(new File(mapPath + "/data/functions/join/join1.mcfunction")));

					bufferedWriter.write("");

					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				this.remove(buttonConfirmItems);
				isShowed = false;

			}
		}
		if (chronoOn) {
			if (buttonChrono.mouseClick(mouseLocation, false)) {
				if (chrono.isRunning()) {
					buttonChrono.setSelected(true);
					chrono.setRunning(false);
				} else {
					buttonChrono.setSelected(false);
					chrono.start();
				}
			}
		}
	}

	public void mouseMove(Point mouseLocation) {
		if (isShowed) {
			buttonConfirmItems.mouseMove(mouseLocation);
		}
		if (chronoOn) {
			buttonChrono.mouseMove(mouseLocation);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(new Color(255, 255, 255));
		g.drawLine(30, 44, getWidth(), 44);

		g.drawImage(imgPoints, 680, 11, null);
	}

	public void setCategoryName(String categoryName) {
		this.categoryName.setText(categoryName);
	}

	public int getPoints() {
		return points;
	}

}
