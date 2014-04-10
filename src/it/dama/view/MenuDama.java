package it.dama.view;

import it.dama.model.ColorePezzo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * 
 * Definisce il menu del gioco.
 * 
 */
public class MenuDama extends JMenuBar {
	// mi serve il tavolo per eseguire il revalidate
	Tavolo tavolo;

	/**
	 * 
	 * @param t
	 *            Tavolo sul quale fanno riferimento i bottoni.
	 */
	public MenuDama(final Tavolo t) {
		tavolo = t;

		// crea voce di menu
		JMenu file = new JMenu("File");
		JMenu modifica = new JMenu("Modifica");
		JMenu aiuto = new JMenu("Aiuto");
		JMenu nuovaPartita = new JMenu("Nuova Partita");
		JMenu livello = new JMenu("Livello di difficoltà");

		ButtonGroup livelli = new ButtonGroup();

		// creo elementi del menu file
		JMenuItem esci = new JMenuItem("Esci");

		// creo elementi del sottomenu livello
		JRadioButtonMenuItem livello0 = new Livello(1, "I Can Win",
				Gioco.livello == 0 ? true : false);
		JRadioButtonMenuItem livello1 = new Livello(2, "Bring It On",
				Gioco.livello == 2 ? true : false);
		JRadioButtonMenuItem livello2 = new Livello(4, "Challenging",
				Gioco.livello == 4 ? true : false);
		JRadioButtonMenuItem livello3 = new Livello(6, "Hardcore",
				Gioco.livello == 6 ? true : false);
		JRadioButtonMenuItem livello4 = new Livello(8, "Nightmare",
				Gioco.livello == 8 ? true : false);

		livelli.add(livello0);
		livelli.add(livello1);
		livelli.add(livello2);
		livelli.add(livello3);
		livelli.add(livello4);

		// creo elementi del sottomenu nuova partita
		JMenuItem bianco = new NuovaPartita(tavolo, "Colore Bianco", ColorePezzo.NERO);
		JMenuItem nero = new NuovaPartita(tavolo, "Colore Nero", ColorePezzo.BIANCO);

		// creo elementi del menu modifica
		JMenuItem undo = new JMenuItem("Undo");

		// creo elementi del menu aiuto
		JMenuItem info = new JMenuItem("Info");
		JMenuItem regole = new JMenuItem("Regole");
		JMenuItem stampaDebug = new JMenuItem("Stampa file log");

		// assegno il listener alla voce "esci"
		esci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// chiude il programma
				System.exit(0);
			}
		});

		// assegno il listener alla voce "Undo"
		undo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// torna indietro di una mossa
				tavolo.setScacchiera(tavolo.undo());
				tavolo.ridisegna();
			}
		});

		// assegno il listener alla voce "Info"
		info.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new FinestraInfo();
			}
		});

		regole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FinestraRegole();
			}
		});

		stampaDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDebug.scrivi();
			}
		});

		// aggiungo elemento al sottomenu nuova partita
		nuovaPartita.add(bianco);
		nuovaPartita.add(nero);

		// aggiungo gli elementi al sottomenu livello
		livello.add(livello0);
		livello.add(livello1);
		livello.add(livello2);
		livello.add(livello3);
		livello.add(livello4);

		// aggiungo elemento di menu alla voce di menu
		file.add(nuovaPartita);
		file.add(livello);
		file.addSeparator();
		file.add(stampaDebug);
		file.addSeparator();
		file.add(esci);
		
		modifica.add(undo);
		
		aiuto.add(regole);
		aiuto.addSeparator();
		aiuto.add(info);

		// aggiunge il menu al JMenuBar
		add(file);
		add(modifica);
		add(aiuto);
	}

}