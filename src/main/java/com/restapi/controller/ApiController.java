package com.restapi.controller;

import com.restapi.entities.UserEntity;
import com.restapi.models.UserModel;
import com.restapi.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {
    UserRepo userRepo;

    public ApiController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @GetMapping("/")
    public String getPage() {
        return "Server is running";
    }

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
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

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        UserEntity user = userRepo.findById(id).get();
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @GetMapping("/users/email")
    public ResponseEntity<UserModel> getUserByEmail(@RequestParam String email) {
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

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @GetMapping("/users/name")
    public ResponseEntity<UserModel> getUserByName(@RequestParam String name) {
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

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @PostMapping("/users")
    public ResponseEntity saveUser(@RequestBody UserModel user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userRepo.save(userEntity);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody UserModel user){
        UserEntity updatedUser = userRepo.findById(id).get();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        userRepo.save(updatedUser);
        return new ResponseEntity(user, HttpStatus.OK);
    }
    @CrossOrigin(origins = "https://kr-login.netlify.app/")
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userRepo.deleteById(id);
        return new ResponseEntity("user deleted",HttpStatus.OK);
    }
}
