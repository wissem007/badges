package tn.com.digivoip.comman.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
	public static void InversionCouleurRgb(BufferedImage source) {
		for (int i = 0; i < source.getWidth(); i++) {
			for (int j = 0; j < source.getHeight(); j++) {
				Color pixelcolor = new Color(source.getRGB(i, j));
				int r = pixelcolor.getRed();
				int g = pixelcolor.getGreen();
				int b = pixelcolor.getBlue();
				r = Math.abs(r - 255);
				g = Math.abs(g - 255);
				b = Math.abs(b - 255);
				int rgb = new Color(r, g, b).getRGB();
				source.setRGB(i, j, rgb);
			}
		}
	}
	
	public static BufferedImage crypte(BufferedImage source) {
		List<Integer> liste = new ArrayList<Integer>();
		BufferedImage imageResultat = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		Color pixelActuel;
		Color pixelOppose;
		Color pixelSymetriqueX;
		Color pixelSymetriquey;
		for (int x = 0; x < source.getWidth(); x++) {
			for (int y = 0; y < source.getHeight(); y++) {
				pixelActuel = new Color(source.getRGB(x, y));
				pixelOppose = new Color(source.getRGB(source.getWidth() - x - 1, source.getHeight() - y - 1));
				pixelSymetriqueX = new Color(source.getRGB(source.getWidth() - x - 1, y));
				pixelSymetriquey = new Color(source.getRGB(x, source.getHeight() - y - 1));
				int r = pixelActuel.getRed();
				int g = pixelActuel.getGreen();
				int b = pixelActuel.getBlue();
				int go = pixelOppose.getGreen();
				int gx = pixelSymetriqueX.getGreen();
				int gy = pixelSymetriquey.getGreen();
				int bo = pixelOppose.getBlue();
				int bx = pixelSymetriqueX.getBlue();
				int by = pixelSymetriquey.getBlue();
				liste.add(g);
				liste.add(b);
				int rgb;
				if (x % 3 == 0) {
					rgb = new Color(r, gy, bo).getRGB();
				} else {
					if (x % 3 == 1) {
						rgb = new Color(r, bx, go).getRGB();
					} else {
						rgb = new Color(by, gx, r).getRGB();
					}
					
				}
				imageResultat.setRGB(x, y, rgb);
			}
			
		}
		return imageResultat;
	}
	
	public static BufferedImage createBufferedAsJPEG(BufferedImage bi, int dpi) throws Exception {
		File temp = File.createTempFile("sss", ".tmp");
		FileOutputStream out = new FileOutputStream(temp);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(1f, false);
		param.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
		param.setXDensity(dpi);
		param.setYDensity(dpi);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bi);
		out.close();
		return ImageIO.read(temp);
	}
	
	public static byte[] toByteArray(BufferedImage originalImage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpg", baos);
		return baos.toByteArray();
	}
	
	public static BufferedImage createSub(BufferedImage imageSource, RectangleImage rectangle) {
		BufferedImage bufferedImage = imageSource.getSubimage(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), rectangle.getWidth());
		return bufferedImage;
	}
	
	public static BufferedImage binarize(BufferedImage imageRGB) {
		BufferedImage imageNB = new BufferedImage(imageRGB.getWidth(), imageRGB.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g = imageNB.createGraphics();
		g.drawRenderedImage(imageRGB, null);
		return imageNB;
	}
	
	public static BufferedImage binarize(BufferedImage imageSource, int seuil) {
		BufferedImage imageResultat = new BufferedImage(imageSource.getWidth(), imageSource.getHeight(), imageSource.getType());
		int noir = new Color(0, 0, 0).getRGB();
		int blanc = new Color(255, 255, 255).getRGB();
		Color pixelcolor;
		for (int x = 0; x < imageSource.getWidth(); x++) {
			for (int y = 0; y < imageSource.getHeight(); y++) {
				pixelcolor = new Color(imageSource.getRGB(x, y));
				if (0.212671 * pixelcolor.getRed() + 0.715160 * pixelcolor.getGreen() + 0.072169 * pixelcolor.getBlue() > seuil) {
					imageResultat.setRGB(x, y, blanc);
					
				} else {
					imageResultat.setRGB(x, y, noir);
				}
			}
			
		}
		return imageResultat;
	}
	
	public static BufferedImage resize(BufferedImage imageSource, DimensionImage dimension) {
		BufferedImage imageResultante = new BufferedImage(dimension.getWidth(), dimension.getHeight(), imageSource.getType());
		Graphics2D g = imageResultante.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(imageSource, 0, 0, dimension.getWidth(), dimension.getHeight(), 0, 0, imageSource.getWidth(), imageSource.getHeight(), null);
		g.dispose();
		return imageResultante;
	}
	
	public static BufferedImage createFocusZone(BufferedImage imageSource) {
		BufferedImage imageResultat = binarize(imageSource, (int) (getSeuil(imageSource) * 14 / 10));
		int maxX = 0;
		int maxY = 0;
		int minX = imageResultat.getWidth();
		int minY = imageResultat.getHeight();
		for (int x = 0; x < imageResultat.getWidth(); x++) {
			for (int y = 0; y < imageResultat.getHeight(); y++) {
				if (imageResultat.getRGB(x, y) != -1) {
					if (maxX < x) {
						maxX = x;
					}
					if (maxY < y) {
						maxY = y;
					}
					if (minX > x) {
						minX = x;
					}
					if (minY > y) {
						minY = y;
					}
				}
			}
		}
		imageResultat = imageSource.getSubimage(minX, minY, maxX - minX, maxY - minY);
		return imageResultat;
	}
	
	public static void saveToJpg(BufferedImage image, String chemin) throws IOException {
		ImageIO.write(image, getExtention(chemin), new File(chemin));
	}
	
	public static String getExtention(String fileName) {
		int positionExtention = fileName.lastIndexOf('.');
		String fileExtention = fileName.substring(positionExtention + 1);
		if (fileExtention.length() == 3) {
			return fileExtention;
		} else {
			return "";
		}
	}
	
	public static BufferedImage rotation(BufferedImage imageSource, double angle) {
		BufferedImage imageResultat = new BufferedImage(imageSource.getWidth(), imageSource.getHeight(), imageSource.getType());
		AffineTransform at = new AffineTransform();
		at.rotate(angle, imageSource.getWidth() / 2, imageSource.getHeight() / 2);
		
		AffineTransformOp atOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		atOp.filter(imageSource, imageResultat);
		return imageResultat;
	}
	
	public static BufferedImage rotation(BufferedImage imageSource, double angle, double centreX, double centreY) {
		BufferedImage imageResultat = new BufferedImage(imageSource.getWidth(), imageSource.getHeight(), imageSource.getType());
		AffineTransform at = new AffineTransform();
		at.rotate(angle, centreX, centreY);
		AffineTransformOp atOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		atOp.filter(imageSource, imageResultat);
		return imageResultat;
	}
	
	public static BufferedImage accentuation(BufferedImage source) {
		BufferedImage image = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		float[] accentuation = new float[] { -1f, -1f, -1f, -1f, 9f, -1f, -1f, -1f, -1f };
		Kernel noyau = new Kernel(3, 3, accentuation);
		ConvolveOp opration = new ConvolveOp(noyau);
		opration.filter(source, image);
		return image;
	}
	
	public static BufferedImage erosion(BufferedImage imageSource) {
		BufferedImage imageResultat = new BufferedImage(imageSource.getWidth(), imageSource.getHeight(), imageSource.getType());
		for (int x = 0; x < imageSource.getWidth(); x++) {
			imageResultat.setRGB(x, 0, imageSource.getRGB(x, 0));
			imageResultat.setRGB(x, imageSource.getHeight() - 1, imageSource.getRGB(x, imageSource.getHeight() - 1));
		}
		for (int y = 0; y < imageSource.getHeight(); y++) {
			imageResultat.setRGB(0, y, imageSource.getRGB(0, y));
			imageResultat.setRGB(imageSource.getWidth() - 1, y, imageSource.getRGB(imageSource.getWidth() - 1, y));
		}
		for (int x = 1; x < imageSource.getWidth() - 1; x++) {
			for (int y = 1; y < imageSource.getHeight() - 1; y++) {
				if (imageSource.getRGB(x, y) == -1) {
					imageResultat.setRGB(x, y, -1);
				} else {
					if (imageSource.getRGB(x - 1, y - 1) == -1 || imageSource.getRGB(x - 1, y) == -1 || imageSource.getRGB(x - 1, y + 1) == -1 || imageSource.getRGB(x, y - 1) == -1
							|| imageSource.getRGB(x, y + 1) == -1 || imageSource.getRGB(x + 1, y - 1) == -1 || imageSource.getRGB(x + 1, y) == -1 || imageSource.getRGB(x + 1, y + 1) == -1) {
						imageResultat.setRGB(x, y, -1);
					} else {
						imageResultat.setRGB(x, y, imageSource.getRGB(x, y));
					}
				}
			}
		}
		return imageResultat;
	}
	
	public static BufferedImage dilatation(BufferedImage imageSource) {
		BufferedImage imageResultat = new BufferedImage(imageSource.getWidth(), imageSource.getHeight(), imageSource.getType());
		for (int x = 0; x < imageSource.getWidth(); x++) {
			for (int y = 0; y < imageSource.getHeight(); y++) {
				imageResultat.setRGB(x, y, imageSource.getRGB(x, y));
			}
		}
		int noir;
		for (int x = 1; x < imageSource.getWidth() - 1; x++) {
			for (int y = 1; y < imageSource.getHeight() - 1; y++) {
				if (imageSource.getRGB(x, y) != -1) {
					noir = imageSource.getRGB(x, y);
					imageResultat.setRGB(x, y + 1, noir);
					imageResultat.setRGB(x, y - 1, noir);
					imageResultat.setRGB(x - 1, y - 1, noir);
					imageResultat.setRGB(x - 1, y, noir);
					imageResultat.setRGB(x - 1, y + 1, noir);
					imageResultat.setRGB(x + 1, y - 1, noir);
					imageResultat.setRGB(x + 1, y, noir);
					imageResultat.setRGB(x + 1, y + 1, noir);
				}
			}
		}
		return imageResultat;
	}
	
	public static void displayBinaire(BufferedImage image) {
		int valeurBinaire;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (image.getRGB(x, y) == -1) {
					valeurBinaire = 1;
				} else {
					valeurBinaire = 0;
				}
				System.out.print(" " + valeurBinaire + " ");
			}
			System.out.println();
		}
	}
	
	public static void displayRGB(BufferedImage image) {
		Color color = null;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				color = new Color(image.getRGB(x, y));
				System.out.println(Integer.toString(x) + "," + Integer.toString(y) + " = ( " + color.getRed() + " , " + color.getGreen() + " , " + color.getBlue() + " ) ");
			}
		}
	}
	
	public static BufferedImage rehausser(BufferedImage image, RehaussementImage rehaussement) throws IOException {
		int[] courbeInitiale = new int[256];
		byte[] courbe = new byte[256];
		for (int i = 0; i < 256; i++) {
			courbeInitiale[i] = (int) ((255 - rehaussement.getFortesDensites() - rehaussement.getHautesLumieres()) * (i - rehaussement.getDecalageNoir())
					/ (255 - rehaussement.getDecalageNoir() - rehaussement.getDecalageBlanc()) + rehaussement.getIntensite()
					* Math.sin((i - rehaussement.getDecalageNoir()) * Math.PI / (255 - rehaussement.getDecalageNoir() - rehaussement.getDecalageBlanc())) - rehaussement.getContraste()
					* Math.sin((i - rehaussement.getDecalageNoir()) * 2 * Math.PI / (255 - rehaussement.getDecalageNoir() - rehaussement.getDecalageBlanc())) + rehaussement.getFortesDensites());
		}
		
		for (int i = 0; i < 256; i++) {
			if (courbeInitiale[i] < 0) {
				courbe[i] = (byte) 0;
			} else if (courbeInitiale[i] > 255) {
				courbe[i] = (byte) 255;
			} else {
				courbe[i] = (byte) courbeInitiale[i];
			}
		}
		
		ByteLookupTable table = new ByteLookupTable(0, courbe);
		LookupOp operation = new LookupOp(table, null);
		operation.filter(image, image);
		return image;
	}
	
	public static BufferedImage rgbToGray(BufferedImage imageSource) {
		BufferedImageOp imageResultat = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return imageResultat.filter(imageSource, null);
	}
	
	public static int getSeuilTiers(BufferedImage image) {
		int min = 255;
		int max = 0;
		int pixel;
		Color pixelcolor;
		
		for (int x = 0; x < image.getWidth() / 3; x++) {
			for (int y = 0; y < image.getHeight() / 3; y++) {
				pixelcolor = new Color(image.getRGB(x, y));
				pixel = (int) (0.212671 * pixelcolor.getRed() + 0.715160 * pixelcolor.getGreen() + 0.072169 * pixelcolor.getBlue());
				if (pixel < min) {
					min = pixel;
				}
				if (pixel > max) {
					max = pixel;
				}
			}
		}
		return (min + (max - min) / 2);
	}
	
	public static int getSeuil(BufferedImage image) {
		int min = 255;
		int max = 0;
		int pixel;
		Color pixelcolor;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixelcolor = new Color(image.getRGB(x, y));
				pixel = (int) (0.212671 * pixelcolor.getRed() + 0.715160 * pixelcolor.getGreen() + 0.072169 * pixelcolor.getBlue());
				if (pixel < min) {
					min = pixel;
				}
				if (pixel > max) {
					max = pixel;
				}
			}
		}
		return (min + (max - min) / 2);
	}
}
