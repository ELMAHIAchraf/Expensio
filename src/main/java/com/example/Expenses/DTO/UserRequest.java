package com.example.Expenses.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private long id;
    @NotBlank(message = "First name is required")
    private String Fname;

    @NotBlank(message = "Last name is required")
    private String Lname;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=8, message="Password should contain more than 8 characters")
    private String password;

    private MultipartFile avatar;


}
