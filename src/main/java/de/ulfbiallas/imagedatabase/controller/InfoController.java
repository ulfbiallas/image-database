package de.ulfbiallas.imagedatabase.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import de.ulfbiallas.imagedatabase.service.SystemInfoService;



@Path("info")
public class InfoController {

    private final SystemInfoService systemInfoService;



    @Inject
    public InfoController(final SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }



    @GET
    public Response getInfo() {
        String info = systemInfoService.getSystemInfo();
        return Response.ok(info).build();
    }

}
