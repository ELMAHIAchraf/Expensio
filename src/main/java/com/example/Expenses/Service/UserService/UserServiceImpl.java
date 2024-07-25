package com.example.Expenses.Service.UserService;

import com.example.Expenses.DTO.PasswordChangeDTO;
import com.example.Expenses.DTO.UserRequest;
import com.example.Expenses.DTO.UserDTO;
import com.example.Expenses.Entity.Expense;
import com.example.Expenses.Entity.User;
import com.example.Expenses.Exception.*;
import com.example.Expenses.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    @Value("${image.upload.path}")
    private List<String> imagePath;
    @Override
    public UserDTO createUser(UserRequest requestUser) throws IOException {

        if(requestUser.getAvatar()==null)
            throw new NoImageUploadedException("Avatar should be uploaded");

        Optional<User> existingUser = userRepository.findByEmail(requestUser.getEmail());
        if(existingUser.isPresent())
            throw new EmailAlreadyExistsException("Email already exists");

        User createdUser = userRepository.save(modelMapper.map(requestUser, User.class));
        byte[] bytes = requestUser.getAvatar().getBytes();
        Path path = Paths.get(imagePath.get(0)+System.getProperty("file.separator")+createdUser.getId()+".jpg");
        Files.write(path, bytes);
        return modelMapper.map(createdUser,UserDTO.class);
    }
    @Override
    public UserDTO updateUser(long id, UserDTO user){
        Optional<User> wantedUser = userRepository.findById(id);
        User updatedUser = wantedUser.orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
        updatedUser.setFname(user.getFname());
        updatedUser.setLname(user.getLname());
        updatedUser.setEmail(user.getEmail());
        return modelMapper.map(userRepository.save(updatedUser),UserDTO.class);
    }

    @Override
    public String changePassword(long id, PasswordChangeDTO data) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", id)
        );
        if(user.getPassword().equals(data.getOldPwd())){
            if (data.getNewPwd().equals(data.getConfirmedPwd())){
                user.setPassword(data.getNewPwd());
                userRepository.save(user);
            }else {
                throw new PasswordsNotMatchException("New password and confirmation password do not match");
            }
        }else{
            throw new InvalidOldPasswordException("The old password provided is incorrect");
        }
        return "Password has been updated";
    }

    @Override
    public UserDTO getUser(long id) throws IOException {
        User fetchedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(fetchedUser, UserDTO.class);
    }
    @Override
    public String deleteUser(long id) throws IOException {
        User fetchedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(fetchedUser);
        return "Your account has been deleted";

    }
}
