package de.ulfbiallas.imagedatabase;

import java.net.URI;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import de.ulfbiallas.imagedatabase.config.ServerConfiguration;



public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages("de.ulfbiallas.imagedatabase.controller");
		resourceConfig.register(MultiPartFeature.class);
		resourceConfig.register(JacksonFeature.class);

		ServerConfiguration serverConfiguration = new ServerConfiguration();
		String url = "http://localhost:"+serverConfiguration.getPort()+"/";

		JdkHttpServerFactory.createHttpServer(URI.create(url),resourceConfig);
	}

}
