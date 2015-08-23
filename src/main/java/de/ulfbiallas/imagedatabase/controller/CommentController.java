package de.ulfbiallas.imagedatabase.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import de.ulfbiallas.imagedatabase.entities.Comment;

@Path("image/{id}/comments")
public class CommentController {

    @OPTIONS
    public Response options() {
        ResponseBuilder responseBuilder = Response.ok();
        responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        return responseBuilder.build();     
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upload(Comment comment) {
        System.out.println("Comment: " + comment);
        return Response.status(Status.CREATED).build();
    }
}
