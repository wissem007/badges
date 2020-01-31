package tn.com.digivoip.comman.barcode;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

import tn.com.digivoip.comman.image.ImageUtils;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class BarCodeUtility {

	public static FileBean encodeToFile(String input) throws Exception {
		byte[] binaryData = ImageUtils.toByteArray(encode(input));
		return new FileBean("image/png", binaryData, (long) binaryData.length);
	}

	public static BufferedImage encode(String value) throws Exception {
		Code39Bean bean = new Code39Bean();
		int resolution = 200;
		int orientation = 0;
		int pixelsPerModule = 1;
		bean.setModuleWidth(pixelsPerModule * 25.4 / resolution);
		BarcodeDimension dim = bean.calcDimensions(value);
		BufferedImage image = BitmapBuilder.prepareImage(dim, orientation, resolution, BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g2d = BitmapBuilder.prepareGraphics2D(image, dim, orientation, false);
		Java2DCanvasProvider canvas = new Java2DCanvasProvider(g2d, orientation);
		bean.generateBarcode(canvas, value);
		g2d.dispose();
		return image;

	}
}
