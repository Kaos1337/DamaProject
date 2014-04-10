package it.dama.view;

import it.dama.controller.TurnoIASmart;
import it.dama.model.ColorePezzo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.Timer;

/**
 * 
 * Per avviare una nuova partita.
 * 
 */
public class NuovaPartita extends JMenuItem {

	/**
	 * 
	 * @param tavolo
	 *            Tavolo sul quale viene avviata la nuova partita.
	 * @param string
	 *            Testo da inserire nel bottone.
	 * @param colore
	 *            Passa il colore col quale il giocatore vuole iniziare la nuova
	 *            partita.
	 */
	public NuovaPartita(final Tavolo tavolo, String string,
			final ColorePezzo colore) {
		super(string);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				// esegue il reset della scacchiera appartenente al tavolo
				tavolo.getScacchiera().setColoreIA(colore);
				tavolo.getScacchiera().reset();
				tavolo.getScacchiera().setBiMa();
				
				FileDebug.resetta();
				
				// elimino le mosse salvate per UNDO
				tavolo.undoRemoveAll();
				tavolo.ridisegna();

				if (tavolo.getScacchiera().getColoreIA() == ColorePezzo.BIANCO) {
					tavolo.getScacchiera().setTurnoNero(true);
					tavolo.cambiaCursore();
					
					Timer timer = new Timer(2000, new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {

							new TurnoIASmart(tavolo);

							// gioca l'IA in base alla difficoltà
							tavolo.ridisegna();
							tavolo.getScacchiera().setTurnoNero(false);
							tavolo.cambiaCursore();
						}
					});
					timer.setRepeats(false);
					timer.start();
				}
			}
		});
	}
}