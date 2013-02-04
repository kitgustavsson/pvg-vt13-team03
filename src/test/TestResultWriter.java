/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import racer.MarathonRacer;
import racer.Racer;
import sorting.RacerMap;
import sorting.ResultWriter;

/**
 * @author dat11rla
 *
 */
public class TestResultWriter {
	private RacerMap map;
	private ResultWriter writer;
	private String filename;
	private String header;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		filename = "resultat.txt";
		header = "StartNr; Namn; TotalTid; StartTider; Måltider";
		map = new RacerMap();
		writer = new ResultWriter(map, filename);
		
		deleteTestFile();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		deleteTestFile();
	}

	/**
	 * Removes testfile after it has been tested to avoid leaving crap around
	 */
	private void deleteTestFile() {
		File file = new File(filename);
		file.delete();
	}

	/**
	 * Reads the testfile and returns it contents
	 * @return The testfiles contents
	 * @throws IOException
	 */
	private String readFile() throws IOException {
		FileReader file = new FileReader(filename);
		BufferedReader reader = new BufferedReader(file);
		
		StringBuilder sb = new StringBuilder();
		String line;
		
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		return sb.toString();
	}

	@Test
	public void testOneRacer() throws IOException {
		Racer racer = new MarathonRacer("1");
		racer.setName("Anders Asson");
		
		map.addRacerToMap(racer);
		writer.writeToFile();
		
		assertEquals("Result doesn't match", header + "\n" + racer.toString() + "\n", readFile());
	}

	@Test
	public void testMultipleRacers() throws IOException {
		Racer racer1 = new MarathonRacer("1");
		racer1.setName("Anders Asson");
		Racer racer2 = new MarathonRacer("2");
		racer2.setName("Bengt Bsson");
		Racer racer3 = new MarathonRacer("3");
		racer3.setName("Chris Csson");
		String expected = header + "\n" +
				racer1.toString() + "\n" +
				racer2.toString() + "\n" +
				racer3.toString() + "\n";


		map.addRacerToMap(racer1);
		map.addRacerToMap(racer3);
		map.addRacerToMap(racer2);
		writer.writeToFile();

		assertEquals("Result doesn't match", expected, readFile());
	}

	@Test
	public void testEmpty() throws IOException {
		writer.writeToFile();
		
		assertEquals("Result not empty", header + "\n", readFile());
	}

}
