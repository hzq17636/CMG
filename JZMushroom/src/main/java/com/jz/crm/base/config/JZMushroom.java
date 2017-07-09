package com.jz.crm.base.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import com.jz.crm.base.exception.SystemException;
import com.jz.crm.base.util.FileUtil;
import com.jz.crm.base.util.ResourceUitl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;

/**
 * Load properties from a customized properties file. This class follows the
 * singleton design pattern.
 * 
 * @param <T>
 */
public class JZMushroom {
	public static final Logger LOG = LoggerFactory.getLogger(JZMushroom.class);
	// load resource from classpath
	private static final String RESOURCE_CLASSPATH = "classpath";

	// load resource from file path.
	private static final String RESOURCE_FILE = "file";

	private static final String RESOURCE_DELIM = ":";

	private static JZMushroom instance = null;

	private String defaultPropertiesFile = "classpath:jz_mushroom.properties";

	private String propFile = null;

	private Properties prop;

	private BeanFactory beanFactory;

	private Map<String, String> userMap = new ConcurrentHashMap<String, String>();

	public static String WORKINGKEY_FRESH_DATE = "";

	public static JAXBContext context;

	private static Map<String, List<String>> imageConf = null;

	private static Map<String, String> jsMap = new HashMap();

	public static String JS_FILE = "js";

	public synchronized static JZMushroom getInstance() {
		try {
			if (instance == null) {
				instance = new JZMushroom();
			}
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取js文件中的内容.
	 * 
	 * @param jsFileName
	 *            js文件名包括后缀，如aa.js
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getJsContent(String jsFileName) {
		if (jsMap == null || jsMap.size() == 0) {
			jsMap = new HashMap();
			try {
				String classpath = JZMushroom.class.getResource("/").getFile().toString();
				List<String> files = FileUtil.getAllFile(classpath + JS_FILE);
				for (String file : files) {
					StringBuffer jsContent = new StringBuffer();
					String line = "";
					String filePathName = new File(file).getName();
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					do {
						jsContent.append(line).append("\r");
						line = br.readLine();
					} while (line != null);
					if (jsContent.length() > 0) {
						jsMap.put(filePathName, jsContent.toString());
					}
				}
			} catch (Exception e) {
				LOG.error("Loaded js file error", e);
			}
		}

		if (jsFileName != null && jsMap != null && jsMap.size() > 0) {
			return jsMap.get(jsFileName);
		}
		return null;
	}

	public int getInt(String key) {
		String tmp = this.get(key);
		try {
			return Integer.parseInt(tmp);
		} catch (Exception e) {
			throw new IllegalArgumentException("The entry(key=" + key + ",value=" + tmp + ") is not INT type.");
		}
	}

	public boolean getBoolean(String key) {
		String value = this.get(key);
		if (value != null && value.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	public double getDouble(String key) {
		String tmp = this.get(key);
		try {
			return Double.parseDouble(tmp);
		} catch (Exception e) {
			throw new IllegalArgumentException("The entry(key=" + key + ",value=" + tmp + ") is not DOUBLE type.");
		}
	}

	public String get(String key) {
		String value = prop.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("can NOT find entry(" + key + ") in properties file:"
					+ this.getPropFile());
		}
		return value;
	}

	public String get(String key, String defaultValue) {
		String value = this.get(key);
		return (value == null) ? defaultValue : value;
	}

	// -----------------------------------------------------
	// BUSINESS METHODS WRAPPER
	// -----------------------------------------------------

	public <T> T getBean(Class<T> c, String key) {
		if (this.getBeanFactory() == null) {
			throw new SystemException("getBeanFactory() is null, please initialize context first!");
		}
		return (T) this.getBeanFactory().getBean(this.get(key), c);
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public String getTriperDesIV() {
		return this.get("triperdes.iv");
	}

	public String getInstantTicketSerialNoFormat() {
		return this.get("dataformat.instantticket.serialno");
	}

	public String getDefaultEncoding() {
		return this.get("default.encoding");
	}

	public String getMappingFile(int transType) {
		return this.get("transtype." + transType);
	}

	/**
	 * Get the port of protocol(http/https), which is defined by
	 * Tomecat(server.xml)
	 */
	public int getProtocolPort(String protocol) {
		protocol = protocol.toLowerCase();
		return this.getInt("port." + protocol);
	}

	public String getLottoNumberFormat(int betOption) {
		return this.get("lotto.selectednumber." + betOption);
	}

	public int getWaitLockTime() {
		return this.getInt("database.waitforlock");
	}

	public String getIgnoredPIN() {
		return this.get("ticket.pin.ignored");
	}

	// -----------------------------------------------------
	// PRIVATE METHODS
	// -----------------------------------------------------

	private JZMushroom() throws Exception {
		prop = new Properties();
		prop.load(ResourceUitl.openInputStream(defaultPropertiesFile));
	}

	public Map<String, String> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}

	public String getPropFile() {
		return propFile;
	}

}
