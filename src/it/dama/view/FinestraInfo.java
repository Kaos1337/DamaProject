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
					+ "<br><br>  Comencini Marco <br>  Marretta Francesco"
					+ "<br>  Rebesco Nevio <br>  Zuliani Davide </p></html>"));

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