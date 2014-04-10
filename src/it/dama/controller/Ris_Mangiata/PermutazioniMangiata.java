package it.dama.controller.Ris_Mangiata;

import it.dama.model.Damone;
import it.dama.model.Pedina;
import it.dama.model.Scacchiera;
import java.util.ArrayList;

/**
 * 
 * Controllo delle regole per la mangiata.
 * 
 */
public class PermutazioniMangiata extends ArrayList<TracciaMangiata> {

	private BiancheMangianti n;

	/**
	 * Crea la lista vuota.
	 */
	public PermutazioniMangiata() {
		this.clear();
	}

	/**
	 * Costruttore mette nella lista tutte le permutazioni per la mangiata.
	 * 
	 * @param n
	 *            Array list si bianche che possono mangiare
	 * @param s
	 *            Scacchiera su cui sono le pedina.
	 */
	public PermutazioniMangiata(BiancheMangianti n, Scacchiera s) {

		this.n = n;
		
		/*for (Pedina t : n)
		 System.out.println(t.toString());
		*/
		
		// per ogni pedina nella lista di chi può mangiare
		for (Pedina p : n)
			if (p != null) {
				// sviluppo l'albero delle mangiate
				NodoMangiata nodo = new NodoMangiata(p);
				nodo.sviluppa(s, 3);
				addAll(nodo.elenca());
			}
		/*for (TracciaMangiata t : this)
			System.out.println(t.getStart().toString()+" "+t.getNext().getStart().toString());
		*/

	}

	/**
	 * Mantiene le permutazioni più lunghe, se sono tutte uguali non fa niente.
	 */
	public void soloLunghe() {

		int l = 1;
		for (TracciaMangiata i : this) {

			// se la lunghezza è inferiore del max trovato elimino la traccia
			if (i != null && i.lenght() < l)
				set(indexOf(i), null);

			// se la lunghezza è superiore al max trovato elimino i precedenti e
			// reimposto il max
			else if (i != null && i.lenght() > l) {
				l = i.lenght();
				for (int k = 0; k < indexOf(i); k++)
					set(k, null);

			}
		}
	}

	/**
	 * Mantiene le permutazioni che iniziano con la dama (se mancano non fa
	 * niente).
	 */
	public void soloIniziaDama() {

		for (TracciaMangiata i : this)
			if (i != null && i.getStart() instanceof Damone) {// se trova dama

				for (TracciaMangiata t : this)
					// rimuove tutte le pedine
					if (t != null && !(t.getStart() instanceof Damone))
						set(indexOf(t), null);

				return;
			}

	}

	/**
	 * Mantiene le permutazioni che hanno il miglior punteggio
	 * (pedina=1,dama=2).
	 */
	public void soloMigliore() {

		int max = 0;
		for (TracciaMangiata i : this) {
			if (i != null) {
				// ricorsione nei salti
				int valore = 0;
				// devo operare la ricorsione su una copia per non perdere i
				TracciaMangiata t = i;
				
				// un po più carino con il do while
				do {
					// trovo la pedina che mangio facendo la media
					// tra la casella di partenza e quella di destinazione
					if (n.getScacchiera().getPedina(
							t.getStart().getX()
									+ (t.getNext().getStart().getX() - t.getStart().getX()) / 2,
							t.getStart().getY()
									+ (t.getNext().getStart().getY() - t.getStart().getY()) / 2) instanceof Damone)

						valore += 2;
					else
						valore += 1;

					t = t.getNext();
				} while (t.getNext() != null);

				// se valore inferiore rimuovo traccia
				if (valore < max)
					set(indexOf(i), null);

				// se valore superiore reimposto ed elimino le precedenti
				if (valore > max) {
					max = valore;
					for (int k = 0; k < indexOf(i); k++)
						set(k, null);
				}
			}
		}
	}

	/**
	 * Mantiene le permutazioni che hanno per prime la dama.
	 */
	public void soloPrimaDama() {

		// tentaivo di ricerca dama al primo salto
		for (TracciaMangiata i : this)
			if (i != null && i.getStart() instanceof Damone) {
				for (TracciaMangiata t : this)
					if (t != null && !(t.getStart() instanceof Damone))
						set(indexOf(t), null);
				return;
			}

		// tentativo di ricerca dama al secondo salto
		for (TracciaMangiata i : this)
			if (i != null && i.getNext() != null
					&& i.getNext().getStart() instanceof Damone) {
				for (TracciaMangiata t : this)
					if (t != null
							&& !(t.getNext().getStart() instanceof Damone))
						set(indexOf(t), null);
				return;
			}
		// tentativo di ricerca dama al terzo salto
		for (TracciaMangiata i : this)
			if (i != null && i.getNext() != null
					&& i.getNext().getNext() != null
					&& i.getNext().getNext().getStart() instanceof Damone) {
				for (TracciaMangiata t : this)
					if (t != null
							&& !(t.getNext().getNext().getStart() instanceof Damone))
						set(indexOf(t), null);
				return;
			}
	}

	/**
	 * Controlla se la pedina è presente al primo salto(arrivo) in una
	 * permutazione.
	 * 
	 * @param p
	 *            Pedina da controllare.
	 * @return Ritorna true se la pedina arriva in una permutazione.
	 */
	public boolean saltaIn(Pedina p) {
		for (TracciaMangiata i : this) {
			if (i != null)

				if (i.getNext() != null && i.getNext().getStart().equals(p))
					return true;
		}
		return false;
	}

	/**
	 * 
	 * @return Ritorna tutte le permutazioni della lista senza il primo
	 *         elemento, ossia la pedina di partenza
	 */
	public PermutazioniMangiata scala() {
		PermutazioniMangiata nuova = new PermutazioniMangiata();
		for (TracciaMangiata t : this)
			// se l'operazione è fatta su una traccia di 2 pezzi (1 salto) la
			// ignora
			if (t != null && t.lenght() > 1)

				nuova.add(new TracciaMangiata(t.getNext().getStart(), t.getNext().getNext()));

		return nuova;
	}
}