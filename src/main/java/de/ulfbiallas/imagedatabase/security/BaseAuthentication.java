package de.ulfbiallas.imagedatabase.security;

import org.glassfish.jersey.internal.util.Base64;

public class BaseAuthentication {

    private String username;

    private String password;

    private Boolean isBaseAuthentication;

    public BaseAuthentication(String authorizationHeader) {

        Boolean isPreValidated = (authorizationHeader != null) && authorizationHeader.length()>6 && (authorizationHeader.substring(0, 6).equals("Basic "));

        if (isPreValidated) {

            String basicAuthEncoded = authorizationHeader.substring(6);
            String basicAuthAsString = new String(Base64.decode(basicAuthEncoded.getBytes()));
            System.out.println(basicAuthAsString);
            String[] credentials = basicAuthAsString.split(":");

            if (credentials.length == 2) {
                username = credentials[0];
                password = credentials[1];
                isBaseAuthentication = true;
            } else {
                isBaseAuthentication = false;
            }

        } else {
            isBaseAuthentication = false;
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isBaseAuthentication() {
        return isBaseAuthentication;
    }

}
