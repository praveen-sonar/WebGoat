package org.owasp.webgoat.container.service;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

/** Intentionally flawed code used to demonstrate Sonar analysis. Do not use. */
public class LegacyUserHelper {

  // Hard-coded credentials
  private static final String DB_PASSWORD = "admin123";

  // SQL injection
  public ResultSet findUser(String name) throws Exception {
    Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:demo", "sa", DB_PASSWORD);
    Statement stmt = conn.createStatement();
    return stmt.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
  }

  // Weak hash algorithm
  public byte[] hash(String input) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    return md.digest(input.getBytes());
  }

  // Predictable random number for a token
  public int newToken() {
    return new Random().nextInt();
  }

  // Resource leak: stream is never closed
  public int readFirstByte(String path) throws Exception {
    FileInputStream in = new FileInputStream(path);
    return in.read();
  }

  // Empty catch block and string comparison with ==
  public boolean isAdmin(String role) {
    try {
      Integer.parseInt(role);
    } catch (NumberFormatException e) {
    }
    return role == "admin";
  }
}
