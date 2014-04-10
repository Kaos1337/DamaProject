package it.dama.view;

import it.dama.controller.TurnoIASmart;
import it.dama.model.ColorePezzo;
import it.dama.model.Scacchiera;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 * 
 * Questa classe è quella di partenza da cui parte il programma.
 * 
 */
public class Gioco extends JFrame {

	private final int height = 700, width = 700;
	public static int livello = 2;
	private static Tavolo t;

	/**
	 * 
	 * Main del gioco.
	 */
	public static void main(String[] args) {
		Scacchiera s = new Scacchiera();
		
		s.reset();
		s.setBiMa();

		Gioco g = new Gioco(s);
		g.setVisible(true);
		if (s.getColoreIA() == ColorePezzo.BIANCO) {
			t.getScacchiera().setTurnoNero(true);
			t.cambiaCursore();
			Timer timer = new Timer(2000, new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// gioca l'IA in base alla difficoltà
					new TurnoIASmart(t);
					t.ridisegna();
					t.getScacchiera().setTurnoNero(false);
					t.cambiaCursore();
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}

	
	/**
	 * 
	 * Questo metodo costruisce il gioco partendo da una scacchiera.
	 * 
	 * @param s
	 *            è la scacchiera con la quale il gioco verrà creato.
	 */
	public Gioco(Scacchiera s) {
		super("Dama");
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.lightGray);

		// imposta il cursore del gioco
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/img/cursore11.gif"));
		Cursor customCursor = toolkit.createCustomCursor(image, new Point(0, 0), "customCursor");
		setCursor(customCursor);

		// sfrutto un bordo vuoto per allineare lettere e numeri
		Border paddingBorder_numeri = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		// area centrale con la scacchiera
		t = new Tavolo(s);
		add(t, BorderLayout.CENTER);

		// area bassa per lettere, un border layout per creare lo spazio sulla
		// sinistra e il grid layout per le lettere
		JPanel area_south = new JPanel(new BorderLayout());
		JPanel inside = new JPanel(new GridLayout(1, 8));

		String arr = "ABCDEFGH";

		for (int i = 0; i < 8; i++) {
			JLabel label = new JLabel("" + arr.charAt(i));
			label.setHorizontalAlignment(0);
			inside.add(label);
		}

		// genero una quantità di spazi necessaria per allineare le lettere
		String spazi = "";
		for (int i = width; i > 0; i /= 3)
			spazi += " ";

		JLabel spazio = new JLabel(" " + spazi);
		spazio.setHorizontalAlignment(0);

		area_south.add(spazio, BorderLayout.WEST);
		area_south.add(inside, BorderLayout.CENTER);

		add(area_south, BorderLayout.SOUTH);

		// area sinistra per i numeri
		JPanel area_west = new JPanel(new GridLayout(8, 1));
		add(area_west, BorderLayout.WEST);

		for (int i = 1; i < 9; i++) {
			JLabel label = new JLabel(i + "");
			label.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder_numeri));
			label.setHorizontalAlignment(0);
			area_west.add(label);
		}

		setJMenuBar(new MenuDama(t));

		this.setLocationRelativeTo(null); // posiziona la finestra in centro
		this.setResizable(false); // finestra non ridimensionabile
	}
}