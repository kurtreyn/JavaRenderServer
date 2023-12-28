package com.restapi.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Base64;

public class UserModel {

    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private String name;

    private String email;

    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName (String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        if (password == null) {
            return null;
        }

        try {
            Base64.Decoder decoder = Base64.getDecoder();
            String decodedPassword = new String(decoder.decode(password));
            return decodedPassword;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        Base64.Encoder encoder = Base64.getEncoder();
        this.password = encoder.encodeToString(password.getBytes());
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='**********" + '\'' +
                '}';
    }
}
