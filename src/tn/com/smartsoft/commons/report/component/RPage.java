package tn.com.smartsoft.commons.report.component;

public class RPage extends RConstant {

	private static final long serialVersionUID = 1L;

	private int height = 0;
	private int width = 0;
	private boolean orientationPortrait = true;

	public boolean isOrientationPortrait() {
		return orientationPortrait;
	}

	public void setOrientationPortrait(boolean orientationPortrait) {
		this.orientationPortrait = orientationPortrait;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public RPage() {

	}

	/**
	 * Default constructor for portrait orientation
	 * 
	 * @param height
	 * @param width
	 */
	public RPage(int height, int width) {
		this.height = height;
		this.width = width;
		this.orientationPortrait = true;
	}

	public RPage(int height, int width, boolean portrait) {
		this.height = height;
		this.width = width;
		this.orientationPortrait = portrait;
	}

	public static RPage Page_A4_Portrait() {
		return new RPage(842, 595, true);
	}

	public static RPage Page_A4_Landscape() {
		return new RPage(595, 842, false);
	}

	public static RPage Page_Legal_Portrait() {
		return new RPage(1008, 612, true);
	}

	public static RPage Page_Legal_Landscape() {
		return new RPage(612, 1008, false);
	}

	public static RPage Page_Letter_Portrait() {
		return new RPage(792, 612, true);
	}

	public static RPage Page_Letter_Landscape() {
		return new RPage(612, 792, false);
	}

}
