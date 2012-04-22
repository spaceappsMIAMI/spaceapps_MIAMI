package org.spaceappschallenge.et.dataaq.services;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class WaterDataInformationTest {

	private WaterDataInformation waterDataInformation;
	
	@Before
	public void setup() {
		waterDataInformation = new WaterDataInformation();
	}
	
	@Test
	public void test() {
		File outputData = new File("out/data.xml");
		File outputSiteDesc = new File("out/siteDescInfo.xml");
		waterDataInformation.getWaterDataInfo(-82.49, 27.11, -80., 25.62, outputData, outputSiteDesc);
		
		assertTrue(outputData.length() > 0);
		assertTrue(outputSiteDesc.length() > 0);
	}

}
