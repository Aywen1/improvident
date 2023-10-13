package fr.nicolas.main.panels;

import java.awt.CardLayout;
import java.awt.Point;

import javax.swing.JPanel;

import fr.nicolas.main.frames.MainFrame;
import fr.nicolas.main.panels.categories.NCategoryPanel;
import fr.nicolas.main.panels.categories.NHome;
import fr.nicolas.main.panels.categories.NPoints;
import fr.nicolas.main.panels.categories.NSettings;
import fr.nicolas.main.panels.categories.NDefault;

public class NBase extends JPanel {

	private NCategoryPanel[] panels;
	private int currentPanelNumber = 0;
	private CardLayout cardLayout;

	public NBase(MainFrame mainFrame) {
		panels = new NCategoryPanel[] { new NHome(mainFrame), new NDefault("Cours �crits"),
				new NDefault("Cours vid�os"), new NDefault("Compr�hension �crite"), new NDefault("Compr�hension orale"),
				new NPoints(mainFrame), new NSettings() };

		cardLayout = new CardLayout();
		setLayout(cardLayout);

		((NSettings) panels[6]).setPoints((NPoints) panels[5]);

		add(panels[0], "Accueil");
		add(panels[1], "Cours �crits");
		add(panels[2], "Cours vid�os");
		add(panels[3], "Compr�hension �crite");
		add(panels[4], "Compr�hension orale");
		add(panels[5], "Points");
		add(panels[6], "Param�tres");
	}

	public void setPanel(String name, int panelNumber) {
		cardLayout.show(this, name);
		currentPanelNumber = panelNumber;
	}

	public NHome getHome() {
		return (NHome) panels[0];
	}

	public void mouseClick(Point mouseLocation, boolean middleClick) {
		panels[currentPanelNumber].mouseClick(mouseLocation, middleClick);
	}

	public void mouseMove(Point mouseLocation) {
		panels[currentPanelNumber].mouseMove(mouseLocation);
	}

}
