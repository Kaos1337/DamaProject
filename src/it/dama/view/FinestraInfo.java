package it.dama.view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * definisce la finestra delle informazioni
 * 
 */

public class FinestraInfo extends JFrame {

	private static JLabel info = new JLabel(
			("<html><p>Questa dama è stata realizzata da:"
					+ "<br><br>  Comencini Marco &#09;VR367740 <br>  Marretta Francesco &#09;VR367451"
					+ "<br>  Rebesco Nevio &#09;VR372850 <br>  Zuliani Davide &#09;VR367494 </p></html>"));

	/**
	 * Costruisce la finestra delle informazioni.
	 */
	public FinestraInfo() {
		setSize(300, 150);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBackground(Color.lightGray);

		info.setHorizontalAlignment(0);
		add(info);

		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}