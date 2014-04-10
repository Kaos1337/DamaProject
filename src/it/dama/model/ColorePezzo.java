package it.dama.model;

/**
 * 
 * Colori dei componenti della scacchiera
 * 
 */
public enum ColorePezzo {
	/*
	 * -- Caselle fisiche --
	 * BIANCO = pedina giocatore
	 * NERO = pedina avversario
	 * 
	 * -- Caselle logiche --
	 * VUOTA = casella libera
	 * GIALLO = casella in cui mi posso spostare
	 */

	BIANCO, NERO, VUOTO, GIALLO;

	/**
	 * 
	 * @return Un carattere informativo sul colore
	 */
	public char toChar(){
		switch(this){
		case BIANCO: return 'b';
		case NERO: return 'n';
		case VUOTO: return '_';
		case GIALLO: return 'g';
		default: return '?';
		}
	}
}