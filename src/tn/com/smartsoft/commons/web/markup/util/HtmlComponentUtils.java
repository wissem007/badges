package tn.com.smartsoft.commons.web.markup.util;

import java.util.HashMap;

import org.apache.commons.lang.ClassUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.web.markup.ConcreteElement;
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.SinglePartElement;
import tn.com.smartsoft.commons.web.markup.html.A;
import tn.com.smartsoft.commons.web.markup.html.Abbr;
import tn.com.smartsoft.commons.web.markup.html.Acronym;
import tn.com.smartsoft.commons.web.markup.html.Address;
import tn.com.smartsoft.commons.web.markup.html.Applet;
import tn.com.smartsoft.commons.web.markup.html.Area;
import tn.com.smartsoft.commons.web.markup.html.B;
import tn.com.smartsoft.commons.web.markup.html.BR;
import tn.com.smartsoft.commons.web.markup.html.Base;
import tn.com.smartsoft.commons.web.markup.html.BaseFont;
import tn.com.smartsoft.commons.web.markup.html.Bdo;
import tn.com.smartsoft.commons.web.markup.html.Big;
import tn.com.smartsoft.commons.web.markup.html.Blink;
import tn.com.smartsoft.commons.web.markup.html.BlockQuote;
import tn.com.smartsoft.commons.web.markup.html.Body;
import tn.com.smartsoft.commons.web.markup.html.Button;
import tn.com.smartsoft.commons.web.markup.html.Caption;
import tn.com.smartsoft.commons.web.markup.html.Center;
import tn.com.smartsoft.commons.web.markup.html.Cite;
import tn.com.smartsoft.commons.web.markup.html.Code;
import tn.com.smartsoft.commons.web.markup.html.Col;
import tn.com.smartsoft.commons.web.markup.html.ColGroup;
import tn.com.smartsoft.commons.web.markup.html.Comment;
import tn.com.smartsoft.commons.web.markup.html.DD;
import tn.com.smartsoft.commons.web.markup.html.DL;
import tn.com.smartsoft.commons.web.markup.html.DT;
import tn.com.smartsoft.commons.web.markup.html.Del;
import tn.com.smartsoft.commons.web.markup.html.Dfn;
import tn.com.smartsoft.commons.web.markup.html.Div;
import tn.com.smartsoft.commons.web.markup.html.Em;
import tn.com.smartsoft.commons.web.markup.html.FieldSet;
import tn.com.smartsoft.commons.web.markup.html.Font;
import tn.com.smartsoft.commons.web.markup.html.Form;
import tn.com.smartsoft.commons.web.markup.html.Frame;
import tn.com.smartsoft.commons.web.markup.html.FrameSet;
import tn.com.smartsoft.commons.web.markup.html.H1;
import tn.com.smartsoft.commons.web.markup.html.H2;
import tn.com.smartsoft.commons.web.markup.html.H3;
import tn.com.smartsoft.commons.web.markup.html.H4;
import tn.com.smartsoft.commons.web.markup.html.H5;
import tn.com.smartsoft.commons.web.markup.html.H6;
import tn.com.smartsoft.commons.web.markup.html.HR;
import tn.com.smartsoft.commons.web.markup.html.Head;
import tn.com.smartsoft.commons.web.markup.html.Html;
import tn.com.smartsoft.commons.web.markup.html.I;
import tn.com.smartsoft.commons.web.markup.html.IFrame;
import tn.com.smartsoft.commons.web.markup.html.IMG;
import tn.com.smartsoft.commons.web.markup.html.Input;
import tn.com.smartsoft.commons.web.markup.html.Ins;
import tn.com.smartsoft.commons.web.markup.html.Kbd;
import tn.com.smartsoft.commons.web.markup.html.LI;
import tn.com.smartsoft.commons.web.markup.html.Label;
import tn.com.smartsoft.commons.web.markup.html.Legend;
import tn.com.smartsoft.commons.web.markup.html.Link;
import tn.com.smartsoft.commons.web.markup.html.Map;
import tn.com.smartsoft.commons.web.markup.html.Meta;
import tn.com.smartsoft.commons.web.markup.html.NOBR;
import tn.com.smartsoft.commons.web.markup.html.NoFrames;
import tn.com.smartsoft.commons.web.markup.html.NoScript;
import tn.com.smartsoft.commons.web.markup.html.OL;
import tn.com.smartsoft.commons.web.markup.html.ObjectElement;
import tn.com.smartsoft.commons.web.markup.html.OptGroup;
import tn.com.smartsoft.commons.web.markup.html.Option;
import tn.com.smartsoft.commons.web.markup.html.P;
import tn.com.smartsoft.commons.web.markup.html.PRE;
import tn.com.smartsoft.commons.web.markup.html.Param;
import tn.com.smartsoft.commons.web.markup.html.Q;
import tn.com.smartsoft.commons.web.markup.html.S;
import tn.com.smartsoft.commons.web.markup.html.Samp;
import tn.com.smartsoft.commons.web.markup.html.Script;
import tn.com.smartsoft.commons.web.markup.html.Select;
import tn.com.smartsoft.commons.web.markup.html.Small;
import tn.com.smartsoft.commons.web.markup.html.Span;
import tn.com.smartsoft.commons.web.markup.html.Strike;
import tn.com.smartsoft.commons.web.markup.html.Strong;
import tn.com.smartsoft.commons.web.markup.html.Style;
import tn.com.smartsoft.commons.web.markup.html.Sub;
import tn.com.smartsoft.commons.web.markup.html.Sup;
import tn.com.smartsoft.commons.web.markup.html.TBody;
import tn.com.smartsoft.commons.web.markup.html.TD;
import tn.com.smartsoft.commons.web.markup.html.TFoot;
import tn.com.smartsoft.commons.web.markup.html.TH;
import tn.com.smartsoft.commons.web.markup.html.THead;
import tn.com.smartsoft.commons.web.markup.html.TR;
import tn.com.smartsoft.commons.web.markup.html.TT;
import tn.com.smartsoft.commons.web.markup.html.Table;
import tn.com.smartsoft.commons.web.markup.html.TextArea;
import tn.com.smartsoft.commons.web.markup.html.Title;
import tn.com.smartsoft.commons.web.markup.html.U;
import tn.com.smartsoft.commons.web.markup.html.UL;
import tn.com.smartsoft.commons.web.markup.html.Var;

public class HtmlComponentUtils {
	static HashMap htmlMap = new HashMap();

	static {
		htmlMap.put("a", A.class);
		htmlMap.put("abbr", Abbr.class);
		htmlMap.put("acronym", Acronym.class);
		htmlMap.put("address", Address.class);
		htmlMap.put("applet", Applet.class);
		htmlMap.put("area", Area.class);
		htmlMap.put("b", B.class);
		htmlMap.put("base", Base.class);
		htmlMap.put("basefont", BaseFont.class);
		htmlMap.put("bdo", Bdo.class);
		htmlMap.put("big", Big.class);
		htmlMap.put("blink", Blink.class);
		htmlMap.put("blockquote", BlockQuote.class);
		htmlMap.put("body", Body.class);
		htmlMap.put("br", BR.class);
		htmlMap.put("button", Button.class);
		htmlMap.put("caption", Caption.class);
		htmlMap.put("center", Center.class);
		htmlMap.put("cite", Cite.class);
		htmlMap.put("code", Code.class);
		htmlMap.put("col", Col.class);
		htmlMap.put("colgroup", ColGroup.class);
		htmlMap.put("comment", Comment.class);
		htmlMap.put("dd", DD.class);
		htmlMap.put("del", Del.class);
		htmlMap.put("dfn", Dfn.class);
		htmlMap.put("div", Div.class);
		htmlMap.put("dl", DL.class);
		htmlMap.put("dt", DT.class);
		htmlMap.put("em", Em.class);
		htmlMap.put("fieldset", FieldSet.class);
		htmlMap.put("font", Font.class);
		htmlMap.put("form", Form.class);
		htmlMap.put("frame", Frame.class);
		htmlMap.put("frameset", FrameSet.class);
		htmlMap.put("h1", H1.class);
		htmlMap.put("h2", H2.class);
		htmlMap.put("h3", H3.class);
		htmlMap.put("h4", H4.class);
		htmlMap.put("h5", H5.class);
		htmlMap.put("h6", H6.class);
		htmlMap.put("head", Head.class);
		htmlMap.put("hr", HR.class);
		htmlMap.put("html", Html.class);
		htmlMap.put("i", I.class);
		htmlMap.put("iframe", IFrame.class);
		htmlMap.put("img", IMG.class);
		htmlMap.put("input", Input.class);
		htmlMap.put("ins", Ins.class);
		htmlMap.put("kbd", Kbd.class);
		htmlMap.put("label", Label.class);
		htmlMap.put("legend", Legend.class);
		htmlMap.put("li", LI.class);
		htmlMap.put("link", Link.class);
		htmlMap.put("map", Map.class);
		htmlMap.put("meta", Meta.class);
		htmlMap.put("nobr", NOBR.class);
		htmlMap.put("noframes", NoFrames.class);
		htmlMap.put("noscript", NoScript.class);
		htmlMap.put("objectelement", ObjectElement.class);
		htmlMap.put("ol", OL.class);
		htmlMap.put("optgroup", OptGroup.class);
		htmlMap.put("option", Option.class);
		htmlMap.put("p", P.class);
		htmlMap.put("param", Param.class);
		htmlMap.put("pre", PRE.class);
		htmlMap.put("q", Q.class);
		htmlMap.put("s", S.class);
		htmlMap.put("samp", Samp.class);
		htmlMap.put("script", Script.class);
		htmlMap.put("select", Select.class);
		htmlMap.put("small", Small.class);
		htmlMap.put("span", Span.class);
		htmlMap.put("strike", Strike.class);
		htmlMap.put("strong", Strong.class);
		htmlMap.put("style", Style.class);
		htmlMap.put("sub", Sub.class);
		htmlMap.put("sup", Sup.class);
		htmlMap.put("table", Table.class);
		htmlMap.put("tbody", TBody.class);
		htmlMap.put("td", TD.class);
		htmlMap.put("textarea", TextArea.class);
		htmlMap.put("tfoot", TFoot.class);
		htmlMap.put("th", TH.class);
		htmlMap.put("thead", THead.class);
		htmlMap.put("title", Title.class);
		htmlMap.put("tr", TR.class);
		htmlMap.put("tt", TT.class);
		htmlMap.put("u", U.class);
		htmlMap.put("ul", UL.class);
		htmlMap.put("var", Var.class);
	}

	public static boolean isMultiPartElement(ConcreteElement concreteElement) {
		return concreteElement instanceof MultiPartElement;
	}

	public static boolean isMultiPartElement(String tagName) {
		Class<?> clazz = (Class<?>) htmlMap.get(tagName.toLowerCase());
		return ClassUtils.isAssignable(clazz, MultiPartElement.class);
	}

	public static boolean isSinglePartElement(String tagName) {
		Class<?> clazz = (Class<?>) htmlMap.get(tagName.toLowerCase());
		return ClassUtils.isAssignable(clazz, SinglePartElement.class);
	}

	public static boolean isSinglePartElement(ConcreteElement concreteElement) {
		return concreteElement instanceof SinglePartElement;
	}

	public static ConcreteElement createElement(String tagName) {
		Class clazz = (Class) htmlMap.get(tagName.toLowerCase());
		if (clazz == null)
			return null;
		try {
			return (ConcreteElement) clazz.newInstance();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
