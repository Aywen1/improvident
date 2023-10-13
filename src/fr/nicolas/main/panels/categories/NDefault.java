package fr.nicolas.main.panels.categories;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.JPanel;

import fr.nicolas.main.components.NBackground;
import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.frames.EditFrame;
import fr.nicolas.main.frames.NewFrame;

public class NDefault extends NCategoryPanel {

	private ArrayList<NButtonImg> buttonList;
	private ArrayList<NButtonImg> buttonCategoryList;
	private ArrayList<Integer> categoryList;
	private ArrayList<String> urlList;
	private CardLayout cardLayout = new CardLayout();
	private JPanel items = new JPanel(), aFaire = new JPanel(), aRevoir = new JPanel(), archives = new JPanel();

	private String[] categories = { "À faire", "À revoir", "Archivés" };
	private int openCategory = 0;
	private NButtonImg buttonAdd;
	private String name;

	public NDefault(String name) {
		this.name = name;

		load();
	}

	public void load() {
		if (new File("Improvident/Categories/Cours écrits/À faire").exists()) {

			buttonList = new ArrayList<NButtonImg>();
			buttonCategoryList = new ArrayList<NButtonImg>();
			categoryList = new ArrayList<Integer>();
			urlList = new ArrayList<String>();
			this.removeAll();
			aFaire.removeAll();
			aRevoir.removeAll();
			archives.removeAll();

			int x2 = 76;
			for (int i = 0; i < categories.length; i++) {
				int y = 42, xNum = 1;

				try {
					BufferedReader bufferedReader = new BufferedReader(
							new FileReader("Improvident/Categories/" + name + "/" + categories[i]));

					String buttonName = "";

					while (buttonName != null) {
						buttonName = bufferedReader.readLine();
						if (buttonName != null) {

							NButtonImg button;
							if (xNum == 1) {
								button = new NButtonImg("category3", new Rectangle(15 + 45, y + 86, 354, 25), 5);
								button.setBounds(15, y, 354, 25);
							} else {
								button = new NButtonImg("category3Inverted", new Rectangle(383 + 45, y + 86, 354, 25),
										5);
								button.setBounds(383, y, 354, 25);
							}

							button.setName(buttonName);

							if (categories[i] == "À faire") {
								aFaire.add(button);
								categoryList.add(0);
							} else if (categories[i] == "À revoir") {
								aRevoir.add(button);
								categoryList.add(1);
							} else if (categories[i] == "Archivés") {
								archives.add(button);
								categoryList.add(2);
							}

							buttonList.add(button);
							urlList.add(bufferedReader.readLine());

							if (xNum == 1) {
								xNum = 2;
							} else {
								xNum = 1;
								y += 32;
							}

						}
					}

					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				NButtonImg category = new NButtonImg("category", new Rectangle(x2 + 45, 4 + 86, 150, 28), 4);
				category.setName(categories[i]);
				category.setBounds(x2, 4, 150, 28);

				buttonCategoryList.add(category);
				this.add(category);

				x2 += 225;
			}
		}

		aFaire.setLayout(null);
		aRevoir.setLayout(null);
		archives.setLayout(null);

		aFaire.add(new NBackground(2));
		aRevoir.add(new NBackground(2));
		archives.add(new NBackground(2));

		buttonAdd = new NButtonImg("add", new Rectangle(15 + 45, 5 + 86, 26, 26), 5);
		buttonAdd.setBounds(15, 5, 26, 26);
		this.add(buttonAdd);

		this.setLayout(new BorderLayout());
		items.setLayout(cardLayout);

		items.add(aFaire, "À faire");
		items.add(aRevoir, "À revoir");
		items.add(archives, "Archivés");

		this.add(items, BorderLayout.CENTER);

		select(openCategory);
	}

	public void mouseMove(Point mouseLocation) {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).mouseMove(mouseLocation);
		}
		for (int i = 0; i < buttonCategoryList.size(); i++) {
			buttonCategoryList.get(i).mouseMove(mouseLocation);
		}
		buttonAdd.mouseMove(mouseLocation);
	}

	public void mouseClick(Point mouseLocation, boolean middleClick) {
		for (int i = 0; i < buttonList.size(); i++) {
			if (buttonList.get(i).mouseClick(mouseLocation, middleClick)) {
				if (categoryList.get(i) == openCategory) {
					try {
						Desktop.getDesktop().browse(URI.create(urlList.get(i)));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (buttonList.get(i).middleClick(mouseLocation, middleClick)) {
				if (categoryList.get(i) == openCategory) {
					new EditFrame(this.getLocationOnScreen(),
							"Improvident/Categories/" + name + "/",
							categories[openCategory],urlList.get(i), this);
				}
			}
		}
		for (int i = 0; i < buttonCategoryList.size(); i++) {
			if (buttonCategoryList.get(i).mouseClick(mouseLocation, middleClick)) {
				select(i);
			}
		}
		if (buttonAdd.mouseClick(mouseLocation, middleClick)) {
			new NewFrame(this.getLocationOnScreen(), "Improvident/Categories/" + name + "/" + categories[openCategory],
					this);
		}
	}

	private void select(int i) {
		cardLayout.show(items, categories[i]);
		openCategory = i;
		for (int i2 = 0; i2 < buttonCategoryList.size(); i2++) {
			buttonCategoryList.get(i2).setSelected(false);
		}
		buttonCategoryList.get(i).setSelected(true);
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
