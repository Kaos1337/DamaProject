package it.dama.view;

import it.dama.controller.TurnoIASmart;
import it.dama.model.ColorePezzo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * Crea una finestra che decreta il vincitore e permette di chiudere il
 * programma o iniziare una nuova partita.
 *
 */
public class Vincitore extends JFrame {

	/**
	 * Costruttore della finestra per la vittoria.
	 * 
	 * @param c
	 *            Colore del giocatore sul quale effettuare il controllo della
	 *            vittoria.
	 * @param t
	 *            Tavolo sul quale eseguire il ocntrollo.
	 */
	public Vincitore(ColorePezzo c, final Tavolo t) {
		setSize(400, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.lightGray);
		setLayout(new BorderLayout());

		// creo un pannello per la parte in basso della finestra che conterrà i
		// bottoni in un GridLayout di una riga e due colonne
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1, 2));

		// creo i bottoni
		JButton nuovaPartita = new NuovaPartita(t, this);
		JButton esci = new Esci();

		// aggiungo i bottoni al pannello
		south.add(nuovaPartita);
		south.add(esci);
		// if che permette di stampare il vincitore se c'è stata l'inversione
		// dei colori
		if (t.getScacchiera().getColoreIA() == ColorePezzo.BIANCO)
			c = (c == ColorePezzo.BIANCO ? ColorePezzo.NERO : c);

		// creo la scritta che indica il vincitore
		JLabel vince = new Vince(c, Gioco.livello);

		// aggiungo la scritta e il pannello alla finestra
		add(vince, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * 
	 * Costruisce la stringa per la vittoria.
	 *
	 */
	public class Vince extends JLabel {

		// crea una label e la centra con la finestra
		public Vince(ColorePezzo c, int l) {
			String diff;
			switch(l){
			case 1:
				diff = "I Can Win";
				break;
			case 2:
				diff = "Bring It On";
				break;
			case 4:
				diff = "Challenging";
				break;
			case 6:
				diff = "Hardcore";
				break;
			case 8:
				diff = "Nightmare";
				break;
			default:
				diff = "Errore rilevamento difficoltà";
			}
				
			this.setText("<html>La partita è stata vinta dal giocatore " + c + "!" + "<br /> Con difficoltà " + diff + "! <br /></html>");
			setHorizontalAlignment(0);
		}
	}


	public class NuovaPartita extends JButton {

		/**
		 * Assegna il nome al bottone per la nuova partita e gli assegna
		 * l'ascoltatore.
		 * 
		 * @param t
		 *            Tavolo sul quale eseguire la nuova patita.
		 * @param finestra
		 *            Finestra di vittoria da nascondere quando vine fatta la
		 *            nuova prtita.
		 */
		public NuovaPartita(final Tavolo t, final Vincitore finestra) {
			setText("Nuova Partita");

			addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					
					// esegue il reset della scacchiera appartenente al tavolo
					t.getScacchiera().reset();
					t.getScacchiera().setBiMa();
					
					FileDebug.resetta();
					
					// elimino le mosse salvate per UNDO
					t.undoRemoveAll();
					t.ridisegna();
					finestra.setVisible(false);

					if (t.getScacchiera().getColoreIA() == ColorePezzo.BIANCO) {
						t.getScacchiera().setTurnoNero(true);
						t.cambiaCursore();
						
						Timer timer = new Timer(2000, new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {

								new TurnoIASmart(t);

								// gioca l'IA in base alla difficoltà
								t.ridisegna();
								t.getScacchiera().setTurnoNero(false);
								t.cambiaCursore();
							}
						});
						timer.setRepeats(false);
						timer.start();
					}
				}
			});
		}

	}

	/**
	 * Definizione del bottone per uscire dal gioco a partita finita.
	 */
	public class Esci extends JButton {

		// assegna il nome al bottone e gli assegna l'ascoltatore
		public Esci() {
			setText("Esci");

			addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);

				}
			});
		}
	}
}
