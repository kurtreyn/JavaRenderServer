package com.sbmysql.restapi.repo;

import com.sbmysql.restapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByName(String name);

}
