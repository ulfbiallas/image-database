package de.ulfbiallas.imagedatabase;

import java.net.URI;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import de.ulfbiallas.imagedatabase.config.ServerConfiguration;
import de.ulfbiallas.imagedatabase.filter.AuthFilter;
import de.ulfbiallas.imagedatabase.filter.CorsFilter;



public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages("de.ulfbiallas.imagedatabase.controller");
		resourceConfig.register(MultiPartFeature.class);
		resourceConfig.register(JacksonFeature.class);
		resourceConfig.register(CorsFilter.class);
		resourceConfig.register(AuthFilter.class);
		resourceConfig.register(RolesAllowedDynamicFeature.class);

		ServerConfiguration serverConfiguration = new ServerConfiguration();
		String url = "http://localhost:"+serverConfiguration.getPort()+"/";

		JdkHttpServerFactory.createHttpServer(URI.create(url),resourceConfig);
	}

}
