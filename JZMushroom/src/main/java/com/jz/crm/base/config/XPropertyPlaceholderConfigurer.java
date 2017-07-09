package com.jz.crm.base.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.jz.crm.base.util.SecUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class XPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	public static final Logger LOG = LoggerFactory.getLogger(XPropertyPlaceholderConfigurer.class);

	private Resource[] locations;

	public void loadProperties(Properties props) throws IOException {
		if (this.locations != null) {
			PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
			for (int i = 0; i < this.locations.length; i++) {
				Resource location = this.locations[i];
				InputStream is = null;
				try {
					is = location.getInputStream();
					propertiesPersister.load(props, is);
					String driverUrl = props.getProperty("jdbc-0.proxool.driver-url");
					String user = props.getProperty("jdbc-0.user");
					String password = props.getProperty("jdbc-0.password");
					if (driverUrl != null) {
						if (user != null && password != null) {
							props.setProperty("jdbc-0.user", SecUtil.decrypt(user));
							props.setProperty("jdbc-0.password", SecUtil.decrypt(password));

							user = props.getProperty("jdbc-0.user");
							password = props.getProperty("jdbc-0.password");
							if (driverUrl.indexOf("?") == -1) {
								driverUrl = driverUrl + "?user=" + user + "&password=" + password;
							} else {
								driverUrl = driverUrl + "&user=" + user + "&password=" + password;
							}

						}
						LOG.info("jdbc-0.proxool.driver-url = " + driverUrl);
						props.setProperty("jdbc-0.proxool.driver-url", driverUrl);
					}
				} finally {
					if (is != null) {

						is.close();
					}
				}
			}
		}
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public static void main(String args[]) {
		System.out.println("?".indexOf("?"));
	}
}
