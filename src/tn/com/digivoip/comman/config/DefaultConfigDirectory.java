package tn.com.digivoip.comman.config;

import java.io.File;
import tn.com.digivoip.comman.FwCore;

public class DefaultConfigDirectory{

	private static DefaultConfigDirectory	instance	= new DefaultConfigDirectory();
	private File							currentPath;

	private DefaultConfigDirectory() {}
	public static DefaultConfigDirectory getInstance() {
		return DefaultConfigDirectory.instance;
	}
	public void setCurrentPath(File currentPath) {
		this.currentPath = currentPath;
	}
	public File getCurrentPath() {
		if (currentPath == null) {
			currentPath = DefaultConfigDirectory.getDefaultPath();
		}
		return currentPath;
	}
	public static File getDefaultPath() {
		return new File(System.getProperty("user.home"), FwCore.tempFilePrefix);
	}
}
