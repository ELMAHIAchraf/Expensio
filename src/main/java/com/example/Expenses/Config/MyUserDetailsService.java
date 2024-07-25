package com.example.Expenses.Config;

import com.example.Expenses.Entity.User;
import com.example.Expenses.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        User userObject = user.get();
        UserDetails userDetails = new MyUser(
                userObject.getId(),
                userObject.getFname(),
                userObject.getLname(),
                userObject.getCurrency(),
                userObject.getEmail(),
                userObject.getPassword());
        System.out.println(userDetails);
        return userDetails;
    }
}
