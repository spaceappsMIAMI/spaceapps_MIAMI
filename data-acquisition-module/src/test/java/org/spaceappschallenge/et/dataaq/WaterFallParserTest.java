package org.spaceappschallenge.et.dataaq;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WaterFallParserTest {
	private WaterFallParser waterFallParser;
	
	@Before
	public void setup() {
		waterFallParser = new WaterFallParser();
	}
	
	@Test
	public void testParse() {
		Reader reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("2011.csv"));
		
		List<WaterFallBean> list = waterFallParser.parse(reader);
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		for (WaterFallBean wf : list)
			assertTrue(wf.getRain() >= 0);
		
	}

}
