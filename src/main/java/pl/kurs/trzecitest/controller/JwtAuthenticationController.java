package pl.kurs.trzecitest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kurs.trzecitest.config.jwt.JwtRequest;
import pl.kurs.trzecitest.config.jwt.JwtTokenUtil;
import pl.kurs.trzecitest.exception.UserNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws UserNotFoundException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtTokenUtil.generateToken(authenticate);
        } else {
            throw new UserNotFoundException("Invalid user request!");
        }
    }

}
