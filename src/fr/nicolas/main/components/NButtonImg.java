package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class NButtonImg extends JPanel {

	private String name = "";
	private BufferedImage img, imgHovered, imgMore;
	private Rectangle button;
	private boolean isHovered = false, isSelected = false;
	private int typeButton = 0;

	public NButtonImg(String name, Rectangle bounds) {
		init1(name, bounds);
	}

	public NButtonImg(String path, Rectangle bounds, int typeButton) {
		this.typeButton = typeButton;

		if (typeButton == 1) {
			this.button = bounds;
			try {
				img = ImageIO.read(new File(path + "icon.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			imgHovered = img;
		} else {
			init1(path, bounds);
		}
	}

	private void init1(String name, Rectangle bounds) {
		this.button = bounds;
		try {
			img = ImageIO.read(new File("res/components/buttons/" + name.toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgHovered = ImageIO.read(new File("res/components/buttons/" + name.toLowerCase() + "Hovered.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mouseMove(Point mouseLocation) {
		if (button.contains(mouseLocation)) {
			isHovered = true;
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			isHovered = false;
			setCursor(Cursor.getDefaultCursor());
		}
	}

	public boolean mouseClick(Point mouseLocation, boolean middleClick) {
		if (button.contains(mouseLocation) && !middleClick) {
			return true;
		} else {
			return false;
		}
	}

	public boolean middleClick(Point mouseLocation, boolean middleClick) {
		if (button.contains(mouseLocation) && middleClick) {
			return true;
		} else {
			return false;
		}
	}

	public void setName(String name) {
		this.name = name;
		repaint();
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void paintComponent(Graphics g) {
		if (typeButton == 1) {
			if (isHovered) {
				g.setColor(new Color(255, 255, 255, 30));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(new Color(255, 255, 255, 100));
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
				g.drawImage(img, 4, 4, getWidth() - 8, getHeight() - 8, null);
			} else {
				g.drawImage(img, 4, 4, getWidth() - 8, getHeight() - 8, null);
			}
		} else {
			if (isHovered || isSelected) {
				g.drawImage(imgHovered, 0, 0, getWidth(), getHeight(), null);
			} else {
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			}
			if (typeButton == 2 || typeButton == 3 || typeButton == 4 || typeButton == 5) {
				g.drawImage(imgMore, 7, 3, 30, 30, null);
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Calibri Light", Font.PLAIN, 20));
				if (typeButton == 2) {
					g.setFont(new Font("Calibri Light", Font.PLAIN, 22));
					g.drawString(name, 15, 25);
				} else if (typeButton == 3) {
					g.drawString(name, 23, 21);
					if (isSelected) {
						g.setColor(new Color(255, 255, 255, 30));
						g.drawLine(0, 26, 180, 26);
						g.setColor(new Color(255, 255, 255, 60));
						g.drawLine(0, 27, 180, 27);
					}
				} else if (typeButton == 4) {
					g.drawString(name, 40, 21);
					if (isSelected) {
						g.setColor(new Color(255, 255, 255, 20));
						g.drawLine(0, 26, 150, 26);
						g.setColor(new Color(255, 255, 255, 30));
						g.drawLine(0, 27, 150, 27);
					}
				} else if (typeButton == 5) {
					g.setFont(new Font("Calibri Light", Font.PLAIN, 18));
					g.drawString(name, 10, 18);
				}
			}
		}
	}

}
