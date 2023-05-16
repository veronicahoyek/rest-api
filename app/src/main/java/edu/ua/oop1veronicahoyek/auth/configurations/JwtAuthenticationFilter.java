package edu.ua.oop1veronicahoyek.auth.configurations;

import edu.ua.oop1veronicahoyek.auth.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Every request in the api first has to be filtered to check for needed validation
// Extends OncePerRequestFilter because after every request is filtered, everything the filter gets cleared
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // FilterChain to call the next filter to be executed in the chain of filters
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check if jwt token is in header, has to start with "Bearer ", else call the next filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt token starts at position number 7 in the auth header string (the first 6 are "Bearer "), remove the Bearer part using substring starting from index 7
        jwt = authHeader.substring(7);
        // we are extracting the user email (since we are considering the email to be the username) after decoding the token
        userEmail = jwtService.extractUsername(jwt);
        // checking the email in the token is not null, and that the user is not already authenticated, SecurityContextHolder would get filled when the user is authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch the user from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            // Check if the token is valid, e.g. not expired
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // this object is needed in order to update our security context (used 3 lines above this comment)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Execute the next filter in the chain of filters
        filterChain.doFilter(request, response);
    }
}
