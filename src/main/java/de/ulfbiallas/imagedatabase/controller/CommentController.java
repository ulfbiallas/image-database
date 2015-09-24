package de.ulfbiallas.imagedatabase.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import de.ulfbiallas.imagedatabase.entities.Account;
import de.ulfbiallas.imagedatabase.entities.Comment;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.repository.CommentRepository;
import de.ulfbiallas.imagedatabase.repository.ImageRecordRepository;
import de.ulfbiallas.imagedatabase.service.AccountService;

@Path("image/{id}/comments")
public class CommentController {

    private final AccountService accountService;

    private final ImageRecordRepository imageRecordRepository;

    private final CommentRepository commentRepository;

    @Inject
    public CommentController(final AccountService accountService, final ImageRecordRepository imageRecordRepository, final CommentRepository commentRepository) {
        this.accountService = accountService;
        this.imageRecordRepository = imageRecordRepository;
        this.commentRepository = commentRepository;
    }

    @OPTIONS
    public Response options() {
        ResponseBuilder responseBuilder = Response.ok();
        responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        return responseBuilder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
    public Response getComments(@PathParam("id") String imageId) {
        //List<Comment> comments = commentRepository.findAll();
        ImageRecord imageRecord = imageRecordRepository.findById(imageId);
        List<Comment> comments = commentRepository.findCommentsBySubject(imageRecord);
        return Response.ok(comments).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upload(Comment comment, @PathParam("id") String imageId, @Context SecurityContext securityContext) {
        String accountName = securityContext.getUserPrincipal().getName();
        System.out.println("Comment: " + comment);
        System.out.println("ImageId: " + imageId);
        System.out.println("loggedInAs: " + accountName);

        Account account = accountService.getByName(accountName);
        ImageRecord imageRecord = imageRecordRepository.findById(imageId);
        comment.setAuthor(account);
        comment.setSubject(imageRecord);
        comment.setTime(new Date());
        comment.setId(UUID.randomUUID().toString());

        comment = commentRepository.save(comment);
        System.out.println("saved comment.");
        imageRecord.getComments().add(comment);
        imageRecordRepository.save(imageRecord);
        System.out.println("saved imageRecord.");

        return Response.status(Status.CREATED).build();
    }
}
