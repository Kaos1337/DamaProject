package it.dama.model;

import it.dama.controller.Mossa;

/**
 * 
 * Classe che identifica un Damone, sottoclasse di Pedina. Contiene Mossa[] che
 * rappresenta le mosse che può fare indietro, quelle in avanti sono già
 * contenute nella supercalasse.
 * 
 */
public class Damone extends Pedina {

	private Mossa[] mosseI = new Mossa[2];

	/**
	 * 
	 * @param c
	 *            Colore da assegnare al damone.
	 * @param x
	 *            Cordinata x del damone.
	 * @param y
	 *            Cordinata y del damone.
	 */
	public Damone(ColorePezzo c, int x, int y) {
		super(c, x, y);
	}

	@Override
	/**
	 * @param pos Posizione della mossa nell'array delle mosse disponibili.
	 * 
	 * @return Ritorna la mossa nella posizione specificata.
	 */
	public Mossa getMossa(int pos) {
		if (pos < 2)
			return super.getMossa(pos);
		else
			return mosseI[pos - 2];
	}

	/**
	 * @return Ritorna l'intero array delle mosse che ha a disposizione.
	 */
	public Mossa[] getMosse() {
		return new Mossa[] { super.getMossa(0), super.getMossa(1), mosseI[0], mosseI[1] };
	}

	/**
	 * @param pos
	 *            Indice della pozizione dell'array nel quale inserire la mossa.
	 * @param m
	 *            Mossa da posizionare nella posizione pos.
	 */
	public void setMossa(int pos, Mossa m) {
		if (pos == 0 || pos == 1)
			super.setMossa(pos, m);
		else
			mosseI[pos - 2] = m;
	}
}