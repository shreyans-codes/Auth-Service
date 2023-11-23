package com.sheru.Auth.Service.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Role role;
    private boolean mfaEnabled;
}
