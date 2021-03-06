package de.ulfbiallas.imagedatabase.filter;

import java.io.IOException;
import java.security.Principal;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import de.ulfbiallas.imagedatabase.entities.Account;
import de.ulfbiallas.imagedatabase.security.BaseAuthentication;
import de.ulfbiallas.imagedatabase.security.SecurityContextImpl;
import de.ulfbiallas.imagedatabase.service.AccountService;
import de.ulfbiallas.imagedatabase.service.PasswordHashService;

@PreMatching
public class AuthFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final AccountService accountService;

    private final PasswordHashService passwordHashService;

    @Inject
    public AuthFilter(final AccountService accountService, final PasswordHashService passwordHashService) {
        this.accountService = accountService;
        this.passwordHashService = passwordHashService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString("Authorization");
        BaseAuthentication baseAuthentication = new BaseAuthentication(authorizationHeader);

        if (baseAuthentication.isBaseAuthentication()) {
            Account account = accountService.getByName(baseAuthentication.getUsername());
            if (account != null) {
                if (passwordHashService.checkPassword(baseAuthentication.getPassword(), account.getPassword())) {
                    System.out.println("LOGGED IN AS: " + account.getName() + " / " + account.getEmail());
                    requestContext.setSecurityContext(new SecurityContextImpl(baseAuthentication));
                } else {
                    System.out.println("WRONG PASSWORD");
                    abortBySendingStatusUnauthorized(requestContext);
                }
            } else {
                System.out.println("NO ACCOUNT FOUND");
                abortBySendingStatusUnauthorized(requestContext);
            }
        } else {
            System.out.println("NO AUTHORIZATION PROVIDED / ANONYMOUS ACCESS");
        }

    }

    private void abortBySendingStatusUnauthorized(ContainerRequestContext requestContext) {
        ResponseBuilder responseBuilder = Response.status(Status.UNAUTHORIZED);
        responseBuilder.header("WWW-Authenticate", "Basic realm=\"ImageDatabase\"");
        requestContext.abortWith(responseBuilder.build());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (responseContext.getStatus() == Status.FORBIDDEN.getStatusCode()) {
            Principal principal = requestContext.getSecurityContext().getUserPrincipal();
            if (principal == null) {
                responseContext.getHeaders().putSingle("WWW-Authenticate", "Basic realm=\"ImageDatabase\"");
                responseContext.setStatusInfo(Status.UNAUTHORIZED);
            }
        }
    }

}
