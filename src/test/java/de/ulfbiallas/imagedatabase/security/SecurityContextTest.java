package de.ulfbiallas.imagedatabase.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SecurityContextTest {

    private SecurityContext SUT;

    @Before
    public void init() {
        BaseAuthentication baseAuthentication = mock(BaseAuthentication.class);
        Mockito.when(baseAuthentication.getUsername()).thenReturn("testuser");
        SUT = new SecurityContextImpl(baseAuthentication);
    }

    @Test
    public void test_SecurityContextImpl() {
        Principal principal = SUT.getUserPrincipal();
        assertEquals("test if principal name is as expected", "testuser", principal.getName());
        assertEquals("test if it is not secure", false, SUT.isSecure());
        assertEquals("test if auth scheme is basic", SecurityContext.BASIC_AUTH, SUT.getAuthenticationScheme());
        assertEquals("test if user role is set", true, SUT.isUserInRole("user"));
        assertEquals("test if admin role is not set", false, SUT.isUserInRole("admin"));
        assertEquals("test if arbitrary other role is not set", false, SUT.isUserInRole("test"));
    }

}
