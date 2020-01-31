/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.com.digivoip.comman.qrCode;

/**
 * 
 * @author m.ktari
 */
public class CodeurUtil {

	public static String coder(String chaineArabe) {
		String chaine = "";
		char caractereCourant;
		
		for (int i = 0; i < chaineArabe.length(); i++) {
			caractereCourant = chaineArabe.charAt(i);
			switch (caractereCourant) {
			
			case 'ا':
				chaine = chaine.concat("$a");
				break;
			
			case 'ب':
				chaine = chaine.concat("$b");
				break;
			case 'ت':
				chaine = chaine.concat("$c");
				break;
			case 'ث':
				chaine = chaine.concat("$d");
				break;
			case 'ج':
				chaine = chaine.concat("$e");
				break;
			case 'ح':
				chaine = chaine.concat("$f");
				break;
			case 'خ':
				chaine = chaine.concat("$g");
				break;
			case 'د':
				chaine = chaine.concat("$h");
				break;
			case 'ذ':
				chaine = chaine.concat("$i");
				break;
			case 'ر':
				chaine = chaine.concat("$j");
				break;
			case 'ز':
				chaine = chaine.concat("$k");
				break;
			case 'س':
				chaine = chaine.concat("$l");
				break;
			case 'ش':
				chaine = chaine.concat("$m");
				break;
			case 'ص':
				chaine = chaine.concat("$n");
				break;
			case 'ض':
				chaine = chaine.concat("$o");
				break;
			case 'ط':
				chaine = chaine.concat("$p");
				break;
			case 'ظ':
				chaine = chaine.concat("$q");
				break;
			case 'ع':
				chaine = chaine.concat("$r");
				break;
			case 'غ':
				chaine = chaine.concat("$s");
				break;
			case '?':
				chaine = chaine.concat("$t");
				break;
			case 'ق':
				chaine = chaine.concat("$u");
				break;
			case 'ك':
				chaine = chaine.concat("$v");
				break;
			case 'م':
				chaine = chaine.concat("$w");
				break;
			case 'ن':
				chaine = chaine.concat("$x");
				break;
			case 'ه':
				chaine = chaine.concat("$y");
				break;
			case 'و':
				chaine = chaine.concat("$z");
				break;
			case 'ي':
				chaine = chaine.concat("_a");
				break;
			case 'ء':
				chaine = chaine.concat("_b");
				break;
			case 'آ':
				chaine = chaine.concat("_c");
				break;
			case 'أ':
				chaine = chaine.concat("_d");
				break;
			case 'إ':
				chaine = chaine.concat("_e");
				break;
			case 'ة':
				chaine = chaine.concat("_f");
				break;
			case 'ؤ':
				chaine = chaine.concat("_g");
				break;
			case 'ئ':
				chaine = chaine.concat("_h");
				break;
			case 'ى':
				chaine = chaine.concat("_i");
				break;
			case 'ل':
				chaine = chaine.concat("_j");
				break;
			case '٠':
				chaine = chaine.concat("_0");
				break;
			case '١':
				chaine = chaine.concat("_1");
				break;
			case '٢':
				chaine = chaine.concat("_2");
				break;
			case '٣':
				chaine = chaine.concat("_3");
				break;
			case '٤':
				chaine = chaine.concat("_4");
				break;
			case '٥':
				chaine = chaine.concat("_5");
				break;
			case '٦':
				chaine = chaine.concat("_6");
				break;
			case '٧':
				chaine = chaine.concat("_7");
				break;
			case '٨':
				chaine = chaine.concat("_8");
				break;
			case '٩':
				chaine = chaine.concat("_9");
				break;
			default:
				chaine = chaine.concat(String.valueOf(caractereCourant));
				break;
			
			}
		}
		return chaine;
	}

	public static String decoder(String chainecode) {
		String chaine = "";
		char cacractereCourant;
		char caractereSuivant;

		for (int i = 0; i < chainecode.length(); i++) {
			cacractereCourant = chainecode.charAt(i);
			if (cacractereCourant == '$') {
				caractereSuivant = chainecode.charAt(i + 1);
				i++;
				switch (caractereSuivant) {
				case 'a':
					chaine = chaine.concat("ا");
					break;

				case 'b':
					chaine = chaine.concat("ب");
					break;
				case 'c':
					chaine = chaine.concat("ت");
					break;
				case 'd':
					chaine = chaine.concat("ث");
					break;
				case 'e':
					chaine = chaine.concat("ج");
					break;
				case 'f':
					chaine = chaine.concat("ح");
					break;
				case 'g':
					chaine = chaine.concat("خ");
					break;
				case 'h':
					chaine = chaine.concat("د");
					break;
				case 'i':
					chaine = chaine.concat("ذ");
					break;
				case 'j':
					chaine = chaine.concat("ر");
					break;
				case 'k':
					chaine = chaine.concat("ز");
					break;
				case 'l':
					chaine = chaine.concat("س");
					break;
				case 'm':
					chaine = chaine.concat("ش");
					break;
				case 'n':
					chaine = chaine.concat("ص");
					break;
				case 'o':
					chaine = chaine.concat("ض");
					break;
				case 'p':
					chaine = chaine.concat("ط");
					break;
				case 'q':
					chaine = chaine.concat("ظ");
					break;
				case 'r':
					chaine = chaine.concat("ع");
					break;
				case 's':
					chaine = chaine.concat("غ");
					break;
				case 't':
					chaine = chaine.concat("�?");
					break;
				case 'u':
					chaine = chaine.concat("ق");
					break;
				case 'v':
					chaine = chaine.concat("ك");
					break;
				case 'w':
					chaine = chaine.concat("م");
					break;
				case 'x':
					chaine = chaine.concat("ن");
					break;
				case 'y':
					chaine = chaine.concat("ه");
					break;
				case 'z':
					chaine = chaine.concat("و");
					break;
				default:
					chaine = chaine.concat("?");
					i--;
					break;
				}
			} else {
				if (cacractereCourant == '_') {
					caractereSuivant = chainecode.charAt(i + 1);
					i++;
					switch (caractereSuivant) {
					case 'a':
						chaine = chaine.concat("ي");
						break;
					case 'b':
						chaine = chaine.concat("ء");
						break;
					case 'c':
						chaine = chaine.concat("آ");
						break;
					case 'd':
						chaine = chaine.concat("أ");
						break;
					case 'e':
						chaine = chaine.concat("إ");
						break;
					case 'f':
						chaine = chaine.concat("ة");
						break;
					case 'g':
						chaine = chaine.concat("ؤ");
						break;
					case 'h':
						chaine = chaine.concat("ئ");
						break;
					case 'i':
						chaine = chaine.concat("ى");
						break;
					case 'j':
						chaine = chaine.concat("ل");
						break;
					case '0':
						chaine = chaine.concat("٠");
						break;
					case '1':
						chaine = chaine.concat("١");
						break;
					case '2':
						chaine = chaine.concat("٢");
						break;
					case '3':
						chaine = chaine.concat("٣");
						break;
					case '4':
						chaine = chaine.concat("٤");
						break;
					case '5':
						chaine = chaine.concat("٥");
						break;
					case '6':
						chaine = chaine.concat("٦");
						break;
					case '7':
						chaine = chaine.concat("٧");
						break;
					case '8':
						chaine = chaine.concat("٨");
						break;
					case '9':
						chaine = chaine.concat("٩");
						break;
					default:
						chaine = chaine.concat("?");
						i--;
						break;
					}
				} else {
					chaine = chaine.concat(String.valueOf(cacractereCourant));
				}
			}
		}

		return chaine;
	}
}
