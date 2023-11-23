package com.sheru.Auth.Service.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationRequest {
    private String username;
    private String password;
    private String code;
}
