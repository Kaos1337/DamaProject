package it.dama.controller.Ris_Mangiata;

import java.util.ArrayList;
import it.dama.model.ColorePezzo;
import it.dama.model.Damone;
import it.dama.model.Pedina;
import it.dama.model.Scacchiera;

/**
 * 
 * Array lista contenente le pedina che possono mangiare.
 * 
 */
public class BiancheMangianti extends ArrayList<Pedina> {

	private Scacchiera s;

	/**
	 * Alla creazione riempie la lista con le pedine che possono mangiare.
	 * 
	 * @param s
	 *            Scacchiera su cui controllare le pedina che possono mangaire.
	 */
	public BiancheMangianti(Scacchiera s) {
		this.s = s;
		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);
			if (s.getPedina(x, y).getColore() == ColorePezzo.BIANCO
					&& puoMangiare(s.getPedina(x, y)))
				add(s.getPedina(x, y));
		}
	}

	/**
	 * 
	 * @param p
	 *            Pedina su cui controllare se può mangiare.
	 * @return Ritorna true se la pedina può mangiare ameno una pedina
	 *         avversaria.
	 */
	private boolean puoMangiare(Pedina p) {

		// avanti a sinistra
		if (p.getX() > 1
				&& p.getY() > 1
				&& s.getPedina(p.getX() - 1, p.getY() - 1).getColore() == ColorePezzo.NERO
				&& s.getPedina(p.getX() - 2, p.getY() - 2).isLogica())
			return true;

		// avanti a destra
		if (p.getX() < 6
				&& p.getY() > 1
				&& s.getPedina(p.getX() + 1, p.getY() - 1).getColore() == ColorePezzo.NERO
				&& s.getPedina(p.getX() + 2, p.getY() - 2).isLogica())
			return true;

		// damone
		if (p instanceof Damone) {
			// indietro a sinistra
			if (p.getX() > 1
					&& p.getY() < 6
					&& s.getPedina(p.getX() - 1, p.getY() + 1).getColore() == ColorePezzo.NERO
					&& s.getPedina(p.getX() - 2, p.getY() + 2).isLogica())
				return true;

			// indietro a destra
			if (p.getX() < 6
					&& p.getY() < 6
					&& s.getPedina(p.getX() + 1, p.getY() + 1).getColore() == ColorePezzo.NERO
					&& s.getPedina(p.getX() + 2, p.getY() + 2).isLogica())
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return Restituisce la scacchiera su cui sta controllando chi può
	 *         mangiare.
	 */
	public Scacchiera getScacchiera() {
		return s;
	}

	/**
	 * Tra le pedine che possono mangiare elimina quelle con priorità inferiore.
	 * 
	 * @return Ritorna un array list delle traccie disponibili.
	 */
	public PermutazioniMangiata controllaPriorita() {
		// creo un arraylist di tutti i movimenti possibili di ogni pezzo che
		// può mangiare
		PermutazioniMangiata p = new PermutazioniMangiata(this, s);

		// mantiene le prese più lunghe disponibili (max 3 salti)
		p.soloLunghe();

		// mantiene solo le prese che sono effettuate con la dama se presente
		p.soloIniziaDama();

		// mantiene solo le prese che hanno la miglior qualità complessiva
		p.soloMigliore();

		// mantiene solo le prese che hanno più vicino la dama
		p.soloPrimaDama();

		return p;
	}
}