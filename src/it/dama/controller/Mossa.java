package it.dama.controller;

import it.dama.model.ColorePezzo;
import it.dama.model.Damone;
import it.dama.model.Pedina;
import it.dama.model.Scacchiera;

/**
 * 
 * Identifica un mossa da una posizione ad un'altra.
 * 
 */
public class Mossa {

	private Pedina da;
	private Pedina verso;

	/**
	 * 
	 * @param da
	 *            Casella di partenza.
	 * @param verso
	 *            Casella di arrivo.
	 */
	public Mossa(Pedina da, Pedina verso) {
		this.da = da;
		if (da instanceof Damone)
			this.verso = new Damone(ColorePezzo.VUOTO, verso.getX(),
					verso.getY());
		else
			this.verso = verso;
	}

	/**
	 * Esegue effettivamente la mossa sulla scacchiera.
	 * 
	 * @param s
	 *            Scacchiera sulla quale eseguire la mossa.
	 */
	public void esegui(Scacchiera s) {
		// se arriva al bordo opposto diventa damone, se era damone resta damone
		if (verso.getY() == 0 || da instanceof Damone) {
			s.posizionaDamone(da.getColore(), verso.getX(), verso.getY());
		} else
			s.posizionaPedina(da.getColore(), verso.getX(), verso.getY());

		// la casella dove si trovava diventa vuota
		s.posizionaPedina(ColorePezzo.VUOTO, da.getX(), da.getY());

		// se lo spostamento è grande da farmi intuire che ha mangiato
		if (da.getX() - verso.getX() == 2 || da.getX() - verso.getX() == -2) {
			int x = (da.getX() + verso.getX()) / 2;
			int y = (da.getY() + verso.getY()) / 2;
			s.posizionaPedina(ColorePezzo.VUOTO, x, y);
		}
	}

	
	public String toString(){
		return da.toString() + " ==> " + verso.toString();
	}
	
	/**
	 * 
	 * @return Restituisce la casella dalla quale inizia la mossa.
	 */
	public Pedina getDa() {
		return da;
	}

	/**
	 * 
	 * @return Restituisce la casella ove termina la mossa.
	 */
	public Pedina getVerso() {
		return verso;
	}
}
