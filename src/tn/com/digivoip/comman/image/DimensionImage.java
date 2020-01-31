package tn.com.digivoip.comman.image;

import java.io.Serializable;

public class DimensionImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int width;
	public int height;
	
	public DimensionImage() {
		super();
	}
	
	public DimensionImage(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
