package it.dama.controller;

import it.dama.model.ColorePezzo;
import it.dama.model.Scacchiera;
import it.dama.view.Tavolo;
import it.dama.view.Vincitore;

/**
 * 
 * Classe per verificare se qualcuno ha vinto.
 * 
 */
public class Vittoria {

	private Scacchiera s;

	/**
	 * Decreta se il giocatore del colore passato ha vinto.
	 * 
	 * @param t
	 *            Tavolo su cui chiamare l'eventuale nuova partita.
	 * @param s
	 *            Scacchiera su cui eseguire il controllo.
	 * 
	 * @param c
	 *            Colore rispetto a cuie eseguire il controllo.
	 * 
	 */
	public Vittoria(Tavolo t, Scacchiera s, ColorePezzo c) {
		this.s = s;
		if (!puoMuovere()) {
			s.setVittoria();
			new Vincitore(c, t);
		}
	}

	/**
	 * 
	 * @return ritorna true se l'avversario può muovere.
	 */
	public boolean puoMuovere() {

		// aggiorno le mosse dei pezzi bianchi e verifico se almeno uno può
		// muoversi
		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);
			if (s.getPedina(x, y).getColore() == ColorePezzo.BIANCO) {
				new GuardaMosse(s, s.getPedina(x, y));
				for (Mossa m : s.getPedina(x, y).getMosse())
					if (m != null)
						return true;
			}
		}
		return false;
	}
}