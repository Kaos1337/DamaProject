package it.dama.model;

import it.dama.controller.Mossa;

/**
 * 
 * Classe che definisce una pedina. Contiene le cordinate in cui è posta la
 * pedina, il colore della pedina e un array di mosse a disposizione.
 * 
 */

public class Pedina {

	private int x;
	private int y;
	private ColorePezzo color;
	private Mossa[] mosseA = new Mossa[2];

	/**
	 * Costruttore della pedina.
	 * 
	 * @param c
	 *            Colore da assegnare alla pedina.
	 * @param x
	 *            Cordinata x della pedina.
	 * @param y
	 *            Cordinata y della pedina.
	 */
	public Pedina(ColorePezzo c, int x, int y) {
		color = c;
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return Restituisce il colore della pedina.
	 */
	public ColorePezzo getColore() {
		return color;
	}

	/**
	 * 
	 * @param c
	 *            Colore a cui settare la pedina.
	 */
	public void setColore(ColorePezzo c) {
		color = c;
	}

	/**
	 * 
	 * @return Cordinata x della pedina.
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return Cordinata y della pedina.
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return Array di mosse che ha a disposizione.
	 */
	public Mossa[] getMosse() {
		return mosseA;
	}

	/**
	 * 
	 * @param pos
	 *            Indice della mossa da restituire.
	 * @return Restituisce la mossa specificata
	 */
	public Mossa getMossa(int pos) {
		return mosseA[pos];
	}

	/**
	 * 
	 * @param pos
	 *            Indice della mossa da settare
	 * @param m
	 *            Mossa da settare.
	 */
	public void setMossa(int pos, Mossa m) {
		mosseA[pos] = m;
	}

	/**
	 * 
	 * @return Ritorna true se la casella è vuota
	 */
	public boolean isLogica() {
		switch (color) {
		case NERO:
		case BIANCO:
			return false;
		default:
			return true;
		}
	}

	/**
	 * 
	 * @return Stringa con informazioni sulla pedina
	 */
	public String toString() {
		String ris = (this instanceof Damone) ? "dama " : "pedina ";
		switch (color) {
		case BIANCO:
			ris += "bianca";
			break;
		case NERO:
			ris += "nera";
			break;
		case VUOTO:
			ris = "casella vuota";
			break;
		case GIALLO:
			ris = "casella gialla";
			break;
		default:
			ris += "SCONOSCIUTA";
		}
		String lettere = "ABCDEFGH";
		return ris + ": (" + lettere.charAt(x) + ", " + (y + 1) + ")";
	}

	/**
	 * @param other
	 *            Oggetto da controllare se è uguale alla pedina this.
	 * @return ritorna true se la pedina other è uguale a this
	 */
	public boolean equals(Object other) {

		if (other.getClass() != this.getClass())
			return false;

		Pedina p = (Pedina) other;

		if (p.getColore() == color && p.getX() == x && p.getY() == y)
			return true;
		return false;
	}

	/**
	 * 
	 * Una pedina può mangiare se una delle pedine in alto è nera e la casella
	 * di destinazione è vuota, nel caso del damone controlla anche in basso.
	 * 
	 * @param s
	 *            Scacchiera su cui esegguire il controllo.
	 * @return Ritorna true se può mangiare almeno un pezzo avversario.
	 */
	public boolean puoMangiare(Scacchiera s) {

		// avanti a sinistra
		if (getX() > 1
				&& getY() > 1
				&& s.getPedina(getX() - 1, getY() - 1).getColore() == ColorePezzo.NERO
				&& s.getPedina(getX() - 2, getY() - 2).isLogica())
			return true;

		// avanti a destra
		if (getX() < 6
				&& getY() > 1
				&& s.getPedina(getX() + 1, getY() - 1).getColore() == ColorePezzo.NERO
				&& s.getPedina(getX() + 2, getY() - 2).isLogica())
			return true;

		// damone
		if (this instanceof Damone) {
			// indietro a sinistra
			if (getX() > 1
					&& getY() < 6
					&& s.getPedina(getX() - 1, getY() + 1).getColore() == ColorePezzo.NERO
					&& s.getPedina(getX() - 2, getY() + 2).isLogica())
				return true;

			// indietro a destra
			if (getX() < 6
					&& getY() < 6
					&& s.getPedina(getX() + 1, getY() + 1).getColore() == ColorePezzo.NERO
					&& s.getPedina(getX() + 2, getY() + 2).isLogica())
				return true;
		}

		return false;
	}
}