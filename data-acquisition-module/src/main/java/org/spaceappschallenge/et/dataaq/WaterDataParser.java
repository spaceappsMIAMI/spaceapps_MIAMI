package org.spaceappschallenge.et.dataaq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.MessageFormat;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

public class WaterDataParser {
	//http://waterdata.usgs.gov/fl/nwis/current?nw_longitude_va=-82.49&nw_latitude_va=27.11&se_longitude_va=-80&se_latitude_va=25.62&coordinate_format=decimal_degrees&index_pmcode_STATION_NM=1&index_pmcode_DATETIME=2&index_pmcode_00045=3&precipitation_interval=precip01h_va&precipitation_interval=precip02h_va&precipitation_interval=precip03h_va&precipitation_interval=precip04h_va&precipitation_interval=precip05h_va&precipitation_interval=precip06h_va&precipitation_interval=precip10h_va&precipitation_interval=precip12h_va&precipitation_interval=precip18h_va&precipitation_interval=precip24h_va&precipitation_interval=precip02d_va&precipitation_interval=precip03d_va&precipitation_interval=precip04d_va&precipitation_interval=precip05d_va&precipitation_interval=precip06d_va&precipitation_interval=precip07d_va&precipitation_interval=precip10d_va&precipitation_interval=precip14d_va&precipitation_interval=precip15d_va&precipitation_interval=precip21d_va&precipitation_interval=precip28d_va&sort_key=site_no&group_key=NONE&sitefile_output_format=html_table&column_name=agency_cd&column_name=site_no&column_name=station_nm&sort_key_2=site_no&html_table_group_key=NONE&format=rdb&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Crealtime_parameter_selection

	public File getDataFile(Double nwLon, Double nwLat, Double seLon, Double seLat) throws WaterDataException {
		//String url = new String("http://waterdata.usgs.gov/fl/nwis/current?nw_longitude_va=%s&nw_latitude_va=%s&se_longitude_va=%s&se_latitude_va=%s&coordinate_format=decimal_degrees&index_pmcode_STATION_NM=1&index_pmcode_DATETIME=2&index_pmcode_00045=3&precipitation_interval=precip01h_va&precipitation_interval=precip02h_va&precipitation_interval=precip03h_va&precipitation_interval=precip04h_va&precipitation_interval=precip05h_va&precipitation_interval=precip06h_va&precipitation_interval=precip10h_va&precipitation_interval=precip12h_va&precipitation_interval=precip18h_va&precipitation_interval=precip24h_va&precipitation_interval=precip02d_va&precipitation_interval=precip03d_va&precipitation_interval=precip04d_va&precipitation_interval=precip05d_va&precipitation_interval=precip06d_va&precipitation_interval=precip07d_va&precipitation_interval=precip10d_va&precipitation_interval=precip14d_va&precipitation_interval=precip15d_va&precipitation_interval=precip21d_va&precipitation_interval=precip28d_va&sort_key=site_no&group_key=NONE&sitefile_output_format=html_table&column_name=agency_cd&column_name=site_no&column_name=station_nm&sort_key_2=site_no&html_table_group_key=NONE&format=rdb&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Crealtime_parameter_selection");
		
		try {
			URL waterdata = new URL(MessageFormat.format("http://waterdata.usgs.gov/fl/nwis/current?nw_longitude_va={0}&nw_latitude_va={1}&se_longitude_va={2}&se_latitude_va={3}&coordinate_format=decimal_degrees&index_pmcode_STATION_NM=1&index_pmcode_DATETIME=2&index_pmcode_00045=3&precipitation_interval=precip01h_va&precipitation_interval=precip02h_va&precipitation_interval=precip03h_va&precipitation_interval=precip04h_va&precipitation_interval=precip05h_va&precipitation_interval=precip06h_va&precipitation_interval=precip10h_va&precipitation_interval=precip12h_va&precipitation_interval=precip18h_va&precipitation_interval=precip24h_va&precipitation_interval=precip02d_va&precipitation_interval=precip03d_va&precipitation_interval=precip04d_va&precipitation_interval=precip05d_va&precipitation_interval=precip06d_va&precipitation_interval=precip07d_va&precipitation_interval=precip10d_va&precipitation_interval=precip14d_va&precipitation_interval=precip15d_va&precipitation_interval=precip21d_va&precipitation_interval=precip28d_va&sort_key=site_no&group_key=NONE&sitefile_output_format=html_table&column_name=agency_cd&column_name=site_no&column_name=station_nm&sort_key_2=site_no&html_table_group_key=NONE&format=rdb&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Crealtime_parameter_selection", nwLon, nwLat, seLon, seLat));
			/*ReadableByteChannel rbc = Channels.newChannel(waterdata.openStream());
			File f =  File.createTempFile("waterdata", ".tsv");
			f.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(f);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);*/
			File f = grabFileTmpFile(waterdata, "waterData", ".tsv", true);
			
			
			if (checkNoData(f))
				throw new WaterDataException(WaterDataException.Type.NO_DATA_FOUND);
			return f;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new WaterDataException(WaterDataException.Type.CANT_CONNECT);
		} catch (IOException e) {
			e.printStackTrace();
			throw new WaterDataException(WaterDataException.Type.CANT_CONNECT);
		}
		
	}
	
	public File getDataFileWaterLevel(Double nwLon, Double nwLat, Double seLon, Double seLat) throws WaterDataException {
		//http://waterdata.usgs.gov/fl/nwis/dv?referred_module=sw&site_tp_cd=OC&site_tp_cd=OC-CO&site_tp_cd=ES&site_tp_cd=LK&site_tp_cd=ST&site_tp_cd=ST-CA&site_tp_cd=ST-DCH&site_tp_cd=ST-TS&nw_longitude_va=-82.49&nw_latitude_va=27.11&se_longitude_va=-80&se_latitude_va=25.62&coordinate_format=decimal_degrees&index_pmcode_63160=1&index_pmcode_00045=1&sort_key=site_no&group_key=NONE&sitefile_output_format=html_table&column_name=agency_cd&column_name=site_no&column_name=station_nm&range_selection=days&format=rdb&date_format=YYYY-MM-DD&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Csite_tp_cd%2Crealtime_parameter_selection
		try {
			URL waterLevelUrl = new URL(MessageFormat.format("http://waterdata.usgs.gov/fl/nwis/dv?referred_module=sw&site_tp_cd=OC&site_tp_cd=OC-CO&site_tp_cd=ES&site_tp_cd=LK&site_tp_cd=ST&site_tp_cd=ST-CA&site_tp_cd=ST-DCH&site_tp_cd=ST-TS&nw_longitude_va={0}&nw_latitude_va={1}&se_longitude_va={2}&se_latitude_va={3}&coordinate_format=decimal_degrees&index_pmcode_63160=1&index_pmcode_00045=1&sort_key=site_no&group_key=NONE&sitefile_output_format=html_table&column_name=agency_cd&column_name=site_no&column_name=station_nm&range_selection=days&format=rdb&date_format=YYYY-MM-DD&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Csite_tp_cd%2Crealtime_parameter_selection", nwLon, nwLat, seLon, seLat));
			File f = grabFileTmpFile(waterLevelUrl, "waterlevel", ".tsv", true);
			if (checkNoData(f))
				throw new WaterDataException(WaterDataException.Type.NO_DATA_FOUND);
			
			return f;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new WaterDataException(WaterDataException.Type.CANT_CONNECT);
		} catch (IOException e) {
			e.printStackTrace();
			throw new WaterDataException(WaterDataException.Type.CANT_CONNECT);
		} 
	}
	
	/*private void cleanUpWaterLevelAgregatedData() {
		
	}*/
	
	public void getSiteDescripInfo(Double nwLon, Double nwLat, Double seLon, Double seLat, File output) {
		//http://waterdata.usgs.gov/fl/nwis/current?nw_longitude_va=-82.49&nw_latitude_va=27.11&se_longitude_va=-80&se_latitude_va=25.62&coordinate_format=decimal_degrees&index_pmcode_STATION_NM=1&index_pmcode_DATETIME=2&index_pmcode_00045=3&precipitation_interval=precip01h_va&precipitation_interval=precip02h_va&precipitation_interval=precip03h_va&precipitation_interval=precip04h_va&precipitation_interval=precip05h_va&precipitation_interval=precip06h_va&precipitation_interval=precip10h_va&precipitation_interval=precip12h_va&precipitation_interval=precip18h_va&precipitation_interval=precip24h_va&precipitation_interval=precip02d_va&precipitation_interval=precip03d_va&precipitation_interval=precip04d_va&precipitation_interval=precip05d_va&precipitation_interval=precip06d_va&precipitation_interval=precip07d_va&precipitation_interval=precip10d_va&precipitation_interval=precip14d_va&precipitation_interval=precip15d_va&precipitation_interval=precip21d_va&precipitation_interval=precip28d_va&sort_key=site_no&group_key=NONE&format=sitefile_output&sitefile_output_format=xml&column_name=agency_cd&column_name=site_no&column_name=station_nm&column_name=dec_lat_va&column_name=dec_long_va&sort_key_2=site_no&html_table_group_key=NONE&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Crealtime_parameter_selection
		URL siteDescUrl;
		try {
			siteDescUrl = new URL(MessageFormat.format("http://waterdata.usgs.gov/fl/nwis/current?nw_longitude_va={0}&nw_latitude_va={1}&se_longitude_va={2}&se_latitude_va={3}&coordinate_format=decimal_degrees&index_pmcode_STATION_NM=1&index_pmcode_DATETIME=2&index_pmcode_00045=3&precipitation_interval=precip01h_va&precipitation_interval=precip02h_va&precipitation_interval=precip03h_va&precipitation_interval=precip04h_va&precipitation_interval=precip05h_va&precipitation_interval=precip06h_va&precipitation_interval=precip10h_va&precipitation_interval=precip12h_va&precipitation_interval=precip18h_va&precipitation_interval=precip24h_va&precipitation_interval=precip02d_va&precipitation_interval=precip03d_va&precipitation_interval=precip04d_va&precipitation_interval=precip05d_va&precipitation_interval=precip06d_va&precipitation_interval=precip07d_va&precipitation_interval=precip10d_va&precipitation_interval=precip14d_va&precipitation_interval=precip15d_va&precipitation_interval=precip21d_va&precipitation_interval=precip28d_va&sort_key=site_no&group_key=NONE&format=sitefile_output&sitefile_output_format=xml&column_name=agency_cd&column_name=site_no&column_name=station_nm&column_name=dec_lat_va&column_name=dec_long_va&sort_key_2=site_no&html_table_group_key=NONE&rdb_compression=value&list_of_search_criteria=lat_long_bounding_box%2Crealtime_parameter_selection", nwLon, nwLat, seLon, seLat));
			grabFileFromUrl(siteDescUrl, output);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void grabFileFromUrl(URL url, File output) throws IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(output);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}
	
	
	private File grabFileTmpFile(URL url, String fileNm, String ext, boolean deleteOnExit) throws IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		File f =  File.createTempFile(fileNm, ext);
		if (deleteOnExit)
			f.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(f);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		return f;
	}
	
	public List<WaterDataBean> parseDataFile(Reader reader) {
		ColumnPositionMappingStrategy<WaterDataBean> strategy = new ColumnPositionMappingStrategy<WaterDataBean>();
		strategy.setType(WaterDataBean.class);
		strategy.setColumnMapping(new String[]{"agency",
				"siteNo",
				"stationNm",
				"ddNu",
				"pecipInter7s",
				"parameterCd5s",
				"date",
				"timezone",
				"result",
				"resultCd",
				"resultMd12d"});

		
		CsvToBean<WaterDataBean> cvs = new CsvToBean<WaterDataBean>();
		List<WaterDataBean> list = cvs.parse(strategy, new CSVReader(reader, '\t', CSVReader.DEFAULT_QUOTE_CHARACTER, 34));
		return list;
	}
	
	private boolean checkNoData(File f) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(f));
		String line = null;
		
		if ((line = in.readLine()) != null) {
			if (line.lastIndexOf("No sites/data found using the selection criteria specified") == 0 )
				return true;
		}
		
		return false;
	}
	
}
