package org.spaceappschallenge.et.dataaq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class WaterDataParserTest {
	private WaterDataParser waterDataParser;
	
	@Before
	public void setup(){
		waterDataParser = new WaterDataParser();
	}
	
	@Test
	public void test() {
		//-82.49&nw_latitude_va=27.11&se_longitude_va=-80&se_latitude_va=25.62
		File tmpFile;
		try {
			tmpFile = waterDataParser.getDataFile(-82.49, 27.11, -80., 25.62);
			assertNotNull(tmpFile);
		} catch (WaterDataException e) {
			fail(e.getMessage());
		}
			
		
	}
	
	@Test
	public void TestEmptyDataSet() {
		//this should throw a empty data
		File tmpFile;
		try {
			tmpFile = waterDataParser.getDataFile(90., 44., 100., 25.62);
		} catch (WaterDataException e) {
			assertThat(e, CoreMatchers.is(WaterDataException.class));
		}
	}
	
	@Test
	public void testParseWaterData() {
		File tmpFile;
		try {
			tmpFile = waterDataParser.getDataFile(-82.49, 27.11, -80., 25.62);
			assertNotNull(tmpFile);
			List<WaterDataBean> list = waterDataParser.parseDataFile(new FileReader(tmpFile));
			
			assertNotNull(list);
			assertTrue(list.size() > 0);
			
			for (WaterDataBean wf : list)
				assertTrue(wf.getResult() != null);
			
		} catch (WaterDataException e) {
			fail(e.getMessage());
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetSiteDescriptions() {
		File siteDescInfo = new File("out/siteDescriptionInfo.xml");
		waterDataParser.getSiteDescripInfo(-82.49, 27.11, -80., 25.62, siteDescInfo);
		assertTrue(siteDescInfo.length() > 0);
	}
	
}
