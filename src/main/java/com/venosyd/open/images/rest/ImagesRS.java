package com.venosyd.open.images.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public interface ImagesRS {

    String IMAGES_BASE_URI = "/images";

    String IMAGES_SAVE = "/save";

    String IMAGES_LOAD = "/load";

    /**
     * Hello from the server siiiiiiide!
     */
    @GET
    @Path("/echo")
    @Produces({ MediaType.APPLICATION_JSON })
    Response echo();

    /**
     * { hash: $token$logindb collection: 'contact' item: 'photos.png' }
     */
    @POST
    @Path(IMAGES_SAVE)
    @Produces({ MediaType.APPLICATION_JSON })
    Response save(String body);

    /**
     * { hash: $token$logindb collection: 'contact' item: 'photos.png' }
     */
    @POST
    @Path(IMAGES_LOAD)
    @Produces({ MediaType.APPLICATION_JSON })
    Response load(String body);

}
