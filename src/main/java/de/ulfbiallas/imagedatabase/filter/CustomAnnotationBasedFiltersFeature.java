package de.ulfbiallas.imagedatabase.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import de.ulfbiallas.imagedatabase.tools.Log;

public class CustomAnnotationBasedFiltersFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (resourceInfo.getResourceMethod().isAnnotationPresent(Log.class)) {
            context.register(LogFilter.class);
        }
    }

}
