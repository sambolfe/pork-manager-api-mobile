package br.csi.porkManagerApi.security;


import br.csi.porkManagerApi.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthFilter(
            JwtService jwtService,
            AuthService authService
    ) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain fc
    ) throws ServletException, IOException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader != null) {
            String jwt = authHeader.replace("Bearer", "").trim();
            if(jwt != null) {
                String subject = jwtService.getSubject(jwt);
                UserDetails userDetails = authService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken authenticationJwt = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),null ,userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationJwt);
            }
        }
        fc.doFilter(req, res);
    }
}
