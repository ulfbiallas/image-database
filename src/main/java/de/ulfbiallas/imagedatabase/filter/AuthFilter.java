package de.ulfbiallas.imagedatabase.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import de.ulfbiallas.imagedatabase.entities.Account;
import de.ulfbiallas.imagedatabase.service.AccountService;
import de.ulfbiallas.imagedatabase.tools.BaseAuthentication;

public class AuthFilter implements ContainerRequestFilter {

    private final AccountService accountService;

    @Inject
    public AuthFilter(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString("Authorization");
        BaseAuthentication baseAuthentication = new BaseAuthentication(authorizationHeader);

        if (baseAuthentication.isBaseAuthentication()) {
            Account account = accountService.getByName(baseAuthentication.getUsername());
            if (account != null) {
                System.out.println("LOGGED IN AS: " + account.getName() + " / " + account.getEmail());
            } else {
                abortBySendingStatusUnauthorized(requestContext);
            }
        } else {
            abortBySendingStatusUnauthorized(requestContext);
        }

    }

    private void abortBySendingStatusUnauthorized(ContainerRequestContext requestContext) {
        ResponseBuilder responseBuilder = Response.status(Status.UNAUTHORIZED);
        responseBuilder.header("WWW-Authenticate", "Basic realm=\"ImageDatabase\"");
        requestContext.abortWith(responseBuilder.build());
    }

}
