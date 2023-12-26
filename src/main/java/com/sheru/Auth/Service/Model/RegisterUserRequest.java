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
    private String email;
    private String password;
    private Role role;
    private boolean mfaEnabled;
}
