package tn.com.digivoip.comman.file;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import tn.com.digivoip.framework.exception.TechnicalException;

public class FileLocator{

	public static URL getConfURL(String filePathName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLocator.class.getClassLoader();
		}
		URL confURL = classLoader.getResource(filePathName);
		if (confURL == null) {
			confURL = classLoader.getResource("META-INF/" + filePathName);
		}
		if (confURL == null) { throw new TechnicalException(" invalid file: " + filePathName); }
		return confURL;
	}
	public static File getConfFile(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLocator.class.getClassLoader();
		}
		URL confURL = classLoader.getResource(fileName);
		if (confURL == null) {
			confURL = classLoader.getResource("META-INF/" + fileName);
		}
		if (confURL == null) {
			throw new TechnicalException(" invalid file: " + fileName);
		} else {
			return new File(confURL.getFile());
		}
	}
	public static String getConfFileName(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLocator.class.getClassLoader();
		}
		URL confURL = classLoader.getResource(fileName);
		if (confURL == null) {
			confURL = classLoader.getResource("META-INF/" + fileName);
		}
		if (confURL == null) {
			throw new TechnicalException(" invalid file: " + fileName);
		} else {
			File file1 = new File(confURL.getFile());
			if (file1.isFile()) {
				return confURL.getFile();
			} else {
				throw new TechnicalException(" it is not a file: " + confURL.getFile());
			}
		}
	}
	public static InputStream getConfStream(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLocator.class.getClassLoader();
		}
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null) {
			stream = classLoader.getResourceAsStream("META-INF/" + fileName);
		}
		if (stream == null) { throw new TechnicalException(" invalid file: " + fileName); }
		return stream;
	}
}
