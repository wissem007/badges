package tn.com.digivoip.comman.qrCode;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class QRCodeDecoder {
	public static String decode(File imageFile) throws Exception {
		BufferedImage image = ImageIO.read(imageFile);
		return decode(image);
	}
	
	public static String decode(BufferedImage image) throws Exception {
		Reader reader = new QRCodeReader();
		LuminanceSource lumSource = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(lumSource));
		Result result = reader.decode(bitmap);
		return result.getText();
	}
	
	public static void main(String[] args) {
		String myCodeText = "http://crunchify.com/";
		String filePath = "D:\\fr\\test\\CrunchifyQR.png";
		int size = 250;
		String fileType = "png";
		File myFile = new File(filePath);
		try {
			BufferedImage image = QRCodeEncoder.encode(size, size, myCodeText);
			ImageIO.write(image, fileType, myFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\nYou have successfully created QR Code.");
	} 
}