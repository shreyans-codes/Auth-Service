package com.sheru.Auth.Service.Service;
import com.sheru.Auth.Service.Model.*;
import com.sheru.Auth.Service.Respository.RoleRepository;
import com.sheru.Auth.Service.Respository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MFAAuthenticationService mfaAuthenticationService;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, MFAAuthenticationService mfaAuthenticationService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.mfaAuthenticationService = mfaAuthenticationService;
        this.tokenService = tokenService;
    }

    public RegisterResponseDTO registerUser(RegisterUserRequest user) {
        User newUser = new User();
        RegisterResponseDTO responseDTO = new RegisterResponseDTO();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);
        newUser.setPassword(encodedPassword);
        newUser.setAuthorities(roleSet);
        newUser.setEmail(user.getEmail());
        newUser.setMfaEnabled(user.isMfaEnabled());
        if (user.isMfaEnabled()) {
            newUser.setSecret(mfaAuthenticationService.generateNewSecret());
            responseDTO.setSecretImageUri(mfaAuthenticationService.generateQrCodeImageUri(newUser.getSecret()));
        }

        userRepository.save(newUser);
        responseDTO.setUser(newUser);
        return responseDTO;
    }

    public LoginResponseDTO loginUser(LoginRequestDTO user) {
        try {

            User recievedUser = userRepository.findByUsername(user.getEmail()).orElseThrow(() -> new EntityNotFoundException(
                    String.format("No user found with %S", user.getEmail())));
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            String token = tokenService.generateJwt(authentication);
            // TODO: Handle empty JWT in front end to redirect to MFA Screen
            if (recievedUser.isMfaEnabled()) {
                return new LoginResponseDTO(recievedUser, "");
            }

            return new LoginResponseDTO(recievedUser, token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "NA");
        }
    }

    public LoginResponseDTO verifyCode(VerificationRequest verificationRequest) {
        System.out.println(verificationRequest);
        try {
            User user = userRepository
                    .findByUsername(verificationRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException(
                            String.format("No user found with %S", verificationRequest.getEmail())));
            System.out.println(user);
            // Idhar error aa rha hai
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(verificationRequest.getEmail(), verificationRequest.getPassword())
            );
            if (mfaAuthenticationService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {
                throw new BadCredentialsException("Code is not correct");
            }
            String token = tokenService.generateJwt(authentication);
            return new LoginResponseDTO(user, token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "NA");
        }
    }
}
