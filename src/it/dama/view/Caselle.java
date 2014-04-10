package it.dama.view;

import it.dama.model.ColorePezzo;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * 
 * Classe per assegnare l'immagine ai componenti della scacchiera
 * 
 */

public class Caselle extends JPanel {

	private final Image pedina_bianca_sel = Toolkit.getDefaultToolkit()
			.createImage(getClass().getResource("/img/pedina_bianca_sel.jpg"));
	private final Image casella_bianca = Toolkit.getDefaultToolkit()
			.createImage(getClass().getResource("/img/casella_bianca.jpg"));
	private final Image casella_nera = Toolkit.getDefaultToolkit().createImage(
			getClass().getResource("/img/casella_nera.jpg"));
	private final Image pedina_nera = Toolkit.getDefaultToolkit().createImage(
			getClass().getResource("/img/pedina_nera.jpg"));
	private final Image pedina_bianca = Toolkit.getDefaultToolkit()
			.createImage(getClass().getResource("/img/pedina_bianca.jpg"));
	private final Image casella_sel = Toolkit.getDefaultToolkit().createImage(
			getClass().getResource("/img/casella_sel.jpg"));
	private final Image dama_bianca = Toolkit.getDefaultToolkit().createImage(
			getClass().getResource("/img/dama_bianca.jpg"));
	private final Image dama_nera = Toolkit.getDefaultToolkit().createImage(
			getClass().getResource("/img/dama_nera.jpg"));
	private final Image dama_bianca_sel = Toolkit.getDefaultToolkit()
			.createImage(getClass().getResource("/img/dama_bianca_sel.jpg"));
	private final Image dama_nera_sel = Toolkit.getDefaultToolkit()
			.createImage(getClass().getResource("/img/dama_nera_sel.jpg"));
	private final Image pedina_nera_sel = Toolkit.getDefaultToolkit()
			.createImage(getClass().getResource("/img/pedina_nera_sel.jpg"));

	private Image img;

	/**
	 * 
	 * @param s
	 *            E' una stringa che indica quale immagine deve assegnare.
	 * @param c
	 *            Indica se l'inteligenza artificiale sta giocando dala parte de
	 *            nero o del bianco, necessario per definire come disegnare la
	 *            scacchiera
	 */

	public Caselle(String s, ColorePezzo c) {
		this.setLayout(new GridLayout(1, 1));

		switch (s) {
		case "b":
			img = casella_bianca;
			break;
		case "n":
			img = casella_nera;
			break;
		case "pn":
			img = (c == ColorePezzo.NERO ? pedina_nera : pedina_bianca);
			break;
		case "pb":
			img = (c == ColorePezzo.NERO ? pedina_bianca : pedina_nera);
			break;
		case "cs":
			img = casella_sel;
			break;
		case "pbs":
			img = (c == ColorePezzo.NERO ? pedina_bianca_sel : pedina_nera_sel);
			break;
		case "db":
			img = (c == ColorePezzo.NERO ? dama_bianca : dama_nera);
			break;
		case "dn":
			img = (c == ColorePezzo.NERO ? dama_nera : dama_bianca);
			break;
		case "dns":
			img = (c == ColorePezzo.NERO ? dama_nera_sel : dama_bianca_sel);
			break;
		case "dbs":
			img = (c == ColorePezzo.NERO ? dama_bianca_sel : dama_nera_sel);
			break;
		}
		loadImage(img);

	}

	/**
	 * 
	 * @param img Immagine da assegnare al pannello
	 */
	
	private void loadImage(Image img) {
		try {
			MediaTracker track = new MediaTracker(this);
			track.addImage(img, 0);
			track.waitForID(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * assegna dimensione all'immagine nel JPanel
	 */
	protected void paintComponent(Graphics g) {
		int larghezza = this.getWidth();
		int altezza = this.getHeight();
		
		setOpaque(false);
		g.drawImage(img, 0, 0, larghezza, altezza, null);
		super.paintComponent(g);

	}
}