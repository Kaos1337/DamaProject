package it.dama.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * Classe utilizzata per stampare un file di log
 *
 */
public class FileDebug {

	// stringa che poi verrà scritta su file
	private static String buffer = "";

	/**
	 * Accoda una stringa al buffer di scrittura.
	 * 
	 * @param s Stringa da aggiungere al buffer
	 */
	public static void accoda(String s) {
		buffer += s + "\n";
	}

	/**
	 * Scrive la stringa sul file.
	 */
	public static void scrivi() {
		// aggiungo un separatore, nel caso vengano stampati più file
		// anche negli altri file sarà presente questo separatore
		buffer += "---------- Stampato log ----------\n";
		String timeLog = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss")
					.format(Calendar.getInstance().getTime());
		
		File logFile = new File("Dama_" + timeLog + ".log");
		
		try {
			// Creo il buffer writer, scrivo e lo chiudo
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			System.out.println("io exception in scrivi");
			e.printStackTrace();
		} catch (NullPointerException e) {
			// nel caso in cui file non è stato creato
			System.out.println("null pointer exception in scrivi");
			e.printStackTrace();
		}
	}

	/**
	 * Crea un nuovo file di debug
	 */
	public static void resetta() {
		buffer = "";
	}
	
}
