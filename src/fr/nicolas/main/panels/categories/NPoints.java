package fr.nicolas.main.panels.categories;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.util.ArrayList;

import javax.swing.JPanel;

import fr.nicolas.main.components.NBackground;
import fr.nicolas.main.components.NButtonImg;
import fr.nicolas.main.frames.MainFrame;

public class NPoints extends NCategoryPanel {

	private MainFrame mainFrame;
	private ArrayList<NButtonImg> buttonList = new ArrayList<NButtonImg>();
	private ArrayList<NButtonImg> buttonCategoryList = new ArrayList<NButtonImg>();
	private ArrayList<String> blockCategoryList = new ArrayList<String>();
	private ArrayList<String> nameList = new ArrayList<String>();
	private ArrayList<String> commandList = new ArrayList<String>();
	private ArrayList<Integer> pointsList = new ArrayList<Integer>();
	private CardLayout cardLayout = new CardLayout();
	private JPanel items = new JPanel(), blocs = new JPanel(), vegetaux = new JPanel(), mineraies = new JPanel(),
			divers = new JPanel(), bonus = new JPanel();

	private String[] categories = { "Blocs", "Végétaux", "Mineraies", "Divers", "Bonus" };
	private String openCategory = "Blocs", mapPath;

	public NPoints(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		this.setLayout(new BorderLayout());
		items.setLayout(cardLayout);
		blocs.setLayout(null);
		vegetaux.setLayout(null);
		mineraies.setLayout(null);
		divers.setLayout(null);
		bonus.setLayout(null);

		items.add(blocs, "Blocs");
		items.add(vegetaux, "Végétaux");
		items.add(mineraies, "Mineraies");
		items.add(divers, "Divers");
		items.add(bonus, "Bonus");

		int x2 = 25;
		for (int i = 0; i < categories.length; i++) {
			NButtonImg category = new NButtonImg("category2", new Rectangle(x2 + 45, 4 + 86, 125, 28), 3);
			category.setName(categories[i]);
			category.setBounds(x2, 4, 125, 28);

			buttonCategoryList.add(category);
			this.add(category);

			x2 += 145;

			String blockName = "";
			int x = 8, y = 40;
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader("res/list" + categories[i] + ".txt"));

				while (blockName != null) {
					blockName = bufferedReader.readLine();
					if (blockName != null) {
						NButtonImg icon = new NButtonImg("item", new Rectangle(x + 45, y + 86, 180, 35), 2);
						buttonList.add(icon);

						if (categories[i] == "Blocs") {
							blocs.add(icon);
						} else if (categories[i] == "Végétaux") {
							vegetaux.add(icon);
						} else if (categories[i] == "Mineraies") {
							mineraies.add(icon);
						} else if (categories[i] == "Divers") {
							divers.add(icon);
						} else if (categories[i] == "Bonus") {
							bonus.add(icon);
						}

						String command = bufferedReader.readLine();
						String quantity = bufferedReader.readLine();

						if (categories[i] == "Bonus") {
							commandList.add(command);
							icon.setName(blockName + " [" + quantity + "pts]");
							pointsList.add(Integer.parseInt(quantity));
							nameList.add(blockName);
						} else {
							if (command.contains("spawn_egg")) {
								commandList.add("give @p spawn_egg 1 0 {EntityTag:{id:"
										+ command.replace("spawn_egg ", "") + "}}");
							} else if (command.contains(" ")) {
								commandList.add("give @p " + command.split(" ")[0] + " " + quantity + " "
										+ command.split(" ")[1]);
							} else {
								commandList.add("give @p " + command + " " + quantity);
							}
							icon.setName(blockName + " (" + quantity + ")");
							pointsList.add(10);
							nameList.add(blockName + " (" + quantity + ")");
						}

						icon.setBounds(x, y, 180, 35);

						blockCategoryList.add(categories[i]);

						x += 185;
						if (x >= 744) {
							x = 8;
							y += 45;
						}
					}
				}

				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			buttonCategoryList.get(0).setSelected(true);
		}

		blocs.add(new NBackground(2));
		vegetaux.add(new NBackground(2));
		mineraies.add(new NBackground(2));
		divers.add(new NBackground(2));
		bonus.add(new NBackground(2));

		this.add(items, BorderLayout.CENTER);
	}

	public void mouseMove(Point mouseLocation) {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).mouseMove(mouseLocation);
		}
		for (int i = 0; i < buttonCategoryList.size(); i++) {
			buttonCategoryList.get(i).mouseMove(mouseLocation);
		}
	}

	public void mouseClick(Point mouseLocation, boolean middleClick) {
		for (int i = 0; i < buttonList.size(); i++) {
			if (buttonList.get(i).mouseClick(mouseLocation, middleClick)) {
				if (blockCategoryList.get(i) == openCategory) {
					if (mainFrame.getTitlePanel().getPoints() >= pointsList.get(i)) {

						try {
							BufferedWriter bufferedWriter = new BufferedWriter(
									new FileWriter(new File(mapPath + "/data/functions/join/join1.mcfunction"), true));

							bufferedWriter.write(commandList.get(i) + "\n");
							if (blockCategoryList.get(i) == "Bonus") {
								bufferedWriter.write(
										"tellraw @a {\"text\":\" + " + nameList.get(i) + "\",\"color\":\"yellow\"}\n");
							} else {
								bufferedWriter.write(
										"tellraw @a {\"text\":\" + " + nameList.get(i) + "\",\"color\":\"green\"}\n");
							}

							bufferedWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

						String newPoints = "" + (mainFrame.getTitlePanel().getPoints() - pointsList.get(i));
						try {
							BufferedWriter bufferedWriter = new BufferedWriter(
									new FileWriter(new File("Improvident/Categories/Points/points")));
							bufferedWriter.write(newPoints);
							bufferedWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						mainFrame.getTitlePanel().reloadPoints();
						mainFrame.getTitlePanel().showConfirmItemsButton();
					}
				}
			}
		}
		for (int i = 0; i < buttonCategoryList.size(); i++) {
			if (buttonCategoryList.get(i).mouseClick(mouseLocation, middleClick)) {
				cardLayout.show(items, categories[i]);
				openCategory = categories[i];
				for (int i2 = 0; i2 < buttonCategoryList.size(); i2++) {
					buttonCategoryList.get(i2).setSelected(false);
				}
				buttonCategoryList.get(i).setSelected(true);
			}
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

}
