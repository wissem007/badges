package tn.com.smartsoft.framework.presentation.view.tags.parser;

import java.io.Serializable;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;

public class UIWindowRessourceId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> parameter;
	private String path;
	private ParserDefinition parserDefinition;

	public UIWindowRessourceId(String path, Map<String, String> parameter, ParserDefinition parserDefinition) {
		super();
		this.parameter = parameter;
		this.path = path;
		this.parserDefinition = parserDefinition;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((parserDefinition == null) ? 0 : parserDefinition.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UIWindowRessourceId other = (UIWindowRessourceId) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (parserDefinition == null) {
			if (other.parserDefinition != null)
				return false;
		} else if (!parserDefinition.equals(other.parserDefinition))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
