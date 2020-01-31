package tn.com.digivoip.comman.config;

import java.io.File;
import tn.com.digivoip.framework.xml.XmlElement;

public interface IConfig{

	public abstract File getConfigDirectory();
	public abstract XmlElement get(String name);
}