package org.spaceappschallenge.et.dataaq;

import lombok.Data;

@Data
public class WaterFallBean {
	private String date;
	private String time;
	private Double tempOut;
	private Double hiTemp;
	private Double lowTemp;
	private Double outHum;
	private String dewPoint;
	private String windDir;
	private String windRun;
	private String hiSpeed;
	private String hiDir;
	private String windChill;
	private String heatIndex;
	private String thwIndex;
	private String Bar;
	private Double rain;

}
