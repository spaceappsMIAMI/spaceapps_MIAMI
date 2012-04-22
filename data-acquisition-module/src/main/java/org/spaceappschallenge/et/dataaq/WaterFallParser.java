package org.spaceappschallenge.et.dataaq;

import java.io.Reader;
import java.util.List;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.CSVReader;

public class WaterFallParser {
	
	public List<WaterFallBean> parse(Reader reader) {
		
		ColumnPositionMappingStrategy<WaterFallBean> strategy = new ColumnPositionMappingStrategy<WaterFallBean>();
		strategy.setType(WaterFallBean.class);
		strategy.setColumnMapping(new String[]{"date",
				"time",
				"ampm",
				"hiTemp",
				"lowTemp",
				"dewPoint",
				"windSpeed",
				"windDir",
				"windRun",
				"hiDir",
				"windChill",
				"heatIndex",
				"thwIndex",
				"bar",
				"na",
				"rain",
				"solarRain"});
		
		
		CsvToBean<WaterFallBean> cvs = new CsvToBean<WaterFallBean>();
		List<WaterFallBean> list = cvs.parse(strategy, new CSVReader(reader, '\t', CSVReader.DEFAULT_QUOTE_CHARACTER, 2));
		return list;
	}
	
	
}
