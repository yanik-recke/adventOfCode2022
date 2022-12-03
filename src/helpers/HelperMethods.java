package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Methoden zum Einlesen des Inputs usw.
 * 
 * @author Yanik Recke
 *
 */
public class HelperMethods {
	
	
	/**
	 * Liest einen einzeiligen Input in eine
	 * Liste von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - Liste mit den Ganzzahlwerten
	 */
	public static List<Integer> getInputAsListOfIntegers(String path){
		assert (path != null);
		
		List<Integer> input = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		        input.add(Integer.parseInt(line));
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return input;
	}
	
	
	/**
	 * Liest einen einzeiligen Input in eine
	 * Liste von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - Liste mit den Ganzzahlwerten
	 */
	public static List<String> getInputAsListOfString(String path){
		assert (path != null);
		
		List<String> input = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		        input.add(line);
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return input;
	}
	
	
	/**
	 * Liest einen einzeiligen Input in einen
	 * Array von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - Liste mit den Ganzzahlwerten
	 */
	public static int[] getInputAsArrayOfIntegers(String path){
		assert (path != null);
		
		return getInputAsListOfIntegers(path).stream().mapToInt(i->i).toArray();
	}

}
