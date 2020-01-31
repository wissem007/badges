package tn.com.digivoip.comman.image;

import java.io.Serializable;

public class NumerisationImage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int resolution;
	private int feederSize;
	
	public NumerisationImage(int resolution, int feederSize) {
		this.resolution = resolution;
		this.feederSize = feederSize;
	}
	
	public int getFeederSize() {
		return feederSize;
	}
	
	public int getResolution() {
		return resolution;
	}
}