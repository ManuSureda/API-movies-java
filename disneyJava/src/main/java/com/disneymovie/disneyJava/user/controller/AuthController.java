package com.disneymovie.disneyJava.user.controller;

import com.disneymovie.disneyJava.user.config.ApiResponse;
import com.disneymovie.disneyJava.user.config.JwtResponse;
import com.disneymovie.disneyJava.user.config.JwtTokenUtil;
import com.disneymovie.disneyJava.user.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid username or password"));
        }
    }


//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody final RegisterRequestDto registerRequestDto) throws JpaSystemException, DataValidationException, URISyntaxException, UserAllReadyExistException {
//
//        if (registerRequestDto.isValid()) {
//            try {
//                Integer newID = userService.register(registerRequestDto);
//                User response = new User();
//                response.setIdUser(newID);
//                if (registerRequestDto.getUserRoleId() == 1) {
//                    response.setUserRole(UserRole.admin);
//                } else {
//                    response.setUserRole(UserRole.client);
//                }
//                response.setEmail(registerRequestDto.getEmail());
//                response.setUserPassword(registerRequestDto.getPassword());
//                return ResponseEntity.created(new URI("http://localhost:8080/auth/"+newID)).body(response);
//            } catch (JpaSystemException e) {
//                throw new UserAllReadyExistException("This email " + registerRequestDto.getEmail() +" is already registered");
//            }
//        } else {
//            throw new DataValidationException("Wrong registration parameters");
//        }
//    }
}

