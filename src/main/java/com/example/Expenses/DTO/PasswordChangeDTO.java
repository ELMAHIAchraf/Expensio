package com.example.Expenses.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordChangeDTO {

    private String oldPwd;
    private String newPwd;
    private String confirmedPwd;
}
