package gulas.saveli.StadtLandFluss.security.config;

import gulas.saveli.StadtLandFluss.security.jwt.JwtAuthenticationFilter;
import gulas.saveli.StadtLandFluss.user.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/home/host")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.GET, "/home/list")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.GET, "/lobby/{gameId}/info")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.POST, "/lobby/{gameId}/connect")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.POST, "/lobby/{gameId}/host/**")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.GET, "/lobby/{gameId}/host/**")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.POST, "/auth/post/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/validator/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/game/lobby/auth/**")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers(HttpMethod.POST, "/game/lobby/disconnect")
                .hasAuthority(Authority.USER_AUTHORITIES.name())
                .requestMatchers("/")
                .permitAll()
                .requestMatchers("/home")
                .permitAll()
                .requestMatchers("/lobby/{gameId}")
                .permitAll()
                .requestMatchers("/auth/**")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://192.168.1.27:8081/**"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "application/json"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Host", "Username", "HostUsername"));
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
