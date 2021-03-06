/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		Racer racer = new Racer("1");
		racer.setName("Anders Asson");
		
		map.addRacer(racer);
		writer.writeToFile(1);
		
		assertEquals("Result doesn't match", "\n" + header + "\n" + racer.toString() + "\n", readFile());
	}

	@Test
	public void testMultipleRacers() throws IOException {
		StringBuilder expected = new StringBuilder();
		Racer racer1 = new Racer("1");
		Racer racer2 = new Racer("2");
		Racer racer3 = new Racer("3");
		
		racer1.setName("Anders Asson");
		racer2.setName("Bengt Bsson");
		racer3.setName("Chris Csson");
		
		expected.append("\n" + header + "\n");
		expected.append(racer1.toString() + "\n");
		expected.append(racer2.toString() + "\n");
		expected.append(racer3.toString() + "\n");


		map.addRacer(racer1);
		map.addRacer(racer3);
		map.addRacer(racer2);
		writer.writeToFile(1);

		assertEquals("Result doesn't match", expected.toString(), readFile());
	}

}
