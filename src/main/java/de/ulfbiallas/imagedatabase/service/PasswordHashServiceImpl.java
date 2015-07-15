package de.ulfbiallas.imagedatabase.service;

import org.springframework.stereotype.Component;

@Component
public class PasswordHashServiceImpl implements PasswordHashService {

    @Override
    public String hashPassword(String password) {
        return password;
    }

    @Override
    public boolean checkPassword(String plainPasswordToCheck, String hashedPassword) {
        return plainPasswordToCheck.equals(hashedPassword);
    }

}
