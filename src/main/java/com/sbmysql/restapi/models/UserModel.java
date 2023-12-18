package com.sbmysql.restapi.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

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
        Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(password));
    }

    public void setPassword(String password) {
        Encoder encoder = Base64.getEncoder();
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
