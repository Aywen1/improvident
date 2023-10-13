package fr.nicolas.main.frames;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;

import fr.nicolas.main.components.NBackground;
import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.components.NLabel;
import fr.nicolas.main.panels.categories.NDefault;

public class NewFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

	private NBackground bg;
	private NButtonImg buttonValider;
	private JTextField textfieldName, textfieldUrl;
	private NLabel labelName, labelUrl;
	private String path;
	private NDefault panel;

	private Point mouseLocation = new Point(0, 0);

	public NewFrame(Point location, String path, NDefault panel) {
		this.path = path;
		this.panel = panel;

		this.setTitle("Improvident - Nouveau lien");
		this.setSize(450, 200);
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
		bg = new NBackground(1);

		buttonValider = new NButtonImg("valider", new Rectangle(258, 148, 178, 40), 4);
		buttonValider.setBounds(255, 122, 178, 40);

		labelName = new NLabel("Nom :", 22);
		labelName.setBounds(15, 26, 80, 20);

		labelUrl = new NLabel("Url du lien :", 22);
		labelUrl.setBounds(15, 76, 120, 20);

		textfieldName = new JTextField();
		textfieldName.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		textfieldName.setBounds(150, 18, 275, 35);
		textfieldName.addKeyListener(this);

		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				textfieldName.requestFocus();
			}
		});

		textfieldUrl = new JTextField();
		textfieldUrl.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		textfieldUrl.setBounds(150, 68, 275, 35);
		textfieldUrl.addKeyListener(this);

		this.setContentPane(bg);

		bg.add(buttonValider);
		bg.add(labelName);
		bg.add(labelUrl);
		bg.add(textfieldName);
		bg.add(textfieldUrl);
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
			if (buttonValider.mouseClick(mouseLocation, false)) {
				valider();
			}
		}
	}

	private void valider() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(new File(path), true));

			bufferedWriter.write(textfieldName.getText() + "\n");
			bufferedWriter.write(textfieldUrl.getText() + "\n");
			
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

		buttonValider.mouseMove(mouseLocation);

		bg.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		buttonValider.mouseMove(mouseLocation);

		bg.repaint();
	}

	// KeyListener

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			valider();
		}
	}
}
