package tn.com.digivoip.comman.qrCode;

import java.awt.image.BufferedImage;

import org.apache.commons.lang.StringUtils;

import tn.com.digivoip.comman.image.DimensionImage;
import tn.com.digivoip.comman.image.ImageUtils;

public class QRCodeUtils {
	public static String decode(BufferedImage qrCode1, BufferedImage qrCode2, BufferedImage qrCode3) {
		String qrCodeReaded = decodeAndMultiResize(qrCode1, 10);
		if (StringUtils.isBlank(qrCodeReaded))
			qrCodeReaded = decodeAndMultiResize(qrCode2, 5);
		if (StringUtils.isBlank(qrCodeReaded))
			qrCodeReaded = decodeAndMultiResize(qrCode3, 10);
		return qrCodeReaded;
	}

	public static String decodeAndMultiResize(BufferedImage image, int j) {
		String qrCodeReaded = "";
		while (StringUtils.isBlank(qrCodeReaded) && image.getWidth() > 50) {
			try {
				qrCodeReaded = QRCodeDecoder.decode(image);
			} catch (Exception ex) {
				image = ImageUtils.resize(image, new DimensionImage(image.getWidth() - j, image.getHeight() - j));
			}
		}
		return qrCodeReaded;
	}

	public static String decodeAndResize(BufferedImage image, int j) {
		image = ImageUtils.resize(image, new DimensionImage(j, j));
		return decode(image);
	}

	public static String decode(BufferedImage image) {
		try {
			return QRCodeDecoder.decode(image);
		} catch (Exception ex) {
			return "";
		}
	}
}
