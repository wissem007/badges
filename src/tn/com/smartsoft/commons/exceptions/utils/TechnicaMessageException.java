package tn.com.smartsoft.commons.exceptions.utils;

import java.text.MessageFormat;

import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class TechnicaMessageException {
	static String[] messages = new String[140];
	static {
		messages[0] = "{0} should not be null or empty";// EmptyException
		messages[1] = "The constructor of {0} for arguments({1}) not found";
		messages[2] = "Field({1}) of class({0}) not found";// FieldNotFoundException
		messages[3] = "Can not configure property({1}) of {0}, because {2}";// IllegalPropertyException
		messages[4] = "The method({1}) of {0} not found";// MethodNotFoundException
		messages[5] = "Property({1}) of class({0}) not found";// PropertyNotFoundException
		messages[6] = "Resource({0}) not found";// ResourceNotFoundException
		messages[7] = "IOException occurred, because {0}";// SIOException
		messages[8] = "Can not transformat FunctionalException in TechnicalException";
		messages[9] = "The Constructor({1}) of {0} not found";// MethodNotFoundException
		messages[10] = "Cannot find parameter: {0}";

	}

	public static String getMessage(int index, Object[] arguments) {
		String message = "";
		if (index > -1 && index < messages.length) {
			message = messages[index];
			message = MessageFormat.format(message, arguments);
		}
		return message;
	}

	public static String getMessage(int index, Object arg0) {
		String message = "";
		if (index > -1 && index < messages.length) {
			message = messages[index];
			message = MessageFormat.format(message, new Object[] { arg0 });
		}
		return message;
	}

	public static String getMessage(int index, Object arg0, Object arg1) {
		String message = "";
		if (index > -1 && index < messages.length) {
			message = messages[index];
			message = MessageFormat.format(message, new Object[] { arg0, arg1 });
		}
		return message;
	}

	public static String getMessage(int index, Object arg0, Object arg1, Object arg2) {
		String message = "";
		if (index > -1 && index < messages.length) {
			message = messages[index];
			message = MessageFormat.format(message, new Object[] { arg0, arg1, arg2 });
		}
		return message;
	}

	public static void throwException(int index, Object[] arguments) {
		throw new TechnicalException(getMessage(index, arguments));
	}

	public static void throwException(int index, Object arg0) {
		throwException(index, new Object[] { arg0 });
	}

	public static void throwException(int index, Object arg0, Object arg1) {
		throwException(index, new Object[] { arg0, arg1 });
	}

	public static void throwException(int index, Object arg0, Object arg1, Object arg2) {
		throwException(index, new Object[] { arg0, arg1, arg2 });
	}
}
