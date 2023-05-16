package edu.ua.oop1veronicahoyek.auth.controllers;

import edu.ua.oop1veronicahoyek.auth.exceptions.UsernameAlreadyExistException;
import edu.ua.oop1veronicahoyek.auth.models.DTOs.SignInDTO;
import edu.ua.oop1veronicahoyek.auth.models.DTOs.AuthResponseDTO;
import edu.ua.oop1veronicahoyek.auth.models.DTOs.SignUpDTO;
import edu.ua.oop1veronicahoyek.auth.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "The Authentication RestAPI")
public class AuthenticationController {
    private final AuthenticationService authService;

    @ExceptionHandler({UsernameAlreadyExistException.class})
    public ResponseEntity<String> handleUsernameExistsException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @PostMapping("sign-up")
    public AuthResponseDTO register(@RequestBody SignUpDTO request) {
        return authService.register(request);
    }

    @PostMapping("sign-in")
    public AuthResponseDTO authenticate(@RequestBody SignInDTO request) {
        return authService.authenticate(request);
    }
}
