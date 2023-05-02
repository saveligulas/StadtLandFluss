package gulas.saveli.StadtLandFluss.security.auth;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.security.jwt.JwtService;
import gulas.saveli.StadtLandFluss.user.Role;
import gulas.saveli.StadtLandFluss.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public String register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isEmpty()) {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            return "User registered successfully";
        } else {
            throw new ApiRequestException("User with email already exists");
        }
    }

    public void registerAdmin(RegisterRequest user) {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            var admin = User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        } else {
            throw new ApiRequestException("User with email already exists");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiRequestException("user with email " + request.getEmail() + " does not exist"));
        var jwtToken = jwtService.generateToken(user);
        System.out.println("Jwt token from service: " + jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
