package it.dama.controller;

import it.dama.model.ColorePezzo;
import it.dama.model.Damone;
import it.dama.model.Pedina;
import it.dama.model.Scacchiera;

/**
 * 
 * Controlla tute le mosse che una pedina può eseguire.
 * 
 */
public class GuardaMosse {

	/**
	 * 
	 * @param s
	 *            Scacchiera sulla quale effettuare il controllo.
	 * @param p
	 *            Pedina sulla quale eseguire il controllo.
	 */
	public GuardaMosse(Scacchiera s, Pedina p) {

			// variabile uitlizzata per evitare di considerare le mosse dove la
			// pedina non può mangiare nel caso fossimo già a conoscienza di una
			// mossa dove può farlo
			boolean mangiato = false;

			// controllo se può mangiare in alto a sinistra
			if (p.getX() > 1 && p.getY() > 1)
				if (s.getPedina(p.getX() - 1, p.getY() - 1).getColore() == ColorePezzo.NERO
						&& s.getPedina(p.getX() - 2, p.getY() - 2).isLogica()) {
					p.setMossa(0, new Mossa(p, s.getPedina(p.getX() - 2, p.getY() - 2)));
					mangiato = true;
				} else
					p.setMossa(0, null);

			// controllo se può mangiare in alto a destra
			if (p.getX() < 6 && p.getY() > 1)
				if (s.getPedina(p.getX() + 1, p.getY() - 1).getColore() == ColorePezzo.NERO
						&& s.getPedina(p.getX() + 2, p.getY() - 2).isLogica()) {
					p.setMossa(1, new Mossa(p, s.getPedina(p.getX() + 2, p.getY() - 2)));
					mangiato = true;
				} else
					p.setMossa(1, null);

			// il damone può mangiare anche indietro
			if (p instanceof Damone) {
				// controllo se può mangiare in basso a sinistra
				if (p.getX() > 1 && p.getY() < 6)
					if (s.getPedina(p.getX() - 1, p.getY() + 1).getColore() == ColorePezzo.NERO
							&& s.getPedina(p.getX() - 2, p.getY() + 2).isLogica()) {
						p.setMossa(2, new Mossa(p, s.getPedina(p.getX() - 2, p.getY() + 2)));
						mangiato = true;
					} else
						p.setMossa(2, null);

				// controllo se può mangiare in basso a destra
				if (p.getX() < 6 && p.getY() < 6)
					if (s.getPedina(p.getX() + 1, p.getY() + 1).getColore() == ColorePezzo.NERO
							&& s.getPedina(p.getX() + 2, p.getY() + 2).isLogica()) {
						p.setMossa(3, new Mossa(p, s.getPedina(p.getX() + 2, p.getY() + 2)));
						mangiato = true;
					} else
						p.setMossa(3, null);
			}

			// nel caso in cui non può mangiare
			if (!mangiato) {
				// se ha lo spazio per andare in alto a sx
				if (p.getX() > 0 && p.getY() > 0)
					if (s.getPedina(p.getX() - 1, p.getY() - 1).isLogica())
						p.setMossa(0, new Mossa(p, s.getPedina(p.getX() - 1, p.getY() - 1)));
					else
						p.setMossa(0, null);

				// se ha lo spazio per andare in alto a dx
				if (p.getX() < 7 && p.getY() > 0)
					if (s.getPedina(p.getX() + 1, p.getY() - 1).isLogica())
						p.setMossa(1, new Mossa(p, s.getPedina(p.getX() + 1, p.getY() - 1)));
					else
						p.setMossa(1, null);

				if (p instanceof Damone) {
					// se ha lo spazio per andare in basso a sx
					if (p.getX() > 0 && p.getY() < 7)
						if (s.getPedina(p.getX() - 1, p.getY() + 1).isLogica())
							p.setMossa(2, new Mossa(p, s.getPedina(p.getX() - 1, p.getY() + 1)));
						else
							p.setMossa(2, null);

					// se ha lo spazio per andare in basso a dx
					if (p.getX() < 7 && p.getY() < 7)
						if (s.getPedina(p.getX() + 1, p.getY() + 1).isLogica())
							p.setMossa(3, new Mossa(p, s.getPedina(p.getX() + 1, p.getY() + 1)));
						else
							p.setMossa(3, null);
				}

			}

		}
}