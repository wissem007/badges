package tn.com.digivoip.comman.qrCode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

import tn.com.digivoip.comman.image.ImageUtils;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeEncoder {
	public static BufferedImage encode(int width, int height, String input) throws Exception {
		Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(input, BarcodeFormat.QR_CODE, width, height, hintMap);
		int crunchifyWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(crunchifyWidth, crunchifyWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, crunchifyWidth, crunchifyWidth);
		graphics.setColor(Color.BLACK);
		for (int i = 0; i < crunchifyWidth; i++) {
			for (int j = 0; j < crunchifyWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		return image;
	}

	public static FileBean encodeToFile(int width, int height, String input) throws Exception {
		byte[] binaryData = ImageUtils.toByteArray(encode(width, height, input));
		return new FileBean("image/jpg", binaryData, (long) binaryData.length);
	}
}