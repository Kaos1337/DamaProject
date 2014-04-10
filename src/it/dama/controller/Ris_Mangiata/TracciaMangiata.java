package it.dama.controller.Ris_Mangiata;

import it.dama.model.Pedina;

/**
 * 
 * Identifica una traccia di mangiata.
 * 
 */
public class TracciaMangiata {

	private Pedina p;
	private TracciaMangiata next;

	/**
	 * Inizializza la tracci mangiata vuota.
	 */
	public TracciaMangiata() {
		p = null;
		next = null;
	}

	/**
	 * Inizializza la traccia mangiata.
	 * 
	 * @param p
	 *            Pedina di partenza della traccia.
	 * @param next
	 *            Traccia mangaita successiva a cui puntare.
	 */
	public TracciaMangiata(Pedina p, TracciaMangiata next) {
		this.p = p;
		this.next = next;
	}

	/**
	 * 
	 * @return Ritorna la pedina da cui inizia la traccia.
	 */
	public Pedina getStart() {
		return p;
	}

	/**
	 * 
	 * @return Ritorna il seguito della traccia.
	 */
	public TracciaMangiata getNext() {
		return next;
	}

	/**
	 * 
	 * @param p
	 *            Pedina da impostare come inizio traccia.
	 */
	public void setStart(Pedina p) {
		this.p = p;
	}

	/**
	 * 
	 * @param next
	 *            Traccia da impostare come seguito di quella attuale.
	 */
	public void setNext(TracciaMangiata next) {
		this.next = next;
	}

	/**
	 * 
	 * @return Ritorna la lunghezza della traccia in salti.
	 */
	public int lenght() {
		if (next == null)
			return 0;
		else
			return 1 + next.lenght();
	}
}