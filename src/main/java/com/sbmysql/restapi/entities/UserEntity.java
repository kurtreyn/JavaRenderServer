package com.sbmysql.restapi.entities;

import jakarta.persistence.*;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

@Entity
@Table(name = "users_db")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String email;

    @Column
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
}
