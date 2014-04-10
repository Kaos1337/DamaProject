package it.dama.view;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * Gestisce i suoni del gioco.
 *
 */
public class Suono extends Thread {
	
	private static final URL clickUrl = Suono.class.getResource("/snd/click.wav");
	
	/**
	 * Costruttore del suono, avvia il thread
	 */
	public Suono() {
		start();
	}

	/**
	 * Metodo per far avviare il suono.
	 */
	public void run() {
			AudioInputStream audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(clickUrl);
			} catch (UnsupportedAudioFileException | IOException e) {
				FileDebug.accoda("-- (Suono) UnsupportedAudioFileException | IOException 1");
				e.printStackTrace();
			}
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = null;
			try {
				clip = (Clip)AudioSystem.getLine(info);
			} catch (LineUnavailableException e) {
				FileDebug.accoda("-- (Suono)LineUnavailableException");
				e.printStackTrace();
			}
			try {
				clip.open(audioInputStream);
			} catch (LineUnavailableException | IOException e) {
				FileDebug.accoda("-- (Suono) UnsupportedAudioFileException | IOException 2");
				e.printStackTrace();
			}
			clip.start();
			clip.drain();
			clip.close();
		
	}
}
