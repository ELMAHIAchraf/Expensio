package com.example.Expenses.Controller;

import com.example.Expenses.Config.MyUser;
import com.example.Expenses.DTO.PasswordChangeDTO;
import com.example.Expenses.DTO.UserRequest;
import com.example.Expenses.DTO.UserDTO;
import com.example.Expenses.Service.UserService.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private UserService userService;
    private ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<UserDTO> getAuthUser(){
        MyUser user =(MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @ModelAttribute UserRequest user) throws IOException {
        UserDTO createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user, @PathVariable long id) throws IOException {
        UserDTO updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @PutMapping("/{id}/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO data, @PathVariable long id) throws IOException {
        String password = userService.changePassword(id, data);
        return new ResponseEntity<>(password, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) throws IOException {
        UserDTO fetchedUser = userService.getUser(id);
        return new ResponseEntity<>(fetchedUser, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws  IOException {
        String deletedUser = userService.deleteUser(id);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }


}
