package it.dama.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.dama.model.ColorePezzo;
import it.dama.model.Pedina;
import it.dama.controller.GuardaMosse;
import it.dama.controller.TurnoIASmart;
import it.dama.controller.Ris_Mangiata.BiancheMangianti;
import it.dama.model.Scacchiera;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * 
 * Questa classe assegna l'azione al bottone sul quale si può interagire.
 * 
 */

public class CasellaAzione extends JButton {


	/**
	 * 
	 * @param p
	 *            Pedina alla auale assegnare l'azione.
	 * @param t
	 *            Tavolo sualla quale si trova la Pedina.
	 */

	public CasellaAzione(final Pedina p, final Tavolo t) {
		final Scacchiera s = t.getScacchiera();
		setOpaque(true);
		setContentAreaFilled(false);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				BiancheMangianti biancheMangianti = s.getBiMa();

				// se esiste un pezzo che può mangiare, gli altri
				// bottoni (fisici) non reagiscono
				if ((p.getColore() != ColorePezzo.GIALLO
						&& !biancheMangianti.isEmpty() && !biancheMangianti.contains(p))
							|| s.getTurnoNero());

				// se il bottone è di un pezzo bianco mostro le opzioni
				else if (p.getColore() == ColorePezzo.BIANCO && s.getSelezionata() != p) {
					s.rimuoviGialli();
					new GuardaMosse(s, p);
					if (s.mostraOpzioni(p, biancheMangianti))
						s.setSelezionata(p);
					else
						s.setSelezionata(null);
					
					t.ridisegna();
				}

				// se il bottone è un'opzione delle mosse eseguo lo spostamento
				else if (p.getColore() == ColorePezzo.GIALLO) {

					int direzione = p.getX() < s.getSelezionata().getX() ? 0 : 1;
					direzione += p.getY() < s.getSelezionata().getY() ? 0 : 2;

					s.rimuoviGialli();
					// aggiungo la configurazione della scacchiera nella pila dell'undo
					t.undo(s);
					
					Pedina verso = s.getSelezionata().getMossa(direzione).getVerso();
					
					Pedina selezionata = s.getSelezionata();
					
					FileDebug.accoda(selezionata.toString() + " ==> "
							+ selezionata.getMossa(direzione).getVerso().toString());
					
					selezionata.getMossa(direzione).esegui(s);
					
					FileDebug.accoda(s.toString());
					s.setSelezionata(null);

					new Suono();
					
					// scalo le tracce se le stavo seguendo
					if (s.getTracce() != null) {
						s.setTracce(s.getTracce().scala());

						if (s.getTracce().isEmpty())
							s.setTracce(null);
						else {
							s.setSelezionata(verso.getX(), verso.getY());
							new GuardaMosse(s, s.getSelezionata());
							s.mostraOpzioni(s.getSelezionata(), biancheMangianti);
						}

					}

					// se ho esaurito le tracce aggiorno disabilitando i
					// pulsanti e passo all'IA
					if (s.getTracce() == null) {

						// aggiorno il tavolo disabilitando i bottoni
						s.setTurnoNero(true);
						t.cambiaCursore();
						t.ridisegna();

						// timer di un secondo
						Timer timer = new Timer(1000, new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								// gioca l'IA in base alla difficoltà
								new TurnoIASmart(t);
								s.setTurnoNero(false);
								t.ridisegna();
								FileDebug.accoda("Muove avversario:\n" + s.toString());
								// aggiorna le pedine bianche che possono mangiare
								s.setBiMa();
								t.cambiaCursore();

							}
						});

						timer.setRepeats(false);
						timer.start();

					}

				}

				// se premo la casella che è già selezionata annulla la selezione
				else if (p == s.getSelezionata()) {
					s.rimuoviGialli();
					s.setSelezionata(null);
					t.ridisegna();

				}

				t.ridisegna();
			}
		});
	}
}
