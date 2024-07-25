package com.example.Expenses.Service.UserService;

import com.example.Expenses.DTO.PasswordChangeDTO;
import com.example.Expenses.DTO.UserRequest;
import com.example.Expenses.DTO.UserDTO;

import java.io.IOException;

public interface UserService {
    UserDTO createUser(UserRequest user) throws IOException;
    UserDTO updateUser(long id, UserDTO user) throws IOException;
    String changePassword(long id, PasswordChangeDTO data);
    UserDTO getUser(long id) throws IOException;
    String deleteUser(long id) throws IOException;



}