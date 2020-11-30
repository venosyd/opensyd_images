package com.venosyd.open.images.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
@ApplicationPath(ImagesRS.IMAGES_BASE_URI)
public class ImagesRESTfulAPI extends Application {

    public Set<Class<?>> getClasses() {
        var classes = new HashSet<Class<?>>();
        classes.add(ImagesRSImpl.class);

        return classes;
    }
}
