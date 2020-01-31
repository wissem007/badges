package tn.com.smartsoft.commons.report;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tn.com.smartsoft.commons.report.component.RBorder;
import tn.com.smartsoft.commons.report.component.RHorizontalAlign;
import tn.com.smartsoft.commons.report.component.RPosition;
import tn.com.smartsoft.commons.report.component.RStyle;
import tn.com.smartsoft.commons.report.component.RTransparency;
import tn.com.smartsoft.commons.report.component.RVerticalAlign;
import tn.com.smartsoft.commons.report.utils.LayoutUtils;
import tn.com.smartsoft.commons.utils.FileLocator;

public class JasperDesignHelper {

	static final Log log = LogFactory.getLog(LayoutUtils.class);

	@SuppressWarnings("unchecked")
	public static void addElement(JRElementGroup elementGroup, JRElement element) {
		elementGroup.getChildren().add(element);
	}

	public static JasperDesign createJasperDesign(String template) {
		try {
			FileLocator fileLocator = new FileLocator();
			JasperDesign jasperDesign = JRXmlLoader.load(fileLocator.getConfStream(template));
			return jasperDesign;
		} catch (JRException e) {
			throw new RException(e.getMessage());
		}
	}

	public static JRDesignParameter addParameter(JasperDesign jasperDesign, String name, String clazz, String description) {
		try {
			JRDesignParameter parameter = new JRDesignParameter();
			parameter.setName(name);
			parameter.setValueClassName(clazz);
			parameter.setDescription(description);
			jasperDesign.addParameter(parameter);
			return (parameter);
		} catch (JRException e) {
			throw new RException(e.getMessage());
		}

	}

	public static JRDesignStaticText createLabelText(int x, int y, int width, int height, String fieldId, String expressionString, RVerticalAlign vAlign, RHorizontalAlign hAlign, JRStyle styleName, RBorder border) {
		JRDesignStaticText labelText = new JRDesignStaticText();
		labelText.setStyle(styleName);
		labelText.setX(x);
		labelText.setText(expressionString);
		labelText.setY(y);
		labelText.setWidth(width);
		labelText.setHeight(height);
		labelText.setKey(fieldId);
		if (border != null)
			labelText.getLineBox().getPen().setLineWidth(border.getValue());
		labelText.setPositionType(RPosition.POSITION_TYPE_FLOAT.getValue());
		labelText.setMode(RTransparency.OPAQUE.getValue());
		labelText.getLineBox().getPen().setLineColor(Color.BLACK);
		labelText.setHorizontalAlignment(hAlign.getValue());
		labelText.setVerticalAlignment(vAlign.getValue());
		labelText.getLineBox().setPadding(2);
		return (labelText);
	}

	public static JRDesignTextField createTextField(int x, int y, int width, int height, String fieldId, String fieldClass, String expressionString, RVerticalAlign vAlign, RHorizontalAlign hAlign, JRStyle styleName, RBorder border) {
		JRDesignTextField textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setStyle(styleName);
		textField.setBlankWhenNull(true);
		textField.setX(x);
		textField.setY(y);
		textField.setWidth(width);
		textField.setHeight(height);
		textField.setKey(fieldId);
		if (border != null)
			textField.getLineBox().getPen().setLineWidth(border.getValue());
		textField.setHorizontalAlignment(hAlign.getValue());
		textField.setVerticalAlignment(vAlign.getValue());
		textField.getLineBox().setLeftPadding(2);
		JRDesignExpression designExpression = new JRDesignExpression();
		designExpression.setValueClassName(fieldClass);
		designExpression.setText(expressionString);
		textField.setExpression(designExpression);
		textField.setStretchType(StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT.getValue());
		return (textField);
	}

	public static JRDesignStyle getStyle(JasperDesign jasperDesign, String name) {
		return (JRDesignStyle) jasperDesign.getStylesMap().get(name);
	}

	public static JRDesignStyle addStyle(JasperDesign jasperDesign, RStyle designStyle) {
		try {
			JRDesignStyle transform = designStyle.transform();
			jasperDesign.addStyle(transform);
			return (transform);
		} catch (JRException e) {
			throw new RException(e.getMessage());
		}

	}

	public static JRDesignField addField(JasperDesign jasperDesign, String name, String clazz, String description) {
		try {
			JRDesignField field = new JRDesignField();
			field.setName(name);
			field.setValueClassName(clazz);
			field.setDescription(description);
			jasperDesign.addField(field);
			return (field);
		} catch (JRException e) {
			throw new RException(e.getMessage());
		}
	}

	public static int findVerticalOffset(JRDesignBand band) {
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

	public static void copyBandElements(JRDesignBand destBand, JRBand sourceBand) {
		int offset = findVerticalOffset(destBand);

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

	public static void moveBandsElemnts(int yOffset, JRDesignBand band) {
		if (band == null)
			return;

		for (Iterator<?> iterator = band.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement elem = (JRDesignElement) iterator.next();
			elem.setY(elem.getY() + yOffset);
		}
	}

	public static JRDesignBand getBandFromSection(JRSection section) {
		return (JRDesignBand) section.getBands()[0];
	}
}
