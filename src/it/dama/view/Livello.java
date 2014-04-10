package it.dama.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;

/**
 * 
 * Classe per settare il livello di difficoltà dal menu
 * 
 */

public class Livello extends JRadioButtonMenuItem {

	/**
	 * 
	 * @param livello
	 *            Indica il livello a cui settare la difficoltà.
	 * @param text
	 *            Testo da inserire nel bottone.
	 * @param selected
	 *            Necessaria per settare il bottone selezionato.
	 */
	public Livello(final int livello, String text, boolean selected) {
		super(text, selected);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// setta il livello di difficoltà
				Gioco.livello = livello;
				FileDebug.accoda("Livello cambiato: " + livello);
			}
		});
	}
}