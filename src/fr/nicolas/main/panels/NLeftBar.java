package fr.nicolas.main.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import fr.nicolas.main.components.NCategory;
import fr.nicolas.main.frames.MainFrame;

public class NLeftBar extends JPanel {

	private MainFrame mainFrame;
	private String[] categoriesName;
	private NCategory[] categories;
	private int selectedCategory = 0;
	private Rectangle leftBar;

	public NLeftBar(String[] categoriesName, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setLayout(null);

		leftBar = new Rectangle(0, 0, 42, 500);

		categories = new NCategory[categoriesName.length];
		for (int i = 0; i < categories.length; i++) {
			categories[i] = new NCategory(categoriesName[i], i);
			this.add(categories[i]);
			categories[i].setBounds(7, 12 + i * 40, 35, 28);
		}

		this.categoriesName = categoriesName;
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 130));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(new Color(255, 255, 255, 225));
		g.drawLine(getWidth() - 1, 12 + selectedCategory * 40, getWidth() - 1, 12 + selectedCategory * 40 + 28);
		g.drawLine(getWidth() - 2, 12 + selectedCategory * 40, getWidth() - 2, 12 + selectedCategory * 40 + 28);
	}

	public void mouseClick(Point mouseLocation) {
		for (int i = 0; i < this.categories.length; i++) {
			if (this.categories[i].mouseClick(mouseLocation)) {
				selectedCategory = i;
				mainFrame.getTitlePanel().setCategoryName(categoriesName[i]);
				mainFrame.getBase().setPanel(categoriesName[i], i);
			}
		}
	}

	public void mouseMove(Point mouseLocation) {
		for (int i = 0; i < this.categories.length; i++) {
			this.categories[i].mouseMove(mouseLocation);
		}
	}

	public void mouseWheelMoved(Point mouseLocation, int wheelRotation) {
		if (leftBar.contains(mouseLocation)) {
			if (wheelRotation < 0) {
				if (selectedCategory - 1 >= 0) {
					selectedCategory--;
				} else {
					selectedCategory = this.categories.length - 1;
				}
			} else if (wheelRotation > 0) {
				if (selectedCategory + 1 <= this.categories.length - 1) {
					selectedCategory++;
				} else {
					selectedCategory = 0;
				}
			}
			mainFrame.getTitlePanel().setCategoryName(categoriesName[selectedCategory]);
			mainFrame.getBase().setPanel(categoriesName[selectedCategory], selectedCategory);
		}
	}

}
