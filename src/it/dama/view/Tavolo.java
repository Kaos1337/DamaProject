package it.dama.view;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import it.dama.model.ColorePezzo;
import it.dama.model.Damone;
import it.dama.model.Pedina;
import it.dama.model.Scacchiera;
import javax.swing.JPanel;

/**
 * 
 * Classe del pannello che contine la scacchiera.
 * 
 */
public class Tavolo extends JPanel {

	private Scacchiera scacchiera;

	// pila per funzione UNDO
	private ArrayList<Scacchiera> undo = new ArrayList<Scacchiera>();

	/**
	 * 
	 * @param s
	 *            Scacchiera che il tavolo deve contenere.
	 */
	public Tavolo(Scacchiera s) {
		scacchiera = s;
		setLayout(new GridLayout(8, 8));
		cambiaCursore();
		aggiorna();
		
	}

	/**
	 * Cambia il cursore in stato di attesa e viceversa.
	 */
	public void cambiaCursore(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		final Image mano = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/img/cursore11.gif"));
		final Image clessidra = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/img/clessidra.gif"));
		
		final Cursor manoCursor = toolkit.createCustomCursor(mano, new Point(0, 0), "manoCursor");
		final Cursor clessidraCursor = toolkit.createCustomCursor(clessidra, new Point(0, 0), "clessidraCursor");
		
		if(!scacchiera.getTurnoNero())
			setCursor(manoCursor);
		
		else
			setCursor(clessidraCursor);
			
	}
	
	/**
	 * 
	 * @return Ritorna scacchiera del tavolo.
	 */
	public Scacchiera getScacchiera() {
		return scacchiera;
	}

	/**
	 * 
	 * @param s
	 *            Scacchiera da assegnare al tavolo.
	 */
	public void setScacchiera(Scacchiera s) {
		scacchiera = s;
	}

	/**
	 * 
	 * Aggiunge nella pila la scacchiera prima della mossa dell'utente.
	 * 
	 * @param s
	 *            Scacchiera da salvare.
	 */
	public void undo(Scacchiera s) {
		Scacchiera aggiungi = new Scacchiera();
		aggiungi.setTracce(s.getTracce());

		// crea la copia
		for (int i = 0; i < 64; i += 2) {
			int y = i / 8;
			int x = (i % 8) + (y % 2);

			if (s.getPedina(x, y) instanceof Damone)
				aggiungi.posizionaDamone(s.getPedina(x, y).getColore(), x, y);
			else
				aggiungi.posizionaPedina(s.getPedina(x, y).getColore(), x, y);
		}

		// la aggiunge alla coda
		undo.add(aggiungi);
	}

	/**
	 * Cancella la coda se viene fatta una nuova partita.
	 */
	public void undoRemoveAll() {
		undo.removeAll(undo);
	}

	/**
	 * Ritorna la partita alla mossa precedente facendo un pop dalla pila.
	 * 
	 * @return Ritorna la scacchiera precedente.
	 */
	public Scacchiera undo() {

		// se la coda delle mosse è vuota ritorna la scacchiera già presente
		if (undo.size() < 1)
			return this.getScacchiera();

		// faccio pop dalla pila
		Scacchiera precedente = undo.get(undo.size() - 1);
		Scacchiera ritorno = precedente;
		ritorno.setTracce(precedente.getTracce());
		undo.remove(undo.size() - 1);
		ritorno.setBiMa();
		
		FileDebug.accoda("Undo, situazione riportata a:\n" + ritorno.toString());
		
		// ritorno la scacchiera uscita dalla pila
		return ritorno;
	}

	/**
	 * Posiziona i pezzi sul tavolo.
	 */
	private void aggiorna() {

		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {

				// pezzi null sono JPanel con icone bianche (tutte le caselle
				// bianche)
				if (scacchiera.getPedina(x, y) == null)
					// b specifica al costruttore di mettere immagine casella
					// bianca
					add(new Caselle("b", scacchiera.getColoreIA()));

				// pezzi bianchi || gialli sono pulsanti con relative icone
				else if (scacchiera.getPedina(x, y).getColore() == ColorePezzo.BIANCO
						|| scacchiera.getPedina(x, y).getColore() == ColorePezzo.GIALLO) {

					Caselle casella = new Caselle(
							tipoBottone(scacchiera.getPedina(x, y)), scacchiera.getColoreIA());
					if (!scacchiera.getVittoria()) {
						CasellaAzione azione = new CasellaAzione(scacchiera.getPedina(x, y), this);
						casella.add(azione);
					}
					add(casella);
				}

				// pezzi neri sono JPanel con icone di pezzi neri su caselle
				// nere
				else if (scacchiera.getPedina(x, y).getColore() == ColorePezzo.NERO
						&& scacchiera.getPedina(x, y) instanceof Damone)
					// pn specifica al costruttore di mettere immagine casella
					// nera
					add(new Caselle("dn", scacchiera.getColoreIA()));

				// pezzi neri sono JPanel con icone di pezzi neri su caselle
				// nere
				else if (scacchiera.getPedina(x, y).getColore() == ColorePezzo.NERO)
					// pn specifica al costruttore di mettere immagine casella
					// nera
					add(new Caselle("pn", scacchiera.getColoreIA()));

				// pezzi vuoti sono JPanel con icone nere
				else
					add(new Caselle("n", scacchiera.getColoreIA()));
			}
	}

	/**
	 * 
	 * @param p
	 *            Pedina dalla quale recuperare le caratteristiche per
	 *            assegnarele l'immagine corretta.
	 * @return Ritorna una stringa che identifica un componente specifico della
	 *         scacchiera.
	 */
	private String tipoBottone(Pedina p) {
		if (p.getColore() == ColorePezzo.BIANCO
				&& scacchiera.getSelezionata() != null
				&& scacchiera.getPedina(p.getX(), p.getY()) == scacchiera.getSelezionata()
				&& p instanceof Damone)
			return "dbs"; // damone bianco selezionato

		else if (p.getColore() == ColorePezzo.BIANCO
				&& scacchiera.getSelezionata() != null
				&& scacchiera.getPedina(p.getX(), p.getY()) == scacchiera.getSelezionata())
			return "pbs"; // pedina bianca selezionata

		else if (p.getColore() == ColorePezzo.BIANCO && p instanceof Damone)
			return "db"; // damone bianco

		else if (p.getColore() == ColorePezzo.BIANCO)
			return "pb"; // pedina bianca

		else
			return "cs"; // casella selezionata
	}

	/**
	 * Aggiorna la visualizzazione della scacchiera dopo le modifiche.
	 */
	public void ridisegna() {
		removeAll();
		revalidate();
		aggiorna();
	}
}