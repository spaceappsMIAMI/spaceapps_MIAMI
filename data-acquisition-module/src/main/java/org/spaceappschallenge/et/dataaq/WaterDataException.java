package org.spaceappschallenge.et.dataaq;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WaterDataException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2667519333469041619L;

	public static enum Type{
		CANT_CONNECT,
		NO_DATA_FOUND
	}
	
	private final Type type;
	
	public WaterDataException(Type type) {
		super();
		this.type = type;
	}
}
