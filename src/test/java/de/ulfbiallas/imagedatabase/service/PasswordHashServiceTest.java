package de.ulfbiallas.imagedatabase.service;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class PasswordHashServiceTest {

    private PasswordHashService SUT;

    @Before
    public void init() {
        SUT = new PasswordHashServiceImpl();
    }

    @Test
    public void testHashPassword() {
        String pwToHash = "abc123456";
        String hashedPW = SUT.hashPassword(pwToHash);
        assertFalse("test if password hash does not contain the password", hashedPW.contentEquals(pwToHash));
    }

}
