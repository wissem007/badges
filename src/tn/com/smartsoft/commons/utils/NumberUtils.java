package tn.com.smartsoft.commons.utils;

import java.util.List;

@SuppressWarnings("rawtypes")
public class NumberUtils {

	public NumberUtils() {
	}

	public static Double somme(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return arrondi(new Double(x + y));
	}

	public static Double sommeNoArrondi(Number a, Number b) {
		double x = a != null ? a.doubleValue() : 0.0D;
		double y = b != null ? b.doubleValue() : 0.0D;
		return new Double(x + y);
	}

	public static Double sommeNoArrondi(List listNumber) {
		Double somme = new Double(0.0D);
		int n = listNumber.size();
		for (int i = 0; i < n; i++) {
			somme = sommeNoArrondi(((Number) (somme)), (Number) listNumber.get(i));
		}

		return somme;
	}

	public static Double somme(List listNumber) {
		Double somme = new Double(0.0D);
		int n = listNumber.size();
		for (int i = 0; i < n; i++) {
			somme = somme(((Number) (somme)), (Number) listNumber.get(i));
		}

		return arrondi(somme);
	}

	public static Double soustraitNoArrondi(Number a, Number b) {
		double x = a != null ? a.doubleValue() : 0.0D;
		double y = b != null ? b.doubleValue() : 0.0D;
		return new Double(x - y);
	}

	public static Double convertInEuro(Double value) {
		return diviserNoArrondi(value, 100);
	}

	public static Double soustrait(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return arrondi(new Double(x - y));
	}

	public static Double multiplier(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return arrondi(new Double(x * y));
	}

	public static Double multiplierNoArrondi(Number a, Number b) {
		double x = a != null ? a.doubleValue() : 0.0D;
		double y = b != null ? b.doubleValue() : 0.0D;
		return new Double(x * y);
	}

	public static Double diviser(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return arrondi(new Double(x / y));
	}

	public static Double diviserNoArrondi(Number a, Number b) {
		double x = a != null ? a.doubleValue() : 0.0D;
		double y = b != null ? b.doubleValue() : 0.0D;
		return new Double(x / y);
	}

	public static boolean isStrictementInferieur(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return x < y;
	}

	public static boolean isStrictementSuperieur(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return x > y;
	}

	public static boolean isInferieur(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return x <= y;
	}

	public static boolean isSuperieur(Number a, Number b) {
		double x = a != null ? arrondi(a).doubleValue() : 0.0D;
		double y = b != null ? arrondi(b).doubleValue() : 0.0D;
		return x >= y;
	}

	public static boolean isEgale(Number a, Number b) {
		return isInferieur(a, b) && isSuperieur(a, b);
	}

	public static boolean isEntre(Number a, Number startNumber, Number endNumber) {
		return isSuperieur(a, startNumber) && isInferieur(a, endNumber);
	}

	public static Double arrondi(Number a) {
		int ar = 10000;
		double x = Math.round(a.doubleValue() * (double) ar);
		x /= ar;
		return new Double(x);
	}
}
