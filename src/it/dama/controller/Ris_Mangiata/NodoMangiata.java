package it.dama.controller.Ris_Mangiata;

import it.dama.controller.GuardaMosse;
import it.dama.model.ColorePezzo;
import it.dama.model.Pedina;
import it.dama.model.Damone;
import it.dama.model.Scacchiera;

/**
 * 
 * Identifica un nodo di cui l'albero delle permutazioni è costruito.
 * 
 */
public class NodoMangiata {

	private Pedina p;
	// la dimensione è di 4 o 2 a seconda che sia un damone o una pedina
	private NodoMangiata[] nextliv;
	private NodoMangiata prec = null;

	/**
	 * Costruttore che dimensiona il livello successivo a seconda se si esegue
	 * su una pedina o damone.
	 * 
	 * @param p
	 *            Pedina che identifica il nodo.
	 */
	public NodoMangiata(Pedina p) {
		this.p = p;
		nextliv = new NodoMangiata[(p instanceof Damone) ? 4 : 2];
	}

	/**
	 * 
	 * @return Ritorna la pedina del nodo.
	 */
	public Pedina getPedina() {
		return p;
	}

	/**
	 * 
	 * @return Restituisce il nodo successivo.
	 */
	public NodoMangiata[] getNextliv() {
		return nextliv;
	}

	/**
	 * 
	 * @param n
	 *            Nodo successivo da assegnare a quello attuale.
	 */
	public void setNexliv(NodoMangiata[] n) {
		nextliv = n;
	}

	/**
	 * 
	 * @param p
	 *            Nodo da impostare come padre.
	 */
	public void setPrec(NodoMangiata p) {
		prec = p;
	}

	/**
	 * 
	 * @return Restituisce il il padre.
	 */
	public NodoMangiata getPrec() {
		return prec;
	}

	/**
	 * Partendo dalla pedina data sviluppa n volte i salti in modo ricorsivo(n
	 * sarà sempre 3 per noi).
	 * 
	 * @param s
	 *            Scacchiera su cui sviluppare l'albero.
	 * @param n
	 *            Numero di figli massimi da visitare.
	 */
	public void sviluppa(Scacchiera s, int n) {
		
		// aggiorna le mosse della destinazione in previsione del
		// prossimo livello e del prima
		new GuardaMosse(s, p);
		
		if (n == 0)
			return;
		else
			// per ogni nodo del successivo livello
			for (int k = 0; k < nextliv.length; k++) 
				// se la k-esima mossa è presente ed è un salto e il nodo non ha
				// precedente, o se lo ha non combacia con il successivo(questo
				// eviterà cicli nel damone)
				if (p.getMosse()[k] != null 
						&& Math.abs(p.getMosse()[k].getVerso().getX() - p.getMosse()[k].getDa().getX()) > 1
						&& (prec == null || (prec != null 
												&& (p.getMosse()[k].getVerso().getX() != prec.getPedina().getX() 
												|| p.getMosse()[k].getVerso().getY() != prec.getPedina().getY())))) {

					// necessari per guardare alla scacchiera e non ai puntatori
					int x = p.getMosse()[k].getVerso().getX();
					int y = p.getMosse()[k].getVerso().getY();

					// posizionamento di un damone vuoto per mangiate multiple
					// damone
					if (p instanceof Damone)
						s.posizionaDamone(ColorePezzo.VUOTO, x, y);
					else
						// necessario per normalizzazione
						s.posizionaPedina(ColorePezzo.VUOTO, x, y);

					nextliv[k] = new NodoMangiata(s.getPedina(x, y));
					nextliv[k].setPrec(this);
					nextliv[k].sviluppa(s, n - 1);
				}

	}

	/**
	 * 
	 * @return ritorna true se il prossimo livello del nodo è vuoto
	 */
	public boolean nextlivIsEmpty() {
		if (nextliv != null)
			for (NodoMangiata n : nextliv) {
				if (n != null)
					return false;
			}
		return true;
	}

	/**
	 * Elenca le TracciaMangiata del nodo per salti di 3
	 * 
	 * @return Ritorna la traccia della mangiata.
	 */
	public PermutazioniMangiata elenca() {
		PermutazioniMangiata perman = new PermutazioniMangiata();

		// se il prossimo livello dell'albero relativo al nodo attuale
		// è null guardo indietro per vedere la traccia
		if (nextlivIsEmpty() && prec != null) {
			TracciaMangiata t = new TracciaMangiata(prec.getPedina(),
					new TracciaMangiata(p, null));
			if (prec.getPrec() != null) {
				
				t = new TracciaMangiata(prec.getPrec().getPedina(), t);
				
				if (prec.getPrec().getPrec() != null)
					t = new TracciaMangiata(prec.getPrec().getPrec().getPedina(), t);
			}
			
			perman.add(t);
			
			return perman;
		}
		
		// altrimenti ricorsione sui nodi del prossimo livello
		else
			for (NodoMangiata n : nextliv)
				// ignora i nodi del livello che sono occupati
				if (n != null)
					for (TracciaMangiata t : n.elenca()) {
						perman.add(t);
					}

		// alla fine avrò la lista delle tracce
		return perman;
	}
}