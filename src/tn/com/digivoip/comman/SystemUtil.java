package tn.com.digivoip.comman;

import java.io.File;
import java.util.ArrayList;
import tn.com.digivoip.comman.string.StringPool;

/** Various system utilities. */
public class SystemUtil{

	// ---------------------------------------------------------------- properties
	public static final String	USER_DIR					= "user.dir";
	public static final String	USER_NAME					= "user.name";
	public static final String	USER_HOME					= "user.home";
	public static final String	JAVA_HOME					= "java.home";
	public static final String	TEMP_DIR					= "java.io.tmpdir";
	public static final String	OS_NAME						= "os.name";
	public static final String	OS_VERSION					= "os.version";
	public static final String	JAVA_VERSION				= "java.version";
	public static final String	JAVA_SPECIFICATION_VERSION	= "java.specification.version";
	public static final String	JAVA_VENDOR					= "java.vendor";
	public static final String	JAVA_CLASSPATH				= "java.class.path";
	public static final String	PATH_SEPARATOR				= "path.separator";
	public static final String	HTTP_PROXY_HOST				= "http.proxyHost";
	public static final String	HTTP_PROXY_PORT				= "http.proxyPort";
	public static final String	HTTP_PROXY_USER				= "http.proxyUser";
	public static final String	HTTP_PROXY_PASSWORD			= "http.proxyPassword";
	public static final String	FILE_ENCODING				= "file.encoding";
	public static final String	SUN_BOOT_CLASS_PATH			= "sun.boot.class.path";
	private static int			javaVersionNumber;
	static {
		// determine the Java version by looking at available classes.
		try {
			// 1.0
			SystemUtil.javaVersionNumber = 10;
			Class.forName("java.lang.Void");
			// 1.1
			SystemUtil.javaVersionNumber++;
			Class.forName("java.lang.ThreadLocal");
			// 1.2
			SystemUtil.javaVersionNumber++;
			Class.forName("java.lang.StrictMath");
			// 1.3
			SystemUtil.javaVersionNumber++;
			Class.forName("java.lang.CharSequence");
			// 1.4
			SystemUtil.javaVersionNumber++;
			Class.forName("java.net.Proxy");
			// 1.5
			SystemUtil.javaVersionNumber++;
			Class.forName("java.net.CookieStore");
			// 1.6
			SystemUtil.javaVersionNumber++;
			Class.forName("java.nio.file.FileSystem");
			// 1.7
			SystemUtil.javaVersionNumber++;
			Class.forName("java.lang.reflect.Executable");
			// 1.8
			SystemUtil.javaVersionNumber++;
		} catch (Throwable ignore) {}
	}
	private static String[]		jrePackages;

	/** Returns list of packages, build into runtime jars. */
	public static String[] getJrePackages() {
		if (SystemUtil.jrePackages == null) {
			SystemUtil.buildJrePackages();
		}
		return SystemUtil.jrePackages;
	}
	/** Builds a set of java core packages. */
	private static void buildJrePackages() {
		ArrayList<String> packages = new ArrayList<String>();
		switch (SystemUtil.javaVersionNumber) {
		case 18:
		case 17:
		case 16:
		case 15:
			// in Java1.5, the apache stuff moved
			packages.add("com.sun.org.apache");
			// fall through...
		case 14:
			if (SystemUtil.javaVersionNumber == 14) {
				packages.add("org.apache.crimson");
				packages.add("org.apache.xalan");
				packages.add("org.apache.xml");
				packages.add("org.apache.xpath");
			}
			packages.add("org.ietf.jgss");
			packages.add("org.w3c.dom");
			packages.add("org.xml.sax");
			// fall through...
		case 13:
			packages.add("org.omg");
			packages.add("com.sun.corba");
			packages.add("com.sun.jndi");
			packages.add("com.sun.media");
			packages.add("com.sun.naming");
			packages.add("com.sun.org.omg");
			packages.add("com.sun.rmi");
			packages.add("sunw.io");
			packages.add("sunw.util");
			// fall through...
		case 12:
			packages.add("com.sun.java");
			packages.add("com.sun.image");
			// fall through...
		case 11:
		default:
			// core stuff
			packages.add("sun");
			packages.add("java");
			packages.add("javax");
		break;
		}
		SystemUtil.jrePackages = packages.toArray(new String[packages.size()]);
	}
	/** Returns current working folder. */
	public static String getUserDir() {
		return System.getProperty(SystemUtil.USER_DIR);
	}
	/** Returns current user. */
	public static String getUserName() {
		return System.getProperty(SystemUtil.USER_NAME);
	}
	/** Returns user home folder. */
	public static String getUserHome() {
		return System.getProperty(SystemUtil.USER_HOME);
	}
	/** Returns current working folder. Just a better name for {@link #getUserDir()}. */
	public static String getWorkingFolder() {
		return System.getProperty(SystemUtil.USER_DIR);
	}
	/** Returns JRE home. */
	public static String getJavaJreHome() {
		return System.getProperty(SystemUtil.JAVA_HOME);
	}
	/** Returns JAVA_HOME which is not equals to "java.home" property since it points to JAVA_HOME/jre folder. */
	public static String getJavaHome() {
		String home = System.getProperty(SystemUtil.JAVA_HOME);
		if (home == null) { return null; }
		int i = home.lastIndexOf('\\');
		int j = home.lastIndexOf('/');
		if (j > i) {
			i = j;
		}
		return home.substring(0, i);
	}
	/** Returns system temp dir. */
	public static String getTempDir() {
		return System.getProperty(SystemUtil.TEMP_DIR);
	}
	/** Returns OS name. */
	public static String getOsName() {
		return System.getProperty(SystemUtil.OS_NAME);
	}
	/** Returns OS version. */
	public static String getOsVersion() {
		return System.getProperty(SystemUtil.OS_VERSION);
	}
	/** Returns Java version string, as specified in system property. Returned string contain major version, minor version and revision.
	 * @see #getJavaSpecificationVersion() */
	public static String getJavaVersion() {
		return System.getProperty(SystemUtil.JAVA_VERSION);
	}
	/** Retrieves the version of the currently running JVM. */
	public static String getJavaSpecificationVersion() {
		return System.getProperty(SystemUtil.JAVA_SPECIFICATION_VERSION);
	}
	/** Returns detected java version. Returned number is a number 10x the <code>major.minor</code>, e.g. Java1.5 returns <code>15</code>. */
	public static int getJavaVersionNumber() {
		return SystemUtil.javaVersionNumber;
	}
	/** Returns Java vendor. */
	public static String getJavaVendor() {
		return System.getProperty(SystemUtil.JAVA_VENDOR);
	}
	/** Checks if the currently running JVM is at least compliant with provided JDK version.
	 * @param version java version multiplied by 10, e.g. <code>1.5</code> is <code>15</code> */
	public static boolean isAtLeastJavaVersion(int version) {
		return SystemUtil.javaVersionNumber >= version;
	}
	/** Checks if the currently running JVM is equal to provided version.
	 * @param version java version, multiplied by 10, e.g. <code>1.5</code> is <code>15</code>. */
	public static boolean isJavaVersion(int version) {
		return SystemUtil.javaVersionNumber == version;
	}
	/** Returns system class path. */
	public static String getClassPath() {
		return System.getProperty(SystemUtil.JAVA_CLASSPATH);
	}
	/** Returns path separator. */
	public static String getPathSeparator() {
		return System.getProperty(SystemUtil.PATH_SEPARATOR);
	}
	/** Returns file encoding. */
	public static String getFileEncoding() {
		return System.getProperty(SystemUtil.FILE_ENCODING);
	}
	/** Returns <code>true</code> if host is Windows. */
	public static boolean isHostWindows() {
		return SystemUtil.getOsName().toUpperCase().startsWith("WINDOWS");
	}
	/** Returns <code>true</code> if host is Linux. */
	public static boolean isHostLinux() {
		return SystemUtil.getOsName().toUpperCase().startsWith("LINUX");
	}
	/** Returns <code>true</code> if host is a general unix. */
	public static boolean isHostUnix() {
		return File.pathSeparator.equals(StringPool.COLON);
	}
	/** Returns <code>true</code> if host is Mac. */
	public static boolean isHostMac() {
		return SystemUtil.getOsName().toUpperCase().startsWith("MAC OS X");
	}
	/** Returns <code>true</code> if host is Solaris. */
	public static boolean isHostSolaris() {
		return SystemUtil.getOsName().toUpperCase().startsWith("SUNOS");
	}
	/** Returns <code>true</code> if host is AIX. */
	public static boolean isHostAix() {
		return SystemUtil.getOsName().toUpperCase().equals("AIX");
	}
	/** Returns bootstrap class path. */
	public static String getSunBoothClassPath() {
		return System.getProperty(SystemUtil.SUN_BOOT_CLASS_PATH);
	}
	// ---------------------------------------------------------------- http proxy
	/** Sets HTTP proxy settings. */
	public static void setHttpProxy(String host, String port, String username, String password) {
		System.getProperties().put(SystemUtil.HTTP_PROXY_HOST, host);
		System.getProperties().put(SystemUtil.HTTP_PROXY_PORT, port);
		System.getProperties().put(SystemUtil.HTTP_PROXY_USER, username);
		System.getProperties().put(SystemUtil.HTTP_PROXY_PASSWORD, password);
	}
	/** Sets HTTP proxy settings. */
	public static void setHttpProxy(String host, String port) {
		System.getProperties().put(SystemUtil.HTTP_PROXY_HOST, host);
		System.getProperties().put(SystemUtil.HTTP_PROXY_PORT, port);
	}
}