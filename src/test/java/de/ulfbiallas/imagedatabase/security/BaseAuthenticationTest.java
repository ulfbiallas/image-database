package de.ulfbiallas.imagedatabase.security;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaseAuthenticationTest {

    @Test
    public void test_BaseAuthentication_Positive() {
        String authenticationHeader = "Basic VGVzdGVyOjEyMzQ1Ng==";

        BaseAuthentication baseAuthentication = new BaseAuthentication(authenticationHeader);

        assertEquals("test if base authentication was detected", true, baseAuthentication.isBaseAuthentication());
        assertEquals("test if username is as expected", "Tester", baseAuthentication.getUsername());
        assertEquals("test if password is as expected", "123456", baseAuthentication.getPassword());
    }

    @Test
    public void test_BaseAuthentication_Negative_AuthHeaderNull() {
        String authenticationHeader = null;

        BaseAuthentication baseAuthentication = new BaseAuthentication(authenticationHeader);

        assertEquals("test if base authentication was not detected", false, baseAuthentication.isBaseAuthentication());
        assertEquals("test if username is not set", null, baseAuthentication.getUsername());
        assertEquals("test if password is not set", null, baseAuthentication.getPassword());
    }

    @Test
    public void test_BaseAuthentication_Negative_EmptyAuthHeader() {
        String authenticationHeader = "";

        BaseAuthentication baseAuthentication = new BaseAuthentication(authenticationHeader);

        assertEquals("test if base authentication was not detected", false, baseAuthentication.isBaseAuthentication());
        assertEquals("test if username is not set", null, baseAuthentication.getUsername());
        assertEquals("test if password is not set", null, baseAuthentication.getPassword());
    }

    @Test
    public void test_BaseAuthentication_Negative_ArbitraryAuthHeader() {
        String authenticationHeader = "MyAuthenticatioHeader";

        BaseAuthentication baseAuthentication = new BaseAuthentication(authenticationHeader);

        assertEquals("test if base authentication was not detected", false, baseAuthentication.isBaseAuthentication());
        assertEquals("test if username is not set", null, baseAuthentication.getUsername());
        assertEquals("test if password is not set", null, baseAuthentication.getPassword());
    }

    @Test
    public void test_BaseAuthentication_Negative_UsernameOnly() {
        String authenticationHeader = "Basic VGVzdGVydGVzdGluZw==";

        BaseAuthentication baseAuthentication = new BaseAuthentication(authenticationHeader);

        assertEquals("test if base authentication was not detected", false, baseAuthentication.isBaseAuthentication());
        assertEquals("test if username is not set", null, baseAuthentication.getUsername());
        assertEquals("test if password is not set", null, baseAuthentication.getPassword());
    }

    @Test
    public void test_BaseAuthentication_Negative_NotABase64Hash() {
        String authenticationHeader = "Basic 12345";

        BaseAuthentication baseAuthentication = new BaseAuthentication(authenticationHeader);

        assertEquals("test if base authentication was not detected", false, baseAuthentication.isBaseAuthentication());
        assertEquals("test if username is not set", null, baseAuthentication.getUsername());
        assertEquals("test if password is not set", null, baseAuthentication.getPassword());
    }

}
