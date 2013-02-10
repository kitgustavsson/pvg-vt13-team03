package sorting;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import racer.Racer;

/**
 * A class representing a sorter. It reads start.txt and finish.txt and outputs
 * result.txt but only for one driver.
 */
public class Sorter {

	private RacerMap racers;
	private int laps;
	
	public Sorter(int laps) {
		this.laps = laps;
		racers = new RacerMap();
		
		read();
		readNames();
		write();
	}

	private void read() {
		// Här läses deltagarna in från start och finish. Deltagare utan tider finns inte.
		// Prova att istället läsa in namnen här och sätta start- och sluttid i readNames()
		racers.readFromFile("start.txt", "finish.txt");
	}

	/**
	 * @todo kolla vad första raden innehåller istället.
	 * @todo skicka in en Map<id, namn> till RacerMap istället
	 */
	private void readNames() {
		Map<String, String> names = new NameReader().readFromNameFile("namnfil.txt");
		String currentClass = "";

		names.remove("StartNo");

		for (String s : names.keySet()) {
			// Kontrollerar att raden är ett startnummer
			if (Character.isDigit(s.charAt(0))) {
				try {
					Racer racer = racers.getRacer(s);
					
					racer.setName(names.get(s));
					racer.setClassType(currentClass);
				} catch (NoSuchElementException e) {
					// Om racern inte finns definerad så hoppas den över
					continue;
				}
			}
			else {
				currentClass = s;
			}
		}
	}

	private void write() {
		racers.writeToFile("result.txt", laps);
	}

	public static void main(String[] args) {
	    String laps;
	    if (args.length == 1) {
	        laps = args[0];
	    } else {
	        laps = JOptionPane.showInputDialog("Fyll i önskat antal varv, 1 för maratontävling");
	    }
	    System.out.println(laps);
		new Sorter(Integer.parseInt(laps));
	}
}
