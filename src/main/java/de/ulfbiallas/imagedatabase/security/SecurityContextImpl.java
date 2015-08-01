package de.ulfbiallas.imagedatabase.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.sun.net.httpserver.HttpPrincipal;

public class SecurityContextImpl implements SecurityContext {

    private BaseAuthentication baseAuthentication;

    public SecurityContextImpl(BaseAuthentication baseAuthentication) {
        this.baseAuthentication = baseAuthentication;
    }

    @Override
    public Principal getUserPrincipal() {
        return new HttpPrincipal(baseAuthentication.getUsername(), "ImageDatabase");
    }

    @Override
    public boolean isUserInRole(String role) {
        if(role.equals("user")) return true;
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }

}
