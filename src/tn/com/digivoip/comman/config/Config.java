package tn.com.digivoip.comman.config;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import tn.com.digivoip.comman.file.FileLocator;
import tn.com.digivoip.comman.file.FileUtil;
import tn.com.digivoip.comman.job.IShutdownManager;
import tn.com.digivoip.comman.job.ShutdownManager;
import tn.com.digivoip.framework.xml.XmlElement;

public class Config implements IConfig{

	private static final String								CORE_STR	= "mailling";
	static final Logger										LOG			= Logger.getLogger("tn.com.digivoip.comman.config.Config");
	protected Map<String, Map<String, DefaultXmlConfig>>	pluginList	= new Hashtable<String, Map<String, DefaultXmlConfig>>();
	protected File											path		= null;
	private static Config									instance;

	public Config() {
		this.path = DefaultConfigDirectory.getDefaultPath();
		if (!this.path.exists()) {
			this.path.mkdir();
		}
		final IShutdownManager shutdownManager = ShutdownManager.getInstance();
		shutdownManager.register(new Runnable(){

			public void run() {
				try {
					save();
				} catch (final Exception e) {
					Config.LOG.severe(e.getMessage());
				}
			}
		});
		Config.instance = this;
	}
	public static Config getInstance() {
		if (Config.instance == null) { throw new RuntimeException("Must call Constructor first!"); }
		return Config.instance;
	}
	public File getConfigDirectory() {
		return path;
	}
	public void registerPlugin(final String moduleName, final String id) {
		File dirModule = new File(this.path, moduleName);
		if (!dirModule.exists()) {
			dirModule.mkdir();
		}
		final File destination = new File(dirModule, id);
		if (!destination.exists()) {
			try {
				FileUtil.copy(FileLocator.getConfFile("tn/com/digivoip/" + moduleName + "/config/" + id), destination);
			} catch (final IOException e) {}
		}
		if (!pluginList.containsKey(moduleName)) {
			final Map<String, DefaultXmlConfig> map = new Hashtable<String, DefaultXmlConfig>();
			pluginList.put(moduleName, map);
		}
		OptionsXmlConfig configPlugin = new OptionsXmlConfig(destination);
		addPlugin(moduleName, id, configPlugin);
		configPlugin.load();
	}
	public DefaultXmlConfig getPlugin(final String moduleName, final String id) {
		if (pluginList.containsKey(moduleName)) {
			final Map<String, DefaultXmlConfig> map = pluginList.get(moduleName);
			if (map.containsKey(id)) {
				final DefaultXmlConfig plugin = map.get(id);
				return plugin;
			}
		}
		return null;
	}
	public void addPlugin(final String moduleName, final String id, final DefaultXmlConfig configPlugin) {
		final Map<String, DefaultXmlConfig> map = pluginList.get(moduleName);
		if (map != null) {
			map.put(id, configPlugin);
		}
	}
	public List<DefaultXmlConfig> getPluginList() {
		final List<DefaultXmlConfig> list = new LinkedList<DefaultXmlConfig>();
		for (String key : pluginList.keySet()) {
			final Map<String, DefaultXmlConfig> map = pluginList.get(key);
			if (map != null) {
				for (String key2 : map.keySet()) {
					final DefaultXmlConfig plugin = map.get(key2);
					list.add(plugin);
				}
			}
		}
		return list;
	}
	public void save() throws Exception {
		for (DefaultXmlConfig plugin : getPluginList()) {
			if (plugin == null) {
				continue;
			}
			plugin.save();
		}
	}
	protected void load() {
		for (DefaultXmlConfig plugin : getPluginList()) {
			if (plugin == null) {
				continue;
			}
			plugin.load();
		}
	}
	public XmlElement get(final String name) {
		final DefaultXmlConfig xml = getPlugin(Config.CORE_STR, name + ".xml");
		return xml.getRoot();
	}
	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.save();
	}
}