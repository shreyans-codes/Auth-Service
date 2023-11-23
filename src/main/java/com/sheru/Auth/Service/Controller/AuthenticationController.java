package com.sheru.Auth.Service.Controller;
import com.sheru.Auth.Service.Model.*;
import com.sheru.Auth.Service.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest user) {
        var response = authenticationService.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO user) {
        System.out.println("Here and " + user);
        return authenticationService.loginUser(user);}

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }
}
