package de.ulfbiallas.imagedatabase.service;

public interface PasswordHashService {

    String hashPassword(String password);

    boolean checkPassword(String plainPasswordToCheck, String hashedPassword);

}
