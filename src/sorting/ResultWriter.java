package sorting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import racer.Racer;
import racer.RacerClass;

public class ResultWriter {
	private RacerMap data;
	private String filename;
	private String header;
	
	// TODO: klass utan modifierbart tillstånd?
	public ResultWriter(RacerMap data, String filename) {
		this.data = data;
		this.filename = filename;
		this.header = "StartNr; Namn; TotalTid; StartTider; Måltider";
	}
	
	/**
	 * Loads and sorts racers before printing them to a file. Actual formatting
	 * is found in Racer class.
	 */
	public void writeToFile(int laps) {
		Set<RacerClass> classes = data.getClassTypes();
		
		for (RacerClass racerClass : classes) {
			writeClassTypeToFile(racerClass, laps);
		}
	}

	/**
	 * 
	 */
	private void writeClassTypeToFile(RacerClass racerClass, int laps) {
		Set<Racer> racers = data.getRacers(racerClass);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			writer.write(racerClass.toString());
			writer.newLine();
			
			if (laps < 2) {
				writer.write(header);
			}
			else {
				writer.write("StartNr; Namn; #Varv; TotalTid; ");
				
				for (int i = 1; i <= laps; i++) {
					writer.write("Varv" + i + "; ");
				}
				
				writer.write("Start; ");
				
				for (int i = 1; i < laps; i++) {
					writer.write("Varvning" + i + "; ");
				}
				
				writer.write("Mål");
			}
			
			writer.newLine();
			
			for (Racer racer : racers) {
				writer.write(racer.toString(laps));
				writer.newLine();
			}
			
			writer.close();
		} catch (IOException e) {
			System.err.println("File " + filename + " could not be written");
		}
	}
}
