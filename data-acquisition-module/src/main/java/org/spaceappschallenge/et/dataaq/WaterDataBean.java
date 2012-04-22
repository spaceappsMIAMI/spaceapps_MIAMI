package org.spaceappschallenge.et.dataaq;

import lombok.Data;

@Data
public class WaterDataBean {

	private String agency;
	private String siteNo;
	private String stationNm;
	private String ddNu;
	private String pecipInter7s;
	private String parameterCd5s;
	private String date;
	private String timezone;
	private Double result;
	private String resultCd;
	private String resultMd12d;
}
