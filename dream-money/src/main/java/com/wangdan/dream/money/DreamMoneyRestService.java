package com.wangdan.dream.money;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/money")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DreamMoneyRestService {
    @GET
    @Path("/accounts")
    String getAccounts();

    @POST
    @Path("/load-from-file")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    void loadFromFile(@FormDataParam("file") InputStream fileInputStream,
                      @FormDataParam("file") FormDataContentDisposition dataContentDisposition) throws Exception;
}
