package de.ulfbiallas.imagedatabase.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfiguration {

	private int port;

	public ServerConfiguration() {
		try {
			Properties properties = new Properties();
			InputStream stream = ClassLoader.getSystemResourceAsStream("server.properties");
			if(stream != null) {
				properties.load(stream);
				setLoadedProperties(properties);
			} else {
				setDefaultProperties();
			}
		} catch (IOException e) {
			setDefaultProperties();
		}
	}

	private void setLoadedProperties(Properties properties) {
		port = Integer.parseInt(properties.getProperty("port"));
	}

	private void setDefaultProperties() {
		port = 8080;
	}

	public int getPort() {
		return port;
	}

}
