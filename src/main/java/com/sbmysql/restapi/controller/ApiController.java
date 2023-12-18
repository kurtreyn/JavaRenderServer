package com.sbmysql.restapi.controller;

import com.sbmysql.restapi.entities.UserEntity;
import com.sbmysql.restapi.models.UserModel;
import com.sbmysql.restapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {
//    @Autowired
    UserRepo userRepo;

    public ApiController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @GetMapping("/")
    public String getPage() {
        return "Server is running";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserEntity> users = userRepo.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for (UserEntity user : users) {
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            userModels.add(userModel);
        }
        return new ResponseEntity<List<UserModel>>(userModels, HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        UserEntity user = userRepo.findById(id).get();
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }


    @GetMapping("/users/email")
    public ResponseEntity<UserModel> getUserByEmail(@RequestParam String email) {
//        http://localhost:8080/users/email?email=kurt@email.com
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepo.findByEmail(email));
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserEntity user = userOptional.get();
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
    }


    @GetMapping("/users/name")
    public ResponseEntity<UserModel> getUserByName(@RequestParam String name) {
//        http://localhost:8080/users/name?name=Kurt
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepo.findByName(name));
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserEntity user = userOptional.get();
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users")
    public ResponseEntity saveUser(@RequestBody UserModel user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userRepo.save(userEntity);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody UserModel user){
        UserEntity updatedUser = userRepo.findById(id).get();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        userRepo.save(updatedUser);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userRepo.deleteById(id);
        return new ResponseEntity("user deleted",HttpStatus.OK);
    }
}
