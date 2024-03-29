// The contents of this file are subject to the Mozilla Public License Version
// 1.1
// (the "License"); you may not use this file except in compliance with the
// License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
// for the specific language governing rights and
// limitations under the License.
//
// The Original Code is "The Columba Project"
//
// The Initial Developers of the Original Code are Frederik Dietz and Timo
// Stich.
// Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
// All Rights Reserved.
package tn.com.digivoip.framework.xml;

/** @author fdietz */
public class TextUtils{

	public static String escapeText(String txt) {
		StringBuffer buffer = new StringBuffer(txt);
		// Important!!
		// -> replacing this character must happen first
		TextUtils.stringReplaceAll(buffer, '&', "&amp;");
		TextUtils.stringReplaceAll(buffer, '<', "&lt;");
		TextUtils.stringReplaceAll(buffer, '>', "&gt;");
		TextUtils.stringReplaceAll(buffer, '"', "&quot;"); // *20030621, karlpeder*
		TextUtils.stringReplaceAll(buffer, '\'', "&apos;");
		return buffer.toString();
	}
	public static StringBuffer stringReplaceAll(StringBuffer orig, char token, String replacement) {
		for (int i = 0; i < orig.length(); i++) {
			if (orig.charAt(i) == token) {
				orig = orig.replace(i, ++i, replacement);
			}
		}
		return orig;
	}
}
