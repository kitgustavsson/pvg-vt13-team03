package sorting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import racer.Racer;

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
	public void writeToFile() {
		Set<String> keys = new TreeSet<String>(data.keySet());
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			writer.write(header);
			writer.newLine();
			
			for (String key : keys) {
				writer.write(data.get(key).toString());
				writer.newLine();
			}
			
			writer.close();
		} catch (IOException e) {
			System.err.println("File " + filename + " could not be written");
		}
	}
}
