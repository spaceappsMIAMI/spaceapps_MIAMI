package org.spaceappschallenge.et.dataaq.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.spaceappschallenge.et.dataaq.WaterDataBean;
import org.spaceappschallenge.et.dataaq.WaterDataDocumentBuilder;
import org.spaceappschallenge.et.dataaq.WaterDataException;
import org.spaceappschallenge.et.dataaq.WaterDataParser;

public class WaterDataInformation {
	private WaterDataParser waterDataParser;
	private WaterDataDocumentBuilder builder;
	
	
	public WaterDataInformation() {
		waterDataParser = new WaterDataParser();
		builder = new WaterDataDocumentBuilder();
	}
	
	public void getWaterDataInfo(Double nwLon, Double nwLat, Double seLon, Double seLat, File outputData, File outputSiteDesc) {
		try {
			File tmpFile = waterDataParser.getDataFile(nwLon, nwLat, seLon, seLat); 
			List<WaterDataBean> list = waterDataParser.parseDataFile(new FileReader(tmpFile));
			builder.convertToXml(list, outputData);
			waterDataParser.getSiteDescripInfo(nwLon, nwLat, seLon, seLat, outputSiteDesc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WaterDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
