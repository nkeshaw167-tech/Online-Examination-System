package com.Keshaw.oes.dao;

import com.Keshaw.oes.model.User;
import com.Keshaw.oes.util.DBconnection;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user)
            throws SQLException, IOException {

        String sql = "INSERT INTO users "
                + "(full_name, email, password_hash, role) "
                + "VALUES (?, ?, ?, ?)";

        String hashedPassword =
                BCrypt.hashpw(user.getPasswordHash(),
                        BCrypt.gensalt(12));

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashedPassword);
            ps.setString(4, "STUDENT");

            return ps.executeUpdate() > 0;
        }
    }

    public User loginUser(String email, String password)
            throws SQLException, IOException {

        String sql = "SELECT user_id, full_name, email, "
                + "password_hash, role FROM users WHERE email = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String storedHash =
                            rs.getString("password_hash");

                    if (BCrypt.checkpw(password, storedHash)) {

                        User user = new User();

                        user.setUserId(rs.getInt("user_id"));
                        user.setFullName(rs.getString("full_name"));
                        user.setEmail(rs.getString("email"));

                        // Do not expose the stored password hash.
                        user.setPasswordHash(null);

                        user.setRole(rs.getString("role"));

                        return user;
                    }
                }
            }
        }

        return null;
    }

    public boolean changePassword(
            int userId,
            String currentPassword,
            String newPassword)
            throws SQLException, IOException {

        String selectSql =
                "SELECT password_hash FROM users WHERE user_id = ?";

        String updateSql =
                "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try (Connection con = DBconnection.getConnection()) {

            // Step 1: Retrieve the existing password hash
            try (PreparedStatement ps = con.prepareStatement(selectSql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        return false;
                    }

                    String storedHash = rs.getString("password_hash");

                    // Step 2: Verify current password
                    if (!BCrypt.checkpw(currentPassword, storedHash)) {
                        return false;
                    }
                }
            }

            // Step 3: Hash the new password
            String newPasswordHash = BCrypt.hashpw(
                    newPassword,
                    BCrypt.gensalt(12)
            );

            // Step 4: Update password in database
            try (PreparedStatement ps = con.prepareStatement(updateSql)) {

                ps.setString(1, newPasswordHash);
                ps.setInt(2, userId);

                return ps.executeUpdate() > 0;
            }
        }
    }

}
