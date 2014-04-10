package it.dama.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * Classe che definisce il testo nella finestra delle regole
 *
 */

public class FinestraRegole extends JFrame {

	public FinestraRegole() {
		
		JLabel etichetta = new JLabel();
		
		String testo = "<html><font size=5>&nbsp;Gioco della dama, regole italiane:</font><br><br>"
				+ "&nbsp;&nbsp;&nbsp;- presa obbligatoria;<br>"
				+ "&nbsp;&nbsp;&nbsp;- la pedina può catturare solo lungo le diagonali in avanti;<br>"
				+ "&nbsp;&nbsp;&nbsp;- la dama può catturare su tutte le diagonali;<br>"
				+ "&nbsp;&nbsp;&nbsp;- è obbligatorio catturare seguendo il percorso con più pezzi catturabili;<br>"
				+ "&nbsp;&nbsp;&nbsp;- è obbligatorio seguire il percorso con la \"qualità\" di pezzi migliore<br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(in caso di qualità uguale, la scelta è lasciata al giocatore);<br>"
				+ "&nbsp;&nbsp;&nbsp;- se una dama e una pedina possono entrambe catturare un pezzo,<br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;è obbligatorio catturare con la dama;<br>"
				+ "<br>&nbsp;(Fonte Wikipedia)";
		
		setSize(550, 250);
		etichetta.setText(testo);
		add(etichetta);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}
