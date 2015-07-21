package de.ulfbiallas.imagedatabase.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import de.ulfbiallas.imagedatabase.entities.Account;
import de.ulfbiallas.imagedatabase.service.AccountService;



@Path("account")
public class AccountController {

    private final AccountService accountService;



    @Inject
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
    public Response getAccount() {
        List<Account> accounts = accountService.getAllAccounts();
        return Response.ok(accounts).build();
    }



    @RolesAllowed("user")
    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
    public Response getAccountByName(@PathParam("name") String name) {
        Account account = accountService.getByName(name);
        return Response.ok(account).build();
    }



    @OPTIONS
    public Response options() {
        ResponseBuilder responseBuilder = Response.ok();
        responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        return responseBuilder.build();     
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account) {
        accountService.createAccount(account);
        return Response.accepted().build();
    }

}
