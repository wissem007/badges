package tn.com.smartsoft.commons.utils.velocity;

import java.io.InputStream;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.utils.ClassUtilities;

/**
 */
public class VelocityInstanceFactory {

	private static final String VELOCITY_PROPERTIES = ClassUtilities.getRessourcePath(VelocityInstanceFactory.class, "/velocity.properties");

	private static final Logger LOG = Logger.getLogger(VelocityInstanceFactory.class);

	public static VelocityEngine velocityEngine;

	public static VelocityEngine getVelocityEngine() {
		if (velocityEngine == null) {
			velocityEngine = newInstance();
		}
		return velocityEngine;
	}

	private static VelocityEngine newInstance() {
		VelocityEngine thisVelocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		LOG.debug("Velocity is init by velocity properties file [" + VELOCITY_PROPERTIES + "]");
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(VELOCITY_PROPERTIES);
			if (is == null) {
				throw new NullPointerException("InputStream to velocity properties is null !");
			} else {
				properties.load(is);
			}

			if (properties == null) {
				throw new NullPointerException("Path to velocity properties is null !");
			} else {
				thisVelocityEngine.init(properties);
			}

		} catch (Exception e) {
			LOG.warn("Velocity's properties are not found ! Using default preferences");
			thisVelocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
			thisVelocityEngine.setProperty("runtime.log.logsystem.log4j.category", VelocityInstanceFactory.class.getName());
			thisVelocityEngine.setProperty(Velocity.COUNTER_INITIAL_VALUE, "0");
			thisVelocityEngine.setProperty(Velocity.RESOURCE_LOADER, "classpath");
			thisVelocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
			thisVelocityEngine.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class", ClasspathResourceLoader.class.getName());
			try {
				thisVelocityEngine.init();
			} catch (Exception ve) {
				LOG.fatal("Error while initing Veloticy Engine ! ", ve);
			}
		}

		return thisVelocityEngine;
	}

}
