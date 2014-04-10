package it.dama.model;

import it.dama.controller.Mossa;
import it.dama.controller.Ris_Mangiata.BiancheMangianti;
import it.dama.controller.Ris_Mangiata.PermutazioniMangiata;
import it.dama.model.ColorePezzo;
import it.dama.model.Pedina;
import it.dama.view.FileDebug;

/**
 * 
 * Classe che definisce una scacchiera.
 * 
 * 
 */
public class Scacchiera {

	// configurazione della scacchiera
	private Pedina[][] config = new Pedina[8][8];
	// specifica la pedina selezionata se presente
	private Pedina selezionata = null;
	// tiene traccia delle mangiate
	private PermutazioniMangiata tracce = null;
	// array list contenente le bianche che possono mangiare
	private static BiancheMangianti bianchemangianti;
	// colore pezzo avversario
	private static ColorePezzo coloreIA = ColorePezzo.NERO;
	// indica se il nero sat muovendo, utilizzato per disabilitare i pulsanti
	private boolean turnoNero;
	// usato per capire quando impedire ai bianchi di reagire a fine partita
	private boolean vittoria;

	/**
	 * Riempie l'array di pedine vuote.
	 */
	public Scacchiera() {

		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);
			config[x][y] = new Pedina(ColorePezzo.VUOTO, x, y);
		}
		// inizializza campo
		this.setBiMa();

	}

	/**
	 * Imposta la scacchiera sulla configurazione di partenza.
	 */
	public void reset() {
		turnoNero = false;
		vittoria = false;
		selezionata = null;
		tracce = null;
		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);
			if (y < 3)
				config[x][y] = new Pedina(ColorePezzo.NERO, x, y);
			else if (y > 4)
				config[x][y] = new Pedina(ColorePezzo.BIANCO, x, y);
			else
				config[x][y] = new Pedina(ColorePezzo.VUOTO, x, y);

		}
	}

	/**
	 * 
	 * @return Ritorna la pedina selezionata.
	 */
	public Pedina getSelezionata() {
		return selezionata;
	}

	/**
	 * 
	 * @param x
	 *            Cordinata x della pedina desiderata.
	 * @param y
	 *            Cordianta y della pedina ch si desidera.
	 * @return Ritorna la Pedina nelle codrinate specificate.
	 */
	public Pedina getPedina(int x, int y) {
		return config[x][y];
	}

	/**
	 * 
	 * @param p
	 *            Pedina da impostare come selezionata.
	 */
	public void setSelezionata(Pedina p) {
		selezionata = p;
		if (p != null)
			FileDebug.accoda("Selezionata " + p.toString());
		else FileDebug.accoda("Rimossa selezione");
	}
	
	/**
	 * 
	 * @return Ritorna una stringa con informazioni sull'attuale configurazione
	 * 			della scacchiera.
	 */
 	public String toString(){

 		String ris = "";
 		for (int y = 0; y < 8; y++) {
 			for(int x = 0; x < 8; x++){
 				ris +="|";
 				if(config[x][y]==null) ris += " ";
 				else {
 					if(config[x][y] instanceof Damone) 
 						ris += Character.toUpperCase(config[x][y].getColore().toChar());
 					else
 						ris += config[x][y].getColore().toChar();
 				}
 			}
 			ris += "|\n";
 		}
 		return ris;
	}

	/**
	 * 
	 * @param x
	 *            Cordinata x della pedina da impostare come selezionata.
	 * @param y
	 *            Cordinata y della pedina da impostare come selezionata.
	 */
	public void setSelezionata(int x, int y) {
		selezionata = config[x][y];
	}

	/**
	 * 
	 * @param c
	 *            Colore della pedina da posizionare.
	 * @param x
	 *            Corinata x ove posizionare la pedina.
	 * @param y
	 *            Corinata y ove posizionare la pedina.
	 */
	public void posizionaPedina(ColorePezzo c, int x, int y) {
		config[x][y] = new Pedina(c, x, y);
	}

	/**
	 * 
	 * @param c
	 *            Colore del damone da posizionare.
	 * @param x
	 *            Corinata x ove posizionare il damone.
	 * @param y
	 *            Corinata y ove posizionare il damone.
	 */
	public void posizionaDamone(ColorePezzo c, int x, int y) {
		config[x][y] = new Damone(c, x, y);
	}

	/**
	 * 
	 * @return Ritorna l'array di pedine come sono disposte sulla scacchiera.
	 */
	public Pedina[][] getConfig() {
		return config;
	}

	/**
	 * rimuove caselle e pedine selezionate.
	 */
	public void rimuoviGialli() {
		if (selezionata != null) {
			for (Mossa m : selezionata.getMosse())
				if (m != null) {
					int x = m.getVerso().getX();
					int y = m.getVerso().getY();
					config[x][y] = new Pedina(ColorePezzo.VUOTO, x, y);
				}
		}
	}

	/**
	 * 
	 * @return Ritorna le strade possibili che sta seguendo per le mangiate
	 *         consecutive.
	 */
	public PermutazioniMangiata getTracce() {
		return tracce;
	}

	/**
	 * 
	 * @param perman
	 *            Assegna una traccia di mangiata.
	 */
	public void setTracce(PermutazioniMangiata perman) {
		this.tracce = perman;
	}

	/**
	 * 
	 * @param n
	 *            Array list contenente le bianche che possono mangiare.
	 * @return Restituisce un array list di tracce ove si può mangiare.
	 */
	public PermutazioniMangiata daiPermutazioni(BiancheMangianti n) {
		PermutazioniMangiata m = null;

		// se non ci sono tracce da seguire raffino le permutazioni e setto le nuove
		if (getTracce() == null) {
			// raffinazione dati
			m = n.controllaPriorita();

			// aggiunge la traccia, che sta seguendo, alla scacchiera
			setTracce(m);

		}

		// altrimenti se ne sto seguendo conto solo quelle
		else
			m = getTracce();
		return m;
	}

	/**
	 * 
	 * @return Se true vuol dire che è turno del computer.
	 */
	public boolean getTurnoNero() {
		return turnoNero;
	}

	/**
	 * 
	 * @param b
	 *            Valore da assegnare a turnoNero.
	 */
	public void setTurnoNero(boolean b) {
		turnoNero = b;
	}

	/**
	 * 
	 * @return ritorna true se qualcuno ha vinto.
	 */
	public boolean getVittoria() {
		return vittoria;
	}

	/**
	 * imposto la vittoria
	 */
	public void setVittoria() {
		vittoria = true;
	}

	/**
	 * Assegna un valore, in base alle pedine, al bianco e al nero, le pedine
	 * valgono 1 e i damoni 2.
	 * 
	 * @return Ritorna array di 2 interi per valutare la
	 *         scacchiera(#bianche,#nere)
	 */
	public int[] valorePezzi() {

		int b = 0, n = 0;

		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);
			if (config[x][y].getColore() == ColorePezzo.BIANCO) {
				b++;
				if (config[x][y] instanceof Damone)
					b++;
			}
			if (config[x][y].getColore() == ColorePezzo.NERO) {
				n++;
				if (config[x][y] instanceof Damone)
					n++;
			}
		}
		return new int[] { b, n };
	}

	/**
	 * 
	 * @param c
	 *            Colore con il quale l'inteligenza artificiale deve giocare.
	 */
	public void setColoreIA(ColorePezzo c) {
		coloreIA = c;
	}

	/**
	 * 
	 * @return Ritorna il colore col quale l'inteligenza artificiale stà
	 *         giocando.
	 */
	public ColorePezzo getColoreIA() {
		return coloreIA;
	}

	/**
	 * 
	 * @param p
	 *            Pedina rispetto alla quale vedere dove si può muovere,
	 *            selezionando di conseguenza le caselle ove può muovere.
	 * @param n
	 *            Array list contenente le pedine che possono mangiare.
	 * @return true se la pedina può mangiare
	 */
	public boolean mostraOpzioni(Pedina p, BiancheMangianti n) {
		boolean muove = false;
		PermutazioniMangiata mangiate = this.daiPermutazioni(n);

		for (Mossa m : p.getMosse())
			// metto il giallo se non ci sono mangiate e mi posso muovere,
			// oppure se la destinazione fa parte delle mangiate
			if (m != null
					&& (mangiate.isEmpty() || mangiate.saltaIn(m.getVerso()))) {
				this.posizionaPedina(ColorePezzo.GIALLO, m.getVerso().getX(), m.getVerso().getY());
				muove = true;
			}
		return muove;
	}

	/**
	 * 
	 * @return Array list di pedine che possono mangiare.
	 */
	public BiancheMangianti getBiMa() {
		return bianchemangianti;
	}

	/**
	 * Imposta l'array list delle pedine che possono mangiare.
	 */
	public void setBiMa() {
		bianchemangianti = new BiancheMangianti(this);
	}
}
