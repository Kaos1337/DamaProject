package it.dama.controller;

import java.util.ArrayList;
import java.util.Random;
import it.dama.controller.Ris_Mangiata.BiancheMangianti;
import it.dama.controller.Ris_Mangiata.PermutazioniMangiata;
import it.dama.controller.Ris_Mangiata.TracciaMangiata;
import it.dama.controller.Vittoria;
import it.dama.model.ColorePezzo;
import it.dama.model.Damone;
import it.dama.model.Pedina;
import it.dama.model.Scacchiera;
import it.dama.view.Gioco;
import it.dama.view.Tavolo;

/**
 * 
 * il funzionamento è basato sul creare una scacchiera temporanea che mostri il
 * punto di vista dell'IA ma con i colori invertiti per sfruttare le stesse
 * classi già realizzate; dopo aver effettuato la mossa la scacchiera originaria
 * è sovrascritta con quella temporanea tornando al punto di vista del giocatore
 * 
 */
public class TurnoIASmart {

	private Scacchiera original;
	private Scacchiera temp = new Scacchiera();

	/**
	 * 
	 * @param t
	 *            Tavolo sul quale eseguire l'inteligenza artificiale.
	 */
	public TurnoIASmart(Tavolo t) {
		original = t.getScacchiera();

		scambiaColori(t.getScacchiera(), temp);

		new Vittoria(t, temp, ColorePezzo.BIANCO);

		// controllo se il Bianco ha vinto verificando se il nero (ora bianco)
		// non ha pezzi o non può muovere
		if (!temp.getVittoria()) {
			if (Gioco.livello == 0)
				mossaRandom();
			else
				mossaIterata(Gioco.livello);

			scambiaColori(temp, t.getScacchiera());

			// controllo se il Nero ha vinto verificando se il bianco non ha
			// pezzi o non può muovere
			new Vittoria(t, t.getScacchiera(), ColorePezzo.NERO);
		} else
			t.getScacchiera().setVittoria();
	}

	/**
	 * Chiamata iterativa della mossa dell'inteligenza artificiale.
	 * 
	 * @param s
	 *            Scacchiera sulla quale eseguire le mosse.
	 * @param liv
	 *            Livello di difficoltà che diminuisce ad ogni iterazione.
	 */
	public TurnoIASmart(Scacchiera s, int liv) {
		original = s;

		scambiaColori(original, temp);
		if (liv == 0)
			mossaRandom();
		else
			mossaIterata(liv - 1);

		scambiaColori(temp, original);
	}

	/**
	 * opera la mossa migliore eseguendo tutte quelle possibili e valutando le
	 * conseguenze nel turno successivo
	 * 
	 * @param liv
	 *            Indica l'iterazione alla quale si trova l'inteligenza
	 *            artificiale.
	 */
	public void mossaIterata(int liv) {

		// contiene le pedine nere che possono mangiare
		BiancheMangianti n = new BiancheMangianti(temp);
		// contiene tutte le pedine nere
		ArrayList<Pedina> nere = new ArrayList<Pedina>();
		Pedina p = null;

		for (Pedina[] f : temp.getConfig())
			for (Pedina i : f)
				// perchè ora sono invertite
				if (i != null && i.getColore() == ColorePezzo.BIANCO) {
					new GuardaMosse(temp, i);
					nere.add(i);
				}

		// variabili per l'algoritmo della mossa migliore del TurnoIASmart
		TracciaMangiata bestTrace = null;
		Mossa bestMove = null;
		int[] bestValue = new int[] { Integer.MAX_VALUE, Integer.MIN_VALUE };
		// valore attuale del tavolo
		int[] preMove = temp.valorePezzi();

		// se nessuno può mangiare
		if (n.isEmpty()) {

			// controlla il valore di ogni mossa possibile
			for (Pedina i : nere)
				for (Mossa k : i.getMosse())
					if (k != null) {
						// prova con questa mossa
						k.esegui(temp);
						// agisce l'IA più sciocca per simulare l'azione del
						// giocatore
						new TurnoIASmart(temp, liv);
						// ristima la situazione
						int[] postMove = temp.valorePezzi();

						if (preMove[0] - postMove[0] < bestValue[0]) {
							// se ho una situazione dei bianchi migliore:

							// tengo la possibile mossa
							bestMove = k;
							// setto il suo valore per i bianchi
							bestValue[0] = preMove[0] - postMove[0];
						}

						// se ho una stessa situazione per i bianchi
						else if (preMove[0] - postMove[0] == bestValue[0])
							// se la sitiazione dei neri peggiora
							if (preMove[1] - postMove[1] > bestValue[1]) {
								// tengo la possibile mossa
								bestMove = k;
								// setto il suo valore per i neri
								bestValue[1] = preMove[1] - postMove[1];
							}

							// se nessuna differenza di valore, tieni una a caso
							else if (preMove[1] - postMove[1] == bestValue[1]) {
								bestMove = new Random().nextBoolean() ? bestMove: k;
							}

						// devo annullare il test sulla mossa
						scambiaColori(original, temp);
					}

			// se l'iterazione non ha trovato mosse possibili, ritorna per
			// chiudere
			if (bestMove == null)
				return;
			// opera la mossa
			bestMove.esegui(temp);

		}

		// se può mangiare
		else {
			// raffino i dati
			PermutazioniMangiata t = n.controllaPriorita();

			for (TracciaMangiata i : t)
				if (i != null) {
					mangiaTutto(i, p);

					// agisce l'IA più sciocca per simulare l'azione del
					// giocatore
					new TurnoIASmart(temp, liv);
					// ristima la situazione
					int[] postMove = temp.valorePezzi();

					if (preMove[0] - postMove[0] < bestValue[0]) {
						// se ho una situazione dei bianchi migliore:

						// tengo la possibile mossa
						bestTrace = i;
						// setto il suo valore per i bianchi
						bestValue[0] = preMove[0] - postMove[0];
					}

					else if (preMove[0] - postMove[0] == bestValue[0])
						// se ho una stessa situazione per i bianchi:

						// se la sitiazione dei neri peggiora
						if (preMove[1] - postMove[1] > bestValue[1]) {
							// tengo la possibile mossa
							bestTrace = i;
							// setto il suo valore per i neri
							bestValue[1] = preMove[1] - postMove[1];
						}
						// se nessuna differenza di valore, tieni una a caso
						else if (preMove[1] - postMove[1] == bestValue[1]) {
							bestTrace = new Random().nextBoolean() ? bestTrace : i;
						}
					// devo annullare il test sulla mossa
					scambiaColori(original, temp);
				}
			// opera la mossa
			mangiaTutto(bestTrace, p);
		}
	}

	/**
	 * 
	 * Esegue l'intera traccia di mangiata se mangia più di una pedina.
	 * 
	 * @param t
	 *            Traccia da eseguire.
	 * @param p
	 *            Pedina che deve eseguire la traccia.
	 */
	public void mangiaTutto(TracciaMangiata t, Pedina p) {
		Pedina verso;

		p = t.getStart();
		verso = t.getNext().getStart();
		// esegue il primo salto
		(new Mossa(p, verso)).esegui(temp);

		if (t.getNext().getNext() != null) {
			p = t.getNext().getStart();
			p.setColore(ColorePezzo.BIANCO);
			verso = t.getNext().getNext().getStart();
			// esegue il secondo salto se c'è
			(new Mossa(p, verso)).esegui(temp);

			if (t.getNext().getNext().getNext() != null) {
				p = t.getNext().getNext().getStart();
				p.setColore(ColorePezzo.BIANCO);
				verso = t.getNext().getNext().getNext().getStart();
				// esegue il terzo salto se c'è
				(new Mossa(p, verso)).esegui(temp);
			}
		}
	}

	/**
	 * Opera una mossa casuale nei limiti delle regole.
	 */
	public void mossaRandom() {

		BiancheMangianti n = new BiancheMangianti(temp);
		ArrayList<Pedina> nere = new ArrayList<Pedina>();
		Pedina p;
		Mossa m;

		for (Pedina[] f : temp.getConfig())
			for (Pedina i : f)
				// perchè ora sono invertite
				if (i != null && i.getColore() == ColorePezzo.BIANCO) {
					new GuardaMosse(temp, i);
					for (Mossa j : i.getMosse())
						if (j != null) {
							nere.add(i);
							break;
						}
				}

		// se nessuno può mangiare
		if (n.isEmpty()) {
			boolean puoMuoversi = false;

			// se il nero non ha pedine non deve fare mosse in quanto il bianco
			// ha vinto ed è uscita la finestra della vittoria
			if (nere.size() == 0)
				return;

			do {
				// scegli una nera a caso
				p = nere.get((new Random()).nextInt(nere.size()));
				for (Mossa k : p.getMosse())
					if (k != null)
						puoMuoversi = true;
			}
			// finchè ne sceglie una che può muoversi
			while (!puoMuoversi);

			do {
				// sceglie una mossa della pedina
				m = p.getMosse()[(new Random().nextInt(p.getMosse().length))];
			}
			// finchè ne sceglie una possibile
			while (m == null);

			// opera la mossa
			m.esegui(temp);
		}

		// se può mangiare
		else {
			Pedina verso;
			// raffino i dati
			PermutazioniMangiata t = n.controllaPriorita();
			Random r = new Random();

			// se fai random invece che usare selta rischia di cambiare pedina
			// che mangia
			int scelta;
			do {
				scelta = r.nextInt(t.size());
			} while (t.get(scelta) == null);
			p = t.get(scelta).getStart();
			verso = t.get(scelta).getNext().getStart();
			// esegue il primo salto
			(new Mossa(p, verso)).esegui(temp);

			if (t.get(scelta).getNext().getNext() != null) {
				p = t.get(scelta).getNext().getStart();
				p.setColore(ColorePezzo.BIANCO);
				verso = t.get(scelta).getNext().getNext().getStart();
				// esegue il secondo salto se c'è
				(new Mossa(p, verso)).esegui(temp);

				if (t.get(scelta).getNext().getNext().getNext() != null) {
					p = t.get(scelta).getNext().getNext().getStart();
					p.setColore(ColorePezzo.BIANCO);
					verso = t.get(scelta).getNext().getNext().getNext().getStart();
					// esegue il terzo salto se c'è
					(new Mossa(p, verso)).esegui(temp);
				}
			}
		}
	}

	/**
	 * Inverte i colori delle scacchiera
	 * 
	 * @param in
	 *            Scacchiera d'origine su cui eseuire lo scambio dei colori.
	 * @param out
	 *            Scacchiera sulla quale impostare la scacchiera d'ingresso
	 *            inverita.
	 */
	public void scambiaColori(Scacchiera in, Scacchiera out) {
		// creo una scacchiera vuota
		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);
			ColorePezzo color;
			// scambia i colori bianco/nero
			switch (in.getPedina(x, y).getColore()) {
			case NERO:
				color = ColorePezzo.BIANCO;
				break;
			case BIANCO:
				color = ColorePezzo.NERO;
				break;
			default:
				color = ColorePezzo.VUOTO;
			}
			if (in.getPedina(x, y) instanceof Damone)
				out.posizionaDamone(color, 7 - x, 7 - y);
			else
				out.posizionaPedina(color, 7 - x, 7 - y);
		}

	}
}