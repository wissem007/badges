package tn.com.smartsoft.commons.report.utils;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStyle;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tn.com.smartsoft.commons.report.RException;
import tn.com.smartsoft.commons.utils.NumberUtils;

public class LayoutUtils {

	static final Log log = LogFactory.getLog(LayoutUtils.class);

	/**
	 * Finds "Y" coordinate value in which more elements could be added in the
	 * band
	 * 
	 * @param band
	 * @return
	 */
	public static int findYOffset(JRElementGroup band) {
		int finalHeight = 0;
		if (band != null) {
			for (Iterator<?> iter = band.getChildren().iterator(); iter.hasNext();) {
				JRDesignElement element = (JRDesignElement) iter.next();
				int currentHeight = element.getY() + element.getHeight();
				if (currentHeight > finalHeight)
					finalHeight = currentHeight;
			}
			return finalHeight;
		}
		return finalHeight;
	}

	public static int findXOffset(JRElementGroup band) {
		int finalWidth = 0;
		if (band != null) {
			for (Iterator<?> iter = band.getChildren().iterator(); iter.hasNext();) {
				JRDesignElement element = (JRDesignElement) iter.next();
				int currentWidth = element.getX() + element.getWidth();
				if (currentWidth > finalWidth)
					finalWidth = currentWidth;
			}
			return finalWidth;
		}
		return finalWidth;
	}

	/**
	 * Copy bands elements from source to dest band, also makes sure copied
	 * elements are placed below existing ones (Y offset is calculated)
	 * 
	 * @param destBand
	 * @param sourceBand
	 */
	public static void copyBandElements(JRDesignBand destBand, JRBand sourceBand) {
		int offset = findYOffset(destBand);

		if (destBand == null)
			throw new RException("destination band cannot be null");

		if (sourceBand == null)
			return;

		for (Iterator<?> iterator = sourceBand.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement element = (JRDesignElement) iterator.next();
			JRDesignElement dest = null;
			try {
				if (element instanceof JRDesignGraphicElement) {
					Constructor<? extends JRDesignElement> constructor = element.getClass().getConstructor(JRDefaultStyleProvider.class);
					JRDesignStyle style = new JRDesignStyle();
					dest = constructor.newInstance(new Object[] { style.getDefaultStyleProvider() });
				} else {
					dest = (JRDesignElement) element.getClass().newInstance();
				}

				BeanUtils.copyProperties(dest, element);
				dest.setY(dest.getY() + offset);
			} catch (Exception e) {
				log.error("Exception copying elements from band to band: " + e.getMessage(), e);
			}
			destBand.addElement((JRDesignElement) dest);
		}
	}

	/**
	 * Moves the elements contained in "band" in the Y axis "yOffset"
	 * 
	 * @param intValue
	 * @param band
	 */
	public static void moveBandsElemnts(int yOffset, JRDesignBand band) {
		if (band == null)
			return;

		for (Iterator<?> iterator = band.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement elem = (JRDesignElement) iterator.next();
			elem.setY(elem.getY() + yOffset);
		}
	}

	/**
	 * Returns the firs band from the section
	 * 
	 * @param section
	 * @return
	 */
	public static JRDesignBand getBandFromSection(JRDesignSection section) {
		return (JRDesignBand) section.getBandsList().get(0);
	}

	public static int arand(int a, float b) {
		return NumberUtils.multiplier(new Double(a), new Double(b)).intValue();
	}
}
