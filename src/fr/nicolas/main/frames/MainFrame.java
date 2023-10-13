package fr.nicolas.main.frames;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.nicolas.main.components.NBackground;
import fr.nicolas.main.panels.NBase;
import fr.nicolas.main.panels.NLeftBar;
import fr.nicolas.main.panels.NTitle;

public class MainFrame extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener {

	private NBackground bg;
	private NLeftBar leftBar;
	private NTitle titlePanel;
	private NBase base;
	private String[] categories = { "Accueil", "Cours écrits", "Cours vidéos", "Compréhension écrite",
			"Compréhension orale", "Points", "Paramètres" };

	private Point mouseLocation = new Point(0, 0);

	public MainFrame(Point location) {
		String path = "Improvident/Categories/";
		if (!(new File(path).exists())) {
			for (int i = 1; i < categories.length; i++) {
				new File(path + categories[i]).mkdirs();
				if (categories[i] != "Points" && categories[i] != "Paramètres") {
					newFile(path + categories[i] + "/À faire", false);
					newFile(path + categories[i] + "/À revoir", false);
					newFile(path + categories[i] + "/Archivés", false);
				}
			}
			newFile("Improvident/Categories/Points/points", true);
		}

		this.setTitle("Improvident");
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("res/icon.png").getImage());
		this.setLocation(location);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.setFocusable(true);

		init();

		this.setVisible(true);
	}

	private void newFile(String path, boolean valueTo0) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));
			if (valueTo0) {
				bufferedWriter.write("0");
			} else {
				bufferedWriter.write("");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		bg = new NBackground(0);
		leftBar = new NLeftBar(categories, this);
		titlePanel = new NTitle(this);
		base = new NBase(this);

		this.setContentPane(bg);

		bg.add(leftBar);
		bg.add(titlePanel);
		bg.add(base);

		leftBar.setBounds(0, 0, 42, getHeight());
		titlePanel.setBounds(42, 0, getWidth(), 60);
		base.setBounds(42, 60, getWidth(), getHeight());
	}

	public NTitle getTitlePanel() {
		return titlePanel;
	}

	public NBase getBase() {
		return base;
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

			leftBar.mouseClick(mouseLocation);
			base.mouseClick(mouseLocation, false);
			titlePanel.mouseClick(mouseLocation);

		} else if (e.getButton() == MouseEvent.BUTTON2) {
			base.mouseClick(mouseLocation, true);
		}

		bg.repaint();
	}

	// MouseMotionListener

	public void mouseDragged(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		leftBar.mouseMove(mouseLocation);
		base.mouseMove(mouseLocation);

		bg.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		leftBar.mouseMove(mouseLocation);
		base.mouseMove(mouseLocation);
		titlePanel.mouseMove(mouseLocation);

		bg.repaint();
	}

	// MouseWheelListener

	public void mouseWheelMoved(MouseWheelEvent e) {
		leftBar.mouseWheelMoved(mouseLocation, e.getWheelRotation());

		bg.repaint();
	}

}
