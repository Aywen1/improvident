package fr.nicolas.main.panels.categories;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.components.NChrono;
import fr.nicolas.main.frames.MainFrame;

public class NHome extends NCategoryPanel {

	private NButtonImg buttonChrono15m, buttonChrono30m, buttonChrono45m, buttonChrono1h, buttonChrono1h30m,
			buttonChronoStop;
	private boolean isButtonChronoStopShowed = false, canStop = false;
	private NChrono chrono;
	private MainFrame mainFrame;

	public NHome(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		buttonChrono15m = new NButtonImg("chrono/chrono15m", new Rectangle(10 + 60, 15 + 86, 158, 37));
		buttonChrono15m.setBounds(25, 15, 158, 37);
		this.add(buttonChrono15m);
		buttonChrono30m = new NButtonImg("chrono/chrono30m", new Rectangle(10 + 60, 60 + 86, 158, 37));
		buttonChrono30m.setBounds(25, 60, 158, 37);
		this.add(buttonChrono30m);
		buttonChrono45m = new NButtonImg("chrono/chrono45m", new Rectangle(10 + 60, 105 + 86, 158, 37));
		buttonChrono45m.setBounds(25, 105, 158, 37);
		this.add(buttonChrono45m);
		buttonChrono1h = new NButtonImg("chrono/chrono1h", new Rectangle(10 + 60, 150 + 86, 158, 37));
		buttonChrono1h.setBounds(25, 150, 158, 37);
		this.add(buttonChrono1h);
		buttonChrono1h30m = new NButtonImg("chrono/chrono1h30m", new Rectangle(10 + 60, 195 + 86, 158, 37));
		buttonChrono1h30m.setBounds(25, 195, 158, 37);
		this.add(buttonChrono1h30m);
		buttonChronoStop = new NButtonImg("chrono/chronoStop", new Rectangle(10 + 60, 15 + 86, 176, 190));
		buttonChronoStop.setBounds(16, 6, 176, 190);
	}

	public void mouseMove(Point mouseLocation) {
		if (!isButtonChronoStopShowed) {
			buttonChrono15m.mouseMove(mouseLocation);
			buttonChrono30m.mouseMove(mouseLocation);
			buttonChrono45m.mouseMove(mouseLocation);
			buttonChrono1h.mouseMove(mouseLocation);
			buttonChrono1h30m.mouseMove(mouseLocation);
		}

		if (isButtonChronoStopShowed) {
			buttonChronoStop.mouseMove(mouseLocation);
			if (!buttonChronoStop.isHovered()) {
				canStop = true;
			}
		}
	}

	public void mouseClick(Point mouseLocation, boolean middleClick) {
		if (!isButtonChronoStopShowed) {
			if (buttonChrono15m.mouseClick(mouseLocation, middleClick)) {
				showButtonChronoStop();
				chrono = new NChrono(mainFrame, 15);
			} else if (buttonChrono30m.mouseClick(mouseLocation, middleClick)) {
				showButtonChronoStop();
				chrono = new NChrono(mainFrame, 30);
			} else if (buttonChrono45m.mouseClick(mouseLocation, middleClick)) {
				showButtonChronoStop();
				chrono = new NChrono(mainFrame, 45);
			} else if (buttonChrono1h.mouseClick(mouseLocation, middleClick)) {
				showButtonChronoStop();
				chrono = new NChrono(mainFrame, 60);
			} else if (buttonChrono1h30m.mouseClick(mouseLocation, middleClick)) {
				showButtonChronoStop();
				chrono = new NChrono(mainFrame, 90);
			}
		}

		if (isButtonChronoStopShowed && canStop) {
			if (buttonChronoStop.mouseClick(mouseLocation, middleClick)) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"Voulez-vous vraiment arrêter le chrono et ne gagner aucun point ?", "Arrêt du chrono",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (confirm == JOptionPane.OK_OPTION) {
					removeButtonChronoStop();
					chrono.stop();
				}
			}
		}
	}

	public void removeButtonChronoStop() {
		this.remove(buttonChronoStop);
		this.add(buttonChrono15m);
		this.add(buttonChrono30m);
		this.add(buttonChrono45m);
		this.add(buttonChrono1h);
		this.add(buttonChrono1h30m);
		isButtonChronoStopShowed = false;
	}

	private void showButtonChronoStop() {
		if (!isButtonChronoStopShowed) {
			this.remove(buttonChrono15m);
			this.remove(buttonChrono30m);
			this.remove(buttonChrono45m);
			this.remove(buttonChrono1h);
			this.remove(buttonChrono1h30m);
			this.add(buttonChronoStop);
			canStop = false;
			isButtonChronoStopShowed = true;
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 255, 50));
		if (isButtonChronoStopShowed) {
			g.drawRect(15, 5, 177, 191);
		} else {
			g.drawRect(15, 5, 177, 236);
		}
	}

}
