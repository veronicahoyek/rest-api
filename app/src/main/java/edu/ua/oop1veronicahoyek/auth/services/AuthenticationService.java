package edu.ua.oop1veronicahoyek.auth.services;

import edu.ua.oop1veronicahoyek.auth.exceptions.UsernameAlreadyExistException;
import edu.ua.oop1veronicahoyek.auth.models.DTOs.SignInDTO;
import edu.ua.oop1veronicahoyek.auth.models.DTOs.AuthResponseDTO;
import edu.ua.oop1veronicahoyek.auth.models.DTOs.SignUpDTO;
import edu.ua.oop1veronicahoyek.auth.models.entities.User;
import edu.ua.oop1veronicahoyek.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));
    }

    public AuthResponseDTO register(SignUpDTO request) {
        // we need to encode our password before saving to db
        // in the ApplicationConfig Configuration class we created our PasswordEncoder

        User user = User.builder()
                .username(request.getUsername())
                .password(passEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        try {
            findByUsername(request.getUsername());
            throw new UsernameAlreadyExistException(request.getUsername());
        } catch (UsernameNotFoundException e) {
            User userSaved = userRepo.save(user);
        }

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken, user.getUsername(), user.getRole());
    }

    public AuthResponseDTO authenticate(SignInDTO request) {
        // We are using the AuthenticationManager bean created in the ApplicationConfig class

        // This line will make sure that the username and password are correct
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = findByUsername(request.getUsername());

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken, user.getUsername(), user.getRole());
    }
}
