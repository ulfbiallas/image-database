package de.ulfbiallas.imagedatabase.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashServiceImpl implements PasswordHashService {

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plainPasswordToCheck, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPasswordToCheck, hashedPassword);
        } catch (RuntimeException exception) {
            // TODO: log error.
            System.out.println(exception);
            return false;
        }
    }

}
