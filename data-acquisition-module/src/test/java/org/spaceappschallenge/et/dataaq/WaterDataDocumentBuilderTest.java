package org.spaceappschallenge.et.dataaq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

public class WaterDataDocumentBuilderTest {
	private WaterDataParser dataParser;
	private WaterDataDocumentBuilder dataDocumentBuilder;
	
	@Before
	public void setup() {
		dataParser = new WaterDataParser();
		dataDocumentBuilder = new WaterDataDocumentBuilder();
	}
	
	@Test
	public void test() {
		//-82.49&nw_latitude_va=27.11&se_longitude_va=-80&se_latitude_va=25.62
		File tmpFile = null;
		try {
			tmpFile = dataParser.getDataFile(-82.49, 27.11, -80., 25.62);
			assertNotNull(tmpFile);
		} catch (WaterDataException e) {
			fail(e.getMessage());
		}
		
		try {
			dataDocumentBuilder.convertToXml(dataParser.parseDataFile(new FileReader(tmpFile)), new File("out.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block`
			e.printStackTrace();
		}
	}

}
