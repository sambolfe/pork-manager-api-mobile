package br.csi.porkManagerApi.security;


import br.csi.porkManagerApi.models.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/**").hasAuthority(Usuario.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/usuario/**").hasAuthority(Usuario.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAuthority(Usuario.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/raca/saveRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/raca/updateRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/raca/deleteRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/raca/getRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/raca/getAllRacas").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.POST, "/alojamento/saveAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/alojamento/updateAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/alojamento/deleteAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/alojamento/getAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/alojamento/getAllAlojamentos").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.POST, "/bovino/saveSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/bovino/updateSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/bovino/deleteSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/bovino/getSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/bovino/getAllSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.POST, "/saude/saveSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/saude/updateSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/saude/deleteSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/saude/getAllSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/saude/getSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
