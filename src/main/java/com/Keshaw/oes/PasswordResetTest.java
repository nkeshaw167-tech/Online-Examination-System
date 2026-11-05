package com.Keshaw.oes;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordResetTest {

    public static void main(String[] args) {

        String newPassword = "Student@123";

        String hashedPassword =
                BCrypt.hashpw(newPassword, BCrypt.gensalt(12));

        System.out.println("New password hash:");
        System.out.println(hashedPassword);
    }
}

