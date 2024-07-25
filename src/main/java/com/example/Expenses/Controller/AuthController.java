package com.example.Expenses.Controller;

import com.example.Expenses.Config.JwtHelper;
import com.example.Expenses.Config.JwtRequest;
import com.example.Expenses.Config.JwtResponse;
import com.example.Expenses.Config.MyUser;
import com.example.Expenses.DTO.UserDTO;
import com.example.Expenses.DTO.UserRequest;
import com.example.Expenses.Entity.User;
import com.example.Expenses.Exception.EmailAlreadyExistsException;
import com.example.Expenses.Exception.NoImageUploadedException;
import com.example.Expenses.Repository.UserRepository;
import com.example.Expenses.Service.CacheService.TokenCacheService;
import com.example.Expenses.Service.UserService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private TokenCacheService tokenCacheService;


    @Value("${image.upload.path}")
    private List<String> imagePath;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @ModelAttribute UserRequest user) throws IOException {
        if(user.getAvatar()==null)
            throw new NoImageUploadedException("Avatar should be uploaded");

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent())
            throw new EmailAlreadyExistsException("Email already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(modelMapper.map(user, User.class));
        byte[] bytes = user.getAvatar().getBytes();
        Path path = Paths.get(imagePath.get(0)+System.getProperty("file.separator")+createdUser.getId()+".jpg");
        Files.write(path, bytes);

        UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .email(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .email(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email or Password is incorrect");
        }
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            tokenCacheService.cacheInvalidToken(token, Instant.now().getEpochSecond());
            //ADDED
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>("User logged out", HttpStatus.OK);
        }
        return new ResponseEntity<>("Token not found in the request header", HttpStatus.BAD_REQUEST);
    }
}