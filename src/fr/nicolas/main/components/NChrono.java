package fr.nicolas.main.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import fr.nicolas.main.frames.MainFrame;

public class NChrono implements Runnable {

	private int initialTime = 0, time = 0, sec = 60;
	private MainFrame mainFrame;
	private boolean isRunning = false;
	private boolean canStart = true;

	public NChrono(MainFrame mainFrame, int time) {
		this.mainFrame = mainFrame;
		this.time = time;
		this.initialTime = time;

		mainFrame.getTitlePanel().reloadChrono(time, this);

		start();
	}

	public void stop() {
		isRunning = false;
		mainFrame.getTitlePanel().reloadChrono(0, this);
		mainFrame.getBase().getHome().removeButtonChronoStop();
	}

	public void start() {
		if (canStart) {
			isRunning = true;
			new Thread(this).start();
		}
	}

	public void run() {
		while (isRunning) {
			canStart = false;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sec--;

			if (sec <= 0) {
				sec = 60;
				time--;
				mainFrame.getTitlePanel().reloadChrono(time, this);
			}
			if (time <= 0) {
				mainFrame.getTitlePanel().reloadChrono(time, this);

				this.stop();

				int gain = 0;
				if (initialTime == 30) {
					gain = 15;
				} else if (initialTime == 45) {
					gain = 25;
				} else if (initialTime == 60) {
					gain = 40;
				} else if (initialTime == 90) {
					gain = 65;
				}

				int oldPoints = 0;
				try {
					BufferedReader bufferedReader = new BufferedReader(
							new FileReader("Improvident/Categories/Points/points"));

					oldPoints = Integer.parseInt(bufferedReader.readLine());

					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				oldPoints += gain;

				try {
					BufferedWriter bufferedWriter = new BufferedWriter(
							new FileWriter(new File("Improvident/Categories/Points/points")));

					bufferedWriter.write("" + oldPoints);

					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				mainFrame.getTitlePanel().reloadPoints();

				JOptionPane
						.showMessageDialog(
								null, "Félicitations, le chrono de " + initialTime
										+ "mins est terminé !\n Vous gagnez donc " + gain + " points !",
								"Chrono terminé", JOptionPane.INFORMATION_MESSAGE);

			}

			canStart = true;
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
